package sql_actions;

import zer.sql.SQLAction;
import zer.sql.SQLActionType;

public class SQLAction_AddService extends SQLAction
{
	{ super.type = SQLActionType.WITHOUT_RES; }

	public SQLAction_AddService(int user_id, String name, String login, String password)
	{
		super.query = "INSERT INTO services (user_id, name, login, password) values ({user_id}, \"{name}\", \"{login}\", \"{password}\")"
			.replace("{user_id}", String.valueOf(user_id))
			.replace("{name}", name)
			.replace("{login}", login)
			.replace("{password}", password);
	}
}