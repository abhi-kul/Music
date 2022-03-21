package com.example.music.model.coverart;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Builder
@AllArgsConstructor
public class CoverArtArchiveDetail {
  List<Image> images;
  String release;

  public List<Image> getImages() {
    return images;
  }
}
