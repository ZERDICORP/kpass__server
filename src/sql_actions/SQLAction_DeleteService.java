package sql_actions;

import zer.sql.SQLAction;
import zer.sql.SQLActionType;

public class SQLAction_DeleteService extends SQLAction
{
	{ super.type = SQLActionType.WITHOUT_RES; }

	public SQLAction_DeleteService(int user_id, int id)
	{
		super.query = "DELETE FROM services WHERE id = {id} AND user_id = {user_id}"
			.replace("{id}", String.valueOf(id))
			.replace("{user_id}", String.valueOf(user_id));
	}
};