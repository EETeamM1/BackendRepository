package com.ee.enigma.common;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

import com.ee.enigma.response.EnigmaResponse;
import com.ee.enigma.response.ResponseCode;

public class CommonUtils {
	private static Logger logger = Logger.getLogger(CommonUtils.class);

	public static java.sql.Date getCurrentDate() {
		Date date = new Date();
		java.sql.Date sqlDate = new java.sql.Date(date.getTime());
		return sqlDate;
	}

	public static java.sql.Timestamp getCurrentDateTime() {
		java.sql.Timestamp sqlDate = new java.sql.Timestamp((new java.util.Date()).getTime());
		return sqlDate;
	}

	public static long getTime() {
		Date date = new Date();
		return (date.getTime());
	}

	public static java.util.Date getSqlDateByString(String dateString) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		java.util.Date sqlDate = null;
		Date parsed = null;
		try {
			parsed = sdf.parse(dateString);
			sqlDate = new java.util.Date(parsed.getTime());
		} catch (ParseException e1) {
			e1.printStackTrace();
			throw new ParseException("Parse Exception", 0);
		}
		return sqlDate;
	}

	public static long getTimeDiffernce(Date beginDate, Date endDate) {
		long minuteDifference = 0;
		try {
			if (beginDate != null && endDate != null) {
				minuteDifference = endDate.getTime() - beginDate.getTime();
				minuteDifference = (long) ((minuteDifference / 1000) / 60);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return minuteDifference;
	}

	public static String displayTimeString(long minuteDifference) {
		String differenceStr = "";
		long minutes = minuteDifference % 60;
		long hours = minuteDifference / 60;
		if (hours > 0)
			differenceStr = hours + " Hour(s) " + minutes + " Minutes ";
		else
			differenceStr = minutes + " Minutes";
		return differenceStr;
	}

	public static Date getDayBeginTime(Date beginDate) {
		try {
			if (beginDate != null) {
				beginDate.setHours(8);
				beginDate.setMinutes(0);
				beginDate.setSeconds(0);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return beginDate;
	}

	public static java.util.Date getDayEndTime(java.util.Date endDate) {
		try {
			if (endDate != null) {
				endDate.setHours(23);
				endDate.setMinutes(59);
				endDate.setSeconds(59);

			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return endDate;
	}

	public static EnigmaResponse badRequest() {
		EnigmaResponse response = new EnigmaResponse();
		ResponseCode responseCode = new ResponseCode();
		responseCode.setCode(Constants.CODE_BAD_REQUEST);
		responseCode.setMessage(Constants.MESSAGE_BAD_REQUEST);
		response.setResponseCode(responseCode);
		return response;
	}

	public static EnigmaResponse badRequest(EnigmaResponse response, ResponseCode responseCode) {
		responseCode.setCode(Constants.CODE_BAD_REQUEST);
		responseCode.setMessage(Constants.MESSAGE_BAD_REQUEST);
		response.setResponseCode(responseCode);
		return response;
	}

	public static String getDateFromTimeStamp(Timestamp timestamp) {

		Date date = new Date(timestamp.getTime());
		return new SimpleDateFormat("yyyy-MM-dd").format(date);
	}

	public static String getTimeStampFormatedString(java.util.Date date) {
		try {
			Date formattedDate = new Date(date.getTime());
			return new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(formattedDate);
		} catch (Exception e) {
			return null;
		}
	}

	public static String getTimeStampFormatedString(Timestamp timestamp) {
		try {
			Date date = new Date(timestamp.getTime());
			return new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(date);
		} catch (Exception e) {
			return null;
		}
	}

	public static Date getLastMonthAndDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -1);
		return calendar.getTime();

	}

	public static EnigmaResponse deviceNotRegisteredResponse(EnigmaResponse response, ResponseCode responseCode) {
		responseCode.setCode(Constants.CODE_NOT_FOUND);
		responseCode.setMessage(Constants.MESSAGE_NOT_FOUND_DEVICE);
		response.setResponseCode(responseCode);
		return response;
	}

	public static EnigmaResponse userNotExistingResponse(EnigmaResponse response, ResponseCode responseCode) {
		responseCode.setCode(Constants.CODE_AUTHENTICATION_FAILD);
		responseCode.setMessage(Constants.USER_NOT_EXISTING);
		response.setResponseCode(responseCode);
		return response;
	}

	public static EnigmaResponse duplicateRequest(EnigmaResponse response, ResponseCode responseCode, String message) {
		responseCode.setCode(Constants.CODE_BAD_REQUEST);
		responseCode.setMessage(message);
		response.setResponseCode(responseCode);
		return response;
	}

	public static void updateResponse(EnigmaResponse response, ResponseCode responseCode, String message, int code) {
		responseCode.setCode(code);
		responseCode.setMessage(message);
		response.setResponseCode(responseCode);

	}
	
	public static java.util.Date getDateWithOutTime() {
		Calendar cal = Calendar.getInstance();
		cal.clear(Calendar.HOUR_OF_DAY);
		cal.clear(Calendar.AM_PM);
		cal.clear(Calendar.MINUTE);
		cal.clear(Calendar.SECOND);
		cal.clear(Calendar.MILLISECOND);
		
		return cal.getTime();
		
	}
	
	public static String displayTimeStringNA(String timeString)
  {
    timeString = (timeString+"").trim();
    if (timeString.equalsIgnoreCase("null") || "".equals(timeString) )
    {
      return Constants.RETURN_NA;
    }
    else
    {
      return timeString;
    }
  }
	
	public static java.util.Date timestampToDate(java.sql.Timestamp timestamp) {
		if (null != timestamp) {
			long milliseconds = timestamp.getTime() + (timestamp.getNanos() / 1000000);
			return new java.util.Date(milliseconds);
		}
		return null;
	}
	
	public static void internalSeverError(EnigmaResponse response, ResponseCode responseCode) {
    responseCode.setCode(Constants.CODE_INTERNAL_SERVER_ERROR);
    responseCode.setMessage(Constants.MESSAGE_INTERNAL_SERVER_ERROR);
    response.setResponseCode(responseCode);

  }
	public static EnigmaResponse internalSeverError() {
	  EnigmaResponse response=new EnigmaResponse();
	  ResponseCode responseCode=new ResponseCode();
	  responseCode.setCode(Constants.CODE_INTERNAL_SERVER_ERROR);
    responseCode.setMessage(Constants.MESSAGE_INTERNAL_SERVER_ERROR);
    response.setResponseCode(responseCode);
    return response;
  }
	public static EnigmaResponse internalSeverError(String error) {
    EnigmaResponse response=new EnigmaResponse();
    ResponseCode responseCode=new ResponseCode();
    responseCode.setCode(Constants.CODE_INTERNAL_SERVER_ERROR);
    responseCode.setMessage(error);
    response.setResponseCode(responseCode);
    return response;
  }
}
