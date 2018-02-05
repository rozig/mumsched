package mumsched.controller;

import mumsched.entity.Role;
import mumsched.entity.User;
import mumsched.repository.RoleRepository;
import mumsched.repository.UserRepository;
import mumsched.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.UUID;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleRepository roleRepo;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private JavaMailSender mailSender;

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
    public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult, WebRequest request) {
        ModelAndView modelAndView = new ModelAndView();

        User userExists = userService.findByEmail(user.getEmail());
        if(userExists != null) {
            bindingResult.rejectValue("email", "error.user", "There is already a user registered with the email provided");
        }
        if(bindingResult.hasErrors()) {
            System.out.println(bindingResult.getAllErrors().toString());
            modelAndView.setViewName("register");
        } else {
            String password = bCryptPasswordEncoder.encode(user.getPassword());
            user.setActivationToken(UUID.randomUUID().toString());
            user.setPassword(password);
            Role userRole = roleRepo.findByRole("STUDENT");
            user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
            userService.save(user);
            modelAndView.addObject("message", "Student has been registered successfully");
            modelAndView.setViewName("login");

            // send confirmation email to user
            String token = user.getActivationToken();

            String recipientAddress = user.getEmail();
            String subject = "Registration Confirmation";
            String confirmationUrl
            = request.getContextPath() + "/registerConfirm/" + token;
            String message = "To activate your registration, please follow the confirmation link.";

            SimpleMailMessage email = new SimpleMailMessage();
            email.setTo(recipientAddress);
            email.setSubject(subject);
            email.setText(message + " \r\n" + "http://localhost:8080" + confirmationUrl);
            mailSender.send(email);
        }
        return modelAndView;
    }

    @RequestMapping(value = "/registerConfirm/{token}", method = RequestMethod.GET)
    public String registrationConfirm(@PathVariable(value = "token") String token) {

        System.out.println("Token: " +token);
        if (token != null && token.length() > 0) {
            User user = userService.findByActivationToken(token);
            if (user != null) {
                user.setActive(true);
                user.setActivationToken("");
                userService.save(user);
                System.out.println("User updated");
            } else {
                System.out.println("User not found");
            }
        }

        return "user/registrationConfirm";
    }

    @RequestMapping(value="/forgot", method=RequestMethod.GET)
    public String forgot() {
        return "forgot";
    }

    @RequestMapping(value="/forgot", method=RequestMethod.POST)
    public ModelAndView forgotAction(@RequestParam("email") String email, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();

        User userExists = userService.findByEmail(email);
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