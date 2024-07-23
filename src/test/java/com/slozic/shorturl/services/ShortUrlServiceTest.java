package com.slozic.shorturl.services;

import com.slozic.shorturl.exceptions.UrlExistsException;
import com.slozic.shorturl.exceptions.UrlNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.MalformedURLException;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class ShortUrlServiceTest {

    private static final String URL_DOMAIN = "http://shorturl.com/";
    private static final int URL_LENGTH = 7;

    @InjectMocks
    private ShortUrlService shortUrlService = new ShortUrlService(URL_LENGTH, "http://shorturl.com/");

    @Test
    public void createShortUrl_shouldReturnSuccess() throws MalformedURLException, UrlExistsException {
        // given
        URL url = new URL("http://dummyurl");

        // when
        String shortUrl = shortUrlService.createShortUrl(url);

        // then
        assertThat(shortUrl.substring(URL_DOMAIN.length()).length()).isEqualTo(URL_LENGTH);
    }

    @Test
    public void getShortUrl_shouldReturnSuccess() throws MalformedURLException, UrlNotFoundException, UrlExistsException {
        // given
        URL url = new URL("http://dummyurl");

        // when
        String shortUrl = shortUrlService.createShortUrl(url);

        // then
        String longUrl = shortUrlService.getShortUrl(shortUrl);
        assertThat(longUrl).isEqualTo(url.toString());
    }

}
