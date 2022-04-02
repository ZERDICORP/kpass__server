package sql_actions;

import zer.sql.SQLAction;
import zer.sql.SQLActionType;

public class SQLAction_GetServices extends SQLAction
{
	{ super.type = SQLActionType.WITH_RES; }

	public SQLAction_GetServices byPattern(int user_id, String pattern)
	{
		super.query = "SELECT * FROM services WHERE user_id = {user_id} AND LOWER(services.name) LIKE \"%{pattern}%\""
			.replace("{user_id}", String.valueOf(user_id))
			.replace("{pattern}", pattern);

		return this;
	}

	public SQLAction_GetServices byId(int user_id, int id)
	{
		super.query = "SELECT * FROM services WHERE user_id = {user_id} AND id = {id}"
			.replace("{user_id}", String.valueOf(user_id))
			.replace("{id}", String.valueOf(id));

		return this;
	}
};