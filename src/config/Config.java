package config;

import zer.sql.SQLInjector;
import zer.mail.MAILClient;

public class Config
{
	public static int port;
	public static String databaseName;
	public static String databasePassword;
	public static String emailSender;
	public static String emailPassword;
	public static SQLInjector injector;
	public static MAILClient mail;
}
