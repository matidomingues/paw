package ar.edu.itba.paw.web.pages.login;

import org.apache.wicket.extensions.markup.html.captcha.CaptchaImageResource;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.lang.Bytes;

import ar.edu.itba.paw.domain.twattuser.TwattUser;
import ar.edu.itba.paw.domain.twattuser.UserRepo;
import ar.edu.itba.paw.utils.exceptions.DuplicatedUserException;
import ar.edu.itba.paw.web.TwatterSession;
import ar.edu.itba.paw.web.pages.base.BasePage;
import ar.edu.itba.paw.web.panels.user.UserPanel;

public class RegisterPage extends BasePage {

	private static final long serialVersionUID = -2908940886199046934L;

	@SpringBean
	private UserRepo userRepo;

	private  String username;
	private  String name;
	private  String surname;
	private  String password;
	private  String extrapassword;
	private  String description;
	private  String secretquestion;
	private  String secretanswer;
	private  FileUploadField photo;
	private  String captchaPassword;

	private final CaptchaImageResource captchaImageResource;
	private final String imagePass = randomString(6, 8);

	@SuppressWarnings({ "serial", "rawtypes", "unchecked" })
	public RegisterPage() {
		add(new FeedbackPanel("feedback"));

		captchaImageResource = new CaptchaImageResource(imagePass);
		Form<RegisterPage> form = new Form<RegisterPage>("registerForm",
				new CompoundPropertyModel<RegisterPage>(this)) {
			@Override
			protected void onSubmit() {
				if (password.compareTo(extrapassword) != 0) {
					error(getString("nonEqualPassword"));
				} else if (captchaPassword.compareTo(imagePass) != 0) {
					error(getString("captchaNotEqual"));
				} else {
					TwatterSession session = TwatterSession.get();
					byte[] img = (photo == null ? new byte[0] : 
						(photo.getFileUpload() == null ? new byte[0] : photo.getFileUpload().getBytes()));
					TwattUser user = new TwattUser(username, name, surname,
							password, description, secretquestion,
							secretanswer, img);
					try {
						userRepo.registerUser(user);
						if (session.signIn(username, password, userRepo)) {
							if (!continueToOriginalDestination()) {
								setResponsePage(getApplication().getHomePage());
							}
						}
					} catch (DuplicatedUserException e) {
						error(getString("duplicatedUser"));
					}
				}
				captchaImageResource.invalidate();
			}
		};
		form.setMultiPart(true);
		form.setMaxSize(Bytes.kilobytes(10));
		form.add(new TextField<String>("username").setRequired(true));
		form.add(new UserPanel("userPanel"));
		form.add(new TextField<String>("secretquestion").setRequired(true));
		form.add(new TextField<String>("secretanswer").setRequired(true));
		form.add(new PasswordTextField("extrapassword").setRequired(true));
		form.add(new Image("captchaImage", captchaImageResource));
		form.add(photo = new FileUploadField("photo", new Model()));
		form.add(new TextField<String>("captchaPassword").setRequired(true));
		form.add(new Button("register", new ResourceModel("register")));
		add(form);

	}

	private static String randomString(int min, int max) {
		int num = randomInt(min, max);
		byte b[] = new byte[num];
		for (int i = 0; i < num; i++)
			b[i] = (byte) randomInt('a', 'z');
		return new String(b);
	}

	private static int randomInt(int min, int max) {
		return (int) (Math.random() * (max - min) + min);
	}
}
