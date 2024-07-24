package com.slozic.shorturl.services;

import com.slozic.shorturl.exceptions.UrlExistsException;
import com.slozic.shorturl.exceptions.UrlNotFoundException;
import com.slozic.shorturl.models.UrlModel;
import com.slozic.shorturl.repositories.ShortUrlRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.security.SecureRandom;
import java.util.Optional;

@Service
public class ShortUrlService {

    private static final String CHARACTERS_POOL = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final SecureRandom RANDOM_GENERATOR = new SecureRandom();
    private final int SHORT_URL_LENGTH;
    private final String SHORT_URL_DOMAIN;
    private final ShortUrlRepository shortUrlRepository;

    public ShortUrlService(@Value("${short-url.length}") int shortUrlLength,
                           @Value("${short-url.domain}") String shortUrlDomain,
                           final ShortUrlRepository shortUrlRepository) {
        this.SHORT_URL_LENGTH = shortUrlLength;
        this.SHORT_URL_DOMAIN = shortUrlDomain;
        this.shortUrlRepository = shortUrlRepository;
    }

    public String getLongUrl(String shortUrl) throws UrlNotFoundException {
        Optional<UrlModel> optionalUrlModel = shortUrlRepository.findUrlModelByShortUrl(shortUrl);
        if (optionalUrlModel.isEmpty()) {
            throw new UrlNotFoundException();
        }
        return optionalUrlModel.get().getLongUrl();
    }

    public String createShortUrl(final URL longUrl) throws UrlExistsException {
        Optional<UrlModel> optionalUrlModel = shortUrlRepository.findUrlModelByLongUrl(longUrl.toString());
        if (optionalUrlModel.isPresent()) {
            throw new UrlExistsException();
        }

        String shortUrlIdentifier = getShortUrlIdentifier();
        String shortUrl = SHORT_URL_DOMAIN + shortUrlIdentifier;

        saveUrlModel(longUrl, shortUrl);
        return shortUrl;
    }

    private void saveUrlModel(URL longUrl, String shortUrl) {
        UrlModel urlModel = new UrlModel();
        urlModel.setShortUrl(shortUrl);
        urlModel.setLongUrl(longUrl.toString());
        shortUrlRepository.save(urlModel);
    }

    private String getShortUrlIdentifier() {
        StringBuilder shortUrlBuilder = new StringBuilder(SHORT_URL_LENGTH);
        for (int i = 0; i < SHORT_URL_LENGTH; i++) {
            int index = RANDOM_GENERATOR.nextInt(CHARACTERS_POOL.length() - 1);
            shortUrlBuilder.append(CHARACTERS_POOL.charAt(index));
        }
        return shortUrlBuilder.toString();
    }
}
