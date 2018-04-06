package com.urlShortener.controller;

import com.urlShortener.model.Url;
import com.urlShortener.repository.UrlRepository;
import com.urlShortener.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

import static com.urlShortener.utilities.UrlUtilities.makeUrlClickable;

/**
 * Created by tbuldina on 29/03/2018.
 */

@Controller
public class MainController implements WebMvcConfigurer {

    @Autowired
    private UrlService urlService;
    @Autowired
    private UrlRepository repository;

    @GetMapping("/index")
    public String mainPage(Model model) {
        model.addAttribute("url", new Url());
        return "index";
    }

    @PostMapping("/index")
    public String mainPageSubmit(Model model, @Valid Url url, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            if (bindingResult.getAllErrors().size()>0) {
                model.addAttribute("urlForm", bindingResult.getAllErrors().get(0));
            }
            return "index";
        } else {
            url = urlService.saveOrFindExisted(url);
            model.addAttribute("url", url);
            return "index";
        }
    }

    @GetMapping("/*")
    public String allPages(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String path = request.getRequestURI().substring(1);

        if (repository.existsByShortUrl(path)) {
            String longUrl = repository.findByShortUrl(path).getLongUrl();
            return "redirect:" + makeUrlClickable(longUrl);
        }
        else
            return "notFound";
    }
}