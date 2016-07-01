package com.ee.enigma.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

public class CommonUtils
{
  private static Logger logger = Logger.getLogger(CommonUtils.class);

  public static java.sql.Date getCurrentDate()
  {
    Date date = new Date();
    java.sql.Date sqlDate = new java.sql.Date(date.getTime());
    return sqlDate;
  }

  public static java.sql.Timestamp getCurrentDateTime()
  {
    java.sql.Timestamp sqlDate = new java.sql.Timestamp((new java.util.Date()).getTime());
    return sqlDate;
  }

  public static long getTime()
  {
    Date date = new Date();
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

  public static long getTimeDiffernce(Date beginDate, Date endDate)
  {
    long difference = 0;
    try
    {
      if (beginDate != null && endDate != null)
      {
        difference = endDate.getTime() - beginDate.getTime();
        difference = (long) ((difference / 1000) / 60);
      }
    }
    catch (Exception e1)
    {
      e1.printStackTrace();
    }
    return difference;
  }

  public static Date getDayBeginTime(Date beginDate)
  {
    try
    {
      if (beginDate != null)
      {
        beginDate.setHours(8);
        beginDate.setMinutes(0);
        beginDate.setSeconds(0);
      }
    }
    catch (Exception e1)
    {
      e1.printStackTrace();
    }
    return beginDate;
  }

  public static Date getDayEndTime(Date endDate)
  {
    try
    {
      if (endDate != null)
      {
        endDate.setHours(23);
        endDate.setMinutes(59);
        endDate.setSeconds(59);
      }
    }
    catch (Exception e1)
    {
      e1.printStackTrace();
    }
    return endDate;
  }

}
