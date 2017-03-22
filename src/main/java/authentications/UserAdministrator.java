package authentications;

import java.sql.Connection;

import javax.servlet.http.HttpSession;

import user.UserDetails;
import utilities.CommonTypes;

public class UserAdministrator {

	public int validateUser(String token, HttpSession session) {
		int userId = CommonTypes.NOT_VALID_USER;

		if (session.getAttribute(CommonTypes.TOKKEN_NAME) != null) {
			userId = Integer.parseInt((String) session.getAttribute("" + token + ""));
		}

		return userId;
	}

	public String signupUser(UserDetails user, Connection conn, HttpSession session) {

		return null;
	}

	public String loginUser(UserDetails user, Connection conn, HttpSession session) {

		return null;
	}

	public String createSession(int UserId, HttpSession sessions) {

		sessions.setAttribute(CommonTypes.TOKKEN_NAME, "");

		return null;
	}

	public boolean logoutUser() {

		return false;
	}

}
