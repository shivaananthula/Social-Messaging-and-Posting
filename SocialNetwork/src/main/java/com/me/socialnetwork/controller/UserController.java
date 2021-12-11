package com.me.socialnetwork.controller;

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
import java.util.List;

import javax.validation.Valid;


@Controller
public class UserController {

	public UserController() {
		
	}
	
	@GetMapping("/user")
    public String userView(@SessionAttribute ("loggedInUser") User loggedInUser, @RequestParam long id, Model model, PostDAO postDao, UserDAO userDao, MessageDAO messageDao) throws SocialNetworkException {
        if(loggedInUser.getUsername()==null) {
            return "redirect:/";
        }
        List<Post> usersPosts = postDao.getAllPostsByUser(id);
        model.addAttribute("usersPosts", usersPosts);
        if(loggedInUser.getId()==id){
            User user = userDao.get(loggedInUser.getId());
            //user.setPassword(null);
            model.addAttribute("user", user);
            List<Message> receivedMessages = messageDao.getAllMessagesofReceiver(loggedInUser.getId());
            List<Message> sentMessages = messageDao.getAllMessagesofSender(loggedInUser.getId());
            model.addAttribute("receivedMessages", receivedMessages);
            model.addAttribute("sentMessages", sentMessages);
            return "userownview";
        }else {
            model.addAttribute("message", new Message());
            return "userview";
        }
    }

    @PostMapping("/user")
    public String changeUserDetails(@SessionAttribute ("loggedInUser") User loggedInUser,
                                    @ModelAttribute @Valid User user, BindingResult result, @RequestParam String confirm, Model model, UserDAO userDao, PostDAO postDao, MessageDAO messageDao) throws SocialNetworkException{
        if(loggedInUser.getUsername()==null) {
            return "redirect:/";
        }
        if(!confirm.equals(user.getPassword())){
            model.addAttribute("outcomeMessage", "Passwords don't match!");
        }
        if(!result.hasErrors() && confirm.equals(user.getPassword())) {
            loggedInUser.setUsername(user.getUsername());
            loggedInUser.hashPassword(user.getPassword());
            loggedInUser.setEmail(user.getEmail());
            userDao.create(loggedInUser);
            model.addAttribute("passwordsDontMatch", "Your details have been changed!");
            return "redirect:/user?id=" + loggedInUser.getId();
        }else {
            List<Post> usersPosts = postDao.getAllPostsByUser(loggedInUser.getId());
            model.addAttribute("usersPosts", usersPosts);
            List<Message> receivedMessages = messageDao.getAllMessagesofReceiver(loggedInUser.getId());
            List<Message> sentMessages = messageDao.getAllMessagesofSender(loggedInUser.getId());
            model.addAttribute("receivedMessages", receivedMessages);
            model.addAttribute("sentMessages", sentMessages);
            return "userownview";
        }
    }
}
