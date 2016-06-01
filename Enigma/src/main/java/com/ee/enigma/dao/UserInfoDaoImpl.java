package com.ee.enigma.dao;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ee.enigma.model.UserInfo;

@Repository(value="userInfoDao")
@Transactional
public class UserInfoDaoImpl implements UserInfoDao{	
	private Logger logger = Logger.getLogger(UserInfoDaoImpl.class);
	private SessionFactory sessionFactory;	
	@Autowired
	@Qualifier(value="sessionFactory")
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	public UserInfo getUserInfo(String userId) {
		Session session = this.sessionFactory.getCurrentSession();
		UserInfo userInfo = (UserInfo) session.load(UserInfo.class, userId);
		logger.info(userInfo.toString());
		return userInfo;
	}

}
