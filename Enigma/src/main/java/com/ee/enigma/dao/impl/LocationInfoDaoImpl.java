package com.ee.enigma.dao.impl;

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

import com.ee.enigma.dao.LocationInfoDao;
import com.ee.enigma.model.LocationInfo;

@Repository(value = "locationInfoDao")
@Transactional
public class LocationInfoDaoImpl implements LocationInfoDao {
	private Logger logger = Logger.getLogger(DeviceInfoDaoImpl.class);
	private SessionFactory sessionFactory;
	@Autowired
	@Qualifier(value = "sessionFactory")
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public LocationInfo getLocationName(float latitude, float longitude) {
		logger.info("latitude: "+latitude+", longitude: "+longitude);
		Session session = this.sessionFactory.getCurrentSession();
		try{
		String hql = "SELECT  location,   ( 6371 * acos( cos( radians(:latitude) ) * cos( radians( latitude ) )   * cos( radians(longitude) - radians(:longitude)) + sin(radians(:latitude))   * sin( radians(latitude)))) AS distance, radius FROM LocationInfo group by location HAVING col_1_0_ < radius ORDER BY distance";
		Query query = session.createQuery(hql);
		query.setMaxResults(1);
		query.setParameter("latitude", latitude);
		query.setParameter("longitude", longitude);
		List<Object> locationInfoList = (List<Object>) query.list();
		if(null == locationInfoList || locationInfoList.size() == 0 ){
			return null;
		}
		LocationInfo locationInfo = new LocationInfo();
		Object[] obj = (Object[]) locationInfoList.get(0);
		locationInfo.setLocation((String)(obj[0]));
		locationInfo.setDistance((double)(obj[1]));
		logger.info(locationInfo);
		return locationInfo;
		}catch(HibernateException e){
			logger.error(e);
			return null;
		}
	}

}
