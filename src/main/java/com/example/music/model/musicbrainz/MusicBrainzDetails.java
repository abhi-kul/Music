package com.example.music.model.musicbrainz;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Builder
@AllArgsConstructor
public class MusicBrainzDetails {

  @JsonProperty(value = "release-groups")
  private List<AlbumDetail> releaseGroups;

  private List<RelationDetail> relations;

  private String id;
  private String name;
  private String gender;
  private String country;
  private String disambiguation;
}
