package handlers;

import java.io.IOException;
import zer.http.HTTPRoute;
import zer.http.HTTPHandler;
import zer.http.HTTPRequest;
import zer.http.HTTPResponse;
import zer.http.HTTPTool;
import org.json.JSONObject;
import org.json.JSONArray;
import java.util.ArrayList;
import models.SQLModel_Service;
import sql_actions.SQLAction_GetServices;
import constants.ResultCode;
import tools.Tools;
import config.Config;

@HTTPRoute
(
	type = "POST",
	pattern = "/get_services"
)
public class Handler_GetServices extends HTTPHandler
{
	@Override
	public void handle(HTTPRequest req, HTTPResponse res) throws IOException
	{
		res.set("Content-Type", HTTPTool.mime(".json"));

		JSONObject reqBody = new JSONObject(req.get("Body"));
		JSONObject resBody = new JSONObject();

		ResultCode authResult = Tools.auth(reqBody.getInt("user_id"), reqBody.getString("password_hash"));
		if (authResult != ResultCode.OK)
		{
			res.setBody(resBody
				.put("result", authResult)
				.toString());
			return;
		}

		ArrayList<SQLModel_Service> services = Config.injector.<SQLModel_Service>inject(SQLModel_Service.class,
			new SQLAction_GetServices().byPattern(
				reqBody.getInt("user_id"),
				reqBody.getString("pattern")));

		JSONArray jarr = new JSONArray();
		for (SQLModel_Service service : services)
			jarr.put(new JSONObject(service, new String[] {"id", "name", "password", "login"}));

		res.setBody(resBody
			.put("services", jarr)
			.put("result", ResultCode.OK)
			.toString());
	}
}