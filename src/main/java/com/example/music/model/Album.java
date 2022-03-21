package com.example.music.model;

import com.example.music.model.coverart.CoverArtArchiveDetail;
import com.example.music.model.coverart.Image;
import com.example.music.model.musicbrainz.AlbumDetail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Builder
@Getter
@ToString
public class Album {

  private final String id;
  private final String title;
  private final String imageUrl;

  public static Album createAlbum(AlbumDetail releaseGroup, CoverArtArchiveDetail coverArtArchiveDetail) {
    return Album.builder()
        .id(releaseGroup.getId())
        .title(releaseGroup.getTitle())
        .imageUrl(
            coverArtArchiveDetail.getImages().stream()
                .filter(Image::isFront)
                .findFirst()
                .map(Image::getImage)
                .orElse(null))
        .build();
  }
}
