package com.devdaniel.monitor.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @GetMapping({"/admin", "/dashboard", "/administrar"})
    public String admin() {
        return "admin";
    }

}