package handlers;

import java.io.IOException;
import zer.http.HTTPRoute;
import zer.http.HTTPHandler;
import zer.http.HTTPRequest;
import zer.http.HTTPResponse;
import zer.http.HTTPTool;
import org.json.JSONObject;
import org.json.JSONArray;
import models.SQLModel_Service;
import sql_actions.SQLAction_UpdateService;
import constants.ResultCode;
import tools.Tools;
import config.Config;

@HTTPRoute
(
	type = "POST",
	pattern = "/update_service"
)
public class Handler_UpdateService extends HTTPHandler
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

		ResultCode serviceExistResult = Tools.serviceExist(reqBody.getInt("user_id"), reqBody.getInt("id"));
		if (serviceExistResult != ResultCode.OK)
		{
			res.setBody(resBody
				.put("result", serviceExistResult)
				.toString());
			return;
		}
		
		Config.injector.<SQLModel_Service>inject(SQLModel_Service.class, new SQLAction_UpdateService().update(reqBody.getInt("user_id"), reqBody.getInt("id"), reqBody.getString("name"),
			Tools.escape(reqBody.getString("password")), Tools.escape(reqBody.getString("login"))));

		res.setBody(resBody
			.put("result", ResultCode.OK)
			.toString());
	}
}