package library;

public class DbInfoGetter {

	private static String dbDriver="com.mysql.jdbc.Driver";
	private static String dbURL="jdbc:mysql://localhost:3306/db_lib";
	private static String dbUser="sea";
	private static String dbPassword="just";
	public static String getDbDriver() {
		return dbDriver;
	}
	public static String getDbURL() {
		return dbURL;
	}
	public static String getDbUser() {
		return dbUser;
	}
	public static String getDbPassword() {
		return dbPassword;
	}
	
}
