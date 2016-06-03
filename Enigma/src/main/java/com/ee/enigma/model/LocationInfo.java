package com.ee.enigma.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="LOCATION_INFO")
public class LocationInfo {
	private float latitude;
	private float longitude;
	private String location;
	private int radius;
	
	public float getLatitude() {
		return latitude;
	}
	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}
	public float getLongitude() {
		return longitude;
	}
	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public int getRadius() {
		return radius;
	}
	public void setRadius(int radius) {
		this.radius = radius;
	}
	
	
}
