package cn.network.interceptor;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.user_db.UserCache;
import cn.network.Url;
import cn.network.model.UserCookie;
import cn.utils.YZStringUtil;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class SaveCookiesInterceptor implements Interceptor {
    private static String TAG = SaveCookiesInterceptor.class.getSimpleName();

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);

        if (!response.headers("set-cookie").isEmpty()) {
            List<String> cookies = response.headers("set-cookie");
            if (cookies.size() >= 3) {
                String cookie = encodeCookie(request, cookies);
                if (request.url().url().getHost().contains(Url.getUrlYoZo(false))) {
                    UserCache.setYozoCookie(cookie);
                } else {
                    UserCache.setCookie(cookie);
                    UserCache.setSsoCookie(cookie);
                }
            }
        }

        return response;
    }

    /**
     * 整合cookie为唯一字符串
     */
    private String encodeCookie(Request request, List<String> cookies) {
        StringBuilder sb = new StringBuilder();
        Set<String> set = new HashSet<>();
        for (String cookie : cookies) {
            String[] arr = cookie.split(";");
            for (String s : arr) {
                if (set.contains(s)) {
                    continue;
                }
                set.add(s);
            }
        }

        UserCookie userCookie = new UserCookie();
        for (String cookie : set) {
            String[] valueList = cookie.split("=");
            if (valueList.length > 1 && valueList[0].equals("access_token")) {
                if (!YZStringUtil.isEmpty(valueList[1])) {
                    userCookie.setAccess_token(valueList[1]);
                }
            }
            if (valueList.length > 1 && valueList[0].equals("refresh_token")) {
                if (!YZStringUtil.isEmpty(valueList[1])) {
                    userCookie.setRefresh_token(valueList[1]);
                }
            }
            if (valueList.length > 1 && valueList[0].equals("JSESSIONID")) {
                if (!YZStringUtil.isEmpty(valueList[1])) {
                    userCookie.setJSESSIONID(valueList[1]);
                }
            }
            sb.append(cookie).append(";");
        }

        if (request.url().url().getHost().contains(Url.getUrlYoZo(false))) {
            UserCache.setYozoCookieModel(userCookie);
        } else {
            UserCache.setCookieModel(userCookie);
        }

        int last = sb.lastIndexOf(";");
        if (sb.length() - 1 == last) {
            sb.deleteCharAt(last);
        }

        return sb.toString();
    }
}