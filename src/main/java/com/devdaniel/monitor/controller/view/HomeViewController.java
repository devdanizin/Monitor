package com.devdaniel.monitor.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeViewController {

    @GetMapping({"/", "/monitoramentos", "/urls", "/config"})
    public String home() {
        return "index";
    }

}
