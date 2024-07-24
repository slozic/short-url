package com.slozic.shorturl.repositories;

import com.slozic.shorturl.models.UrlModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ShortUrlRepository extends MongoRepository<UrlModel, String> {

    public Optional<UrlModel> findUrlModelByShortUrl(String shortUrl);

    public Optional<UrlModel> findUrlModelByLongUrl(String longUrl);
}
