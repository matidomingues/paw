package ar.edu.itba.paw.web.pages.home;

import java.util.LinkedList;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.Radio;
import org.apache.wicket.markup.html.form.RadioGroup;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.joda.time.DateTime;

import ar.edu.itba.paw.domain.hashtag.Hashtag;
import ar.edu.itba.paw.domain.hashtag.HashtagRepo;
import ar.edu.itba.paw.domain.twatt.Twatt;
import ar.edu.itba.paw.domain.twatt.TwattRepo;
import ar.edu.itba.paw.domain.twattuser.TwattUser;
import ar.edu.itba.paw.domain.twattuser.UserRepo;
import ar.edu.itba.paw.web.pages.base.SecuredPage;
import ar.edu.itba.paw.web.pages.hashtag.HashtagPage;
import ar.edu.itba.paw.web.panels.twatt.TwattPanel;

public class HomePage extends SecuredPage{
	
	private static final long serialVersionUID = 7859944391145830157L;
	private static final int DEFAULT_HASHTAG_RANGE = 1;
	@SpringBean	private HashtagRepo hastags;
	@SpringBean private UserRepo userRepo;
	@SpringBean private TwattRepo twattRepo;
	
	public HomePage() {
		this(HomePage.DEFAULT_HASHTAG_RANGE);
	}

	@SuppressWarnings("serial")
	public HomePage(final int hashtagRange){		
		final IModel<TwattUser> viewerModel = getViewer();		
		final IModel<List<Hashtag>> hashtagsModel = new LoadableDetachableModel<List<Hashtag>>() {
			@Override
			protected List<Hashtag> load() {
				return hastags.getTrendingsHashtagsAfter(new DateTime().minusDays(hashtagRange));
			}
		};
		final IModel<List<Twatt>> myTwattsModel = new LoadableDetachableModel<List<Twatt>>() {
			@Override
			protected List<Twatt> load() {
				return viewerModel.getObject().getTwatts();
			}			
		};
		final IModel<List<Twatt>> followingsTwattsModel = new LoadableDetachableModel<List<Twatt>>() {
			@Override
			protected List<Twatt> load() {
				List<Twatt> twatts = new LinkedList<Twatt>();
				for (TwattUser following : viewerModel.getObject().getFollowings()) {
					twatts.addAll(following.getTwatts());
				}
				return twatts;
			}
			
		};
		final IModel<List<TwattUser>> userModel = new LoadableDetachableModel<List<TwattUser>>() {
			@Override
			protected List<TwattUser> load() {
				return userRepo.getRecomendations(viewerModel.getObject());
			}			
		};
		ListView<Hashtag> hashtagListView = new PropertyListView<Hashtag>("hashtag", hashtagsModel) {
			@Override
			protected void populateItem(ListItem<Hashtag> item) {
				item.add(new BookmarkablePageLink<Hashtag>("link", HashtagPage.class, 
						new PageParameters().add("hashtag", item.getModelObject().getTagName()))
						.add(new Label("tagname", item.getModelObject().getTagName())));
				item.add(new Label("count", String.valueOf(item.getModelObject().getTwatts().size())));
			}
		};
		ListView<Twatt> myTwattListView = new PropertyListView<Twatt>("myTwatts", myTwattsModel) {
			@Override
			protected void populateItem(ListItem<Twatt> item) {
				item.add(new TwattPanel("myTwatt", item.getModel(), getViewer()));				
			}
		};
		ListView<Twatt> followingTwattListView = new PropertyListView<Twatt>("followingTwatts", followingsTwattsModel) {
			@Override
			protected void populateItem(ListItem<Twatt> item) {
				item.add(new TwattPanel("followingTwatt", item.getModel(), getViewer()));				
			}
		};
		ListView<TwattUser> recommendationsView = new PropertyListView<TwattUser> ("candidates", userModel) {
			@Override
			protected void populateItem(ListItem<TwattUser> item) {
				item.add(new Label("candidateName", item.getModelObject().getUsername()));
				item.add(new Link<TwattUser>("candidateFollow", item.getModel()){
					@Override
					public void onClick() {
						viewerModel.getObject().addFollowing(getModelObject());
					}
				});
			}			
		};
		final RadioGroup<Integer> rangeGroup = new RadioGroup<Integer>("rangeGroup");
		rangeGroup.setRequired(true);
		Form<?> hashtagRangeForm = new Form<Void>("hashtagRangeForm") {
			@Override
			protected void onSubmit() {
				setResponsePage(new HomePage(rangeGroup.getConvertedInput()));
			}
		};
		rangeGroup.add(new Radio<Integer>("day", new Model<Integer>(1)));
		rangeGroup.add(new Radio<Integer>("week", new Model<Integer>(7)));
		rangeGroup.add(new Radio<Integer>("month", new Model<Integer>(30)));
		hashtagRangeForm.add(rangeGroup);
		hashtagRangeForm.add(new Button("rangeSubmit"));
		
		add(hashtagListView);
		add(myTwattListView);
		add(followingTwattListView);		
		add(hashtagRangeForm);
		add(recommendationsView);
	}
}
