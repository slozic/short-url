package com.slozic.shorturl.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "urlModel")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UrlModel {
    @Id
    private String id;
    private String shortUrl;
    private String longUrl;
}
