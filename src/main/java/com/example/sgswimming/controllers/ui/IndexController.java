package com.example.sgswimming.controllers.ui;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Profile("ui")
@Controller
public class IndexController {

    @GetMapping({"", "/"})
    public String index() {
        return "redirect:/instructors/";
    }
}
