package com.ee.enigma.dao;

import java.sql.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ee.enigma.common.CommonUtils;
import com.ee.enigma.model.DeviceIssueInfo;
import com.ee.enigma.model.UserInfo;

@Repository(value="deviceIssueInfoDao")
@Transactional
public class DeviceIssueInfoDaoImpl implements DeviceIssueInfoDao{
	
	private Logger logger = Logger.getLogger(DeviceIssueInfoDaoImpl.class);
	private SessionFactory sessionFactory;	
	@Autowired
	@Qualifier(value="sessionFactory")
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	public DeviceIssueInfo getDeviceIssueInfo(long deviceId) {
		Session session = this.sessionFactory.getCurrentSession();
		DeviceIssueInfo deviceIssueInfo = (DeviceIssueInfo) session.load(DeviceIssueInfo.class, deviceId);
		logger.info(deviceIssueInfo.toString());
		return deviceIssueInfo;
	}
	
	 @Override
	 public void createDeviceIssueInfo(DeviceIssueInfo deviceIssueInfo) {
	   logger.info(deviceIssueInfo);
	    Session session = this.sessionFactory.getCurrentSession();
	    deviceIssueInfo.setIssueId(issueIdGenerator( deviceIssueInfo.getDeviceId(),deviceIssueInfo.getUserId()));
	    session.persist(deviceIssueInfo); 
	 }
	 
	 @Override
   public void updateDeviceIssueInfo(DeviceIssueInfo deviceIssueInfo) {
     logger.info(deviceIssueInfo);
      Session session = this.sessionFactory.getCurrentSession();
      session.update(deviceIssueInfo); 
   }
	 
	 @Override
	  public List<DeviceIssueInfo> getDeviceIssueInfoList(long deviceId) {
	    try {
	      String hql = "from DeviceIssueInfo where deviceId= :deviceId ";
	      Session session = this.sessionFactory.getCurrentSession();
	      Query query = session.createQuery(hql);
	      query.setParameter("deviceId", deviceId);
	      List<DeviceIssueInfo> deviceIssueInfoList = (List<DeviceIssueInfo>) query.list();
	      if(null == deviceIssueInfoList || deviceIssueInfoList.size() == 0 ){
	        return null;
	      }
	      logger.info(deviceIssueInfoList.toString());
	      return deviceIssueInfoList;
	    } catch (HibernateException e) {
	      logger.error(e);
	      return null;
	    }
	 }
	
	 @Override
	  public List<DeviceIssueInfo> getDeviceIssueInfoList(long deviceId,String userId) {
	    try {
	      String hql = "from DeviceIssueInfo where deviceId= :deviceId and userId= :userId";
	      Session session = this.sessionFactory.getCurrentSession();
	      Query query = session.createQuery(hql);
	      query.setParameter("deviceId", deviceId);
	      query.setParameter("userId", userId);
	      List<DeviceIssueInfo> deviceIssueInfoList = (List<DeviceIssueInfo>) query.list();
	      if(null == deviceIssueInfoList || deviceIssueInfoList.size() == 0 ){
	        return null;
	      }
	      logger.info(deviceIssueInfoList.toString());
	      return deviceIssueInfoList;
	    } catch (HibernateException e) {
	      logger.error(e);
	      return null;
	    }
	 }
	
	 
	 
	@Override
  public String submitDeviceIssueInfo(long deviceId, String userId)
  {
    String issueId=null;
    DeviceIssueInfo newDeviceIssueInfo=null;
    List<DeviceIssueInfo> deviceIssueInfoList = getDeviceIssueInfoList(deviceId);
    boolean deviveInfoFound=false;
    for(DeviceIssueInfo deviceIssueInfo : deviceIssueInfoList)
    {
      if(deviceIssueInfo.getIssueId().equals(userId))
      {
        deviveInfoFound=true;
      }
    }
    if(!deviveInfoFound)
    {
      newDeviceIssueInfo=new DeviceIssueInfo();
      newDeviceIssueInfo.setDeviceId(deviceId);
      newDeviceIssueInfo.setUserId(userId);
      newDeviceIssueInfo.setIssueTime(CommonUtils.getCurrentDate());
      newDeviceIssueInfo.setIssueId(issueIdGenerator(deviceId,userId));
      createDeviceIssueInfo(newDeviceIssueInfo);
    }
    else
    {
      boolean flag=false;
      for(DeviceIssueInfo deviceIssueInfo : deviceIssueInfoList)
      {
        if(deviceIssueInfo.getUserId().equals(userId) && deviceIssueInfo.getSubmitTime()==null)
        {
          deviceIssueInfo.setSubmitTime(CommonUtils.getCurrentDate());
          updateDeviceIssueInfo(deviceIssueInfo);
          flag=true;
          break;
        }
      }
      if(!flag)
      {
        //Need to implement , where submit time is not null
      }
    }
    return issueId;
  }
	
	 private String issueIdGenerator( long deviceId,String userId) {
     return deviceId+userId+CommonUtils.getCurrentDate();
   }

	@Override
	public DeviceIssueInfo getDeviceIssueInfoByIssueID(String issueId) {
		Session session = this.sessionFactory.getCurrentSession();
		DeviceIssueInfo deviceIssueInfo = (DeviceIssueInfo) session.load(DeviceIssueInfo.class, issueId);
		logger.info(deviceIssueInfo.toString());
		return deviceIssueInfo;
	}
	
}