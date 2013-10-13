//package ar.edu.itba.paw.web;
//
//import ar.edu.itba.paw.model.User;
//import ar.edu.itba.paw.service.UserService;
//import ar.edu.itba.paw.service.implementations.UserServiceImpl;
//import ar.edu.itba.paw.utils.exceptions.IllegalImageIDException;
//
//import com.google.common.base.Strings;
//
//import net.sf.jmimemagic.Magic;
//import net.sf.jmimemagic.MagicException;
//import net.sf.jmimemagic.MagicMatchNotFoundException;
//import net.sf.jmimemagic.MagicParseException;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//
//public class GetImage extends HttpServlet {
//
//    private UserService userHelper = UserServiceImpl.getInstance();
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String sUser_id = req.getParameter("id");
//        if (Strings.isNullOrEmpty(sUser_id)) {
//            throw new IllegalImageIDException();
//        }
//        int user_id = Integer.parseInt(sUser_id);
//        User user = this.userHelper.find(user_id);
//        byte [] photo = null;
//        if ((photo = user.getPhoto()) == null || photo.length == 0) {
//            Path path = Paths.get(getServletContext().getRealPath("/img/default_user_icon.png"));
//            photo = Files.readAllBytes(path);
//        }
//        String contentType = null;
//        try {
//            contentType = Magic.getMagicMatch(photo).getMimeType();
//        } catch (MagicParseException e) {
//            throw new ServletException(e.getMessage(), e);
//        } catch (MagicMatchNotFoundException e) {
//            throw new ServletException(e.getMessage(), e);
//        } catch (MagicException e) {
//            throw new ServletException(e.getMessage(), e);
//        }
//        resp.setContentType(contentType);
//        resp.getOutputStream().write(photo);
//    }
//
//}
