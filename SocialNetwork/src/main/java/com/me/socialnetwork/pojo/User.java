package com.me.socialnetwork.pojo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;

import org.mindrot.jbcrypt.BCrypt;


@Entity
@Table(name = "user")
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Enter username!")
    @NotNull(message = "Username can't be empty!")
    @Column(nullable = false, unique = true)
    private String username;

    @NotBlank(message = "Enter password!")
    @NotNull(message = "Password can't be empty!")
    @Column(nullable = false)
    private String password;

    @NotBlank(message = "Enter email!")
    @NotNull(message = "Email can't be empty!")
    @Email(message = "Enter valid email!")
    @Column(nullable = false, unique = true)
    private String email;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@Override
    public String toString() {
        return "User [id=" + id + ", username=" + username + ", password=" + password + ", email=" + email + "]";
    }
	
	public void hashPassword(String password) {
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
    }
    
    
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user", cascade = CascadeType.ALL)
    private List<Post> posts = new ArrayList<Post>();

    @OneToMany(mappedBy = "user")
    private List<Comment> comments = new ArrayList<Comment>();

    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL)
    private List<Message> sentMessages = new ArrayList<Message>();

    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL)
    private List<Message> receivedMessages = new ArrayList<Message>();

	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public List<Message> getSentMessages() {
		return sentMessages;
	}

	public void setSentMessages(List<Message> sentMessages) {
		this.sentMessages = sentMessages;
	}

	public List<Message> getReceivedMessages() {
		return receivedMessages;
	}

	public void setReceivedMessages(List<Message> receivedMessages) {
		this.receivedMessages = receivedMessages;
	}
}
