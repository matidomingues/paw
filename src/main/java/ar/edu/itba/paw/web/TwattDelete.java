package ar.edu.itba.paw.web;

import ar.edu.itba.paw.helper.TwattHelper;
import ar.edu.itba.paw.helper.UserHelper;
import ar.edu.itba.paw.helper.implementations.TwattHelperImpl;
import ar.edu.itba.paw.helper.implementations.UserHelperImpl;
import ar.edu.itba.paw.model.Twatt;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.utils.exceptions.InvalidOperationExcetion;
import ar.edu.itba.paw.utils.exceptions.InvalidTwattIDException;
import com.google.common.base.Strings;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@SuppressWarnings("serial")
public class TwattDelete extends HttpServlet {

    private TwattHelper twattHelper = TwattHelperImpl.getInstance();
    private UserHelper userHelper = UserHelperImpl.getInstance();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String sTwatt_id = request.getParameter("twattId");

        if (Strings.isNullOrEmpty(sTwatt_id)) {
            throw new InvalidTwattIDException();
        }
        int twatt_id = Integer.parseInt(sTwatt_id);

        Twatt twatt = this.twattHelper.getTwatt(twatt_id);
        int userId = (Integer)request.getSession().getAttribute("user_id");
        User user = this.userHelper.find(userId);

        if (!twatt.getCreator().equals(user) && !user.isEnabled() && !twatt.isDeleted()) {
            throw new InvalidOperationExcetion();
        }

        this.twattHelper.delete(twatt);

        request.setAttribute("success", "Has elminiado un twatt!");

        request.getRequestDispatcher("home").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
