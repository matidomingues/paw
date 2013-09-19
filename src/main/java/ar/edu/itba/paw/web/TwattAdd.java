package ar.edu.itba.paw.web;

import ar.edu.itba.paw.helper.TwattHelper;
import ar.edu.itba.paw.helper.UserHelper;
import ar.edu.itba.paw.helper.implementations.TwattHelperImpl;
import ar.edu.itba.paw.helper.implementations.UserHelperImpl;
import ar.edu.itba.paw.model.Twatt;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.utils.exceptions.MessageEmptyException;
import com.google.common.base.Strings;
import org.joda.time.DateTime;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TwattAdd extends HttpServlet {

	private TwattHelper twattmanager = TwattHelperImpl.getInstance();
    private UserHelper userHelper = UserHelperImpl.getInstance();

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String message = req.getParameter("message");
        if (Strings.isNullOrEmpty(message)) {
            throw new MessageEmptyException();
        }

        int user_id = (Integer) req.getSession().getAttribute("user");
        User user = this.userHelper.find(user_id);
        twattmanager.addTwatt(new Twatt(user, message, false, DateTime.now()));
        boolean result = true;
        if (!result) {
			req.setAttribute("error", "No es posible Twattear en este momento");
		} else {
			req.setAttribute("success", "Se Twatteo correctamente");
		}
		req.getRequestDispatcher("/home").forward(req, resp);
	}
}
