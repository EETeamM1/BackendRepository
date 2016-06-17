package com.ee.enigma.request;

public class RequestParameters {
	private String userId;
	private String password;
	private long deviceId;
	private String osVersion;
	private float latitude;
	private float longitude;
	private String sessionToken;
	private boolean byAdmin;
	 private String beginDate;
	  private String endDate;
	
	
	
	public String getBeginDate()
    {
      return beginDate;
    }
    public void setBeginDate(String beginDate)
    {
      this.beginDate = beginDate;
    }
    public String getEndDate()
    {
      return endDate;
    }
    public void setEndDate(String endDate)
    {
      this.endDate = endDate;
    }
  public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public long getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(long deviceId) {
		this.deviceId = deviceId;
	}
	public String getOsVersion() {
		return osVersion;
	}
	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}
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
	public String getSessionToken() {
		return sessionToken;
	}
	public void setSessionToken(String sessionToken) {
		this.sessionToken = sessionToken;
	}
	public boolean isByAdmin() {
		return byAdmin;
	}
	public void setByAdmin(boolean byAdmin) {
		this.byAdmin = byAdmin;
	}
	
	
}
