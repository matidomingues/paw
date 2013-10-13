//package ar.edu.itba.paw.web;
//
//import ar.edu.itba.paw.model.Hashtag;
//import ar.edu.itba.paw.service.HashtagService;
//import ar.edu.itba.paw.service.implementations.HashtagServiceImpl;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import java.io.IOException;
//
//@SuppressWarnings("serial")
//public class HashtagDetail extends HttpServlet {
//
//    private HashtagService hashtagHelper  = HashtagServiceImpl.getInstance();
//
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String[] uri = request.getRequestURI().split("/");
//        Hashtag hashtag = hashtagHelper.getHashtag(uri[uri.length-1]);
//        if (hashtag == null) {
//            request.setAttribute("error", "No existe este hashtag");
//        } else {
//            request.setAttribute("hastag", hashtag);
//        }
//        request.getRequestDispatcher("").forward(request, response);
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//
//    }
//}
