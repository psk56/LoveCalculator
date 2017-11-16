package com.psktechnology.lovecalculator.global;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class AgeCalculation {

	public static String age(int year, int month, int day) {
		return String.valueOf(calculateMyAge(year, month, day));
	}
	
	private static int calculateMyAge(int year, int month, int day) {
		
		Calendar birthCal = new GregorianCalendar(year, month, day);
		Calendar nowCal = new GregorianCalendar();
		int age = nowCal.get(Calendar.YEAR) - birthCal.get(Calendar.YEAR);

		boolean isMonthGreater = birthCal.get(Calendar.MONTH) >= nowCal.get(Calendar.MONTH);
		boolean isMonthSameButDayGreater = birthCal.get(Calendar.MONTH) == nowCal.get(Calendar.MONTH) && birthCal.get(Calendar.DAY_OF_MONTH) > nowCal.get(Calendar.DAY_OF_MONTH);

		if (isMonthGreater || isMonthSameButDayGreater) {
			age = age - 1;
		}
		return age;
	}

}