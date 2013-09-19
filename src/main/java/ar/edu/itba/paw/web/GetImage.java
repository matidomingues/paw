package ar.edu.itba.paw.web;

import ar.edu.itba.paw.helper.UserHelper;
import ar.edu.itba.paw.helper.implementations.UserHelperImpl;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.utils.exceptions.IllegalImageIDException;
import com.google.common.base.Strings;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created with IntelliJ IDEA.
 * User: facundo
 * Date: 18/09/13
 * Time: 22:04
 * To change this template use File | Settings | File Templates.
 */
public class GetImage extends HttpServlet {

    private UserHelper userHelper = UserHelperImpl.getInstance();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sUser_id = req.getParameter("id");
        if (Strings.isNullOrEmpty(sUser_id)) {
            throw new IllegalImageIDException();
        }
        int user_id = Integer.parseInt(sUser_id);
        User user = this.userHelper.find(user_id);
        byte [] photo = null;
        if (user.getPhoto() == null || user.getPhoto().length == 0) {
            Path path = Paths.get("/img/default_user_icon.png");
            photo = Files.readAllBytes(path);
        }
        resp.setContentType("image/png");
        resp.getOutputStream().write(photo);
    }

}
