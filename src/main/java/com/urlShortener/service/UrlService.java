package com.urlShortener.service;

import com.urlShortener.model.Url;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.urlShortener.shortener.UrlShortener.encode;
import static com.urlShortener.utilities.UrlUtilities.*;

/**
 * Created by tbuldina on 30/03/2018.
 */
@Service
public class UrlService {

    private PropertiesLoader propertiesLoader;

    @Autowired
    public UrlService(PropertiesLoader propertiesLoader) {
        this.propertiesLoader = propertiesLoader;
    }

    public Url saveOrFindExisted(Url url) {
        String env = "http://" + propertiesLoader.serverHost + ":" + propertiesLoader.serverPort + "/";
        List<String> longUrls = new ArrayList<String>();

        String longUrl = url.getLongUrl().trim();
        url.setOriginalUrl(longUrl);

        url.setLongUrl(makeProtocolLowCasePathCaseSensitive(longUrl));

        longUrls = defineSimilarUrls(url.getLongUrl());

        // if url in repository - get it
        if (propertiesLoader.repository.existsByLongUrlIn(longUrls)) {
            String shortUrl = propertiesLoader.repository.findByLongUrlIn(longUrls).getShortUrl();
            url.setShortUrl(shortUrl);
            url.setShortUrlLink(env + shortUrl);
            return url;
        }
        // save url to repository
        else {
            url = propertiesLoader.repository.save(url);
            String shortUrl = encode(url.getId());

            Url urlAfterSaving = url;
            urlAfterSaving.setShortUrl(shortUrl);
            urlAfterSaving.setShortUrlLink(env + shortUrl);

            urlAfterSaving = propertiesLoader.repository.save(urlAfterSaving);
            return urlAfterSaving;
        }
    }
}
