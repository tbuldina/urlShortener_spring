package com.urlShortener;

import com.urlShortener.controller.MainController;
import com.urlShortener.model.Url;
import com.urlShortener.repository.UrlRepository;
import com.urlShortener.service.UrlService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by tbuldina on 01/04/2018.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(MainController.class)
@TestPropertySource("/ValidationMessages.properties")
public class MainControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private MainController mainController;
    @MockBean
    private UrlService urlService;
    @MockBean
    private UrlRepository urlRepository;
    @MockBean
    private BindingResult mockBindingResult;
    @Mock
    private Model model;
    @Value("${url.invalid.message}")
    private String urlInvalidMessage;

    @Test
    public void whenGetIndex_thenReturnIndexPage() throws Exception {

        mvc.perform(get("/index"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Enter URL for shortening")));
    }

    @Test
    public void givenCorrectUrl_whenPostIndex_thenReturnIndexPage() throws Exception {
        Url url = Url.builder().longUrl("apple.com").build();
        Url urlAfterSaving = Url.builder()
                .longUrl("apple.com")
                .id(1)
                .shortUrl("b")
                .shortUrlLink("http://localhost:8080/b")
                .build();

        Mockito.when(urlService.saveOrFindExisted(url))
                .thenReturn(urlAfterSaving);

        mvc.perform(MockMvcRequestBuilders.post("/index")
                .param("longUrl", "apple.com"))
                        .andExpect(status().isOk())
                        .andExpect(content().string(containsString("Enter URL for shortening")))
                        .andExpect(content().string(containsString("Following URL:")))
                        .andExpect(content().string(containsString(urlAfterSaving.getLongUrl())))
                        .andExpect(content().string(containsString("resulted short URL")))
                        .andExpect(content().string(containsString(urlAfterSaving.getShortUrl())));
    }

    @Test
    public void givenEmptyUrl_whenPostIndex_thenReturnIndexPage() throws Exception {

        mvc.perform(MockMvcRequestBuilders.post("/index")
                .param("longUrl", ""))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(urlInvalidMessage)));
    }

    @Test
    public void givenUrlOnlyFromHttp_whenPostIndex_thenReturnIndexPage() throws Exception {

        mvc.perform(MockMvcRequestBuilders.post("/index")
                .param("longUrl", "http://"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(urlInvalidMessage)));
    }

    @Test
    public void givenUrlOnlyFromHttps_whenPostIndex_thenReturnIndexPage() throws Exception {

        mvc.perform(MockMvcRequestBuilders.post("/index")
                .param("longUrl", "https://"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(urlInvalidMessage)));
    }

    @Test
    public void givenUrlOnlyFromHttpsRegister_whenPostIndex_thenReturnIndexPage() throws Exception {

        mvc.perform(MockMvcRequestBuilders.post("/index")
                .param("longUrl", " HTtps:// "))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(urlInvalidMessage)));
    }

    @Test
    public void givenNoUrlWithShortUrl_whenGetPage_thenReturnNotFound() throws Exception {

        Mockito.when(urlRepository.existsByShortUrl("b"))
                .thenReturn(false);

        mvc.perform(get("/b"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Requested page is not found")));
    }

    @Test
    public void givenUrlWithShortUrlExist_whenGetPage_thenReturnLongUrl() throws Exception {
        Url url = Url.builder().longUrl("apple.com").shortUrl("b").build();

        Mockito.when(urlRepository.existsByShortUrl("b"))
                .thenReturn(true);
        Mockito.when(urlRepository.findByShortUrl("b"))
                .thenReturn(url);

        mvc.perform(get("/b"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://" + url.getLongUrl()));
    }
}

