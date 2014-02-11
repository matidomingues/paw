package ar.edu.itba.paw.web.pages.login;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ar.edu.itba.paw.domain.twattuser.TwattUser;
import ar.edu.itba.paw.domain.twattuser.UserRepo;
import ar.edu.itba.paw.web.pages.base.BasePage;

public class PasswordRetrivalQuestion extends BasePage{
	
	@SpringBean
	UserRepo userRepo;
	
	private transient String secretAnswer;
	private transient String password;
	private transient String extraPassword;
	
	public PasswordRetrivalQuestion(final IModel<TwattUser> userModel){
		add(new FeedbackPanel("feedback"));
		Form<PasswordRetrivalQuestion> form = new Form<PasswordRetrivalQuestion>("retrivalQuestionForm", new CompoundPropertyModel<PasswordRetrivalQuestion>(this)) {
			@Override
			protected void onSubmit() {
				if(password.compareTo(extraPassword) != 0){
					error(getString("notSamePassword"));
				}else if(secretAnswer.compareTo(userModel.getObject().getSecretAnswer()) != 0){
					error(getString("incorrectAnswer"));
				}else{
					userModel.getObject().setPassword(password);
					userRepo.forceUpdate(userModel.getObject());
					setResponsePage(new LoginPage());
				}
				
			}
		};
		form.add(new Label("username", userModel.getObject().getUsername()));
		form.add(new Label("secretQuestion", userModel.getObject().getSecretQuestion()));
		
		form.add(new TextField<String>("secretAnswer").setRequired(true));
		form.add(new PasswordTextField("password"));
		form.add(new PasswordTextField("extraPassword"));
		form.add(new Button("recover", new ResourceModel("recover")));
		add(form);
	}
}
