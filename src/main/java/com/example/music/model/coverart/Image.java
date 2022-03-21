package com.example.music.model.coverart;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Image {
  boolean approved;
  boolean back;
  String comment;
  int edit;
  boolean front;
  long id;
  String image;

  public String getImage() {
    return image;
  }

  public boolean isFront() {
    return front;
  }
}
