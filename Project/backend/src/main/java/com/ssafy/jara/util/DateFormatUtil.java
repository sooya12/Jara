package com.ssafy.jara.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatUtil {

	public static String DateToString(Date date) {
		DateFormat format = new SimpleDateFormat("yyyy/MM/dd k:mm");
		return format.format(date);
	}
}
