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

import com.me.socialnetwork.dao.MessageDAO;
import com.me.socialnetwork.dao.PostDAO;
import com.me.socialnetwork.dao.UserDAO;
import com.me.socialnetwork.exception.SocialNetworkException;
import com.me.socialnetwork.pojo.Message;
import com.me.socialnetwork.pojo.Post;
import com.me.socialnetwork.pojo.User;

@Controller
public class MessageController {
	
	public MessageController() {
		
	}
	
	@GetMapping("/message")
    public String viewMessage(@SessionAttribute("loggedInUser") User loggedInUser, @RequestParam long id, Model model, MessageDAO messageDao)throws SocialNetworkException {
        if(loggedInUser.getUsername()==null) {
            return "redirect:/";
        }
        Message message = messageDao.getMessageById(id);
        if(loggedInUser.getId()==message.getReceiver().getId()) {
            message.setUnread(false);
            messageDao.create(message);
        }
        model.addAttribute("message", message);
        return "messageview";
    }

    @PostMapping("/message")
    public String sendMessage(@SessionAttribute("loggedInUser") User loggedInUser, @ModelAttribute @Valid Message message, BindingResult result,
                              @RequestParam long senderId, String text, long receiverId, Model model, UserDAO userDao, MessageDAO messageDao, PostDAO postDao) throws SocialNetworkException {
        if(loggedInUser.getUsername()==null) {
            return "redirect:/";
        }
        System.out.println(message);
        if(!result.hasErrors()) {
            java.util.Date date = new java.util.Date();
            java.sql.Timestamp created = new java.sql.Timestamp(date.getTime());
            message.setCreated(created);
            message.setSender(userDao.get(senderId));
            message.setText(text);
            message.setReceiver(userDao.get(receiverId));
            messageDao.create(message);
            System.out.println("Success!");
            return "redirect:/user?id=" + receiverId;
        }else {
            List<Post> usersPosts = postDao.getAllPostsByUser(receiverId);
            model.addAttribute("usersPosts", usersPosts);
            System.out.println("Failure!");
            return "userview";
        }
    }
}
