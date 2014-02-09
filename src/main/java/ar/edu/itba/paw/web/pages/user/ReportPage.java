package ar.edu.itba.paw.web.pages.user;

import java.util.Date;
import java.util.Locale;

import org.apache.wicket.datetime.StyleDateConverter;
import org.apache.wicket.datetime.markup.html.form.DateTextField;
import org.apache.wicket.extensions.markup.html.form.select.Select;
import org.apache.wicket.extensions.markup.html.form.select.SelectOption;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ar.edu.itba.paw.domain.twatt.TwattRepo;
import ar.edu.itba.paw.domain.twattuser.TwattUser;
import ar.edu.itba.paw.utils.Report;
import ar.edu.itba.paw.web.pages.base.SecuredPage;

public class ReportPage extends SecuredPage {

	@SpringBean
	TwattRepo twattRepo;

	private transient Date formStartDate;
	private transient Date formEndDate;
	private transient String formDaysType;

	public ReportPage() {
		this("day", 0L, 0L);
	}

	private ReportPage(String days, Long startDate, Long endDate) {
		Label ajaxString = new Label("ajaxString", "ajaxString = '"
				+ getAjax(days, startDate, endDate) + "';");
		ajaxString.setEscapeModelStrings(false);
		add(ajaxString);
		add(getForm());

	}

	private String getAjax(String days, Long startDate, Long endDate) {

		DateTime startTime, endTime;
		if (startDate.compareTo(0L) == 0) {
			startTime = new DateTime(0);
		} else {
			startTime = new DateTime(startDate);
		}
		if (endDate.compareTo(0L) == 0) {
			endTime = new DateTime();
		} else {
			endTime = new DateTime(endDate);
		}

		JSONArray arr = new JSONArray();
		final IModel<TwattUser> viewerModel = getViewer();

		for (Report report : twattRepo.getTwattReportByDate(
				viewerModel.getObject(), startTime, endTime, days)) {
			try {
				arr.put(new JSONObject().put("label", report.getHeader()).put(
						"y", report.getValue()));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		JSONObject object = new JSONObject();
		try {
			object.put("datagrams", arr);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return object.toString();
	}

	private Form<ReportPage> getForm() {
		Form<ReportPage> form = new Form<ReportPage>("reportForm",
				new CompoundPropertyModel<ReportPage>(this)) {
			@Override
			protected void onSubmit() {
				setResponsePage(new ReportPage(formDaysType,
						formStartDate == null ? 0L : formStartDate.getTime(),
						formEndDate == null ? 0L : formEndDate.getTime()));
			}
		};
		form.add(new DateTextField("formStartDate", new PropertyModel<Date>(
				this, "formStartDate"), new StyleDateConverter("S-", true)) {
			@Override
			public Locale getLocale() {
				return Locale.ENGLISH;
			}
		});
		form.add(new DateTextField("formEndDate", new PropertyModel<Date>(this,
				"formEndDate"), new StyleDateConverter("S-", true)) {
			@Override
			public Locale getLocale() {
				return Locale.ENGLISH;
			}
		});
		Select daysType = new Select("formDaysType", new PropertyModel<String>(this, "formDaysType"));
		form.add(daysType);
		daysType.add(new SelectOption<String>("day", new Model<String>("day")));
		daysType.add(new SelectOption<String>("month", new Model<String>("month")));
		daysType.add(new SelectOption<String>("year", new Model<String>("year")));
		form.add(new Button("results", new ResourceModel("results")));
		return form;
	}
}
