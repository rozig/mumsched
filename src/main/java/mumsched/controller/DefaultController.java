package mumsched.controller;

import mumsched.entity.Role;
import mumsched.entity.User;
import mumsched.repository.RoleRepository;
import mumsched.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;

@Controller
public class DefaultController {
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private RoleRepository roleRepo;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @RequestMapping(value="/", method=RequestMethod.GET)
    public String index(Map<String, Object> model) {
        return "login";
    }

    @RequestMapping(value="/dashboard", method=RequestMethod.GET)
    public String dashboard(Map<String, Object> model) {
        return "index";
    }

    @RequestMapping(value="/login", method=RequestMethod.GET)
    public String login() {
        Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(user instanceof User) {
            return "redirect:/dashboard";
        } else {
            return "login";
        }
    }

    @RequestMapping(value="/register", method=RequestMethod.GET)
    public ModelAndView registration() {
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("register");
        return modelAndView;
    }

    @RequestMapping(value="/register", method=RequestMethod.POST)
    public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();

        User userExists = userRepo.findByEmail(user.getEmail());
        if(userExists != null) {
            bindingResult.rejectValue("email", "error.user", "There is already a user registered with the email provided");
        }
        if(bindingResult.hasErrors()) {
            modelAndView.setViewName("register");
        } else {
            String password = bCryptPasswordEncoder.encode(user.getPassword());
            user.setActive(1);
            user.setPassword(password);
            Role userRole = roleRepo.findByRole("STUDENT");
            user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
            userRepo.save(user);
            modelAndView.addObject("message", "Student has been registered successfully");
            modelAndView.setViewName("login");

        }
        return modelAndView;
    }

    @RequestMapping(value="/forgot", method=RequestMethod.GET)
    public String forgot() {
        return "forgot";
    }

    @RequestMapping(value="/forgot", method=RequestMethod.POST)
    public ModelAndView forgotAction(@RequestParam("email") String email, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();

        User userExists = userRepo.findByEmail(email);
        if(userExists == null) {
            bindingResult.rejectValue("email", "error.user", "User not found!");
        }
        if(bindingResult.hasErrors()) {
            modelAndView.setViewName("forgot");
        } else {
            // String password = bCryptPasswordEncoder.encode(user.getPassword());
            // user.setActive(1);
            // user.setPassword(password);
            // Role userRole = roleRepo.findByRole("STUDENT");
            // user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
            // userRepo.save(user);
            // modelAndView.addObject("message", "Student has been registered successfully");
            // modelAndView.setViewName("login");

        }
        return modelAndView;
    }
}