package mumsched.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DefaultController {
    @RequestMapping("/")
    public String index(Map<String, Object> model) {
        model.put("message", "Hello World");
        return "index";
    }
}