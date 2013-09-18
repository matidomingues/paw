package ar.edu.itba.paw.web;

import ar.edu.itba.paw.helper.HashtagHelper;
import ar.edu.itba.paw.helper.TwattHelper;
import ar.edu.itba.paw.helper.implementations.HashtagHelperImpl;
import ar.edu.itba.paw.helper.implementations.TwattHelperImpl;
import ar.edu.itba.paw.model.Hashtag;
import ar.edu.itba.paw.model.Twatt;
import ar.edu.itba.paw.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: facundo
 * Date: 17/09/13
 * Time: 00:50
 * To change this template use File | Settings | File Templates.
 */
public class ViewHashtag extends HttpServlet {

    private HashtagHelper hashtagHelper = HashtagHelperImpl.getInstance();
    private TwattHelper twattHelper = TwattHelperImpl.getInstance();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String[] url = req.getRequestURI().split("/");
        Hashtag hashtag = this.hashtagHelper.getHashtag(url[2]);

        if (hashtag == null) {
            req.setAttribute("error", "No existe ese hashtag");
        } else {
            List<Twatt> twatts = this.twattHelper.getTwattsByHashtag(hashtag);
            req.setAttribute("hashtag", hashtag);
            req.setAttribute("twatts", twatts);
        }
        req.getRequestDispatcher("/WEB-INF/jsp/viewhashtag.jsp").forward(req, resp);
    }

}
