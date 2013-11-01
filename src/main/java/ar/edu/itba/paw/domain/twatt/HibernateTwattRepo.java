package ar.edu.itba.paw.domain.twatt;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ar.edu.itba.paw.domain.repository.AbstractHibernateRepo;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.Type;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.datetime.joda.JodaDateTimeFormatAnnotationFormatterFactory;
import org.springframework.format.datetime.joda.JodaTimeContext;
import org.springframework.stereotype.Repository;

import com.google.common.base.Strings;

import ar.edu.itba.paw.domain.hashtag.Hashtag;
import ar.edu.itba.paw.domain.twattuser.TwattUser;
import ar.edu.itba.paw.domain.hashtag.HashtagRepo;
import ar.edu.itba.paw.domain.url.UrlRepo;
import ar.edu.itba.paw.domain.twattuser.UserRepo;
import ar.edu.itba.paw.utils.Report;

@Repository
public class HibernateTwattRepo extends AbstractHibernateRepo<Twatt> implements
		TwattRepo {

	private UserRepo userRepo;
	private HashtagRepo hastagRepo;
	private UrlRepo urlRepo;

	@Autowired
	public HibernateTwattRepo(SessionFactory sessionFactory, UserRepo userRepo,
			HashtagRepo hastagRepo, UrlRepo urlRepo) {
		super(sessionFactory);
		this.userRepo = userRepo;
		this.hastagRepo = hastagRepo;
		this.urlRepo = urlRepo;
	}

	private String shortenUrls(String message) {
		if (Strings.isNullOrEmpty(message)) {
			throw new IllegalArgumentException("Invalid message received");
		}
		Pattern urlPattern = Pattern
				.compile("((https?|ftp|gopher|telnet|file):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)");
		Matcher urlMatcher = urlPattern.matcher(message);
		String newurl;
		while (urlMatcher.find()) {
			String oldurl = urlMatcher.group();
			newurl = urlRepo.shorten(oldurl);
			message = message.replace(oldurl, newurl);
		}
		return message;
	}

	public void addTwatt(Twatt twatt) {
		if (this.isValidTwatt(twatt) && !twatt.isDeleted()) {
			twatt.setMessage(shortenUrls(twatt.getMessage()));
			save(twatt);
			hastagRepo.resolveHashtags(twatt);
		} else {
			throw new IllegalArgumentException("Invalid twatt");
		}
	}

	public List<Twatt> getTwattsByUsername(String username) {
		if (Strings.isNullOrEmpty(username)) {
			throw new IllegalArgumentException("Invalid username");
		}
		TwattUser user = userRepo.getUserByUsername(username);
		if (user != null) {
			return user.getTwatts();

		}
		return new LinkedList<Twatt>();
	}

	public boolean isValidTwatt(Twatt twatt) {
		return twatt != null && twatt.getCreator() != null
				&& twatt.getCreator().isValidUser()
				&& !Strings.isNullOrEmpty(twatt.getMessage())
				&& twatt.getTimestamp() != null;
	}

	public List<Twatt> getTwattsByHashtag(Hashtag hashtag) {
		if (!hashtag.isValid()) {
			throw new IllegalArgumentException("Invalid hashtag");
		}
		return hashtag.getTwatts();
	}

	public Twatt getTwatt(int twatt_id) {
		if (twatt_id <= 0) {
			throw new IllegalArgumentException("Invalid id");
		}
		return get(Twatt.class, twatt_id);
	}

	public void delete(Twatt twatt) {
		twatt.setDeleted();
		save(twatt);
	}

	@Override
	public List<Twatt> getTwattsByFollowings(TwattUser user) {
		Session session = super.getSession();
		String hql = "from Twatt where creator IN (:ids)";
		Query query = session.createQuery(hql);
		query.setParameterList("ids", user.getFollowings());
		List<Twatt> list = query.list();
		return list;
	}

	public List<Report> getTwattReportByDate(TwattUser user, DateTime startDate, DateTime endDate,
			String days) {
		Session session = getSession();
		String sql;
		if (days.compareTo("day") == 0) {
			sql = "select count(*),day(timestamp), month(timestamp), year(timestamp) from Twatt where timestamp >= ? and timestamp<= ? group by day(timestamp), month(timestamp),year(timestamp)";
		} else if (days.compareTo("month") == 0) {
			sql = "select count(*),month(timestamp), year(timestamp) from Twatt where timestamp >= ? and timestamp<= ? group by month(timestamp),year(timestamp)";
		} else if (days.compareTo("year") == 0) {
			sql = "select count(*),year(timestamp) from Twatt where timestamp >= ? and timestamp<= ? group by year(timestamp)";
		} else {
			throw new RuntimeException("Day not correct");
		}
		Query query = session.createQuery(sql);
		query.setParameter(0, startDate);
		query.setParameter(1, endDate);
		List<Object[]> list = query.list();
		List<Report> report = new LinkedList<Report>();
		for (Object[] elem : list) {
			report.add(new Report(elem, days));
		}
		return report;
	}

//	public List<Report> getTwattReportByDate(TwattUser user,
//			DateTime startDate, DateTime endDate, String days) {
//		test(user);
//		if (!(days.compareTo("day") == 0) && !(days.compareTo("month") == 0)
//				&& !(days.compareTo("year") == 0)) {
//			throw new RuntimeException();
//		}
//		Session session = super.getSession();
//		Criteria criteria = session.createCriteria(Twatt.class);
//		criteria.add(Restrictions.eq("creator", user));
//		if (startDate != null) {
//			criteria.add(Restrictions.ge("timestamp", startDate));
//		}
//		if (endDate != null) {
//			criteria.add(Restrictions.le("timestamp", endDate));
//		}
//		criteria.setProjection(Projections.projectionList().add(
//				Projections.sqlGroupProjection("date_trunc('" + days
//						+ "', timestamp) as date, count(*) as count",
//						"date_trunc('" + days + "', timestamp)", new String[] {
//								"date", "count" },
//						(org.hibernate.type.Type[]) new Type[] {
//								Hibernate.TIMESTAMP, Hibernate.LONG })));
//		List<Object[]> result = criteria.list();
//		List<Report> report = new LinkedList<Report>();
//		for (Object[] elem : result) {
//			report.add(new Report(elem));
//		}
//
//		return report;
//	}
}
