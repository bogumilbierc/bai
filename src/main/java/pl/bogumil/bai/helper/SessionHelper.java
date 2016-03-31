package pl.bogumil.bai.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.bogumil.bai.dto.UserInSession;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by bbierc on 2016-03-31.
 */
@Component
public class SessionHelper {

    private static final String USER_IN_SESSION = "USER_IN_SESSION";

    @Autowired
    HttpServletRequest request;

    public UserInSession getUserFromSession() {
        if (request.getSession() != null) {
            HttpSession session = request.getSession();
            if (session.getAttribute(USER_IN_SESSION) != null) {
                return (UserInSession) session.getAttribute(USER_IN_SESSION);
            }
        }
        return null;
    }

    public void setUserInSession(UserInSession userInSession) {
        request.getSession().setAttribute(USER_IN_SESSION, userInSession);
    }

    public void removeUserInSession() {
        request.getSession().removeAttribute(USER_IN_SESSION);
    }

}
