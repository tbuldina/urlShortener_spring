package com.urlShortener;

import com.urlShortener.model.Url;
import com.urlShortener.repository.UrlRepository;
import com.urlShortener.service.UrlService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by tbuldina on 01/04/2018.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UrlShortenerApplication.class)
public class UrlServiceTest {

    @Autowired
    private UrlService urlService;
    @MockBean
    private UrlRepository urlRepository;

    @Test
    public void noUrlInRepository_whenSaveOrFindExisted_thenSavedUrlReturned() {
        Url url = Url.builder().longUrl("apple.com").build();

        Url urlWithId = Url.builder().longUrl("apple.com").id(1).build();

        Url urlFinal = Url.builder()
                .longUrl("apple.com")
                .shortUrl("b")
                .shortUrlLink("http://localhost:8080/b")
                .id(1)
                .build();

        Mockito.when(urlRepository.existsByLongUrlIn(Arrays.asList(url.getLongUrl())))
                .thenReturn(false);
        Mockito.when(urlRepository.save(url))
                .thenReturn(urlWithId);
        Mockito.when(urlRepository.save(urlFinal))
                .thenReturn(urlFinal);

        Url found = urlService.saveOrFindExisted(url);

        assertThat(found)
                .isEqualTo(urlFinal);
    }

    @Test
    public void urlInRepository_whenSaveOrFindExisted_thenUrlReturned() {
        Url url = Url.builder().longUrl("apple.com").build();

        Url urlFromRepo = Url.builder()
                .longUrl("apple.com")
                .shortUrl("b")
                .id(1)
                .build();

        Url urlFinal = Url.builder()
                .longUrl("apple.com")
                .shortUrl("b")
                .shortUrlLink("http://localhost:8080/b")
                .originalUrl("apple.com")
                .build();

        Mockito.when(urlRepository.existsByLongUrlIn(Arrays.asList(url.getLongUrl(), "http://" + url.getLongUrl())))
                .thenReturn(true);
        Mockito.when(urlRepository.findByLongUrlIn(Arrays.asList(url.getLongUrl(), "http://" + url.getLongUrl())))
                .thenReturn(urlFromRepo);

        Url found = urlService.saveOrFindExisted(url);

        assertThat(found)
                .isEqualTo(urlFinal);
    }

    @Test
    public void httpsUrlInRepository_whenSaveOrFindExisted_thenHttpsUrlReturned() {
        Url url = Url.builder().longUrl("https://apple.com").build();

        Url urlFromRepo = Url.builder()
                .longUrl("https://apple.com")
                .shortUrl("b")
                .id(1).build();

        Url urlFinal = Url.builder()
                .longUrl("https://apple.com")
                .shortUrl("b")
                .shortUrlLink("http://localhost:8080/b")
                .originalUrl("https://apple.com")
                .build();

        Mockito.when(urlRepository.existsByLongUrlIn(Arrays.asList(url.getLongUrl())))
                .thenReturn(true);
        Mockito.when(urlRepository.findByLongUrlIn(Arrays.asList(url.getLongUrl())))
                .thenReturn(urlFromRepo);

        Url found = urlService.saveOrFindExisted(url);

        assertThat(found)
                .isEqualTo(urlFinal);
    }

    @Test
    public void httpUrlInRepository_whenSaveOrFindExisted_thenUrlReturned() {
        Url url = Url.builder().longUrl("apple.com").build();

        Url urlFromRepo = Url.builder()
                .longUrl("http://apple.com")
                .shortUrl("b")
                .id(1).build();

        Url urlFinal = Url.builder()
                .longUrl("apple.com")
                .shortUrl("b")
                .shortUrlLink("http://localhost:8080/b")
                .originalUrl("apple.com")
                .build();

        Mockito.when(urlRepository.existsByLongUrlIn(Arrays.asList(url.getLongUrl(), urlFromRepo.getLongUrl())))
                .thenReturn(true);
        Mockito.when(urlRepository.findByLongUrlIn(Arrays.asList(url.getLongUrl(), urlFromRepo.getLongUrl())))
                .thenReturn(urlFromRepo);

        Url found = urlService.saveOrFindExisted(url);

        assertThat(found)
                .isEqualTo(urlFinal);
    }

    @Test
    public void urlInRepository_whenSaveOrFindExisted_thenHttpUrlReturned() {
        Url url = Url.builder().longUrl("http://apple.com").build();

        Url urlFromRepo = Url.builder()
                .longUrl("apple.com")
                .shortUrl("b")
                .id(1).build();

        Url urlFinal = Url.builder()
                .longUrl("http://apple.com")
                .shortUrl("b")
                .shortUrlLink("http://localhost:8080/b")
                .originalUrl("http://apple.com")
                .build();

        Mockito.when(urlRepository.existsByLongUrlIn(Arrays.asList(url.getLongUrl(), urlFromRepo.getLongUrl())))
                .thenReturn(true);
        Mockito.when(urlRepository.findByLongUrlIn(Arrays.asList(url.getLongUrl(), urlFromRepo.getLongUrl())))
                .thenReturn(urlFromRepo);

        Url found = urlService.saveOrFindExisted(url);

        assertThat(found)
                .isEqualTo(urlFinal);
    }
}
