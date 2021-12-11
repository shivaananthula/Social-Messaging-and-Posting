package com.me.socialnetwork.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.query.Query;

import com.me.socialnetwork.exception.SocialNetworkException;
import com.me.socialnetwork.pojo.Comment;
import com.me.socialnetwork.pojo.Post;

public class CommentDAO extends DAO {
	
	public CommentDAO() {
		
	}
	
	public List<Comment> getAllCommentsByPostId(long id) throws SocialNetworkException{
		
		try {
			begin();
            Query q = getSession().createQuery("from Comment where post= :id");
            q.setLong("id", id);
            List<Comment> comments = (List<Comment>) q.getResultList();
            commit();
            return comments;
		} catch (HibernateException e) {
            rollback();
            throw new SocialNetworkException("Could not get comments " + id, e);
        }
	}
	
	public Comment create(Comment comment) throws SocialNetworkException {
		try {
            begin();
            getSession().save(comment);
            commit();
            return comment;
        } catch (HibernateException e) {
            rollback();
            //throw new AdException("Could not create comment " + username, e);
            throw new SocialNetworkException("Exception while creating comment: " + e.getMessage());
        }
	}
	
}
