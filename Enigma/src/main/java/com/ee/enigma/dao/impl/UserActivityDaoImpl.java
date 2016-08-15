package com.ee.enigma.dao.impl;

import java.util.ArrayList;
import java.util.Date;
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

import com.ee.enigma.common.Constants;
import com.ee.enigma.dao.UserActivityDao;
import com.ee.enigma.model.UserActivity;

@Repository(value = "userActivityDao")
@Transactional
public class UserActivityDaoImpl implements UserActivityDao {

    private static final Logger LOGGER = Logger.getLogger(UserActivityDaoImpl.class);
    private SessionFactory sessionFactory;

    @Autowired
    @Qualifier(value = "sessionFactory")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void createNewActivity(UserActivity userActivity) {
        LOGGER.info(userActivity);
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(userActivity);
    }

    public void updateUserActivity(UserActivity userActivity) {
        LOGGER.info(userActivity);
        Session session = this.sessionFactory.getCurrentSession();
        session.update(userActivity);
    }

    @Override
    public UserActivity getUserActivityByActivityId(String activityId) {
        try {
            Session session = this.sessionFactory.getCurrentSession();
            return (UserActivity) session.load(UserActivity.class, activityId);
        } catch (ObjectNotFoundException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
        return null;
    }

    @Override
    public List<UserActivity> getUserActivityByDeviceId(String deviceId) {
        String hql = "FROM UserActivity WHERE deviceId = :deviceId AND logoutTime is NULL";

        try {
            Session session = this.sessionFactory.getCurrentSession();
            Query query = session.createQuery(hql);
            query.setParameter("deviceId", deviceId);
            return (List<UserActivity>) query.list();
        } catch (ObjectNotFoundException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
        return new ArrayList<>();
    }

    @Override
    public String logOutBYActivityId(String activityId) {
        UserActivity userActivity = getUserActivityByActivityId(activityId);
        if (null != userActivity) {
            if (null == userActivity.getLogoutTime()) {
                userActivity.setLogoutTime(new Date());
                updateUserActivity(userActivity);
                return Constants.MESSAGE_SUCCESS;
            } else {
                return Constants.USER_ALREADY_LOGOUT;
            }
        }
        return Constants.USER_ACTIVITY_ID_NOT_FOUND;
    }

    @Override
    public void logOutBYDeviceId(String deviceId) {
        List<UserActivity> userActivityList = getUserActivityByDeviceId(deviceId);
        if (!userActivityList.isEmpty()) {
            for (UserActivity userActivity : userActivityList) {
                if (null == userActivity.getLogoutTime()) {
                    userActivity.setLogoutTime(new Date());
                    updateUserActivity(userActivity);
                }
            }
        }
    }

    @Override
    public List<UserActivity> getUserActivityByDates(String deviceId, Date fromDate, Date toDate) {
        try {
            String hql ;
            Query query ;
            Session session = this.sessionFactory.getCurrentSession();
            if (fromDate == null) {
                hql = "from UserActivity where deviceId= :deviceId order by loginTime desc";
            } else {
                hql = "from UserActivity where deviceId= :deviceId and ((loginTime between :fromDate and :toDate) or (logoutTime between :fromDate and :toDate)) order by issueId ";
            }
            query = session.createQuery(hql);
            query.setParameter("deviceId", deviceId);
            if (fromDate != null) {
                query.setParameter("fromDate", fromDate);
                query.setParameter("toDate", toDate);
            }
            List<UserActivity> userActivityList = (List<UserActivity>) query.list();
            LOGGER.info(userActivityList.toString());
            return userActivityList;
        } catch (HibernateException e) {
            LOGGER.error(e);
            return new ArrayList<>();
        }
    }

}
