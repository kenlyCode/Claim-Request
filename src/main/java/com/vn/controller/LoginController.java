package com.vn.controller;

import com.vn.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    @Autowired
    StaffService staffService;

    @GetMapping({"/login", "/"})
    public String login() {
        return "login";
    }

}
