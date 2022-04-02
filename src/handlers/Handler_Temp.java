package handlers;

import zer.cipher.CaesarEncryptionAlgorithm;
import zer.http.*;
import zer.sql.*;
import org.json.JSONObject;
import org.json.JSONArray;
import models.*;
import sql_actions.*;
import java.util.ArrayList;
import java.io.IOException;
import zer.file.*;
import tools.Tools;
import config.Config;

@HTTPRoute
(
	type = "POST",
	pattern = "^\\/temp$"
)
public class Handler_Temp extends HTTPHandler
{
	@Override
	public void handle(HTTPRequest req, HTTPResponse res) throws IOException
	{
		CaesarEncryptionAlgorithm caesar = new CaesarEncryptionAlgorithm("123123");
		caesar.setRange(32, 126);

		JSONArray jarr = new JSONArray(FPlain.read("db_temp.json"));
		for (int i = 0; i < jarr.length(); ++i)
		{
			JSONObject jobj = jarr.getJSONObject(i);
			Config.injector.<SQLModel_Service>inject(SQLModel_Service.class, new SQLAction_AddService(0, jobj.getString("name"),
				Tools.escape(caesar.encrypt(jobj.getString("login"))), Tools.escape(caesar.encrypt(jobj.getString("password")))));
		}

		// ArrayList<SQLModel_Service> services = Config.injector.<SQLModel_Service>inject(SQLModel_Service.class, new SQLAction_GetServices("2"));

		// CaesarEncryptionAlgorithm caesar = new CaesarEncryptionAlgorithm("zerdicorp1937");
		// caesar.setRange(32, 126);

		// for (SQLModel_Service service : services)
		// {
		// 	if (service.login != null)
		// 		Config.injector.<SQLModel_Service>inject(SQLModel_Service.class, new SQLAction_UpdateService(service.id,
		// 			,
		// 			Tools.escape(caesar.encrypt(service.password)),
		// 			Tools.escape(caesar.encrypt(service.login))));
		// 	else
		// 		Config.injector.<SQLModel_Service>inject(SQLModel_Service.class, new SQLAction_UpdateService(service.id,
		// 			Tools.escape(caesar.encrypt(service.name)),
		// 			Tools.escape(caesar.encrypt(service.password))));
		// }

		res
			.set("Content-Type", HTTPTool.mime(".json"))
			.setBody("");
	}
}