package com.karthik.BillingSoftware.Utils;

public enum Month {
	JANUARY, FEBRUARY, MARCH, APRIL,
	MAY, JUNE, JULY, AUGUST,
	SEPTEMBER, OCTOBER, NOVEMBER, DECEMBER;

  public static String getMonthName(int month)
  {
	  return Month.values()[month].toString();
  }
  
  public static String[] getMonths() {
		String[] months = new String[12];
		for (int i = 0; i < 12; i++)
			months[i] = Month.values()[i].toString();
		return months;
	}

	/**
	 * Returns the number of days for a month selected
	 * @param month
	 * @return
	 */
	public static String[] getDays(String month) {
		String[] days;
		if ((Month.JANUARY.toString().equals(month)) || 
				(Month.MARCH.toString().equals(month)) ||
				(Month.MAY.toString().equals(month)) ||
				(Month.JULY.toString().equals(month)) ||
				(Month.AUGUST.toString().equals(month)) ||
				(Month.OCTOBER.toString().equals(month)) ||
				(Month.DECEMBER.toString().equals(month))) {
			days = new String[32];
			days[0] = "";
			for (int i = 1; i <= 31; i++)
				days[i] = "" + i;
		}
		else if ((Month.APRIL.toString().equals(month)) ||
				(Month.JUNE.toString().equals(month)) ||
				(Month.SEPTEMBER.toString().equals(month)) ||
				(Month.NOVEMBER.toString().equals(month))){
			days = new String[31];
			days[0] = "";
			for (int i = 1; i <= 30; i++)
				days[i] = "" + i;
		}
		else {
			days = new String[30];
			days[0] = "";
			for (int i = 1; i <= 29; i++)
				days[i] = "" + i;
		}
		return days;
	}
	
 }

