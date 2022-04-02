package com.kpass.www;

import zer.http.HTTPServer;
import zer.http.HTTPConfig;
import zer.sql.SQLConfig;
import zer.sql.SQLInjector;
import zer.mail.MAILConfig;
import zer.mail.MAILClient;
import tools.ConfigLoader;
import config.Config;
import handlers.Handler_GetServices;
import handlers.Handler_UpdateService;
import handlers.Handler_AddService;
import handlers.Handler_DeleteService;
import handlers.Handler_Auth;
import handlers.Handler_ChangePassword;
import handlers.Handler_CreateAccount;
import handlers.Handler_ConfirmAccount;
import handlers.Handler_Temp;

public class Main
{
	public static void main(String[] args)
	{
		ConfigLoader.load(Config.class, "resources/app.cfg");

    SQLConfig.setDatabase(Config.databaseName);
		SQLConfig.setPassword(Config.databasePassword);

    Config.injector = new SQLInjector();
        
    MAILConfig.setSender(Config.emailSender);
    MAILConfig.setPassword(Config.emailPassword);

    Config.mail = new MAILClient();

		HTTPConfig.setPort(83);

		HTTPServer server = new HTTPServer();
		
		server.addHandler(new Handler_GetServices());
		server.addHandler(new Handler_UpdateService());
		server.addHandler(new Handler_AddService());
		server.addHandler(new Handler_DeleteService());
		server.addHandler(new Handler_Auth());
		server.addHandler(new Handler_ChangePassword());
		server.addHandler(new Handler_CreateAccount());
		server.addHandler(new Handler_ConfirmAccount());

		server.run();
	}
}
