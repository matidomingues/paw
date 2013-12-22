package ar.edu.itba.paw.utils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public enum SiteMap {
    LOGIN("/user/login"),
    LOGOUT("/user/logout"),
    REGISTER("/user/register"),
    HOME("/home"),
    TWATT_ADD("/twatt"),
    USER_EDIT("/user/settings"),
    USER_DETAIL("/profile/"),
    USER_SEARCH("/find"),
    RESTORE("/restore"),
    RESTOREQ("/restore/"),
    SOCIAL("/social/"),
    FAVOURITE("/favourite"),
    UNFAVOURITE("/unfavourite"),
    FAVOURITES("/favourites"),
    RETWATT("/retwatt"),
    FOLLOW("/follow/"),
    UNFOLLOW("/unfollow/"),
    IMAGE("/image/"),
    REPORT("/user/report"),
    ;

    public static final String PRE_ADDRESS = "/bin";
    private static List<SiteMap> authenticated;
    private static List<SiteMap> unauthenticated;
    private static String[] staticFileExtensions;

    static {
        authenticated = new ArrayList<SiteMap>();
        authenticated.add(HOME);
        authenticated.add(TWATT_ADD);
        authenticated.add(USER_EDIT);
        authenticated.add(USER_DETAIL);
        authenticated.add(USER_SEARCH);
        authenticated.add(LOGOUT);
        authenticated.add(SOCIAL);
        authenticated.add(IMAGE);
        authenticated.add(FAVOURITE);
        authenticated.add(UNFAVOURITE);
        authenticated.add(FAVOURITES);
        authenticated.add(RETWATT);
        authenticated.add(FOLLOW);
        authenticated.add(UNFOLLOW);
        authenticated.add(REPORT);

        unauthenticated = new ArrayList<SiteMap>();
        unauthenticated.add(HOME);
        unauthenticated.add(LOGIN);
        unauthenticated.add(REGISTER);
        unauthenticated.add(USER_DETAIL);
        unauthenticated.add(USER_SEARCH);
        unauthenticated.add(RESTORE);
        unauthenticated.add(RESTOREQ);
        unauthenticated.add(IMAGE);


        staticFileExtensions = new String[] {
                "css",
                "js",
                "img",
                "jpg",
                "jpeg",
                "png",
                "gif"
        };
    }

    public static boolean isStatic(String uri) {
        for(String extension : staticFileExtensions) {
            if (uri.endsWith(extension)) {
                return true;
            }
        }
        return false;
    }

    public static boolean needsAuthentication(String uri) {
        for(SiteMap sm : authenticated) {
            if (uri.startsWith(sm.getAddress())) {
                return true;
            }
        }
        return false;
    }

    public static boolean needsAnonimity(String uri) {
        for(SiteMap sm : unauthenticated) {
            if (uri.startsWith(sm.getAddress())) {
                return true;
            }
        }
        return false;
    }

    private String address;

    private SiteMap(String address) {
        this.address = address;
    }

    public String getAddress(){
        return PRE_ADDRESS + address;
    }
}
