package sql_actions;

import zer.sql.SQLAction;
import zer.sql.SQLActionType;

public class SQLAction_AddUser extends SQLAction
{
	{ super.type = SQLActionType.WITHOUT_RES; }

	public SQLAction_AddUser(String email, String password_hash)
	{
		super.query = "INSERT INTO users (email, password_hash) values (\"{email}\", \"{password_hash}\")"
			.replace("{email}", email)
			.replace("{password_hash}", password_hash);
	}
}