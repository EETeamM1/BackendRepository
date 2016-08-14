package com.ee.enigma.dao.impl;

import java.util.List;

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

import com.ee.enigma.dao.DeviceInfoDao;
import com.ee.enigma.model.DeviceInfo;
import com.ee.enigma.model.DeviceIssueInfo;
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
  
  
  @Override
  public List<DeviceInfo> getDevicesList() {
    try {
      String hql = "from DeviceInfo  ";
      Session session = this.sessionFactory.getCurrentSession();
      Query query = session.createQuery(hql);
      List<DeviceInfo> deviceInfo = (List<DeviceInfo>) query.list();
      if(null == deviceInfo || deviceInfo.size() == 0 ){
        return null;
      }
      logger.info(deviceInfo.toString());
      return deviceInfo;
    } catch (HibernateException e) {
      logger.error(e);
      return null;
    }
 }
  
  @Override
  public List<DeviceInfo> getDevicesListByIDAndStatus(String deviceId,String deviceAvailability) {
    try {
      String hql = "";
      Session session = this.sessionFactory.getCurrentSession();
      Query query = null;
      if(deviceId!=null && deviceAvailability!=null)
      {
        hql = "from DeviceInfo  where deviceId =:deviceId and deviceAvailability=:deviceAvailability ";
        query = session.createQuery(hql);
        query.setParameter("deviceId", deviceId);
        query.setParameter("deviceAvailability", deviceAvailability);
      }
      else if(deviceId!=null )
      {
        hql = "from DeviceInfo  where deviceId =:deviceId ";
        query = session.createQuery(hql);
        query.setParameter("deviceId", deviceId);
      }
      else if(deviceAvailability!=null )
      {
        hql = "from DeviceInfo  where deviceAvailability =:deviceAvailability ";
        query = session.createQuery(hql);
        query.setParameter("deviceAvailability", deviceAvailability);
      }
      else
      {
        hql = "from DeviceInfo  ";
        query = session.createQuery(hql);
      }
      List<DeviceInfo> deviceInfo = (List<DeviceInfo>) query.list();
      if(null == deviceInfo || deviceInfo.size() == 0 ){
        return null;
      }
      logger.info(deviceInfo.toString());
      return deviceInfo;
    } catch (HibernateException e) {
      logger.error(e);
      return null;
    }
 }
 
  @Override
	public List<DeviceInfo> getDeviceFields(String searchText) {
		logger.info(searchText);
		String hql = "select deviceName, deviceId from DeviceInfo where deviceId like :searchText or deviceName like :searchText or manufacturer like :searchText or OS like :searchText or OSVersion like :searchText";
	      Session session = this.sessionFactory.getCurrentSession();
	      Query query = session.createQuery(hql);
	      query.setString("searchText","%"+searchText+"%");
	      
	      List<DeviceInfo> deviceInfoList = (List<DeviceInfo>) query.list();
	      if(null == deviceInfoList || deviceInfoList.size() == 0 ){
	        return null;
	      }
		return deviceInfoList;
  	}
  
}
