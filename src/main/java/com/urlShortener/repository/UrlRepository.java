package com.urlShortener.repository;

import com.urlShortener.model.Url;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

/**
 * Created by tbuldina on 30/03/2018.
 */
public interface UrlRepository extends CrudRepository<Url, Long> {

    Url findByLongUrlIn(Collection longUrls);

    Url findByShortUrl(String shortUrl);

    Boolean existsByLongUrlIn(Collection longUrls);

    Boolean existsByShortUrl(String shortUrl);

}