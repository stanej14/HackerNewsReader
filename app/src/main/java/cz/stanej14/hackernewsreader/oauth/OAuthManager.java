package cz.stanej14.hackernewsreader.oauth;

import android.content.Context;
import android.content.SharedPreferences;

import cz.stanej14.hackernewsreader.domain.model.OAuthCredentials;

/**
 * Manager for dealing with Oauth2 authorization
 * Created by Jan Stanek[jan.stanek@ackee.cz] on {12.11.16}
 **/
public class OAuthManager {
    public static final String TAG = OAuthManager.class.getName();
    private static final String SP_NAME = "oauth";
    private static final String ACCESS_TOKEN_KEY = "access_token";
    private static final String REFRESH_TOKEN_KEY = "refresh_token";
    private final SharedPreferences authSharedPreferences;


    public OAuthManager(Context ctx) {
        authSharedPreferences = ctx.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
    }

    public void saveOauthCredentials(OAuthCredentials credentials) {
        authSharedPreferences.edit().putString(ACCESS_TOKEN_KEY, credentials.getAccessToken())
                .putString(REFRESH_TOKEN_KEY, credentials.getRefreshToken())
                .apply();
    }

    public String getAccessToken() {
        return authSharedPreferences.getString(ACCESS_TOKEN_KEY, null);
    }

    public String getRefreshToken() {
        return authSharedPreferences.getString(REFRESH_TOKEN_KEY, null);
    }

    public void onLogout() {
        authSharedPreferences.edit().clear().apply();
    }
}
