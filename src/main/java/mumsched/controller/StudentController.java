package mumsched.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.mail.MailMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.context.request.WebRequest;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.UUID;
import mumsched.AjaxResponse;

import mumsched.service.EntryService;
import mumsched.service.StudentService;
import mumsched.service.UserService;
import mumsched.entity.Entry;
import mumsched.entity.Student;
import mumsched.entity.Role;
import mumsched.entity.User;
import mumsched.entity.UserRoles;
import mumsched.repository.RoleRepository;

@Controller
@RequestMapping(path="/student")
public class StudentController {
    @Autowired
    private StudentService studentServ;
    @Autowired
    private EntryService entryService;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleRepository roleRepo;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private JavaMailSender mailSender;

    @RequestMapping(value="/", method=RequestMethod.GET)
    public ModelAndView index() {
        List<Student> students = studentServ.findAll();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("students", students);
        modelAndView.setViewName("student/index");
        return modelAndView;
    }

    @RequestMapping(value="/read/{id}", method=RequestMethod.GET)
    public String read(@PathVariable(value="id") Long id, Model model) {

        Student student = studentServ.findOne(id);
        if(student == null) {
            // not found
            return "404";
        }
        model.addAttribute("student", student);

        return "student/read";
    }

    @RequestMapping(value="/new", method=RequestMethod.GET)
    public ModelAndView newStudent(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("student", new Student());
        modelAndView.addObject("user", new User());
        modelAndView.addObject("entries", entryService.findAll());
        modelAndView.setViewName("student/create");
        return modelAndView;
    }

    @RequestMapping(value="/create", method=RequestMethod.POST)
    public String create(
        @Valid @ModelAttribute("Student") Student student,
        BindingResult bindingResultStudent,
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
            Role userRole = roleRepo.findByRole(UserRoles.STUDENT.getValue());
            user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
            userService.save(user);
            userService.saveNewStudentUserWithProfile(user, student);

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
            model.addAttribute("message", "Student has been registered successfully");
            return "redirect:/student/";
        }
        model.addAttribute("user", user);
        model.addAttribute("student", student);
        model.addAttribute("entries", entryService.findAll());
        return "student/create";
    }

    @RequestMapping(value="/edit/{id}", method=RequestMethod.GET)
    public String edit(@PathVariable(value="id") Long id, Model model) {
        Student student = studentServ.findOne(id);
        if(student == null) {
            // not found
            return "404";
        }
        model.addAttribute("student", student);
        model.addAttribute("entries", entryService.findAll());

        return "student/update";
    }

    @RequestMapping(value="/update", method=RequestMethod.POST)
    public String update(
        @Valid @ModelAttribute("student") Student student,
        BindingResult resultStudent,
        Model model, WebRequest request
    ) {
        if(!resultStudent.hasErrors()) {
            User user = student.getUser();
            userService.save(user);
            student.setUser(user);
            studentServ.save(student);

            // redirect
            model.addAttribute("message", "Student info has been updated successfully");
            return "redirect:/student/";
        }
        model.addAttribute("student", student);
        model.addAttribute("entries", entryService.findAll());
        return "student/update";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody AjaxResponse delete(@PathVariable(value="id") Long id) {
        Student student = studentServ.findOne(id);
        Long userId = student.getUser().getId();
        AjaxResponse response = new AjaxResponse();
        if(student != null) {
            try {
                studentServ.delete(id);
                userService.delete(userId);
                response.success = true;
                response.msg = "Successfully deleted.";
            } catch (DataIntegrityViolationException ignore) {
                response.success = false;
                response.msg = "Cannot remove student that is registered in active section.";
            }
        }
        return response;
    }
}