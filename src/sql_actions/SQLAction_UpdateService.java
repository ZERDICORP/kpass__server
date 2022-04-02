package sql_actions;

import zer.sql.SQLAction;
import zer.sql.SQLActionType;

public class SQLAction_UpdateService extends SQLAction
{
	{ super.type = SQLActionType.WITHOUT_RES; }

	public SQLAction_UpdateService update(int user_id, int id, String name, String password, String login)
	{
		super.query = "UPDATE services SET name = {name}, password = {password}, login = {login} WHERE id = {id} AND user_id = {user_id}"
			.replace("{user_id}", String.valueOf(user_id))
			.replace("{id}", String.valueOf(id))
			.replace("{name}", name.equals("_") ? "services.name" : "\"" + name + "\"")
			.replace("{password}", password.equals("_") ? "services.password" : "\"" + password + "\"")
			.replace("{login}", login.equals("_") ? "services.login" : "\"" + login + "\"");

		return this;
	}
};