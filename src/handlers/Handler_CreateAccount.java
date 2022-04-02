package handlers;

import java.io.IOException;
import zer.http.HTTPRoute;
import zer.http.HTTPHandler;
import zer.http.HTTPRequest;
import zer.http.HTTPResponse;
import zer.http.HTTPConfig;
import zer.http.HTTPTool;
import org.json.JSONObject;
import java.util.ArrayList;
import models.SQLModel_User;
import sql_actions.SQLAction_GetUsers;
import sql_actions.SQLAction_AddUser;
import config.Config;
import zer.hash.PaED62;
import constants.ResultCode;

@HTTPRoute
(
	type = "POST",
	pattern = "/create_account"
)
public class Handler_CreateAccount extends HTTPHandler
{
	@Override
	public void handle(HTTPRequest req, HTTPResponse res) throws IOException
	{
		res.set("Content-Type", HTTPTool.mime(".json"));

		JSONObject reqBody = new JSONObject(req.get("Body"));
		JSONObject resBody = new JSONObject();

		ArrayList<SQLModel_User> users = Config.injector.<SQLModel_User>inject(SQLModel_User.class, new SQLAction_GetUsers().byEmail(reqBody.getString("email")));
		if (users.size() > 0)
		{
			res.setBody(resBody
				.put("result", ResultCode.ALREADY_EXIST)
				.toString());
			return;
		}
		
		ArrayList<SQLModel_User> notConfirmedUsers = Config.injector.<SQLModel_User>inject(SQLModel_User.class, new SQLAction_GetUsers().byEmailAndNotConfirmed(reqBody.getString("email")));
		if (notConfirmedUsers.size() == 0)
			Config.injector.<SQLModel_User>inject(SQLModel_User.class, new SQLAction_AddUser(reqBody.getString("email"), reqBody.getString("password_hash")));

		Config.mail.send(reqBody.getString("email"), "KPASS", 
			"To verify your account follow the link below:\n\n" + HTTPConfig.getFullHostname() + "/confirm_account/" + PaED62.hash(reqBody.getString("email") + reqBody.getString("password_hash")));

		res.setBody(resBody
			.put("result", ResultCode.OK)
			.toString());
	}
}