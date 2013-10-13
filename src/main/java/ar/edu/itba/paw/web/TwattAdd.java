//package ar.edu.itba.paw.web;
//
//import ar.edu.itba.paw.model.Twatt;
//import ar.edu.itba.paw.model.User;
//import ar.edu.itba.paw.service.TwattService;
//import ar.edu.itba.paw.service.UserService;
//import ar.edu.itba.paw.service.implementations.TwattServiceImpl;
//import ar.edu.itba.paw.service.implementations.UserServiceImpl;
//import ar.edu.itba.paw.utils.exceptions.MessageEmptyException;
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
//
//@SuppressWarnings("serial")
//public class TwattAdd extends HttpServlet {
//
//	private TwattService twattmanager = TwattServiceImpl.getInstance();
//    private UserService userHelper = UserServiceImpl.getInstance();
//
//	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//
//        String message = req.getParameter("message");
//        if (Strings.isNullOrEmpty(message)) {
//            throw new MessageEmptyException();
//        }
//
//        int user_id = (Integer) req.getSession().getAttribute("user_id");
//        User user = this.userHelper.find(user_id);
//        twattmanager.addTwatt(new Twatt(user, message, false, DateTime.now()));
//        boolean result = true;
//        if (!result) {
//			req.setAttribute("error", "No es posible Twattear en este momento");
//		} else {
//			req.setAttribute("success", "Se Twatteo correctamente");
//		}
//		req.getRequestDispatcher("/user/"+user.getUsername()).forward(req, resp);
//	}
//}
