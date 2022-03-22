package com.example.music.service.Impl;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.music.exception.CoverArtArchiveException;
import com.example.music.model.coverart.CoverArtArchiveDetail;
import com.example.music.service.DataInterface;

@Service
public class CoverArtArchiveServiceImpl implements DataInterface<CoverArtArchiveDetail> {

  @Value("${cover.art.archive.base.url}")
  private String BASE_URL;

  private final RestTemplate restTemplate;

  public CoverArtArchiveServiceImpl(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  // https://coverartarchive.org/release-group/500d9b05-68c3-3535-86e3-cf685869efc0
  @Override
  public CoverArtArchiveDetail getData(String releaseGroupId) {
    String URL = BASE_URL.concat(releaseGroupId);
    try {
      ResponseEntity<CoverArtArchiveDetail> responseEntity =
          restTemplate.getForEntity(URL, CoverArtArchiveDetail.class);
      CoverArtArchiveDetail coverArtArchiveData = responseEntity.getBody();
      if (Objects.isNull(coverArtArchiveData)) {
        throw new CoverArtArchiveException("CoverArtArchive data not available!!!");
      }
      return coverArtArchiveData;
    } catch (Exception exception) {
      throw new CoverArtArchiveException("CoverArtArchive data not available!!!");
    }
  }
}
