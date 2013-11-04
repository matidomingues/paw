package ar.edu.itba.paw.domain.twatt;

import ar.edu.itba.paw.domain.hashtag.Hashtag;
import ar.edu.itba.paw.domain.hashtag.HashtagRepo;
import ar.edu.itba.paw.domain.notification.MentionNotification;
import ar.edu.itba.paw.domain.notification.Notification;
import ar.edu.itba.paw.domain.notification.NotificationRepo;
import ar.edu.itba.paw.domain.notification.RetwattNotification;
import ar.edu.itba.paw.domain.repository.AbstractHibernateRepo;
import ar.edu.itba.paw.domain.twattuser.TwattUser;
import ar.edu.itba.paw.domain.twattuser.UserRepo;
import ar.edu.itba.paw.domain.url.UrlRepo;
import ar.edu.itba.paw.helper.MessageHelper;
import ar.edu.itba.paw.utils.Report;
import com.google.common.base.Strings;
import org.hibernate.Query;
import org.hibernate.classic.Session;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Repository
public class HibernateTwattRepo extends AbstractHibernateRepo<Twatt> implements
		TwattRepo {

	@Autowired
    private UserRepo userRepo;

    @Autowired
    private HashtagRepo hastagRepo;

    @Autowired
    private UrlRepo urlRepo;

    @Autowired
    private NotificationRepo notificationRepo;

    @Autowired
    private MessageHelper messageHelper;
	
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

	public void create(Twatt twatt) {
		if (this.isValidTwatt(twatt) && !twatt.isDeleted()) {
			twatt.setMessage(shortenUrls(twatt.getMessage()));
            save(twatt);
            hastagRepo.resolveHashtags(twatt);
            List<TwattUser> mentions = this.messageHelper.getMentions(twatt.getMessage());
            for(TwattUser mentioned : mentions) {
                Notification notification = new MentionNotification(mentioned, twatt);
                this.notificationRepo.save(notification);
                mentioned.notify(this.notificationRepo.find(notification));
            }
        } else {
            throw new IllegalArgumentException("Invalid retwatt");
        }
	}

    public void create(Retwatt retwatt) {
        this.create((Twatt) retwatt);
        Notification notification = new RetwattNotification(retwatt.getCreator(), retwatt);
        this.notificationRepo.save(notification);
        notification = this.notificationRepo.find(notification);
        retwatt.getCreator().notify(notification);
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
			sql = "select count(*),day(timestamp), month(timestamp), year(timestamp) from Twatt where timestamp >= ? and timestamp<= ? and creator = ? group by day(timestamp), month(timestamp),year(timestamp)";
		} else if (days.compareTo("month") == 0) {
			sql = "select count(*),month(timestamp), year(timestamp) from Twatt where timestamp >= ? and timestamp<= ? and creator = ? group by month(timestamp),year(timestamp)";
		} else if (days.compareTo("year") == 0) {
			sql = "select count(*),year(timestamp) from Twatt where timestamp >= ? and timestamp<= ? and creator = ? group by year(timestamp)";
		} else {
			throw new RuntimeException("Day not correct");
		}
		Query query = session.createQuery(sql);
		query.setParameter(0, startDate);
		query.setParameter(1, endDate);
		query.setParameter(2, user);
		List<Object[]> list = query.list();
		List<Report> report = new LinkedList<Report>();
		for (Object[] elem : list) {
			report.add(new Report(elem, days));
		}
		return report;
	}
}
