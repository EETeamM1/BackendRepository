package com.ee.enigma.dao;

import com.ee.enigma.model.LocationInfo;

public interface LocationInfoDao {
    public LocationInfo getLocationName(float latitude, float longitude);
}
