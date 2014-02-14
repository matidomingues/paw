package ar.edu.itba.paw.web.panels.twatt;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.RefreshingView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.StringResourceModel;

import ar.edu.itba.paw.domain.entity.EntityModel;
import ar.edu.itba.paw.domain.twatt.Twatt;
import ar.edu.itba.paw.domain.twattuser.TwattUser;

public class TwattListPanel extends Panel {
	
	private static final long serialVersionUID = 1962914823340243457L;
	
	public TwattListPanel(String id, List<IModel<Twatt>> twattModels, IModel<TwattUser> viewerModel) {
		this(id, twattModels, viewerModel, new StringResourceModel("defaultNoTwattsMessage", null, (Object[])null));
	}

	@SuppressWarnings("serial")
	public TwattListPanel(String id, final List<IModel<Twatt>> twattModels, final IModel<TwattUser> viewerModel, StringResourceModel noTwattsMessage) {
		super(id);
		cleanTwatts(twattModels);
		add(new WebMarkupContainer("noTwattsMessageContainer")
			.add(new Label("noTwattsMessage", noTwattsMessage)).setVisible(twattModels.isEmpty()));
		add(new RefreshingView<Twatt>("twatt") {

			@Override
			protected Iterator<IModel<Twatt>> getItemModels() {
				return twattModels.iterator();
			}

			@Override
			protected void populateItem(Item<Twatt> item) {
				item.add(new TwattPanel("twattPanel", item.getModel(), viewerModel));				
			}
		});
	}
	
	private void cleanTwatts(List<IModel<Twatt>> twattModels) {
		Iterator<IModel<Twatt>> twattModelsIt = twattModels.iterator();
		while (twattModelsIt.hasNext()) {
			if (twattModelsIt.next().getObject().isDeleted()) {
				twattModelsIt.remove();
			}
		}
		
	}

	public static List<IModel<Twatt>> convertList(List<Twatt> twatts) {
		List<IModel<Twatt>> list = new LinkedList<IModel<Twatt>>();
		for (Twatt twatt : twatts) {
			list.add(new EntityModel<Twatt>(Twatt.class, twatt));
		}
		return list;
	}

}
