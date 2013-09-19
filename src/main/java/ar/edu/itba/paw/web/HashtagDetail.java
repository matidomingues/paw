package ar.edu.itba.paw.web;

import ar.edu.itba.paw.helper.HashtagHelper;
import ar.edu.itba.paw.helper.implementations.HashtagHelperImpl;
import ar.edu.itba.paw.model.Hashtag;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: facundo
 * Date: 14/09/13
 * Time: 20:52
 * To change this template use File | Settings | File Templates.
 */
public class HashtagDetail extends HttpServlet {

    private HashtagHelper hashtagHelper  = HashtagHelperImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] uri = request.getRequestURI().split("/");
        Hashtag hashtag = hashtagHelper.getHashtag(uri[uri.length]);
        if (hashtag == null) {
            request.setAttribute("error", "No existe este hashtag");
        } else {
            request.setAttribute("hastag", hashtag);
        }
        request.getRequestDispatcher("").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
