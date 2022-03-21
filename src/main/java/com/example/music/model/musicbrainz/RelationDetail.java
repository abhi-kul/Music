package com.example.music.model.musicbrainz;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Builder
@AllArgsConstructor
public class RelationDetail {

  String type;

  @JsonProperty(value = "type-id")
  String typeId;

  RelationURL url;
}
