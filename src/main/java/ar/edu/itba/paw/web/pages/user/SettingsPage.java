package ar.edu.itba.paw.web.pages.user;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.image.ContextImage;
import org.apache.wicket.markup.html.image.NonCachingImage;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.resource.DynamicImageResource;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.lang.Bytes;

import ar.edu.itba.paw.domain.twattuser.TwattUser;
import ar.edu.itba.paw.domain.twattuser.UserRepo;
import ar.edu.itba.paw.web.pages.base.SecuredPage;
import ar.edu.itba.paw.web.panels.user.UserPanel;

public class SettingsPage extends SecuredPage{

	private static final long serialVersionUID = -5184913454430545840L;

	@SpringBean
	private UserRepo userRepo;

	private transient String name;
	private transient String surname;
	private transient String password;
	private transient String description;
	private transient Boolean privacy;
	private transient FileUploadField photo;
	
	@SuppressWarnings({ "serial", "rawtypes", "unchecked" })
	public SettingsPage(){
		TwattUser user = getViewer().getObject();
		this.name = user.getName();
		this.surname = user.getSurname();
		this.description = user.getDescription();
		this.privacy = user.getPrivacy();
		final Form<SettingsPage> form = new Form<SettingsPage>("settingsForm", new CompoundPropertyModel<SettingsPage>(this));

		form.add(new UserPanel("userPanel"));
		add(new FeedbackPanel("feedback"));
		form.add(photo = new FileUploadField("photo", new Model()));
		
		if (user.getPhoto() == null || user.getPhoto().length == 0) {
			form.add(new ContextImage("preview", "img/default_user_icon.png"));
		} else {
			form.add(new NonCachingImage("preview", new DynamicImageResource() {				
				@Override
				protected byte[] getImageData(Attributes attributes) {
					return getViewer().getObject().getPhoto();
				}
			}));
		}
		
		form.add(new CheckBox("privacy"));
		form.add(new Button("save", new ResourceModel("save")) {
			@Override
			public void onSubmit() {
				IModel<TwattUser> userModel = getViewer();
				if (userModel.getObject().getPassword().equals(password)) {
					userModel.getObject().setName(name);
					userModel.getObject().setSurname(surname);
					userModel.getObject().setDescription(description);
					userModel.getObject().setPhoto((photo == null ? new byte[0] : 
							(photo.getFileUpload() == null ? new byte[0] : photo.getFileUpload().getBytes())));
					userModel.getObject().setPrivacy(privacy);
					setResponsePage(new ProfilePage(getViewer()));
				} else {
					error(new StringResourceModel("passwordMismatch", SettingsPage.this, null).getString());
				}
			}
		});
		form.setMultiPart(true);
		form.setMaxSize(Bytes.megabytes(10));
		add(form);
	}
}
