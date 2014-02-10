package ar.edu.itba.paw.web.pages.login;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
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

	@SpringBean
	private UserRepo userRepo;

	private transient String username;
	private transient String name;
	private transient String surname;
	private transient String password;
	private transient String extrapassword;
	private transient String description;
	private transient String secretquestion;
	private transient String secretanswer;
	private transient FileUploadField photo;

	public RegisterPage() {
		add(new FeedbackPanel("feedback"));

		Form<RegisterPage> form = new Form<RegisterPage>("registerForm",
				new CompoundPropertyModel<RegisterPage>(this)) {
			@Override
			protected void onSubmit() {
				if(password.compareTo(extrapassword) != 0){
					error(getString("nonEqualPassword"));
				}
				TwatterSession session = TwatterSession.get();
				byte[] img = (photo == null) ? new byte[0]
						: photo.getFileUpload().getBytes();
				TwattUser user = new TwattUser(username, name, surname,
						password, description, secretquestion, secretanswer,
						img);
				try {
					userRepo.registerUser(user);
				} catch (DuplicatedUserException e) {
					error(getString("duplicatedUser"));
				}
				if(session.signIn(username, password, userRepo)){
					if (!continueToOriginalDestination()) {
						setResponsePage(getApplication().getHomePage());
					}
				}
			}
		};
		form.setMultiPart(true);
		form.setMaxSize(Bytes.kilobytes(10));
		form.add(new TextField<String>("username").setRequired(true));
		form.add(new UserPanel("userPanel"));
		form.add(new TextField<String>("secretquestion").setRequired(true));
		form.add(new TextField<String>("secretanswer").setRequired(true));
		form.add(new PasswordTextField("extrapassword").setRequired(true));
		
		form.add(new Button("register", new ResourceModel("register")));
		add(form);
	}
}
