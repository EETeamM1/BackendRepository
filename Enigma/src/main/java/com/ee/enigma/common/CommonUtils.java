package com.ee.enigma.common;

import java.util.Date;

public class CommonUtils
{
  public static java.sql.Date getCurrentDate()
  {
    Date date= new java.util.Date();
    return (new java.sql.Date(date.getTime()));
  }
  public static long getTime()
  {
    Date date= new java.util.Date();
    return (date.getTime());
  }
}
