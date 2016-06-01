package com.ee.enigma.dao;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ee.enigma.model.UserActivity;

@Repository(value="userActivityDao")
@Transactional
public class UserActivityDaoImpl implements UserActivityDao{

	private SessionFactory sessionFactory;
	
	@Autowired
	@Qualifier(value="sessionFactory")
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	private Logger logger = Logger.getLogger(UserActivityDaoImpl.class);
	
	public void createNewActivity(UserActivity userActivity) {
		logger.info(userActivity);
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(userActivity);		
	}

	public void updateUserActivity(UserActivity userActivity) {
		// TODO Auto-generated method stub
		
	}

}
