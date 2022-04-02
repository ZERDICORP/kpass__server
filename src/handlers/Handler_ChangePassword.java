package handlers;

import java.io.IOException;
import zer.http.HTTPRoute;
import zer.http.HTTPHandler;
import zer.http.HTTPRequest;
import zer.http.HTTPResponse;
import zer.http.HTTPTool;
import org.json.JSONObject;
import models.SQLModel_User;
import sql_actions.SQLAction_UpdateUser;
import constants.ResultCode;
import tools.Tools;
import config.Config;

@HTTPRoute
(
	type = "POST",
	pattern = "/change_password"
)
public class Handler_ChangePassword extends HTTPHandler
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

		Config.injector.<SQLModel_User>inject(SQLModel_User.class, new SQLAction_UpdateUser().password(reqBody.getInt("user_id"), reqBody.getString("new_password_hash")));

		res.setBody(resBody
			.put("result", ResultCode.OK)
			.toString());
	}
}