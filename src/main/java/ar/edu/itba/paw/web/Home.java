//package ar.edu.itba.paw.web;
//
//import ar.edu.itba.paw.model.Hashtag;
//import ar.edu.itba.paw.service.HashtagService;
//import ar.edu.itba.paw.service.UserService;
//import ar.edu.itba.paw.service.implementations.HashtagServiceImpl;
//import ar.edu.itba.paw.service.implementations.UserServiceImpl;
//import ar.edu.itba.paw.utils.HashtagBundle;
//
//import com.google.common.base.Strings;
//
//import org.joda.time.DateTime;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import java.io.IOException;
//import java.util.LinkedList;
//import java.util.List;
//
//@SuppressWarnings("serial")
//public class Home extends HttpServlet{
//	
//	UserService usermanager = UserServiceImpl.getInstance();
//	HashtagService hashtagmanager = HashtagServiceImpl.getInstance();
//
//	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
//        String sDays = req.getParameter("dayfilter");
//        int days = 1;
//        try {
//            if (!Strings.isNullOrEmpty(sDays)) {
//                days = Integer.parseInt(sDays);
//            }
//        } catch (Exception e) {
//
//        }
//        DateTime filterDate = DateTime.now().minusDays(days);
//        List<Hashtag> hashtagList = hashtagmanager.getTrendingsHashtagsAfter(filterDate);
//        List<HashtagBundle> hashtagBundles = new LinkedList<HashtagBundle>();
//        for(Hashtag hashtag : hashtagList) {
//            hashtagBundles.add(new HashtagBundle(hashtag, hashtagmanager.getMentions(hashtag, filterDate)));
//        }
//		req.setAttribute("hashtags", hashtagBundles);
//		req.getRequestDispatcher("/WEB-INF/jsp/home.jsp").forward(req, resp);
//	}
//	
//	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		doGet(req, resp);
//	}
//
//}
