package ar.edu.itba.paw.web.panels.twatt;

import java.util.Locale;

import net.sf.jmimemagic.Magic;
import net.sf.jmimemagic.MagicException;
import net.sf.jmimemagic.MagicMatchNotFoundException;
import net.sf.jmimemagic.MagicParseException;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.image.NonCachingImage;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.resource.ByteArrayResource;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ar.edu.itba.paw.domain.hashtag.HashtagRepo;
import ar.edu.itba.paw.domain.twatt.Retwatt;
import ar.edu.itba.paw.domain.twatt.Twatt;
import ar.edu.itba.paw.domain.twatt.TwattRepo;
import ar.edu.itba.paw.domain.twattuser.TwattUser;
import ar.edu.itba.paw.domain.twattuser.UserRepo;
import ar.edu.itba.paw.domain.url.UrlRepo;
import ar.edu.itba.paw.helper.MessageHelper;
import ar.edu.itba.paw.web.TwatterApp;

public class TwattPanel extends Panel {
	
	private static final long serialVersionUID = -5650124758240347486L;
	@SpringBean private HashtagRepo hashtagRepo;
	@SpringBean private TwattRepo twattRepo;
	@SpringBean private UserRepo userRepo;
	@SpringBean private UrlRepo urlRepo;
	@SpringBean private MessageHelper messageHelper;
	
	@SuppressWarnings("serial")
	public TwattPanel(String id, final IModel<Twatt> twattModel, final IModel<TwattUser> viewerModel) {
		super(id);
		if (twattModel.getObject().getCreator().getPhoto() == null || twattModel.getObject().getCreator().getPhoto().length == 0) {
			add(new Image("avatar", TwatterApp.DEFAULT_IMAGE));
		}
		else {
			try {
				add(new NonCachingImage("avatar", new ByteArrayResource(
						Magic.getMagicMatch(twattModel.getObject().getCreator().getPhoto()).getMimeType(), twattModel.getObject().getCreator().getPhoto())));
			} catch (MagicParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MagicMatchNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MagicException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		add(new Label("author", twattModel.getObject().getCreator().getName()));
		add(new Label("timestamp", twattModel.getObject().getTimestamp().toString("yyyyMMdd", Locale.getDefault())));
		WebMarkupContainer message = new WebMarkupContainer("message", Model.of(messageHelper.prepareMessage(twattModel.getObject().getMessage())));
		add(message);

		Form<?> actionForm = new Form<Void>("actionForm");
		Button deleteButton = new Button("remove") {

			@Override
            public void onSubmit() {
				twattModel.getObject().setDeleted();
            }
		};
		Button retwattButton = new Button("retwatt") {
			@Override
			public void onSubmit() {
				twattRepo.create(new Retwatt(twattModel.getObject(), viewerModel.getObject()));
			};
		};
		Button favouriteButton = new Button("favourite") {
			@Override
			public void onSubmit() {
				viewerModel.getObject().addFavourite(twattModel.getObject());
			};
		};
		Button unfavouriteButton = new Button("unfavourite") {
			@Override
			public void onSubmit() {
				viewerModel.getObject().removeFavourite(twattModel.getObject());
			};
		};
		actionForm.add(deleteButton);
		actionForm.add(retwattButton);
		actionForm.add(favouriteButton);
		actionForm.add(unfavouriteButton);
		add(actionForm);

		if (twattModel.getObject().getCreator().equals(viewerModel.getObject())) {
			deleteButton.setEnabled(false);
			deleteButton.setVisible(false);
		}
		if (viewerModel.getObject().isFavourite(twattModel.getObject())) {
			favouriteButton.setEnabled(false);
			favouriteButton.setVisible(false);
		}
		if (!viewerModel.getObject().isFavourite(twattModel.getObject())) {
			unfavouriteButton.setEnabled(false);
			unfavouriteButton.setVisible(false);
		}

	}

}
