package cz.stanej14.hackernewsreader.domain.model;

/**
 * OAuth2 credentials.
 * Created by Jan Stanek[jan.stanek@ackee.cz] on {12.11.16}
 **/
public class OAuthCredentials {

    final String accessToken;
    final String refreshToken;
    final int expiresIn;

    public OAuthCredentials(String accessToken, String refreshToken, int expiresIn) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.expiresIn = expiresIn;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public int getExpiresIn() {
        return expiresIn;
    }
}
