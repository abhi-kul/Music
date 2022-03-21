package com.example.music.controller;

import static com.example.music.controller.MusicArtistController.MUSIC_ARTIST_PATH;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.music.exception.CoverArtArchiveException;
import com.example.music.exception.MusicBrainzException;
import com.example.music.exception.MusicServiceException;
import com.example.music.exception.WikipediaException;
import com.example.music.service.MusicArtistService;

@RestController
@RequestMapping(MUSIC_ARTIST_PATH)
public class MusicArtistController {

  public static final String MUSIC_ARTIST_PATH = "/musify/music-artist";

  private final MusicArtistService musicArtistService;

  public MusicArtistController(MusicArtistService musicArtistService) {
    this.musicArtistService = musicArtistService;
  }

  @GetMapping("/details/{mbid}")
  public ResponseEntity getMusicArtistDetails(@PathVariable String mbid) {
    try {
      return ResponseEntity.ok().body(musicArtistService.getArtistDetail(mbid));
    } catch (MusicBrainzException
        | WikipediaException
        | CoverArtArchiveException
        | MusicServiceException e) {
      return ResponseEntity.status(e.getResponseCode()).body(e.getMessage());
    }
  }
}
