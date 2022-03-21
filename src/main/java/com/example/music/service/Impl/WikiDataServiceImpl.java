package com.example.music.service.Impl;

import java.util.Objects;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.music.exception.WikiDataException;
import com.example.music.model.wikidata.WikiData;
import com.example.music.service.DataInterface;

@Service
public class WikiDataServiceImpl implements DataInterface<Object> {

  private final String BASE_URL = "https://www.wikidata.org/wiki/";
  private final String ENTITY_DATA_URL = "Special:EntityData/";
  private final RestTemplate restTemplate;

  public WikiDataServiceImpl(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  // https://www.wikidata.org/wiki/Special:EntityData/Q2831.json
  @Override
  public WikiData getData(String url) {
    String finalUrl = url.replace(BASE_URL, BASE_URL + ENTITY_DATA_URL);
    try {
      ResponseEntity<WikiData> responseEntity = restTemplate.getForEntity(finalUrl, WikiData.class);
      WikiData wikiData = responseEntity.getBody();
      if (Objects.isNull(wikiData)) {
        throw new WikiDataException("Wiki data not available!!!");
      }
      return wikiData;
    } catch (Exception exception) {
      throw new WikiDataException("Wiki data not available!!!");
    }
  }
}
