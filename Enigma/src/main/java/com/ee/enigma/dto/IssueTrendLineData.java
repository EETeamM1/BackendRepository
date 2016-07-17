package com.ee.enigma.dto;


public class IssueTrendLineData
{
  String date;
  int byAdmin;
  int bySystem;
  int byUser;

  public String getDate()
  {
    return date;
  }

  public void setDate(String date)
  {
    this.date = date;
  }

  public int getByAdmin()
  {
    return byAdmin;
  }

  public void setByAdmin(int byAdmin)
  {
    this.byAdmin = byAdmin;
  }

  public int getBySystem()
  {
    return bySystem;
  }

  public void setBySystem(int bySystem)
  {
    this.bySystem = bySystem;
  }

  public int getByUser()
  {
    return byUser;
  }

  public void setByUser(int byUser)
  {
    this.byUser = byUser;
  }

  
}