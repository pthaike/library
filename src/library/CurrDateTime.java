package library;

import java.util.Date;
import java.text.SimpleDateFormat;

public class CurrDateTime {

	public static String currTime(){
		return new SimpleDateFormat("[HH:mm:ss:SSS]").format(new Date());
	}
	
	public static String currDate(){
		return new SimpleDateFormat("[yyyy-MM-dd]").format(new Date());
	}
	
	public static String currDateTime(){
		return new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss:SSS]").format(new Date());
	}
	
	public static String myDateTime(String datetimeformat){
		return new SimpleDateFormat(datetimeformat).format(new Date());
	}
}
