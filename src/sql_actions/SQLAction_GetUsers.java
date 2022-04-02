package sql_actions;

import zer.sql.SQLAction;
import zer.sql.SQLActionType;

public class SQLAction_GetUsers extends SQLAction
{
	{ super.type = SQLActionType.WITH_RES; }

	public SQLAction_GetUsers byId(int id)
	{
		super.query = "SELECT * FROM users WHERE id = {id}"
			.replace("{id}", String.valueOf(id));

		return this;
	}

	public SQLAction_GetUsers byEmail(String email)
	{
		super.query = "SELECT * FROM users WHERE email = \"{email}\" AND confirmed = 1"
			.replace("{email}", email);
	
		return this;
	}

	public SQLAction_GetUsers byEmailAndNotConfirmed(String email)
	{
		super.query = "SELECT * FROM users WHERE email = \"{email}\" AND confirmed = 0"
			.replace("{email}", email);
	
		return this;
	}

	public SQLAction_GetUsers allNotConfirmed()
	{
		super.query = "SELECT * FROM users WHERE confirmed = 0";
	
		return this;
	}
}