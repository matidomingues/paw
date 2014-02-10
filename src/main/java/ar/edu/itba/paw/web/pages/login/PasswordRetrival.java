package ar.edu.itba.paw.web.pages.login;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.google.common.base.Strings;

import ar.edu.itba.paw.domain.entity.EntityModel;
import ar.edu.itba.paw.domain.twattuser.TwattUser;
import ar.edu.itba.paw.domain.twattuser.UserRepo;
import ar.edu.itba.paw.utils.exceptions.InvalidUserException;
import ar.edu.itba.paw.web.pages.base.BasePage;

public class PasswordRetrival extends BasePage{
	
	@SpringBean
	UserRepo userRepo;
	
	private transient String username;
	
	public PasswordRetrival(){
		add(new FeedbackPanel("feedback"));
		Form<PasswordRetrival> form = new Form<PasswordRetrival>("retrivalForm", new CompoundPropertyModel<PasswordRetrival>(this)) {
			@Override
			protected void onSubmit() {
				if (Strings.isNullOrEmpty(username)){
					error(getString("incorrectUsername"));;
				}else{
					TwattUser user = userRepo.getUserByUsername(username);
					if(user == null ){
						error(getString("incorrectUser"));
					}else{
						setResponsePage(new PasswordRetrivalQuestion(getUser(user)));
					}
				}
				
				
			}
		};
		form.add(new TextField<String>("username").setRequired(true));
		form.add(new Button("recover", new ResourceModel("recover")));
		add(form);
	}
	
	private IModel<TwattUser> getUser(TwattUser user) {
		return new EntityModel<TwattUser>(TwattUser.class, user);
	}
}
