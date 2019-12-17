package tandem;

public class DaysOfWeek {

	public String getDay(int number)
	{
		if(number == 0)
			return "SUNDAY";
		else if(number == 1)
			return "MONDAY";
		else if(number == 2)
			return "TUESDAY";
		else if(number == 3)
			return "WEDNESDAY";
		else if(number == 4)
			return "THURSDAY";
		else if(number == 5)
			return "FRIDAY";
		else
			return "SATURDAY";
	}
}
