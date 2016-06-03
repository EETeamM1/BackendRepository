package com.ee.enigma.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="MASTER_INFO")
public class Master {

	@Id
	private int id;
        private String masterPassword;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMasterPassword() {
		return masterPassword;
	}
	public void setMasterPassword(String masterPassword) {
		this.masterPassword = masterPassword;
	}	
	
	@Override
	public String toString() {
		return "id: "+this.getId()+", masterPassword: "+this.getMasterPassword();
	}
}
