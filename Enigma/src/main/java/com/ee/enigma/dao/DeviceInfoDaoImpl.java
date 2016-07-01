package com.ee.enigma.dao;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ee.enigma.model.DeviceInfo;
import com.ee.enigma.model.UserInfo;

@Repository(value = "deviceInfoDao")
@Transactional
public class DeviceInfoDaoImpl implements DeviceInfoDao {

	private Logger logger = Logger.getLogger(DeviceInfoDaoImpl.class);
	private SessionFactory sessionFactory;
	@Autowired
	@Qualifier(value = "sessionFactory")
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public DeviceInfo getDeviceInfo(String deviceId) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			DeviceInfo deviceInfo = (DeviceInfo) session.load(DeviceInfo.class, deviceId);
			logger.info(deviceInfo.toString());
			return deviceInfo;
		} catch (ObjectNotFoundException e) {
			logger.error(e);
			return null;
		}
	}

	@Override
	public void updateDeviceInfo(DeviceInfo deviceInfo) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(deviceInfo);		
	}

	@Override
  public void createDeviceInfo(DeviceInfo deviceInfo)
  {
    logger.info(deviceInfo);
    Session session = this.sessionFactory.getCurrentSession();
    session.persist(deviceInfo);
  }

  @Override
  public int deleteDeviceInfo(DeviceInfo deviceInfo)
  {
    int result = 0;
    try
    {
      String hql = "delete DeviceInfo where deviceId= :deviceId";
      Session session = this.sessionFactory.getCurrentSession();
      Query query = session.createQuery(hql);
      query.setParameter("deviceId", deviceInfo.getDeviceId());
      result = query.executeUpdate();
      return result;
    }
    catch (HibernateException e)
    {
      logger.error(e);
      return result;
    }
  }
}
