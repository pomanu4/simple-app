package ua.com.company.component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter {
	static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	public static Date getDate(long seconds) {
		return new Date(seconds * 1000);
	}

	public static String getStringDateRepresent(long seconds) {
		Date date = new Date(seconds * 1000);
		return dateFormat.format(date);
	}

	public static long getDateInMilisecond(String stringDate) {
		if (!stringDate.equals("")) {
			Date date;
			long milisecond = 0;
			try {
				date = dateFormat.parse(stringDate);
				milisecond = date.getTime();
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return milisecond;
		}
		return getCurrentDate();
	}

	public static long getCurrentDate() {
		Date date = new Date();
		String formatedDate = dateFormat.format(date);
		long miliseconds = 0;
		try {
			miliseconds = dateFormat.parse(formatedDate).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return miliseconds;
	}

	public static long getThreeDayLater() {
		long threeDayMilisec = 86400000 * 3;
		Date date = new Date();
		String formatedDate = dateFormat.format(date);
		long dateAfter = 0;
		try {
			dateAfter = dateFormat.parse(formatedDate).getTime() + threeDayMilisec;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dateAfter;
	}

	public static long get24HourLater(long milisec) {
		long fourHour = 3600000 * 24;
		return milisec + fourHour;
	}
}
