package uk.gov.hmcts.probate.functional;


import io.restassured.RestAssured;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import uk.gov.hmcts.reform.authorisation.generators.ServiceAuthTokenGenerator;

import java.util.Base64;

@Component
public class TestAuthTokenGenerator {

    @Value("${idam.oauth2.client.id}")
    private String clientId;

    @Value("${idam.oauth2.client.secret}")
    private String clientSecret;

    @Value("${idam.oauth2.redirect_uri}")
    private String redirectUri;

    @Value("${service.name}")
    private String serviceName;

    @Value("${service.auth.provider.base.url}")
    private String baseServiceAuthUrl;

    @Value("${user.auth.provider.oauth2.url}")
    private String baseServiceOauth2Url;

    @Value("${idam.username}")
    private String idamUsername;

    @Value("${idam.userpassword}")
    private String idamPassword;

    @Value("${env}")
    private String environment;

    @Value("${idam.secret}")
    private String secret;

    @Value("${user.auth.provider.oauth2.url}")
    private String idamUserBaseUrl;

    private String userToken;

    private String idamCreateUrl() {
        return idamUserBaseUrl + "/testing-support/accounts";
    }

    @Autowired
    private ServiceAuthTokenGenerator tokenGenerator;

    public String generateServiceToken() {
        return tokenGenerator.generate();
    }

    public String getUserId() {
        return "" + RestAssured.given()
                .header("Authorization", userToken)
                .get(idamUserBaseUrl + "/details")
                .body()
                .path("id");
    }

    public String generateUserTokenWithNoRoles() {
        userToken = generateClientToken();
        return userToken;
    }

    private String generateClientToken() {
        String code = generateClientCode();
        String token = RestAssured.given().post(idamUserBaseUrl + "/oauth2/token?code=" + code +
                "&client_secret=" + secret +
                "&client_id=probate" +
                "&redirect_uri=" + redirectUri +
                "&grant_type=authorization_code")
                .body().path("access_token");
        return "Bearer " + token;
    }

    private String generateClientCode() {
        final String encoded = Base64.getEncoder().encodeToString((idamUsername + ":" + idamPassword).getBytes());
        return RestAssured.given().baseUri(idamUserBaseUrl)
                .header("Authorization", "Basic " + encoded)
                .post("/oauth2/authorize?response_type=code&client_id=probate&redirect_uri=" + redirectUri)
                .body().path("code");
    }
}
