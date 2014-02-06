package ar.edu.itba.paw.web.pages.home;

import java.util.LinkedList;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.Radio;
import org.apache.wicket.markup.html.form.RadioGroup;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.joda.time.DateTime;

import ar.edu.itba.paw.domain.hashtag.Hashtag;
import ar.edu.itba.paw.domain.hashtag.HashtagRepo;
import ar.edu.itba.paw.domain.twatt.Twatt;
import ar.edu.itba.paw.domain.twatt.TwattRepo;
import ar.edu.itba.paw.domain.twattuser.TwattUser;
import ar.edu.itba.paw.domain.twattuser.UserRepo;
import ar.edu.itba.paw.web.TwatterSession;
import ar.edu.itba.paw.web.commons.HashtagLink;
import ar.edu.itba.paw.web.pages.base.SecuredPage;
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

	public HomePage(final int hashtagRange){		
//		final TwattUser currentUser = userRepo.getUserByUsername(TwatterSession.get().getUsername());		
//		final IModel<List<Hashtag>> hashtagsModel = new LoadableDetachableModel<List<Hashtag>>() {
//			private static final long serialVersionUID = -6268538965573752066L;
//
//			@Override
//			protected List<Hashtag> load() {
//				return hastags.getTrendingsHashtagsAfter(new DateTime().minusDays(hashtagRange));
//			}
//		};
//		final IModel<List<Twatt>> myTwattsModel = new LoadableDetachableModel<List<Twatt>>() {
//			private static final long serialVersionUID = -8404018619785924904L;
//
//			@Override
//			protected List<Twatt> load() {
//				return currentUser.getTwatts();
//			}			
//		};
//		final IModel<List<Twatt>> followingsTwattsModel = new LoadableDetachableModel<List<Twatt>>() {
//			private static final long serialVersionUID = -2031702187344624651L;
//
//			@Override
//			protected List<Twatt> load() {
//				List<Twatt> twatts = new LinkedList<Twatt>();
//				for (TwattUser following : currentUser.getFollowings()) {
//					twatts.addAll(following.getTwatts());
//				}
//				return twatts;
//			}
//			
//		};
//		final IModel<List<TwattUser>> userModel = new LoadableDetachableModel<List<TwattUser>>() {
//			private static final long serialVersionUID = -8404018619785924904L;
//			
//			@Override
//			protected List<TwattUser> load() {
//				return userRepo.getRecomendations(currentUser);
//			}			
//		};
//		ListView<Hashtag> hashtagListView = new PropertyListView<Hashtag>("hashtag", hashtagsModel) {
//			private static final long serialVersionUID = -2444058066953405295L;
//
//			@Override
//			protected void populateItem(ListItem<Hashtag> item) {
//				item.add(new HashtagLink("tagname", item.getModel()));
//				item.add(new Label("count", String.valueOf(item.getModelObject().getTwatts().size())));
//			}
//		};
//		ListView<Twatt> myTwattListView = new PropertyListView<Twatt>("myTwatts", myTwattsModel) {
//			private static final long serialVersionUID = 5406848800779154916L;
//
//			@Override
//			protected void populateItem(ListItem<Twatt> item) {
//				item.add(new TwattPanel("myTwatt", item.getModelObject(), currentUser));				
//			}
//		};
//		ListView<Twatt> followingTwattListView = new PropertyListView<Twatt>("followingTwatts", followingsTwattsModel) {
//			private static final long serialVersionUID = -50950665579363624L;
//
//			@Override
//			protected void populateItem(ListItem<Twatt> item) {
//				item.add(new TwattPanel("followingTwatt", item.getModelObject(), currentUser));				
//			}
//		};
//		ListView<TwattUser> recommendationsView = new PropertyListView<TwattUser> ("candidates", userModel) {
//			private static final long serialVersionUID = 3701198213953203844L;
//
//			@Override
//			protected void populateItem(ListItem<TwattUser> item) {
//				item.add(new Label("candidateName", item.getModelObject().getUsername()));
//				item.add(new Link<TwattUser>("candidateFollow", item.getModel()){
//					@Override
//					public void onClick() {
//						currentUser.addFollowing(getModelObject());
//					}
//				});
//			}			
//		};
//		final RadioGroup<Integer> rangeGroup = new RadioGroup<Integer>("rangeGroup");
//		rangeGroup.setRequired(true);
//		Form<?> hashtagRangeForm = new Form("hashtagRangeForm") {
//			/**
//			 * 
//			 */
//			private static final long serialVersionUID = -88577752173547670L;
//
//			@Override
//			protected void onSubmit() {
//				setResponsePage(new HomePage(rangeGroup.getConvertedInput()));
//			}
//		};
//		rangeGroup.add(new Radio<Integer>("day", new Model<Integer>(1)));
//		rangeGroup.add(new Radio<Integer>("week", new Model<Integer>(7)));
//		rangeGroup.add(new Radio<Integer>("month", new Model<Integer>(30)));
//		hashtagRangeForm.add(rangeGroup);
//		hashtagRangeForm.add(new Button("rangeSubmit"));
//		
//		add(hashtagListView);
//		add(myTwattListView);
//		add(followingTwattListView);		
//		add(hashtagRangeForm);
	}
}
