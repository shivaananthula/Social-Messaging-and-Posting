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
	/* When the signup page is called, adding an empty user object to model and sending back to view
	 * so that the view can add the form details and send back to the post method! */
	@GetMapping("/signup")
    public String signUpForm(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }
	
	/* When the signup form is submitted, all the form details are sent in the modelattribute and the confirm password is sent as request param.*/
	@PostMapping("/signup")
    public String signUpAction(@Valid @ModelAttribute("user") User user, BindingResult result, @RequestParam String confirm, Model model, UserDAO userDao)
	throws SocialNetworkException{
        if(!confirm.equals(user.getPassword())){
            model.addAttribute("passwordsDontMatch", "Passwords don't match!");
        }
        if(!result.hasErrors() && confirm.equals(user.getPassword())) {
            user.hashPassword(user.getPassword());
            
            User retrieveduser = userDao.get(user.getUsername());
            if(retrieveduser!= null) {
            	model.addAttribute("passwordsDontMatch", "The username already exists!");
            	return "signup";
            }
            userDao.create(user);            
            return "redirect:/";
        }else {
        
            return "signup";
        }
    }
}
