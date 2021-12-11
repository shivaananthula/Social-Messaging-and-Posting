package com.me.socialnetwork.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
public class CommentController {
	
	public CommentController() {
		
	}
	
	@PostMapping("/comment")
    public String addComment(@SessionAttribute("loggedInUser") User loggedInUser, @ModelAttribute @Valid Comment comment, BindingResult result, @RequestParam long postId, Model model, PostDAO postDao, CommentDAO commentDao)throws SocialNetworkException {
        if(loggedInUser.getUsername()==null) {
            return "redirect:/";
        }
        
        if(!result.hasErrors() && !comment.getText().isEmpty()) {
            java.util.Date date = new java.util.Date();
            java.sql.Timestamp created = new java.sql.Timestamp(date.getTime());
            comment.setCreated(created);
            comment.setUser(loggedInUser);
            comment.setPost(postDao.getPostById(postId));
            commentDao.create(comment);
            return "redirect:/post?id=" + comment.getPost().getId();
        }else {
            Post post = postDao.getPostById(postId);
            List<Comment> allComments = commentDao.getAllCommentsByPostId(postId);
            model.addAttribute("allComments", allComments);
            model.addAttribute("post", post);
            return "postview";
        }
    }
}
