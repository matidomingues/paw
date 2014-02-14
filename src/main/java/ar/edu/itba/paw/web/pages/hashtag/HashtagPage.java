package ar.edu.itba.paw.web.pages.hashtag;

import java.util.Collections;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ar.edu.itba.paw.domain.entity.EntityModel;
import ar.edu.itba.paw.domain.hashtag.Hashtag;
import ar.edu.itba.paw.domain.hashtag.HashtagRepo;
import ar.edu.itba.paw.web.pages.base.SecuredPage;
import ar.edu.itba.paw.web.panels.twatt.TwattListPanel;

public class HashtagPage extends SecuredPage {
	
	private static final long serialVersionUID = 77803663888322154L;
	@SpringBean private HashtagRepo hashtagRepo;
	
	public HashtagPage(PageParameters parameters) {
		populate(new EntityModel<Hashtag>(Hashtag.class, hashtagRepo.getHashtag(parameters.get("hashtag").toString(""))));
	}

	public HashtagPage(IModel<Hashtag> hashtagModel) {
		populate(hashtagModel);
	}
	
	private void populate(IModel<Hashtag> hashtagModel) {
		add(firstTwatt(hashtagModel));
		add(twattList(hashtagModel));
		add(new Label("tagname", Model.of(hashtagModel.getObject().getTagName())));
	}
	
	private Component firstTwatt(IModel<Hashtag> hashtagModel) {
		WebMarkupContainer firstTwatt = new WebMarkupContainer("firstTwattContainer");
		firstTwatt.add(new Label("deletedFirstTwatt", new StringResourceModel("deletedFirstTwatt", this, null))
							.setVisible(hashtagModel.getObject().getFirstTwatt().isDeleted()));
		firstTwatt.add(new TwattListPanel("firstTwatt", 
				TwattListPanel.convertList(Collections.singletonList(hashtagModel.getObject().getFirstTwatt())), 
				getViewer()).setVisible(!hashtagModel.getObject().getFirstTwatt().isDeleted()));
		return firstTwatt;
	}
	
	private Component twattList(IModel<Hashtag> hashtagModel) {
		WebMarkupContainer twattList = new WebMarkupContainer("twattList");
		twattList.add(new Label("noTwatts", new StringResourceModel("noTwatts", this, null))
							.setVisible(hashtagModel.getObject().getTwatts().isEmpty()));
		twattList.add(new TwattListPanel("twatt", 
					TwattListPanel.convertList(hashtagModel.getObject().getTwatts()), getViewer())
					.setVisible(!hashtagModel.getObject().getTwatts().isEmpty()));
		return twattList;
	}

}
