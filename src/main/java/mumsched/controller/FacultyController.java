package mumsched.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import mumsched.AjaxResponse;
import mumsched.entity.Block;
import mumsched.entity.Course;
import mumsched.entity.Faculty;
import mumsched.entity.Role;
import mumsched.entity.Section;
import mumsched.entity.Student;
import mumsched.entity.User;
import mumsched.entity.UserRoles;
import mumsched.repository.RoleRepository;
import mumsched.service.BlockService;
import mumsched.service.CourseService;
import mumsched.service.FacultyService;
import mumsched.service.SectionService;
import mumsched.service.UserService;

@Controller
@RequestMapping(path="/faculty")
public class FacultyController {
    @Autowired
    private FacultyService facultyService;
    @Autowired
    private SectionService sectionService;
    @Autowired
    private BlockService blockService;
    @Autowired
    private UserService userService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private RoleRepository roleRepo;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private JavaMailSender mailSender;

    @RequestMapping(value="/", method=RequestMethod.GET)
    public ModelAndView index() {
        List<Faculty> faculties = facultyService.findAll();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("faculties", faculties);
        modelAndView.setViewName("faculty/index");
        return modelAndView;
    }
    
    @RequestMapping(value="/view", method=RequestMethod.GET)
    public String view(Model model) {
    	Faculty faculty;
    	String email = ((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userService.findByEmail(email);

        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();

        if (authorities.contains(new SimpleGrantedAuthority(UserRoles.FACULTY.getValue()))) {
        	faculty = facultyService.findByUser(user);
        }else {
        	return "redirect:/dashboard";
        }
    	
        model.addAttribute("sections", sectionService.findByFaculty(faculty));
        
        return "faculty/view";
    }
    
    @RequestMapping(value="/schedule", method=RequestMethod.GET)
    public String schedule(Model model) {
    	
        model.addAttribute("blocks", blockService.findAll());
        
        return "faculty/schedule";
    }

    @RequestMapping(value="/read/{id}", method=RequestMethod.GET)
    public String read(@PathVariable(value="id") Long id, Model model) {

        Faculty faculty = facultyService.findOne(id);
        if(faculty == null) {
            // not found
            return "404";
        }
        model.addAttribute("faculty", faculty);

        return "faculty/read";
    }
    
    @RequestMapping(value="/course/{id}/{block_id}", method=RequestMethod.GET)
    public String course(@PathVariable(value="id") Long id, @PathVariable(value="block_id") Long block_id, Model model) {
        Course course = courseService.findOne(id);
        Block block = blockService.findOne(block_id);
        
        if(course == null || block==null) {
            // not found
            return "404";
        }
        
        Faculty faculty;
    	String email = ((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userService.findByEmail(email);

        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();

        if (authorities.contains(new SimpleGrantedAuthority(UserRoles.FACULTY.getValue()))) {
        	faculty = facultyService.findByUser(user);
        }else {
        	return "redirect:/dashboard";
        }
        
        List<Section> list = sectionService.findByFacultyAndCourseAndBlock(faculty, course, block);
        List<Student> st = new ArrayList<Student>();
        
        for(Section s: list) {
        		st.addAll( s.getEnrolledStudents() );
        }
        
        model.addAttribute("students", st);
        model.addAttribute("course", course);

        return "faculty/students";
    }

    @RequestMapping(value="/new", method=RequestMethod.GET)
    public ModelAndView newFaculty(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("faculty", new Faculty());
        modelAndView.addObject("user", new User());
        modelAndView.addObject("courses", courseService.findAll());
        modelAndView.setViewName("faculty/create");
        return modelAndView;
    }

    @RequestMapping(value="/create", method=RequestMethod.POST)
    public String create(
        @Valid @ModelAttribute("Faculty") Faculty faculty,
        BindingResult bindingResultFaculty,
        @Valid @ModelAttribute("User") User user,
        BindingResult bindingResultUser,
        Model model, WebRequest request
    ) {
        User userExists = userService.findByEmail(user.getEmail());
        if(userExists != null) {
            bindingResultUser.rejectValue("email", "error.user", "There is already a user registered with the email provided");
        }
        if(!bindingResultUser.hasErrors()) {
            String password = bCryptPasswordEncoder.encode(user.getPassword());
            user.setPassword(password);
            user.setActive(true);
            Role userRole = roleRepo.findByRole(UserRoles.FACULTY.getValue());
            user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
            userService.save(user);
            userService.saveNewFacultyUserWithProfile(user, faculty);

            String recipientAddress = user.getEmail();
            String subject = "Welcome to the MUMSched";
            String loginUrl
            = request.getContextPath() + "/login";
            String message = "Welcome to the MUMSched. Your username is \"" + user.getEmail() + "\" and password is \"" + user.getPassword() + "\".\nLogin at: " + loginUrl;

            SimpleMailMessage email = new SimpleMailMessage();
            email.setTo(recipientAddress);
            email.setSubject(subject);
            email.setText(message + " \r\n" + "http://localhost:8080" + loginUrl);
            mailSender.send(email);

            // redirect
            model.addAttribute("message", "Faculty has been registered successfully");
            return "redirect:/faculty/";
        }
        model.addAttribute("user", user);
        model.addAttribute("faculty", faculty);
        model.addAttribute("courses", courseService.findAll());
        return "faculty/create";
    }

    @RequestMapping(value="/edit/{id}", method=RequestMethod.GET)
    public String edit(@PathVariable(value="id") Long id, Model model) {
        Faculty faculty = facultyService.findOne(id);
        if(faculty == null) {
            // not found
            return "404";
        }
        model.addAttribute("faculty", faculty);
        model.addAttribute("courses", courseService.findAll());

        return "faculty/update";
    }

    @RequestMapping(value="/update", method=RequestMethod.POST)
    public String update(
        @Valid @ModelAttribute("faculty") Faculty faculty,
        BindingResult resultFaculty,
        Model model, WebRequest request
    ) {
        if(!resultFaculty.hasErrors()) {
            User user = faculty.getUser();
            userService.save(user);
            facultyService.save(faculty);

            // redirect
            model.addAttribute("message", "Faculty info has been updated successfully");
            return "redirect:/faculty/";
        }
        model.addAttribute("faculty", faculty);
        model.addAttribute("courses", courseService.findAll());
        return "faculty/update";
    }

    @RequestMapping(value="/delete/{id}", method=RequestMethod.DELETE, produces=MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody AjaxResponse delete(@PathVariable(value="id") Long id) {
        Faculty faculty = facultyService.findOne(id);
        Long userId = faculty.getUser().getId();
        AjaxResponse response = new AjaxResponse();
        if(faculty != null) {
            try {
                facultyService.delete(id);
                userService.delete(userId);
                response.success = true;
                response.msg = "Successfully deleted.";
            } catch(DataIntegrityViolationException ignore) {
                response.success = false;
                response.msg = "Cannot remove faculty that is registered in active section.";
            }
        }
        return response;
    }
}