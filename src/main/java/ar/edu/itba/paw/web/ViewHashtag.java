//package ar.edu.itba.paw.web;
//
//import ar.edu.itba.paw.model.Hashtag;
//import ar.edu.itba.paw.model.Twatt;
//import ar.edu.itba.paw.service.HashtagService;
//import ar.edu.itba.paw.service.TwattService;
//import ar.edu.itba.paw.service.implementations.HashtagServiceImpl;
//import ar.edu.itba.paw.service.implementations.TwattServiceImpl;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import java.io.IOException;
//import java.util.List;
//
///**
// * Created with IntelliJ IDEA.
// * User: facundo
// * Date: 17/09/13
// * Time: 00:50
// * To change this template use File | Settings | File Templates.
// */
//@SuppressWarnings("serial")
//public class ViewHashtag extends HttpServlet {
//
//    private HashtagService hashtagHelper = HashtagServiceImpl.getInstance();
//    private TwattService twattHelper = TwattServiceImpl.getInstance();
//
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
//            throws ServletException, IOException {
//        String[] url = req.getRequestURI().split("/");
//        Hashtag hashtag = this.hashtagHelper.getHashtag(url[url.length-1]);
//
//        if (hashtag == null) {
//            req.setAttribute("error", "No existe ese hashtag");
//        } else {
//            List<Twatt> twatts = this.twattHelper.getTwattsByHashtag(hashtag);
//            req.setAttribute("hashtag", hashtag);
//            req.setAttribute("twatts", twatts);
//        }
//        req.getRequestDispatcher("/WEB-INF/jsp/view.jsp").forward(req, resp);
//    }
//
//}
