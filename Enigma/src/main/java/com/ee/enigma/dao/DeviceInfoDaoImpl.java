package com.ee.enigma.dao;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ee.enigma.model.DeviceInfo;

@Repository(value="deviceInfoDao")
@Transactional
public class DeviceInfoDaoImpl implements DeviceInfoDao{
	
	private Logger logger = Logger.getLogger(DeviceInfoDaoImpl.class);
	private SessionFactory sessionFactory;	
	@Autowired
	@Qualifier(value="sessionFactory")
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	public DeviceInfo getDeviceInfo(long deviceId) {
		Session session = this.sessionFactory.getCurrentSession();
		DeviceInfo deviceInfo = (DeviceInfo) session.load(DeviceInfo.class, deviceId);
		logger.info(deviceInfo.toString());
		return deviceInfo;
	}

}
