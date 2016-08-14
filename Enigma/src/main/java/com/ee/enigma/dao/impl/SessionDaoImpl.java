package com.ee.enigma.dao.impl;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ee.enigma.dao.SessionDao;
import com.ee.enigma.model.Session;

@Repository(value="sessionDao")
@Transactional
public class SessionDaoImpl implements SessionDao{
	private Logger logger = Logger.getLogger(UserInfoDaoImpl.class);
	private SessionFactory sessionFactory;
	@Autowired
	@Qualifier(value = "sessionFactory")
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	public void createSeesionEntry(Session session) {
		org.hibernate.Session hibernateSession = this.sessionFactory.getCurrentSession();
		hibernateSession.persist(session);		
	}

}
