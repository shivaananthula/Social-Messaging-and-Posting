package com.me.socialnetwork.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.query.Query;

import com.me.socialnetwork.exception.SocialNetworkException;
import com.me.socialnetwork.pojo.*;

public class PostDAO extends DAO {
	
	public PostDAO() {
		
	}
	
	public List<Post> getAllPosts() throws SocialNetworkException{
		try {
            begin();
            Query q = getSession().createQuery("from Post");
            List<Post> posts = (List<Post>) q.getResultList();
            commit();
            return posts;
        } catch (HibernateException e) {
            rollback();
            throw new SocialNetworkException("Could not get Posts ", e);
        }
	}
	
	public List<Post> getAllPostsByUser(long id) throws SocialNetworkException{
		try {
            begin();
            Query q = getSession().createQuery("from Post where user= :id");
            q.setLong("id", id);
            List<Post> posts = (List<Post>) q.getResultList();
            commit();
            return posts;
        } catch (HibernateException e) {
            rollback();
            throw new SocialNetworkException("Could not get user " + id, e);
        }
	}
	
	public Post getPostById(long id) throws SocialNetworkException {
		try {
			begin();
            Query q = getSession().createQuery("from Post where id= :id");
            q.setLong("id", id);
            Post post = (Post) q.uniqueResult();
            commit();
            return post;
		} catch (HibernateException e) {
            rollback();
            throw new SocialNetworkException("Could not get user " + id, e);
        }
	}
	
	public Post create(Post post) throws SocialNetworkException {
		try {
            begin();
            getSession().save(post);
            commit();
            return post;
        } catch (HibernateException e) {
            rollback();
            throw new SocialNetworkException("Exception while creating post: " + e.getMessage());
        }
	}

}
