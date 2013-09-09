package ar.edu.itba.paw.utils;

import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: facundo
 * Date: 07/09/13
 * Time: 15:04
 * To change this template use File | Settings | File Templates.
 */
public class URLManager {

    public enum SiteMap {
        LOGIN("login"),
        LOGOUT("logut"),
        REGISTER("register"),
        HOME("home"),
        TWATT_ADD("twatt"),
        USER_EDIT("settings"),
        USER_DETAIL("user"),
        USER_SEARCH("find");

        private static String PRE_ADDRESS = "";

        private String address;

        private SiteMap(String address) {
            this.address = address;
        }

        public String getAddress(){
            return PRE_ADDRESS + address;
        }
    }

    private List<SiteMap> authenticated;
    private List<SiteMap> unauthenticated;
    private String[] staticFileTypes;

    public URLManager() {

        this.authenticated = new LinkedList<SiteMap>();
        this.authenticated.add(SiteMap.HOME);
        this.authenticated.add(SiteMap.TWATT_ADD);
        this.authenticated.add(SiteMap.USER_EDIT);
        this.authenticated.add(SiteMap.USER_DETAIL);
        this.authenticated.add(SiteMap.USER_SEARCH);
        this.authenticated.add(SiteMap.LOGOUT);

        this.unauthenticated = new LinkedList<SiteMap>();
        this.unauthenticated.add(SiteMap.HOME);
        this.unauthenticated.add(SiteMap.LOGIN);
        this.unauthenticated.add(SiteMap.REGISTER);
        this.unauthenticated.add(SiteMap.USER_DETAIL);
        this.unauthenticated.add(SiteMap.USER_SEARCH);

        this.staticFileTypes = new String[] {
                "css",
                "js",
                "img",
                "jpg",
                "jpeg",
                "png",
                "gif"
        };
    }

    public boolean isStatic(String uri) {
        for(String extension : staticFileTypes) {
            if (uri.endsWith(extension)) {
                return true;
            }
        }
        return false;
    }

    public boolean needsLogin(String uri) {
        for(SiteMap sm : this.authenticated) {
            if (uri.compareToIgnoreCase(sm.getAddress()) == 0) {
                return true;
            }
        }
        return false;
    }

    public boolean needsLogout(String uri) {
        for(SiteMap sm : this.unauthenticated) {
            if (uri.compareToIgnoreCase(sm.getAddress()) == 0) {
                return true;
            }
        }
        return false;
    }
}
