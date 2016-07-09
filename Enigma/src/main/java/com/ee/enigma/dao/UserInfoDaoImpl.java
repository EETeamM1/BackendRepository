package com.ee.enigma.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.StringType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ee.enigma.model.UserInfo;

@Repository(value = "userInfoDao")
@Transactional
public class UserInfoDaoImpl implements UserInfoDao {
	private Logger logger = Logger.getLogger(UserInfoDaoImpl.class);
	private SessionFactory sessionFactory;
	@Autowired
	@Qualifier(value = "sessionFactory")
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
  @Override
  public void createUserInfo(UserInfo userInfo)
  {
    logger.info(userInfo);
    Session session = this.sessionFactory.getCurrentSession();
    session.persist(userInfo);
  }

  @Override
  public void updateUserInfo(UserInfo userInfo)
  {
    logger.info(userInfo);
    Session session = this.sessionFactory.getCurrentSession();
    session.update(userInfo);
  }

  @Override
  public int deleteUserInfo(UserInfo userInfo)
  {
    int result = 0;
    try
    {
      String hql = "delete UserInfo where userId= :userId";
      Session session = this.sessionFactory.getCurrentSession();
      Query query = session.createQuery(hql);
      query.setParameter("userId", userInfo.getUserId());
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
	public UserInfo getUserInfo(String userId, String password) {
		try {
			String hql = "from UserInfo where lower(userId)= :userId AND password= :password";
			Session session = this.sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(UserInfo.class);
			criteria.add(Restrictions.sqlRestriction("userId = ? collate Latin1_General_CS", userId.toLowerCase(), new StringType()));
			criteria.add(Restrictions.sqlRestriction("password = ? collate Latin1_General_CS", password, new StringType()));
			UserInfo userInfo = (UserInfo) criteria.uniqueResult();			
			return userInfo;
		} catch (HibernateException e) {
			logger.error(e);
			return null;
		}
	}
	
	@Override
  public UserInfo getUserInfo(String userId) {
    try {
      String hql = "from UserInfo where userId= :userId ";
      Session session = this.sessionFactory.getCurrentSession();
      Query query = session.createQuery(hql);
      query.setParameter("userId", userId);
     
      List<UserInfo> userInfoList = (List<UserInfo>) query.list();
      if(null == userInfoList || userInfoList.size() == 0 ){
        return null;
      }
      UserInfo userInfo = userInfoList.get(0);
      logger.info(userInfo.toString());
      return userInfo;
    } catch (HibernateException e) {
      logger.error(e);
      return null;
    }
  }

	@Override
	public List<UserInfo> getAllUserInfo() {
		String hql = "from UserInfo";
		Session session = this.sessionFactory.getCurrentSession();
	    Query query = session.createQuery(hql);
	    List<UserInfo> userInfoList = (List<UserInfo>) query.list();
	    if(null == userInfoList || userInfoList.size() == 0 ){
	      return null;
	    }
	    return userInfoList;
	}

}
