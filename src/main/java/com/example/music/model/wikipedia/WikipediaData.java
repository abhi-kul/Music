package com.example.music.model.wikipedia;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class WikipediaData {
  private String title;
  private String extract_html;
}
