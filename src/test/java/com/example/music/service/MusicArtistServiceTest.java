package com.example.music.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.example.music.exception.MusicBrainzException;
import com.example.music.model.ArtistDetail;
import com.example.music.model.coverart.CoverArtArchiveDetail;
import com.example.music.model.coverart.Image;
import com.example.music.model.musicbrainz.AlbumDetail;
import com.example.music.model.musicbrainz.MusicBrainzDetails;
import com.example.music.model.musicbrainz.RelationDetail;
import com.example.music.model.musicbrainz.RelationURL;
import com.example.music.model.wikidata.EnWiki;
import com.example.music.model.wikidata.EnWikiDetail;
import com.example.music.model.wikidata.Entity;
import com.example.music.model.wikidata.WikiData;
import com.example.music.model.wikipedia.WikipediaData;
import com.example.music.service.Impl.CoverArtArchiveServiceImpl;
import com.example.music.service.Impl.MusicBrainzServiceImpl;
import com.example.music.service.Impl.WikiDataServiceImpl;
import com.example.music.service.Impl.WikipediaServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class MusicArtistServiceTest {

  @Mock MusicBrainzServiceImpl musicBrainzService;
  @Mock WikiDataServiceImpl wikiDataService;
  @Mock WikipediaServiceImpl wikipediaService;
  @Mock CoverArtArchiveServiceImpl coverArtArchiveService;

  @InjectMocks MusicArtistService musicArtistService;

  @Test(expected = MusicBrainzException.class)
  public void getArtistDetailNoWikiData() {
    when(musicBrainzService.getData(Mockito.anyString()))
        .thenReturn(createMusicBrainzResponseWithoutWikiData());
    musicArtistService.getArtistDetail("mbid");
  }

  @Test
  public void getArtistDetailSuccess() {
    when(musicBrainzService.getData(Mockito.anyString()))
        .thenReturn(createMusicBrainzResponseWithWikiData());
    when(wikiDataService.getData(Mockito.anyString())).thenReturn(createWikiData());
    when(wikipediaService.getData(Mockito.anyString())).thenReturn(createWikipediaData());
    when(coverArtArchiveService.getData(Mockito.anyString()))
        .thenReturn(createCoverArtArchiveDetails());
    ArtistDetail artistDetail = musicArtistService.getArtistDetail("mbid");
    assertEquals(
        "ArtistDetail(mbid=id, name=Name, gender=Male, country=US, disambiguation=Disambiguation, description=extract_html, albums=[Album(id=id, title=title, imageUrl=image)])",
        artistDetail.toString());
  }

  private MusicBrainzDetails createMusicBrainzResponseWithoutWikiData() {

    RelationDetail relationDetail =
        RelationDetail.builder()
            .type("wiki")
            .typeId("typeid")
            .url(RelationURL.builder().id("id").resource("resource").build())
            .build();

    AlbumDetail albumDetail =
        AlbumDetail.builder()
            .id("id")
            .disambiguation("Disambiguation")
            .firstReleaseDate("firstReleaseDate")
            .title("title")
            .primaryType("primaryType")
            .primaryTypeId("primaryTypeId")
            .build();

    MusicBrainzDetails musicBrainzDetails =
        MusicBrainzDetails.builder()
            .id("id")
            .country("US")
            .gender("Male")
            .name("Name")
            .disambiguation("Disambiguation")
            .releaseGroups(Collections.singletonList(albumDetail))
            .relations(Collections.singletonList(relationDetail))
            .build();

    return musicBrainzDetails;
  }

  private MusicBrainzDetails createMusicBrainzResponseWithWikiData() {

    RelationDetail relationDetail =
        RelationDetail.builder()
            .type("wikidata")
            .typeId("typeid")
            .url(RelationURL.builder().id("id").resource("resource").build())
            .build();

    AlbumDetail albumDetail =
        AlbumDetail.builder()
            .id("id")
            .disambiguation("Disambiguation")
            .firstReleaseDate("firstReleaseDate")
            .title("title")
            .primaryType("primaryType")
            .primaryTypeId("primaryTypeId")
            .build();

    MusicBrainzDetails musicBrainzDetails =
        MusicBrainzDetails.builder()
            .id("id")
            .country("US")
            .gender("Male")
            .name("Name")
            .disambiguation("Disambiguation")
            .releaseGroups(Collections.singletonList(albumDetail))
            .relations(Collections.singletonList(relationDetail))
            .build();

    return musicBrainzDetails;
  }

  private WikipediaData createWikipediaData() {
    return WikipediaData.builder().title("title").extract_html("extract_html").build();
  }

  private WikiData createWikiData() {

    EnWikiDetail enWikiDetail =
        EnWikiDetail.builder().site("sire").url("wikipediaurrl").title("wikipediatitle").build();

    EnWiki enWiki = EnWiki.builder().enwiki(enWikiDetail).build();

    Entity entity =
        Entity.builder()
            .id("id")
            .ns(1)
            .lastrevid(123456712l)
            .type("type")
            .sitelinks(enWiki)
            .build();
    java.util.Map<String, Entity> entities = new HashMap<>();
    entities.put("Q2831", entity);
    return WikiData.builder().entities(entities).build();
  }

  private CoverArtArchiveDetail createCoverArtArchiveDetails() {

    Image image =
        Image.builder()
            .image("image")
            .approved(true)
            .back(false)
            .comment("comment")
            .front(true)
            .build();

    return CoverArtArchiveDetail.builder()
        .release("releaseDetails")
        .images(Arrays.asList(image))
        .build();
  }
}
