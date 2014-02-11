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
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.list.AbstractItem;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.resource.ByteArrayResource;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ar.edu.itba.paw.domain.hashtag.Hashtag;
import ar.edu.itba.paw.domain.hashtag.HashtagRepo;
import ar.edu.itba.paw.domain.twatt.Retwatt;
import ar.edu.itba.paw.domain.twatt.Twatt;
import ar.edu.itba.paw.domain.twatt.TwattRepo;
import ar.edu.itba.paw.domain.twattuser.TwattUser;
import ar.edu.itba.paw.domain.twattuser.UserRepo;
import ar.edu.itba.paw.domain.url.UrlRepo;
import ar.edu.itba.paw.helper.MessageHelper;
import ar.edu.itba.paw.web.TwatterApp;
import ar.edu.itba.paw.web.pages.hashtag.HashtagPage;
import ar.edu.itba.paw.web.pages.user.ProfilePage;

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
		add(new Label("author", 
				(twattModel.getObject() instanceof Retwatt ? 
						twattModel.getObject().getCreator().getUsername() + " : " 
						+ ((Retwatt)twattModel.getObject()).getOriginalTwatt().getCreator().getUsername() :
							twattModel.getObject().getCreator().getUsername())));
		add(new Label("timestamp", twattModel.getObject().getTimestamp().toString("yyyy-MM-dd", Locale.getDefault())));
		WebMarkupContainer message = new WebMarkupContainer("message");
		RepeatingView messageFragmentRepeater = new RepeatingView("messageFragment");
		message.add(messageFragmentRepeater);

		for(String string : messageHelper.prepareMessage(twattModel.getObject().getMessage())) {
			AbstractItem item = new AbstractItem(messageFragmentRepeater.newChildId());
			messageFragmentRepeater.add(item);
			String trimmed = string.trim();
			Label plain = new Label("plain","");
			BookmarkablePageLink<Hashtag> hashtag = new BookmarkablePageLink<Hashtag>("hashtag", HashtagPage.class);
			BookmarkablePageLink<TwattUser> mention = new BookmarkablePageLink<TwattUser>("mention", ProfilePage.class);
			ExternalLink url = new ExternalLink("url", "http://127.0.0.1");
			item.add(plain).add(hashtag).add(mention).add(url);
			if (trimmed.startsWith("#")) {
				//Hashtag
				hashtag.getPageParameters().add("hashtag", trimmed.substring(1));
				hashtag.add(new Label("hashtagBody", string));
				mention.setEnabled(false).setVisible(false);
				url.setEnabled(false).setVisible(false);
				plain.setEnabled(false).setVisible(false);
			} else if (trimmed.startsWith("@")) {
				//Mention
				mention.getPageParameters().add("user", trimmed.substring(1));
				mention.add(new Label("mentionBody", string));
				hashtag.setEnabled(false).setVisible(false);
				url.setEnabled(false).setVisible(false);
				plain.setEnabled(false).setVisible(false);
			} else if (trimmed.startsWith("/s/")) {
				//external
				url.remove();
				item.add(new ExternalLink("url", urlRepo.resolve(trimmed), string));
				hashtag.setEnabled(false).setVisible(false);
				mention.setEnabled(false).setVisible(false);
				plain.setEnabled(false).setVisible(false);
			} else {
				plain.remove();
				item.add(new Label("plain", string));
				hashtag.setEnabled(false).setVisible(false);
				mention.setEnabled(false).setVisible(false);
				url.setEnabled(false).setVisible(false);
			}
		}
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

		if (!twattModel.getObject().getCreator().equals(viewerModel.getObject())) {
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
