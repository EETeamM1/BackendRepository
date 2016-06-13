package com.ee.enigma.dao;

import java.util.Date;

import org.apache.log4j.Logger;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ee.enigma.common.Constants;
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
		System.out.println(" userActivity "+userActivity);
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(userActivity);		
	}

	public void updateUserActivity(UserActivity userActivity) {
		logger.info(userActivity);
		Session session = this.sessionFactory.getCurrentSession();
		session.update(userActivity);
	}

	@Override
	public UserActivity getUserActivityByActivityId(String activityId) {
		try {
		Session session = this.sessionFactory.getCurrentSession();
		UserActivity UserActivity = (UserActivity) session.load(UserActivity.class, activityId);
		logger.info(UserActivity.toString());
		return UserActivity;
		}catch(ObjectNotFoundException ex){
			logger.error(ex.getMessage(), ex);
		}
		return null;
	}

	@Override
	public String logOutActivity(String activityId) {
		UserActivity userActivity = getUserActivityByActivityId(activityId);
		if (null != userActivity) {
			if ( null == userActivity.getLogoutTime()) {
				userActivity.setLogoutTime(new Date());
				updateUserActivity(userActivity);
				return Constants.MESSAGE_SUCCESS;
			} else {
				return Constants.USER_ALREADY_LOGOUT;
			}
		}
		return Constants.USER_ACTIVITY_ID_NOT_FOUND;
	}

}
