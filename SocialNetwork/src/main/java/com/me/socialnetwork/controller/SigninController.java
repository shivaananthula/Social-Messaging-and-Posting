package com.me.socialnetwork.controller;

import javax.validation.Valid;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.me.socialnetwork.dao.UserDAO;
import com.me.socialnetwork.exception.SocialNetworkException;
import com.me.socialnetwork.pojo.User;

@Controller
@SessionAttributes("loggedInUser")
public class SigninController {
	public SigninController() {
		
	}
	
	@GetMapping("/signin")
    public String signIn(Model model) {
        model.addAttribute("user", new User());
        return "signin";
    }

    @PostMapping("/signin")
    public String signInAction(@ModelAttribute @Valid User user, BindingResult result, Model model, UserDAO userDao)throws SocialNetworkException {
        if(!result.hasErrors()) {
        	User loggedInUser = userDao.get(user.getUsername());
            if(loggedInUser==null){
                model.addAttribute("usernameError", "This username does not exist!");
                return "signin";
            }
            if(!BCrypt.checkpw(user.getPassword(), loggedInUser.getPassword())) {
                model.addAttribute("passwordError", "Incorrect password!");
                return "signin";
            }
            model.addAttribute("loggedInUser", loggedInUser);
            return "redirect:/home";
        }else {
            return "signin";
        }
    }

    @GetMapping("/signout")
    public String signOut(Model model) {
        model.addAttribute("loggedInUser", new User());
        return "redirect:/";
    }
}
