package sql_actions;

import zer.sql.SQLAction;
import zer.sql.SQLActionType;

public class SQLAction_UpdateUser extends SQLAction
{
	{ super.type = SQLActionType.WITHOUT_RES; }

	public SQLAction_UpdateUser confirm(int id)
	{
		super.query = "UPDATE users SET confirmed = 1 WHERE id = {id}"
			.replace("{id}", String.valueOf(id));

		return this;
	}

	public SQLAction_UpdateUser password(int id, String new_password_hash)
	{
		super.query = "UPDATE users SET password_hash = \"{new_password_hash}\" WHERE id = {id}"
			.replace("{new_password_hash}", new_password_hash)
			.replace("{id}", String.valueOf(id));

		return this;
	}
}