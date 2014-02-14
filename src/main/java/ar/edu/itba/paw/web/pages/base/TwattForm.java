package ar.edu.itba.paw.web.pages.base;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ar.edu.itba.paw.domain.twatt.Twatt;
import ar.edu.itba.paw.domain.twatt.TwattRepo;
import ar.edu.itba.paw.domain.twattuser.TwattUser;
import ar.edu.itba.paw.web.pages.user.ProfilePage;

@SuppressWarnings("serial") 
public class TwattForm extends Form<Void> {
	
	@SpringBean private TwattRepo twattRepo;
	private IModel<TwattUser> commiterModel;
	private String message;
	private TextField<String> messageField;

	public TwattForm(String id, IModel<TwattUser> commiterModel) {
		super(id);
		this.commiterModel = commiterModel;
		this.messageField = new TextField<String>("message", new PropertyModel<String>(this, "message"));
		this.messageField.setRequired(true);
		add(messageField);
		add(new Button("twatt", new ResourceModel("Twatt!")));
	}
	
	@Override
	protected void onSubmit() {
		twattRepo.create(new Twatt(message, commiterModel.getObject()));
		commiterModel.detach();
		setResponsePage(new ProfilePage(commiterModel));
	}
	
}