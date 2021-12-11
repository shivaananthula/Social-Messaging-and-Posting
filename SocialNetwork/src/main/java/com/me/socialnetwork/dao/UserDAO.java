package com.me.socialnetwork.dao;

import org.hibernate.HibernateException;
import org.hibernate.query.Query;

import com.me.socialnetwork.exception.SocialNetworkException;
import com.me.socialnetwork.pojo.User;

public class UserDAO extends DAO {
	
	public UserDAO(){
		
	}
	
	public User get(String username)throws SocialNetworkException {
		try {
            begin();
            Query q = getSession().createQuery("from User where username = :username");
            q.setString("username", username);
            User user = (User) q.uniqueResult();
            commit();
            return user;
        } catch (HibernateException e) {
            rollback();
            throw new SocialNetworkException("Could not get user " + username, e);
        }
		
	}
	
	public User get(long id)throws SocialNetworkException {
		try {
            begin();
            Query q = getSession().createQuery("from User where id = :id");
            q.setLong("id", id);
            User user = (User) q.uniqueResult();
            commit();
            return user;
        } catch (HibernateException e) {
            rollback();
            throw new SocialNetworkException("Could not get user " + id, e);
        }
		
	}
	
	public User create(User user) throws SocialNetworkException {
		try {
            begin();
            getSession().save(user);
            commit();
            return user;
        } catch (HibernateException e) {
            rollback();
            //throw new AdException("Could not create user " + username, e);
            throw new SocialNetworkException("Exception while creating user: " + e.getMessage());
        }
	}
}
