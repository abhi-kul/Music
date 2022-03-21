package com.example.music.model.wikidata;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Entity {
  int pageId;
  int ns;
  String title;
  long lastrevid;
  String modified;
  String type;
  String id;
  EnWiki sitelinks;
}
