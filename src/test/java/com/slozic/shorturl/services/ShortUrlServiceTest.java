package com.slozic.shorturl.services;

import com.slozic.shorturl.exceptions.UrlExistsException;
import com.slozic.shorturl.exceptions.UrlNotFoundException;
import com.slozic.shorturl.models.UrlModel;
import com.slozic.shorturl.repositories.ShortUrlRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ShortUrlServiceTest {

    private static final String URL_DOMAIN = "http://shorturl.com/";
    private static final int URL_LENGTH = 7;
    private final ShortUrlRepository shortUrlRepository = mock(ShortUrlRepository.class);
    @InjectMocks
    private ShortUrlService shortUrlService = new ShortUrlService(URL_LENGTH, "http://shorturl.com/", shortUrlRepository);

    @Test
    public void createShortUrl_shouldReturnSuccess() throws MalformedURLException, UrlExistsException {
        // given
        URL url = new URL("http://dummyurl");
        when(shortUrlRepository.findUrlModelByLongUrl(url.toString())).thenReturn(Optional.empty());

        // when
        String shortUrl = shortUrlService.createShortUrl(url);

        // then
        assertThat(shortUrl.substring(URL_DOMAIN.length()).length()).isEqualTo(URL_LENGTH);
    }

    @Test
    public void getShortUrl_shouldReturnSuccess() throws MalformedURLException, UrlNotFoundException, UrlExistsException {
        // given
        URL url = new URL("http://dummyurl");
        when(shortUrlRepository.findUrlModelByLongUrl(url.toString())).thenReturn(Optional.empty());
        when(shortUrlRepository.findUrlModelByShortUrl(anyString())).thenReturn(Optional.of(new UrlModel("id", "http://shorturl.com/xyzdefg", url.toString())));
        // when
        String shortUrl = shortUrlService.createShortUrl(url);

        // then
        String longUrl = shortUrlService.getLongUrl(shortUrl);
        assertThat(longUrl).isEqualTo(url.toString());
    }

}
