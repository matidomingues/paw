package ar.edu.itba.paw.web.panels.twatt;

import java.util.Iterator;
import java.util.List;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.RefreshingView;
import org.apache.wicket.model.IModel;

import ar.edu.itba.paw.domain.twatt.Twatt;
import ar.edu.itba.paw.domain.twattuser.TwattUser;

public class TwattListPanel extends Panel {
	
	private static final long serialVersionUID = 1962914823340243457L;

	@SuppressWarnings("serial")
	public TwattListPanel(String id, final List<IModel<Twatt>> twattModels, final IModel<TwattUser> viewerModel) {
		super(id);
		add(new RefreshingView<Twatt>("twatts") {

			@Override
			protected Iterator<IModel<Twatt>> getItemModels() {
				return twattModels.iterator();
			}

			@Override
			protected void populateItem(Item<Twatt> item) {
				item.add(new TwattPanel("twatt", item.getModel(), viewerModel));				
			}
		});
	}

}
