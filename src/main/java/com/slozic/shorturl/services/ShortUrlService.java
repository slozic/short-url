package com.slozic.shorturl.services;

import com.slozic.shorturl.exceptions.UrlExistsException;
import com.slozic.shorturl.exceptions.UrlNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.security.SecureRandom;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ShortUrlService {

    private static final ConcurrentHashMap<String, String> URL_STORAGE = new ConcurrentHashMap<>();
    private static final String CHARACTERS_POOL = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final SecureRandom RANDOM_GENERATOR = new SecureRandom();
    private final int SHORT_URL_LENGTH;
    private final String SHORT_URL_DOMAIN;

    public ShortUrlService(@Value("${short-url.length}") int shortUrlLength,
                           @Value("${short-url.domain}") String shortUrlDomain) {
        this.SHORT_URL_LENGTH = shortUrlLength;
        this.SHORT_URL_DOMAIN = shortUrlDomain;
    }

    public String getShortUrl(String shortUrl) throws UrlNotFoundException {
        if (!URL_STORAGE.containsKey(shortUrl)) {
            throw new UrlNotFoundException();
        }
        return URL_STORAGE.get(shortUrl);
    }

    public String createShortUrl(final URL longUrl) throws UrlExistsException {
        if (URL_STORAGE.containsValue(longUrl)) {
            throw new UrlExistsException();
        }

        StringBuilder shortUrlBuilder = new StringBuilder(SHORT_URL_LENGTH);
        for (int i = 0; i < SHORT_URL_LENGTH; i++) {
            int index = RANDOM_GENERATOR.nextInt(CHARACTERS_POOL.length() - 1);
            shortUrlBuilder.append(CHARACTERS_POOL.charAt(index));
        }

        String shortUrl = SHORT_URL_DOMAIN + shortUrlBuilder;
        URL_STORAGE.put(shortUrl, longUrl.toString());
        return shortUrl;
    }
}
