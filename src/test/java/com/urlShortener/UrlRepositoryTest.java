package com.urlShortener;

import com.urlShortener.model.Url;
import com.urlShortener.repository.UrlRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * Created by tbuldina on 01/04/2018.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class UrlRepositoryTest {

    @Autowired
    private UrlRepository urlRepository;
    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void whenFindByLongUrlIn_thenReturnUrl() {
        Url url = Url.builder().longUrl("apple.com").shortUrl("b").build();

        entityManager.persist(url);
        entityManager.flush();

        Url found = urlRepository.findByLongUrlIn(Arrays.asList(url.getLongUrl()));

        assertThat(found.getLongUrl())
                .isEqualTo(url.getLongUrl());
        assertThat(found.getShortUrl())
                .isEqualTo(url.getShortUrl());
    }

    @Test
    public void whenFindByShortUrl_thenReturnUrl() {
        Url url = Url.builder().longUrl("apple.com").shortUrl("b").build();

        entityManager.persist(url);
        entityManager.flush();

        Url found = urlRepository.findByShortUrl(url.getShortUrl());

        assertThat(found.getLongUrl())
                .isEqualTo(url.getLongUrl());
        assertThat(found.getShortUrl())
                .isEqualTo(url.getShortUrl());
    }

    @Test
    public void whenExistsByLongUrlIn_thenReturnTrue() {
        Url url = Url.builder().longUrl("apple.com").shortUrl("b").build();

        entityManager.persist(url);
        entityManager.flush();

        Boolean found = urlRepository.existsByLongUrlIn(Arrays.asList(url.getLongUrl()));

        assertThat(found).isTrue();
    }

    @Test
    public void whenExistsByLongUrlIn_thenReturnFalse() {
        Boolean found = urlRepository.existsByLongUrlIn(Arrays.asList("apple.com"));

        assertThat(found).isFalse();
    }

    @Test
    public void whenExistsByShortUrl_thenReturnTrue() {
        Url url = Url.builder().longUrl("apple.com").shortUrl("b").build();

        entityManager.persist(url);
        entityManager.flush();

        Boolean found = urlRepository.existsByShortUrl(url.getShortUrl());

        assertThat(found).isTrue();
    }

    @Test
    public void whenExistsByShortUrl_thenReturnFalse() {
        Boolean found = urlRepository.existsByShortUrl("b");

        assertThat(found).isFalse();
    }

}

