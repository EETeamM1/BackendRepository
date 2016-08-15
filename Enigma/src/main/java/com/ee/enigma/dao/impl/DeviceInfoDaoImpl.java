package com.ee.enigma.dao.impl;

import java.util.ArrayList;
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

@Repository(value = "deviceInfoDao")
@Transactional
public class DeviceInfoDaoImpl implements DeviceInfoDao {

    private static final Logger LOGGER = Logger.getLogger(DeviceInfoDaoImpl.class);
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
            LOGGER.info(deviceInfo.toString());
            return deviceInfo;
        } catch (ObjectNotFoundException e) {
            LOGGER.error(e);
            return null;
        }
    }

    @Override
    public void updateDeviceInfo(DeviceInfo deviceInfo) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(deviceInfo);
    }

    @Override
    public void createDeviceInfo(DeviceInfo deviceInfo) {
        LOGGER.info(deviceInfo);
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(deviceInfo);
    }

    @Override
    public int deleteDeviceInfo(DeviceInfo deviceInfo) {
        int result = 0;
        try {
            String hql = "delete DeviceInfo where deviceId= :deviceId";
            Session session = this.sessionFactory.getCurrentSession();
            Query query = session.createQuery(hql);
            query.setParameter("deviceId", deviceInfo.getDeviceId());
            result = query.executeUpdate();
            return result;
        } catch (HibernateException e) {
            LOGGER.error(e);
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
            return deviceInfo;
        } catch (HibernateException e) {
            LOGGER.error(e);
            return new ArrayList<>();
        }
    }

    @Override
    public List<DeviceInfo> getDevicesListByIDAndStatus(String deviceId, String deviceAvailability) {
        try {
            String hql;
            Session session = this.sessionFactory.getCurrentSession();
            Query query;
            if (deviceId != null && deviceAvailability != null) {
                hql = "from DeviceInfo  where deviceId =:deviceId and deviceAvailability=:deviceAvailability ";
                query = session.createQuery(hql);
                query.setParameter("deviceId", deviceId);
                query.setParameter("deviceAvailability", deviceAvailability);
            } else if (deviceId != null) {
                hql = "from DeviceInfo  where deviceId =:deviceId ";
                query = session.createQuery(hql);
                query.setParameter("deviceId", deviceId);
            } else if (deviceAvailability != null) {
                hql = "from DeviceInfo  where deviceAvailability =:deviceAvailability ";
                query = session.createQuery(hql);
                query.setParameter("deviceAvailability", deviceAvailability);
            } else {
                hql = "from DeviceInfo  ";
                query = session.createQuery(hql);
            }
            List<DeviceInfo> deviceInfo = (List<DeviceInfo>) query.list();
            if (null == deviceInfo || deviceInfo.isEmpty()) {
                return new ArrayList<>();
            }
            return deviceInfo;
        } catch (HibernateException e) {
            LOGGER.error(e);
            return new ArrayList<>();
        }
    }

    @Override
    public List<DeviceInfo> getDeviceFields(String searchText) {
        LOGGER.info(searchText);
        String hql = "select deviceName, deviceId from DeviceInfo where deviceId like :searchText or deviceName like :searchText or manufacturer like :searchText or OS like :searchText or OSVersion like :searchText";
        Session session = this.sessionFactory.getCurrentSession();
        Query query = session.createQuery(hql);
        query.setString("searchText", "%" + searchText + "%");

        List<DeviceInfo> deviceInfoList = (List<DeviceInfo>) query.list();
        if (null == deviceInfoList || deviceInfoList.isEmpty()) {
            return new ArrayList<>();
        }
        return deviceInfoList;
    }

}
