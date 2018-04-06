package com.urlShortener.model;

import com.urlShortener.validator.UrlFormatConstraint;
import lombok.*;
import javax.persistence.*;

/**
 * Created by tbuldina on 30/03/2018.
 */

@Builder
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Url {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(length = 10000)
    @UrlFormatConstraint
    private String longUrl;

    private String shortUrl;

    @Transient
    private String shortUrlLink;

    @Transient
    private String originalUrl;

    @Override
    public String toString() {
        return String.format(
                "Url[id=%d, longUrl='%s', shortUrl='%s']",
                id, longUrl, shortUrl);
    }
}
