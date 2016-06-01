package com.ee.enigma.dao;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ee.enigma.model.Master;

@Repository(value="masterDao")
@Transactional
public class MasterDaoImpl implements MasterDao{

	private Logger logger = Logger.getLogger(MasterDaoImpl.class);
	private SessionFactory sessionFactory;	
	@Autowired
	@Qualifier(value="sessionFactory")
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	public Master getMasterInfo() {
		Session session = this.sessionFactory.getCurrentSession();
		Master master = (Master) session.createQuery("from Master");
		return master;
	}
	
}
