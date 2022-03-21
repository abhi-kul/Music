package com.example.music.service.Impl;

import java.util.Objects;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.music.exception.MusicBrainzException;
import com.example.music.model.musicbrainz.MusicBrainzDetails;
import com.example.music.service.DataInterface;

@Service
public class MusicBrainzServiceImpl implements DataInterface<MusicBrainzDetails> {

  private static final String BASE_URL = "http://musicbrainz.org/ws/2/artist/";

  private final RestTemplate restTemplate;

  public MusicBrainzServiceImpl(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  // http://musicbrainz.org/ws/2/artist/f27ec8db-af05-4f36-916e-3d57f91ecf5e?&fmt=json&inc=url-rels+release-groups
  @Override
  public MusicBrainzDetails getData(String mbid) {
    String URL = BASE_URL.concat(mbid).concat("?fmt=json&inc=url-rels+release-groups");
    try {
      ResponseEntity<MusicBrainzDetails> responseEntity =
          restTemplate.getForEntity(URL, MusicBrainzDetails.class);
      MusicBrainzDetails musicBrainzDetails = responseEntity.getBody();
      if (Objects.isNull(musicBrainzDetails)) {
        throw new MusicBrainzException("MusicBrainz data not available!!!");
      }
      return musicBrainzDetails;
    } catch (Exception exception) {
      throw new MusicBrainzException("MusicBrainz data not available!!!");
    }
  }
}
