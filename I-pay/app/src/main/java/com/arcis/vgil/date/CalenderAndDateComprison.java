package com.arcis.vgil.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CalenderAndDateComprison {

	public String dateComprison(String fromDate, String toDate) {
		
		String result = null;
		try {

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			Date date1 = sdf.parse(fromDate);
			Date date2 = sdf.parse(toDate);
			Calendar cal1 = Calendar.getInstance();
			Calendar cal2 = Calendar.getInstance();
			cal1.setTime(date1);
			cal2.setTime(date2);

			if (cal1.after(cal2)) {
				
				result="-1";
			}

			if (cal1.before(cal2)) {
				
				result="1";
			}

			if (cal1.equals(cal2)) {
			
				result="0";
			}

		} catch (ParseException ex) {
			ex.printStackTrace();
		}

		return result;

	}

}
