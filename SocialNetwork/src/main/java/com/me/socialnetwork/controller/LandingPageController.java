package com.me.socialnetwork.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.me.socialnetwork.dao.PostDAO;
import com.me.socialnetwork.exception.SocialNetworkException;
import com.me.socialnetwork.pojo.Post;
import com.me.socialnetwork.pojo.User;

@Controller
public class LandingPageController {
	
	public LandingPageController() {
		
	}
	
	@GetMapping("/home")
    public String home(@SessionAttribute("loggedInUser") User loggedInUser, Model model, PostDAO postDao) throws SocialNetworkException {
        if(loggedInUser.getUsername()==null) {
            return "redirect:/";
        }
        model.addAttribute("loggedInUser", loggedInUser);
        //List<Post> allPosts = new ArrayList<Post>();
        List<Post> allPosts = postDao.getAllPosts();
        model.addAttribute("allPosts", allPosts);
        return "home";
    }
}
