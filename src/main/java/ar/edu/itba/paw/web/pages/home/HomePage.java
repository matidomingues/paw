package ar.edu.itba.paw.web.pages.home;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.Radio;
import org.apache.wicket.markup.html.form.RadioGroup;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.RefreshingView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.joda.time.DateTime;

import ar.edu.itba.paw.domain.entity.EntityModel;
import ar.edu.itba.paw.domain.hashtag.Hashtag;
import ar.edu.itba.paw.domain.hashtag.HashtagRepo;
import ar.edu.itba.paw.domain.twatt.Twatt;
import ar.edu.itba.paw.domain.twatt.TwattRepo;
import ar.edu.itba.paw.domain.twattuser.TwattUser;
import ar.edu.itba.paw.domain.twattuser.UserRepo;
import ar.edu.itba.paw.web.pages.base.SecuredPage;
import ar.edu.itba.paw.web.pages.hashtag.HashtagPage;
import ar.edu.itba.paw.web.panels.twatt.TwattListPanel;

public class HomePage extends SecuredPage{
	
	private static final long serialVersionUID = 7859944391145830157L;
	private static final int DEFAULT_HASHTAG_RANGE = 1;
	@SpringBean	private HashtagRepo hastags;
	@SpringBean private UserRepo userRepo;
	@SpringBean private TwattRepo twattRepo;
	
	public HomePage() {
		this(HomePage.DEFAULT_HASHTAG_RANGE);
	}

	public HomePage(final int hashtagRange){		
		IModel<TwattUser> viewerModel = getViewer();		
		add(hashtagList(hashtagRange));
		add(new TwattListPanel("myTwatts", TwattListPanel.convertList(viewerModel.getObject().getTwatts()), viewerModel));
		add(new TwattListPanel("followingTwatts", TwattListPanel.convertList(followingTwatts(viewerModel)), viewerModel));
		add(candidates(viewerModel));
		add(hashtagRangeForm(hashtagRange));
	}

	@SuppressWarnings("serial")
	private Component candidates(final IModel<TwattUser> viewerModel) {
		return new RefreshingView<TwattUser>("candidates") {

			@Override
			protected Iterator<IModel<TwattUser>> getItemModels() {
				List<IModel<TwattUser>> userModels = new LinkedList<IModel<TwattUser>>();
				for (TwattUser user : userRepo.getRecomendations(viewerModel.getObject())) {
					userModels.add(new EntityModel<TwattUser>(TwattUser.class, user));
				}
				return userModels.iterator();
			}

			@Override
			protected void populateItem(Item<TwattUser> item) {
				item.add(new Label("candidateName", item.getModelObject().getUsername()));
				item.add(new Link<TwattUser>("candidateFollow", item.getModel()){
					@Override
					public void onClick() {
						viewerModel.getObject().addFollowing(getModelObject());
					}
				});
			}
			
		};
	}

	private List<Twatt> followingTwatts(IModel<TwattUser> viewerModel) {
		List<Twatt> followingTwatts = new LinkedList<Twatt>();
		for(TwattUser following : viewerModel.getObject().getFollowings()) {
			followingTwatts.addAll(following.getTwatts());
		}
		return followingTwatts;
	}

	@SuppressWarnings("serial")
	private Component hashtagList(final int hashtagRange) {
		return new RefreshingView<Hashtag>("hashtag") {
			@Override
			protected Iterator<IModel<Hashtag>> getItemModels() {
				List<IModel<Hashtag>> hashtagModels = new LinkedList<IModel<Hashtag>>();
				for (Hashtag hashtag : hastags.getTrendingsHashtagsAfter(new DateTime().minusDays(hashtagRange))) {
					hashtagModels.add(new EntityModel<Hashtag>(Hashtag.class, hashtag));
				}
				return hashtagModels.iterator();
			}

			@Override
			protected void populateItem(Item<Hashtag> item) {
				item.add(new BookmarkablePageLink<Hashtag>("link", HashtagPage.class, 
						new PageParameters().add("hashtag", item.getModelObject().getTagName()))
						.add(new Label("tagname", item.getModelObject().getTagName())));
				item.add(new Label("count", String.valueOf(item.getModelObject().getTwatts().size())));
			}			
		};
	}
	@SuppressWarnings("serial")
	private Component hashtagRangeForm(int selectedValue) {
		Form<Integer> hashtagRangeForm = new Form<Integer>("hashtagRangeForm", Model.of(new Integer(selectedValue)));
		final RadioGroup<Integer> rangeGroup = new RadioGroup<Integer>("rangeGroup");
		rangeGroup.setRequired(true);
		rangeGroup.add(new Radio<Integer>("day", new Model<Integer>(1)));
		rangeGroup.add(new Radio<Integer>("week", new Model<Integer>(7)));
		rangeGroup.add(new Radio<Integer>("month", new Model<Integer>(30)));
		hashtagRangeForm.add(rangeGroup);
		hashtagRangeForm.add(new Button("rangeSubmit") {
			@Override
			public void onSubmit() {
				setResponsePage(new HomePage(rangeGroup.getConvertedInput()));
			}
		});
		return hashtagRangeForm;
	}
}
