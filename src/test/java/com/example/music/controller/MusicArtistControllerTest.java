package com.example.music.controller;

import static com.example.music.controller.MusicArtistController.MUSIC_ARTIST_PATH;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.example.music.exception.CoverArtArchiveException;
import com.example.music.exception.MusicBrainzException;
import com.example.music.exception.MusicServiceException;
import com.example.music.exception.WikipediaException;
import com.example.music.model.ArtistDetail;
import com.example.music.service.MusicArtistService;

@SpringBootTest
@AutoConfigureMockMvc
public class MusicArtistControllerTest {

  @MockBean private MusicArtistService musicArtistService;

  @Autowired private MockMvc mvc;

  @Test
  void getMusicArtistEmpty() throws Exception {
    Mockito.when(musicArtistService.getArtistDetail(Mockito.anyString())).thenReturn(null);
    MvcResult mvcResult =
        mvc.perform(
                get(MUSIC_ARTIST_PATH.concat("/details/mbid"))
                    .contentType(MediaType.APPLICATION_JSON))
            .andReturn();
    assertEquals(200, mvcResult.getResponse().getStatus());
  }

  @Test
  void getMusicArtistSuccess() throws Exception {
    ArtistDetail artistDetail =
        ArtistDetail.builder()
            .mbid("mbid")
            .description("description")
            .country("US")
            .name("Michael jackson")
            .build();

    Mockito.when(musicArtistService.getArtistDetail(Mockito.anyString())).thenReturn(artistDetail);
    MvcResult mvcResult =
        mvc.perform(
                get(MUSIC_ARTIST_PATH.concat("/details/mbid"))
                    .contentType(MediaType.APPLICATION_JSON))
            .andReturn();
    assertEquals(200, mvcResult.getResponse().getStatus());
    assertEquals(
        "{\"mbid\":\"mbid\",\"name\":\"Michael jackson\",\"gender\":null,\"country\":\"US\",\"disambiguation\":null,\"description\":\"description\",\"albums\":null}",
        mvcResult.getResponse().getContentAsString());
  }

  @Test
  void getEmptyCoverArtException() throws Exception {
    Mockito.when(musicArtistService.getArtistDetail(Mockito.anyString()))
        .thenThrow(new CoverArtArchiveException("CoverArtArchive exception found!!!"));
    MvcResult mvcResult =
        mvc.perform(
                get(MUSIC_ARTIST_PATH.concat("/details/mbid"))
                    .contentType(MediaType.APPLICATION_JSON))
            .andReturn();
    assertEquals(400, mvcResult.getResponse().getStatus());
    assertEquals(
        "CoverArtArchive exception found!!!", mvcResult.getResponse().getContentAsString());
  }

  @Test
  void getEmptyMusicBrainzException() throws Exception {
    Mockito.when(musicArtistService.getArtistDetail(Mockito.anyString()))
        .thenThrow(new MusicBrainzException("Musicbrainz exception found!!!"));
    MvcResult mvcResult =
        mvc.perform(
                get(MUSIC_ARTIST_PATH.concat("/details/mbid"))
                    .contentType(MediaType.APPLICATION_JSON))
            .andReturn();
    assertEquals(400, mvcResult.getResponse().getStatus());
    assertEquals("Musicbrainz exception found!!!", mvcResult.getResponse().getContentAsString());
  }

  @Test
  void getEmptyWikipediaException() throws Exception {
    Mockito.when(musicArtistService.getArtistDetail(Mockito.anyString()))
        .thenThrow(new WikipediaException("Wikipedia exception found!!!"));
    MvcResult mvcResult =
        mvc.perform(
                get(MUSIC_ARTIST_PATH.concat("/details/mbid"))
                    .contentType(MediaType.APPLICATION_JSON))
            .andReturn();
    assertEquals(400, mvcResult.getResponse().getStatus());
    assertEquals("Wikipedia exception found!!!", mvcResult.getResponse().getContentAsString());
  }

  @Test
  void getEmptyMusicArtistException() throws Exception {
    Mockito.when(musicArtistService.getArtistDetail(Mockito.anyString()))
        .thenThrow(new MusicServiceException("MusicArtist exception found!!!"));
    MvcResult mvcResult =
        mvc.perform(
                get(MUSIC_ARTIST_PATH.concat("/details/mbid"))
                    .contentType(MediaType.APPLICATION_JSON))
            .andReturn();
    assertEquals(400, mvcResult.getResponse().getStatus());
    assertEquals("MusicArtist exception found!!!", mvcResult.getResponse().getContentAsString());
  }
}
