package com.urlShortener.service;

import com.urlShortener.repository.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by tbuldina on 05/04/2018.
 */
@Component
public class PropertiesLoader {
    @Autowired
    public UrlRepository repository;

    @Value("${local.server.host}")
    public String serverHost;

    @Value("${local.server.port}")
    public Integer serverPort;

}
