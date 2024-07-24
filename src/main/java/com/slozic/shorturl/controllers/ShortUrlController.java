package com.slozic.shorturl.controllers;

import com.slozic.shorturl.controllers.dtos.CreateShortUrlRequest;
import com.slozic.shorturl.controllers.dtos.CreateUrlResponse;
import com.slozic.shorturl.controllers.dtos.GetUrlResponse;
import com.slozic.shorturl.exceptions.UrlExistsException;
import com.slozic.shorturl.exceptions.UrlNotFoundException;
import com.slozic.shorturl.services.ShortUrlService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.net.URL;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/short-url")
public class ShortUrlController {

    private final ShortUrlService shortUrlService;

    @Operation(
            summary = "Fetch a original long url based on provided short url identifier.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved Long Url.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetUrlResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid url identifier given.",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Url identifier not found.",
                    content = @Content)})
    @GetMapping
    public ResponseEntity getLongUrl(@RequestParam("url") String shortUrl) {
        String longUrl = null;
        try {
            longUrl = shortUrlService.getLongUrl(shortUrl);
        } catch (UrlNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Url not found: " + shortUrl);
        }
        GetUrlResponse getUrlResponse = new GetUrlResponse(shortUrl, longUrl);
        return ResponseEntity.ok(getUrlResponse);
    }

    @Operation(
            summary = "Create a short url based on actual url.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created Short Url.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CreateUrlResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid url identifier given.",
                    content = @Content)})
    @PostMapping
    public ResponseEntity createShortUrl(@RequestBody @Valid CreateShortUrlRequest shortUrlRequest) {
        URL url;
        String shortUrl;
        try {
            // basic url validation, update with e.g. regex checks
            url = new URL(shortUrlRequest.longUrl());
            shortUrl = shortUrlService.createShortUrl(url);
            CreateUrlResponse createUrlResponse = new CreateUrlResponse(shortUrl);
            return ResponseEntity.ok(createUrlResponse);
        } catch (UrlExistsException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Url already exists: " + shortUrlRequest.longUrl());
        } catch (MalformedURLException e) {
            return ResponseEntity.badRequest().body("Invalid url sent: " + shortUrlRequest.longUrl());
        }
    }

}
