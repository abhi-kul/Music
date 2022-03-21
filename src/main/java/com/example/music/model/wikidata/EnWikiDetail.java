package com.example.music.model.wikidata;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class EnWikiDetail {
  String site;
  String title;
  String url;
  List<String> badges;
}
