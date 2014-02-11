package ar.edu.itba.paw.web.pages.hashtag;

import java.util.Collections;
import java.util.List;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ar.edu.itba.paw.domain.entity.EntityModel;
import ar.edu.itba.paw.domain.hashtag.Hashtag;
import ar.edu.itba.paw.domain.hashtag.HashtagRepo;
import ar.edu.itba.paw.domain.twatt.Twatt;
import ar.edu.itba.paw.web.pages.base.SecuredPage;
import ar.edu.itba.paw.web.panels.twatt.TwattListPanel;

public class HashtagPage extends SecuredPage {
	
	private static final long serialVersionUID = 77803663888322154L;
	@SpringBean private static HashtagRepo hashtagRepo;
	
	public HashtagPage(PageParameters parameters) {
		this(new EntityModel<Hashtag>(Hashtag.class, hashtagRepo.getHashtag(parameters.get("hashtag").toString(""))));
	}

	public HashtagPage(IModel<Hashtag> hashtagModel) {
		
		if (hashtagModel.getObject().getFirstTwatt().isDeleted()) {
			add(new WebMarkupContainer("firstTwatt")
				.add(new Label("deletedFirstTwatt", new StringResourceModel("deletedFirstTwatt", this, null))));
		} else {
			List<IModel<Twatt>> list = Collections.emptyList();
			list.add(new EntityModel<Twatt>(Twatt.class, hashtagModel.getObject().getFirstTwatt()));
			add(new TwattListPanel("firstTwatt", list, getViewer()));
		}
		
		if (hashtagModel.getObject().getTwatts().isEmpty()) {
			add(new WebMarkupContainer("twattList")
			.add(new Label("noTwatts", new StringResourceModel("noTwatts", this, null))));
		} else {
			List<IModel<Twatt>> list = Collections.emptyList();
			for(Twatt twatt : hashtagModel.getObject().getTwatts()) {
				list.add(new EntityModel<Twatt>(Twatt.class, twatt));
			}
			add(new TwattListPanel("twattList", list, getViewer()));
		}
	}

}
