package com.slozic.shorturl.controllers;

import com.slozic.shorturl.services.ShortUrlService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest
public class ShortUrlControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShortUrlService shortUrlService;

    @Test
    public void getShortUrl_returnsWithSuccess() throws Exception {
        //given
        String url = "dummyUrl";

        // when
        var mvcResult = mockMvc.perform(
                        get("/api/short-url")
                                .param("url", url))
                .andReturn();

        // then
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(200);
        assertThat(mvcResult.getResponse().getContentAsString()).isEqualTo("{\"shortUrl\":\"dummyUrl\",\"longUrl\":null}");
    }

    @Test
    public void createShortUrl_returnsWithSuccess() throws Exception {
        //given
        String url = "http://dummyUrl";

        // when
        var mvcResult = mockMvc.perform(
                        post("/api/short-url")
                                .content("{\"longUrl\":\"" + url + "\"}")
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // then
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(200);
        assertThat(mvcResult.getResponse().getContentAsString()).isEqualTo("{\"shortUrl\":null}");
    }

    @Test
    public void createShortUrl_returnsErrorWithInvalidUrl() throws Exception {
        //given
        String url = "httx://dummyUrl";

        // when
        var mvcResult = mockMvc.perform(
                        post("/api/short-url")
                                .content("{\"longUrl\":\"" + url + "\"}")
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // then
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(400);
    }
}
