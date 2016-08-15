package com.ee.enigma.dao.impl;

import org.apache.log4j.Logger;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ee.enigma.dao.UserRoleDao;
import com.ee.enigma.model.UserRole;

@Repository(value = "userRoleDao")
@Transactional
public class UserRoleDaoImpl implements UserRoleDao {

    private static final Logger LOGGER = Logger.getLogger(DeviceInfoDaoImpl.class);
    private SessionFactory sessionFactory;

    @Autowired
    @Qualifier(value = "sessionFactory")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public UserRole getUserRole(String userId) {
        try {
            Session session = this.sessionFactory.getCurrentSession();
            UserRole userRole = (UserRole) session.load(UserRole.class, userId);
            LOGGER.info(userRole.toString());
            return userRole;
        } catch (ObjectNotFoundException e) {
            LOGGER.error(e);
            return null;
        }
    }

    @Override
    public void updateUserRole(UserRole userRole) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(userRole);

    }

    @Override
    public void saveUserRole(UserRole userRole) {
        try {
            LOGGER.info(userRole);
            Session session = this.sessionFactory.getCurrentSession();
            session.persist(userRole);
        } catch (Exception ex) {
            LOGGER.error(ex);
        }

    }

}
