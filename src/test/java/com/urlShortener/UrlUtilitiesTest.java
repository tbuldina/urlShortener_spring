package com.urlShortener;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.urlShortener.utilities.UrlUtilities.defineSimilarUrls;
import static com.urlShortener.utilities.UrlUtilities.makeProtocolLowCasePathCaseSensitive;
import static com.urlShortener.utilities.UrlUtilities.makeUrlClickable;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by tbuldina on 01/04/2018.
 */
public class UrlUtilitiesTest {

    @Test
    public void test() {
        assertThat(makeUrlClickable("apple.com"))
                .isEqualTo("http://apple.com");
    }

    @Test
    public void test1() {
        assertThat(makeUrlClickable("http://apple.com"))
                .isEqualTo("http://apple.com");
    }

    @Test
    public void test2() {
        assertThat(makeUrlClickable("https://apple.com"))
                .isEqualTo("https://apple.com");
    }

    @Test
    public void urlWithoutProtocol_defineSimilarUrls() {
        List<String> urls = new ArrayList<String>();
        urls.add("apple.com");
        urls.add("http://apple.com");

        List<String> found = defineSimilarUrls("apple.com");

        assertThat(found)
                .isEqualTo(urls);
    }

    @Test
    public void urlWithHttp_defineSimilarUrls() {
        List<String> urls = new ArrayList<String>();
        urls.add("http://apple.com");
        urls.add("apple.com");

        List<String> found = defineSimilarUrls("http://apple.com");

        assertThat(found)
                .isEqualTo(urls);
    }

    @Test
    public void urlWithHttps_defineSimilarUrls() {
        List<String> urls = new ArrayList<String>();
        urls.add("https://apple.com");

        List<String> found = defineSimilarUrls("https://apple.com");

        assertThat(found)
                .isEqualTo(urls);
    }

    @Test
    public void httpUrlLowCase_returnSameUrl() {
        assertThat(makeProtocolLowCasePathCaseSensitive("http://apple.com"))
                .isEqualTo("http://apple.com");
    }

    @Test
    public void httpUrlPathRegister_returnSameUrl() {
        assertThat(makeProtocolLowCasePathCaseSensitive("http://Apple.coM"))
                .isEqualTo("http://Apple.coM");
    }

    @Test
    public void httpUrlProtocolRegister_returnProtocolLowCase() {
        assertThat(makeProtocolLowCasePathCaseSensitive("Http://apple.com"))
                .isEqualTo("http://apple.com");
    }

    @Test
    public void httpUrlProtocolAndPathRegister_returnProtocolLowCase() {
        assertThat(makeProtocolLowCasePathCaseSensitive("HTTP://APPLE.com"))
                .isEqualTo("http://APPLE.com");
    }

    @Test
    public void httpsUrlPathRegister_returnSameUrl() {
        assertThat(makeProtocolLowCasePathCaseSensitive("https://APPLE.com"))
                .isEqualTo("https://APPLE.com");
    }

    @Test
    public void httpsProtocolAndPathRegister_returnProtocolLowCase() {
        assertThat(makeProtocolLowCasePathCaseSensitive("hTTps://Apple.com"))
                .isEqualTo("https://Apple.com");
    }

    @Test
    public void noProtocolPathLowCase_returnSameUrl() {
        assertThat(makeProtocolLowCasePathCaseSensitive("apple.com"))
                .isEqualTo("apple.com");
    }

    @Test
    public void noProtocolPathRegister_returnSameUrl() {
        assertThat(makeProtocolLowCasePathCaseSensitive("Apple.Com"))
                .isEqualTo("Apple.Com");
    }

    @Test
    public void noProtocolOneLetterUrl_returnSameUrl() {
        assertThat(makeProtocolLowCasePathCaseSensitive("m"))
                .isEqualTo("m");
    }
}
