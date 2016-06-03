package com.ee.enigma.dao;

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
	public UserInfo getUserInfo(String userId, String password) {
		try {
			String hql = "from UserInfo where userId= :userId AND password= :password";
			Session session = this.sessionFactory.getCurrentSession();
			Query query = session.createQuery(hql);
			query.setParameter("userId", userId);
			query.setParameter("password", password);
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

}
