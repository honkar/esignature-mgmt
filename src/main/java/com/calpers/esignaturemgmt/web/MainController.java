package com.calpers.esignaturemgmt.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String root() {
        return "dashboard";
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "calperslogin";
    }

    @GetMapping("/user")
    public String userIndex() {
        return "user/index";
    }
    
    @RequestMapping("/upload")
	public String UploadSignature(Model model) {
		return "signature_v2";
	}

}
