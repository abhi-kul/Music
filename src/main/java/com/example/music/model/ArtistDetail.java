package com.example.music.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@AllArgsConstructor
@ToString
public class ArtistDetail {

  private final String mbid;
  private final String name;
  private final String gender;
  private final String country;
  private final String disambiguation;
  private final String description;
  private final List<Album> albums;
}
