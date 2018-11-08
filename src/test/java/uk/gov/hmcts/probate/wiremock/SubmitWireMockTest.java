package uk.gov.hmcts.probate.wiremock;

import com.github.tomakehurst.wiremock.junit.WireMockClassRule;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.WireMockSpring;
import org.springframework.test.context.junit4.SpringRunner;
import uk.gov.hmcts.probate.utils.TestUtils;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.matching;
import static com.github.tomakehurst.wiremock.client.WireMock.matchingJsonPath;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.port;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SubmitWireMockTest {

    private static final String DETAILS_ENDPOINT = "/details";

    private static final String LEASE_ENDPOINT = "/lease";

    private static final String CCD_GET_TOKEN_ENDPOINT = "/citizens/33/jurisdictions/PROBATE/case-types/GrantOfRepresentation/" +
            "event-triggers/applyForGrant/token";

    private static final String CCD_SUBMIT_ENDPOINT = "/citizens/33/jurisdictions/PROBATE/case-types/GrantOfRepresentation/cases?ignore-warning=true";

    private static final String AUTHORIZATION = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI4c28yYzVlajF0bTI0cDNub" +
            "zhpcDc3cjhkciIsInN1YiI6IjMzIiwiaWF0IjoxNTQxNDQ1Nzg1LCJleHAiOjE1NDE0NzQ1ODUsImRhdGEiOiJjYXN" +
            "ld29ya2VyLXByb2JhdGUsY2l0aXplbixjYXNld29ya2VyLGNhc2V3b3JrZXItcHJvYmF0ZS1sb2ExLGNpdGl6ZW4tbG9h" +
            "MSxjYXNld29ya2VyLWxvYTEiLCJ0eXBlIjoiQUNDRVNTIiwiaWQiOiIzMyIsImZvcmVuYW1lIjoiVXNlciIsInN1cm5hb" +
            "WUiOiJUZXN0IiwiZGVmYXVsdC1zZXJ2aWNlIjoiQ0NEIiwibG9hIjoxLCJkZWZhdWx0LXVybCI6Imh0dHBzOi8vbG9jYWxob3" +
            "N0OjkwMDAvcG9jL2NjZCIsImdyb3VwIjoiY2FzZXdvcmtlciJ9.3piYNImokd_aMLIajwExl35p2nhxJJjULb8gUGbJoRw";

    private static final String SERVICE_AUTHORIZATION = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJwcm9iYXRlX2Zyb250ZW5kIiwiZXhw" +
            "IjoxNTQxNDYwMzYwfQ.Bvaeaz_sCJzPZ81i3mC1uixGZM6U6m9uoUN9XoKUNgx-8aXtAr6l_nXdj9dOm_Eq6QkfryhV2EEGwsO_dMqgTw";

    private static final String GENERATED_SERVICE_TOKEN = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJwcm9iYXRlX2JhY2tlbmQiLCJleHAiOjE1NDE2OTI" +
            "yNjV9.OdP9oz7tB2KNTo9020r2z4sFPUV8OpheD2nmRiMJWP4xDK6Bvb5VcDTLy-O4S0FWItPKgPLU9o2qQy881rRc5A";

    @Value("${local.server.port}")
    private int serverPort;


    @ClassRule
    public static WireMockClassRule serviceAuthServer = new WireMockClassRule(
            WireMockSpring.options().port(4502).bindAddress("localhost"));

    @ClassRule
    public static WireMockClassRule idamServer = new WireMockClassRule(
            WireMockSpring.options().port(4501).bindAddress("localhost"));

    @ClassRule
    public static WireMockClassRule ccdServer = new WireMockClassRule(
            WireMockSpring.options().port(4452).bindAddress("localhost"));


    @Before
    public void setUp() throws Exception {
        port = serverPort;

        idamServer.stubFor(get(DETAILS_ENDPOINT)
                .withHeader("Authorization", equalTo(AUTHORIZATION))
                .willReturn(aResponse()
                        .withHeader("Content-type", "application/json")
                        .withBody(TestUtils.getJSONFromFile("idamDetails.json"))
                        .withStatus(200)));

        serviceAuthServer.stubFor(get(DETAILS_ENDPOINT)
                .withHeader("Authorization", equalTo("Bearer " + SERVICE_AUTHORIZATION))
                .willReturn(aResponse()
                        .withHeader("Content-type", "application/json")
                        .withBody("probate_frontend")
                        .withStatus(200)));

        serviceAuthServer.stubFor(post(LEASE_ENDPOINT)
                .withRequestBody(matchingJsonPath("$.oneTimePassword", matching("\\p{Digit}{6}+")))
                .withRequestBody(matchingJsonPath("$.microservice", matching("probate_backend")))
                .willReturn(aResponse()
                        .withBody(GENERATED_SERVICE_TOKEN)
                        .withStatus(201)));


        ccdServer.stubFor(get(CCD_GET_TOKEN_ENDPOINT)
                .withHeader("Authorization", equalTo(AUTHORIZATION))
                .withHeader("ServiceAuthorization", equalTo(GENERATED_SERVICE_TOKEN))
                .willReturn(aResponse()
                        .withBody(TestUtils.getJSONFromFile("ccdEventTokenResponse.json"))
                        .withStatus(200)));

        ccdServer.stubFor(post(CCD_SUBMIT_ENDPOINT)
                .withHeader("Authorization", equalTo(AUTHORIZATION))
                .withHeader("ServiceAuthorization", equalTo(GENERATED_SERVICE_TOKEN))
                //.withRequestBody(equalTo(TestUtils.getJSONFromFile("ccdSubmitRequest.json")))
                .willReturn(aResponse()
                        .withBody(TestUtils.getJSONFromFile("ccdSubmitResponse.json"))
                        .withStatus(201)));
    }

    @Test
    public void shouldSubmit() throws Exception {

        String jsonFromFile = TestUtils.getJSONFromFile("formData_success.json");

        given()
                .body(jsonFromFile)
                .headers(Headers.headers(
                        new Header("Authorization", AUTHORIZATION),
                        new Header("ServiceAuthorization", SERVICE_AUTHORIZATION),
                        new Header("Content-Type", ContentType.JSON.toString())))
                .when().post("/submit")
                .then()
                .statusCode(201);
    }
}
