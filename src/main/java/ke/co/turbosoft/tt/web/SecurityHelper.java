package ke.co.turbosoft.tt.web;

import ke.co.turbosoft.tt.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


/**
 * Created by akipkoech on 1/28/15.
 */
public class SecurityHelper {

    static final String SESSION_ATTRIB_USER = "sessionuser";

    public static User getSessionUser(HttpServletRequest request){
        User user=null;
        HttpSession session = request.getSession(true);
        Object obj = session .getAttribute(SESSION_ATTRIB_USER);

        if (obj != null && obj instanceof User){
            user = (User) obj;
        }
        return user;
    }

}
