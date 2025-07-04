package com.devdaniel.monitor.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminViewController {

    @GetMapping({"/admin", "/dashboard", "/administrar"})
    public String admin() {
        return "admin";
    }

}