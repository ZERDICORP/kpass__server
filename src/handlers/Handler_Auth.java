package handlers;

import java.io.IOException;
import zer.http.HTTPRoute;
import zer.http.HTTPHandler;
import zer.http.HTTPRequest;
import zer.http.HTTPResponse;
import zer.http.HTTPTool;
import org.json.JSONObject;
import java.util.ArrayList;
import models.SQLModel_User;
import sql_actions.SQLAction_GetUsers;
import constants.ResultCode;
import config.Config;

@HTTPRoute
(
	type = "POST",
	pattern = "/auth"
)
public class Handler_Auth extends HTTPHandler
{
	@Override
	public void handle(HTTPRequest req, HTTPResponse res) throws IOException
	{
		res.set("Content-Type", HTTPTool.mime(".json"));

		JSONObject reqBody = new JSONObject(req.get("Body"));
		JSONObject resBody = new JSONObject();

		ArrayList<SQLModel_User> users = Config.injector.<SQLModel_User>inject(SQLModel_User.class, new SQLAction_GetUsers().byEmail(reqBody.getString("email")));
		if (users.size() == 0)
		{
			res.setBody(resBody
				.put("result", ResultCode.DOES_NOT_EXIST)
				.toString());
			return;
		}

		SQLModel_User user = users.get(0);
		if (!user.password_hash.equals(reqBody.getString("password_hash")))
		{
			res.setBody(resBody
				.put("result", ResultCode.ACCESS_DENIED)
				.toString());
			return;
		}

		res.setBody(resBody
			.put("id", user.id)
			.put("result", ResultCode.OK)
			.toString());
	}
}