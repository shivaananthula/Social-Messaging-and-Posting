package com.me.socialnetwork.dao;
import com.me.socialnetwork.exception.SocialNetworkException;
import com.me.socialnetwork.pojo.*;
import java.util.*;

import org.hibernate.HibernateException;
import org.hibernate.query.Query;

public class MessageDAO extends DAO {
	
	public MessageDAO() {
		
	}
	
	public List<Message> getAllMessagesofSender(long id) throws SocialNetworkException{
		try {
			begin();
            Query q = getSession().createQuery("from Message where sender= :id");
            q.setLong("id", id);
            List<Message> messages = (List<Message>) q.getResultList();
            commit();
            return messages;
		} catch (HibernateException e) {
            rollback();
            throw new SocialNetworkException("Could not get sender messages of " + id, e);
        }
	}
	
	public List<Message> getAllMessagesofReceiver(long id) throws SocialNetworkException{
		try {
			begin();
            Query q = getSession().createQuery("from Message where receiver= :id");
            q.setLong("id", id);
            List<Message> messages = (List<Message>) q.getResultList();
            commit();
            return messages;
		} catch (HibernateException e) {
            rollback();
            throw new SocialNetworkException("Could not get messages of " + id, e);
        }
	}
	
	public Message getMessageById(long id) throws SocialNetworkException {
		try {
			begin();
            Query q = getSession().createQuery("from Message where id= :id");
            q.setLong("id", id);
            Message message = (Message) q.uniqueResult();
            commit();
            return message;
		} catch (HibernateException e) {
            rollback();
            throw new SocialNetworkException("Could not get messages of " + id, e);
        }
	}
	
	public Message create(Message message) throws SocialNetworkException {
		try {
			begin();
            getSession().save(message);
            commit();
            return message;
		} catch (HibernateException e) {
            rollback();
            throw new SocialNetworkException("Could not save message ", e);
        }
	}
}
