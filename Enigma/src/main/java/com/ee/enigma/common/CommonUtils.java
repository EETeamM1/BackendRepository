package com.ee.enigma.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;



public class CommonUtils
{
  private static Logger  logger = Logger.getLogger(CommonUtils.class);	
  public static java.sql.Date getCurrentDate()
  {
    Date date= new Date();
    java.sql.Date sqlDate=new java.sql.Date(date.getTime());
    //logger.info(sqlDate);
    return sqlDate;
  }
  public static long getTime()
  {
    Date date= new Date();
    return (date.getTime());
  }
  
  public static java.sql.Date getSqlDateByString(String dateString)
  {
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    java.sql.Date sqlDate = null;
    Date parsed = null;
    try
    {
      parsed = sdf.parse(dateString);
      sqlDate = new java.sql.Date(parsed.getTime());
    }
    catch (ParseException e1)
    {
      e1.printStackTrace();
    }
    return sqlDate;
  }
 
}
