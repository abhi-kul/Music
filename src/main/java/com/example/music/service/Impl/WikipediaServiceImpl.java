package com.example.music.service.Impl;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.music.exception.WikipediaException;
import com.example.music.model.wikipedia.WikipediaData;
import com.example.music.service.DataInterface;

@Service
public class WikipediaServiceImpl implements DataInterface<WikipediaData> {

  @Value("${wikipedia.base.url}")
  private String BASE_URL;

  private final RestTemplate restTemplate;

  public WikipediaServiceImpl(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  //https://en.wikipedia.org/api/rest_v1/page/summary/{title}
  @Override
  public WikipediaData getData(String title) {
    String URL = BASE_URL.concat(title);
    try {
      ResponseEntity<WikipediaData> responseEntity =
          restTemplate.getForEntity(URL, WikipediaData.class);
      WikipediaData wikipediaData = responseEntity.getBody();
      if (Objects.isNull(wikipediaData)) {
        throw new WikipediaException("Wikipedia data not available!!!");
      }
      return wikipediaData;
    } catch (Exception exception) {
      throw new WikipediaException("Wikipedia data not available!!!");
    }
  }
}
