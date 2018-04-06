package com.urlShortener.utilities;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tbuldina on 31/03/2018.
 */

public final class UrlUtilities {

    private UrlUtilities() {
       /*Private Constructor will prevent
       * the instantiation of this class directly*/
    }

    public static String makeUrlClickable(String url) {
        if ((!url.startsWith("http://") && (!url.startsWith("https://")))) {
            url = "http://" + url;
        }
        return url;
    }

    public static List<String> defineSimilarUrls (String longUrl) {
        String longUrlModified;
        List<String> longUrls = new ArrayList<String>();

        if (!longUrl.startsWith("https://")) {
            // url with http://
            if (longUrl.startsWith("http://")) {
                longUrlModified = longUrl.substring(7);
            }
            // url without protocol
            else longUrlModified = "http://" + longUrl;
            longUrls.add(longUrl);
            longUrls.add(longUrlModified);
        } else {
            // url with https://
            longUrls.add(longUrl);
        }
        return longUrls;
    }

    /**
     *  Transform url before saving to repo
     *  protocol - to lowCase
     *  main part - leave case sensitive
     * @param longUrl
     * @return
     */
    public static String makeProtocolLowCasePathCaseSensitive(String longUrl) {
        String[] urlsParts = longUrl.split("://");
        if (urlsParts[0].toLowerCase().startsWith("http")) {
            urlsParts[0] = urlsParts[0].toLowerCase();
        }
        return String.join("://", urlsParts);
    }
}