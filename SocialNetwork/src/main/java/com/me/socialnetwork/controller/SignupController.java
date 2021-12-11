package com.me.socialnetwork.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.me.socialnetwork.dao.UserDAO;
import com.me.socialnetwork.exception.SocialNetworkException;
import com.me.socialnetwork.pojo.User;

@Controller
public class SignupController {
	
	public SignupController() {
		
	}
	
	@GetMapping("/signup")
    public String signUpForm(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }
	
	@PostMapping("/signup")
    public String signUpAction(@ModelAttribute @Valid User user, BindingResult result, @RequestParam String confirm, Model model, UserDAO userDao)
	throws SocialNetworkException{
        if(!confirm.equals(user.getPassword())){
            model.addAttribute("passwordsDontMatch", "Passwords don't match!");
        }
        if(!result.hasErrors() && confirm.equals(user.getPassword())) {
            user.hashPassword(user.getPassword());
            
            userDao.create(user);            
            return "redirect:/";
        }else {
            return "signup";
        }
    }
}
