package com.ee.enigma.dao;

import com.ee.enigma.model.Master;

public interface MasterDao {
	public Master getMasterInfo();
	public void updateMasterInfo(Master master);
}
