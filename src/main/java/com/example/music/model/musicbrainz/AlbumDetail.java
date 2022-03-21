package com.example.music.model.musicbrainz;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Builder
@AllArgsConstructor
public class AlbumDetail {

  private String id;

  @JsonProperty(value = "primary-type")
  private String primaryType;

  private String title;
  private String disambiguation;

  @JsonProperty(value = "primary-type-id")
  private String primaryTypeId;

  @JsonProperty(value = "first-release-date")
  private String firstReleaseDate;

  @JsonProperty(value = "secondary-types")
  private List<String> secondaryTypes;

  @JsonProperty(value = "secondary-type-ids")
  private List<String> secondaryTypeIds;

  public String getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }
}
