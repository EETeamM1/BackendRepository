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
	 public List<DeviceIssueInfo> getDeviceIssueInfoListByDate(String deviceId,Date beginDate,Date  endDate) {
	   try {
	     String hql=null;
	     Query query=null;
	     Session session = this.sessionFactory.getCurrentSession();
	     if(beginDate==null)
	     {  
	       hql = "from DeviceIssueInfo where deviceId= :deviceId ";
       }
	     else
	     {
	       hql = "from DeviceIssueInfo where deviceId= :deviceId and ((issueTime between :beginDate and :endDate) or (submitTime between :beginDate and :endDate)) ";
	     }
	     query = session.createQuery(hql);
       query.setParameter("deviceId", deviceId);
       if(beginDate!=null)
       {
         query.setParameter("beginDate", beginDate);
         query.setParameter("endDate", endDate);
       }
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
	public DeviceIssueInfo getDeviceIssueInfo(String deviceId) {
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
	  public List<DeviceIssueInfo> getDeviceIssueInfoList(String deviceId) {
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
	  public List<DeviceIssueInfo> getDeviceIssueInfoList(String deviceId,String userId) {
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
	
	 private String issueIdGenerator( String deviceId,String userId) {
     return deviceId+"_"+CommonUtils.getTime();
   }

	@Override
	public DeviceIssueInfo getDeviceIssueInfoByIssueID(String issueId) {
		Session session = this.sessionFactory.getCurrentSession();
		DeviceIssueInfo deviceIssueInfo = (DeviceIssueInfo) session.load(DeviceIssueInfo.class, issueId);
		logger.info(deviceIssueInfo.toString());
		return deviceIssueInfo;
	}
	
}