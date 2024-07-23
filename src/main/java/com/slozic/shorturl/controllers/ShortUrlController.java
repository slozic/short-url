package com.slozic.shorturl.controllers;

import com.slozic.shorturl.controllers.dtos.CreateUrlResponse;
import com.slozic.shorturl.controllers.dtos.CreateShortUrlRequest;
import com.slozic.shorturl.controllers.dtos.GetUrlResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.websocket.server.PathParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/short-url")
public class ShortUrlController {

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
    @GetMapping("/{url}")
    public ResponseEntity getLongUrl(@PathParam("url") String shortUrl) {
        // TODO Call service logic
        GetUrlResponse getUrlResponse = new GetUrlResponse("dummyUrl", "dummyUrl");
        return ResponseEntity.ok(getUrlResponse);
    }

    @Operation(
            summary = "Create a short url based on actual long url.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created Short Url.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CreateUrlResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid url identifier given.",
                    content = @Content)})
    @PostMapping
    public ResponseEntity createShortUrl(@RequestBody CreateShortUrlRequest shortUrlRequest) {
        // TODO Call service logic
        CreateUrlResponse createUrlResponse = new CreateUrlResponse("dummyUrl");
        return ResponseEntity.ok(createUrlResponse);
    }

}
