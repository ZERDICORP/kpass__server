package tools;

import constants.ResultCode;
import java.util.ArrayList;
import models.SQLModel_User;
import models.SQLModel_Service;
import sql_actions.SQLAction_GetUsers;
import sql_actions.SQLAction_GetServices;
import config.Config;

public class Tools
{
	public static String escape(String s) { return s.replaceAll("([+\\-!\\(\\){}\\[\\]^\"~*?:\\\\]|[&\\|]{2})", "\\\\$1"); }

	public static ResultCode auth(int user_id, String password_hash)
	{
		ArrayList<SQLModel_User> users = Config.injector.<SQLModel_User>inject(SQLModel_User.class, new SQLAction_GetUsers().byId(user_id));
		if (users.size() == 0)
			return ResultCode.DOES_NOT_EXIST;

		if (!users.get(0).password_hash.equals(password_hash))
			return ResultCode.ACCESS_DENIED;

		return ResultCode.OK;
	}

	public static ResultCode serviceExist(int user_id, int id)
	{
		ArrayList<SQLModel_Service> services = Config.injector.<SQLModel_Service>inject(SQLModel_Service.class, new SQLAction_GetServices().byId(user_id, id));
		if (services.size() == 0)
			return ResultCode.BAD_INDEX;
		
		return ResultCode.OK;
	}
}