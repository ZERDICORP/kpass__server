package handlers;

import java.io.IOException;
import zer.http.HTTPRoute;
import zer.http.HTTPHandler;
import zer.http.HTTPRequest;
import zer.http.HTTPResponse;
import zer.http.HTTPTool;
import java.util.ArrayList;
import models.SQLModel_User;
import sql_actions.SQLAction_UpdateUser;
import sql_actions.SQLAction_GetUsers;
import config.Config;
import zer.hash.PaED62;

@HTTPRoute
(
	type = "GET",
	pattern = "/confirm_account/[a-zA-Z0-9]{62}"
)
public class Handler_ConfirmAccount extends HTTPHandler
{
	@Override
	public void handle(HTTPRequest req, HTTPResponse res) throws IOException
	{
		res.set("Content-Type", HTTPTool.mime(".html"));

		String[] parts = req.get("Path").split("/");
		String seal = parts[2];
		String resBody = new String();

		ArrayList<SQLModel_User> users = Config.injector.<SQLModel_User>inject(SQLModel_User.class, new SQLAction_GetUsers().allNotConfirmed());
		for (SQLModel_User user : users)
			if (seal.equals(PaED62.hash(user.email + user.password_hash)))
			{
				Config.injector.<SQLModel_User>inject(SQLModel_User.class, new SQLAction_UpdateUser().confirm(user.id));

				resBody += "<h1>KPASS account confirmed!</h1></br>";
				resBody += "<h3>To log into your account use the following command:</br>";
				resBody += "kpass auth &lt;email&gt; &lt;password&gt;</h3>";

				break;
			}

		if (resBody.length() == 0)
			resBody += "<h1>Outdated link</h1>";

		res.setBody(resBody);
	}
}