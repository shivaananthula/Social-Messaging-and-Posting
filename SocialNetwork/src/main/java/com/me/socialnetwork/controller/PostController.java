package com.me.socialnetwork.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.me.socialnetwork.dao.CommentDAO;
import com.me.socialnetwork.dao.PostDAO;
import com.me.socialnetwork.exception.SocialNetworkException;
import com.me.socialnetwork.pojo.Comment;
import com.me.socialnetwork.pojo.Post;
import com.me.socialnetwork.pojo.User;

@Controller
public class PostController {
	
	public PostController() {
		
	}
	
	@PostMapping("/post")
    public String newPostAction(@SessionAttribute("loggedInUser") User loggedInUser, @ModelAttribute @Valid Post post, BindingResult result, PostDAO postDao) throws SocialNetworkException {
        if(loggedInUser.getUsername()==null) {
            return "redirect:/";
        }
        if(!result.hasErrors()) {
            post.setUser(loggedInUser);
            java.util.Date date = new java.util.Date();
            java.sql.Timestamp created = new java.sql.Timestamp(date.getTime());
            post.setCreated(created);
            postDao.create(post);
            return "redirect:/home";
        }else {
            return "newpost";
        }
    }
	
	@GetMapping("/post")
    public String postView(@SessionAttribute("loggedInUser") User loggedInUser,
                           @RequestParam(required = false) Long id, Model model, PostDAO postDao, CommentDAO commentDao)  throws SocialNetworkException{
        if(loggedInUser.getUsername()==null) {
            return "redirect:/";
        }
        if(id == null) {
            model.addAttribute("post", new Post());
            model.addAttribute("loggedInUser", loggedInUser);
            return "newpost";
        }
        Post post = postDao.getPostById((long)id);
        List<Comment> allComments = commentDao.getAllCommentsByPostId(id);
        model.addAttribute("allComments", allComments);
        model.addAttribute("post", post);
        model.addAttribute("comment", new Comment());
        return "postview";
    }
}
