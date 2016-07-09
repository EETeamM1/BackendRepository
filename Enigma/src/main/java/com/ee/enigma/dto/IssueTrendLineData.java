package com.ee.enigma.dto;


public class IssueTrendLineData
{
  String date;
  int issedByAdmin;
  int issedBySystem;

  public String getDate()
  {
    return date;
  }

  public void setDate(String date)
  {
    this.date = date;
  }

  public int getIssedByAdmin()
  {
    return issedByAdmin;
  }

  public void setIssedByAdmin(int issedByAdmin)
  {
    this.issedByAdmin = issedByAdmin;
  }

  public int getIssedBySystem()
  {
    return issedBySystem;
  }

  public void setIssedBySystem(int issedBySystem)
  {
    this.issedBySystem = issedBySystem;
  }
}