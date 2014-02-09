package ar.edu.itba.paw.web.pages.user;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ar.edu.itba.paw.domain.entity.EntityModel;
import ar.edu.itba.paw.domain.twattuser.TwattUser;
import ar.edu.itba.paw.domain.twattuser.UserRepo;
import ar.edu.itba.paw.web.pages.base.SecuredPage;
import ar.edu.itba.paw.web.panels.user.UserPanel;

public class SettingsPage extends SecuredPage{

	@SpringBean
	private UserRepo userRepo;

	private transient String extrapassword;
	
	public SettingsPage(TwattUser user){
		add(new FeedbackPanel("feedback"));
		Form<TwattUser> form = new Form<TwattUser>("settingsForm", new CompoundPropertyModel<TwattUser>(new EntityModel<TwattUser>(TwattUser.class, user)));
		form.add(new UserPanel("userPanel"));
		form.add(new Button("save", new ResourceModel("save")) {
			@Override
			public void onSubmit() {
				setResponsePage(SettingsPage.class);
			}
		});
		add(form);
	}
}
