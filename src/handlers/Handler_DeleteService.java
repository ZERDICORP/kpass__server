package handlers;

import java.io.IOException;
import zer.http.HTTPRoute;
import zer.http.HTTPHandler;
import zer.http.HTTPRequest;
import zer.http.HTTPResponse;
import zer.http.HTTPTool;
import org.json.JSONObject;
import models.SQLModel_Service;
import sql_actions.SQLAction_DeleteService;
import constants.ResultCode;
import tools.Tools;
import config.Config;

@HTTPRoute
(
	type = "POST",
	pattern = "/delete_service"
)
public class Handler_DeleteService extends HTTPHandler
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

		Config.injector.<SQLModel_Service>inject(SQLModel_Service.class, new SQLAction_DeleteService(reqBody.getInt("user_id"), reqBody.getInt("id")));

		res.setBody(resBody
			.put("result", ResultCode.OK)
			.toString());
	}
}