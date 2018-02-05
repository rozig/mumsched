package mumsched.controller;

import mumsched.entity.Role;
import mumsched.entity.User;
import mumsched.repository.RoleRepository;
import mumsched.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
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
public class DefaultController {

    @RequestMapping(value="/", method=RequestMethod.GET)
    public String index(Map<String, Object> model) {
        return "login";
    }

    @RequestMapping(value="/dashboard", method=RequestMethod.GET)
    public String dashboard(Map<String, Object> model) {
        return "index";
    }

}