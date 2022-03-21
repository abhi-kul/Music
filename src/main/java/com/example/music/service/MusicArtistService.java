package com.example.music.service;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.music.exception.MusicBrainzException;
import com.example.music.exception.MusicServiceException;
import com.example.music.exception.WikiDataException;
import com.example.music.model.Album;
import com.example.music.model.ArtistDetail;
import com.example.music.model.musicbrainz.AlbumDetail;
import com.example.music.model.musicbrainz.MusicBrainzDetails;
import com.example.music.model.musicbrainz.RelationDetail;
import com.example.music.model.musicbrainz.RelationURL;
import com.example.music.model.wikidata.Entity;
import com.example.music.model.wikidata.WikiData;
import com.example.music.model.wikipedia.WikipediaData;
import com.example.music.service.Impl.CoverArtArchiveServiceImpl;
import com.example.music.service.Impl.MusicBrainzServiceImpl;
import com.example.music.service.Impl.WikiDataServiceImpl;
import com.example.music.service.Impl.WikipediaServiceImpl;

@Service
public class MusicArtistService {

  private static final String RELATION_TYPE_WIKIDATA = "wikidata";

  private final MusicBrainzServiceImpl musicBrainzService;
  private final WikiDataServiceImpl wikiDataService;
  private final WikipediaServiceImpl wikipediaService;
  private final CoverArtArchiveServiceImpl coverArtArchiveService;

  public MusicArtistService(
      MusicBrainzServiceImpl musicBrainzService,
      WikiDataServiceImpl wikiDataService,
      WikipediaServiceImpl wikipediaService,
      CoverArtArchiveServiceImpl coverArtArchiveService) {
    this.musicBrainzService = musicBrainzService;
    this.wikiDataService = wikiDataService;
    this.wikipediaService = wikipediaService;
    this.coverArtArchiveService = coverArtArchiveService;
  }

  public ArtistDetail getArtistDetail(String mbid) {
    MusicBrainzDetails artistData = musicBrainzService.getData(mbid);

    List<Album> albums;
    try {
      albums = getAlbums(artistData);
    } catch (InterruptedException | ExecutionException e) {
      throw new MusicServiceException("Internal server error!!!");
    }

    String wikiDataLink =
        artistData.getRelations().stream()
            .filter(relationDetail -> RELATION_TYPE_WIKIDATA.equals(relationDetail.getType()))
            .map(RelationDetail::getUrl)
            .map(RelationURL::getResource)
            .findFirst()
            .orElse(null);

    if (Objects.isNull(wikiDataLink) || wikiDataLink.isEmpty()) {
      throw new MusicBrainzException("No details found for WikiData!!!");
    }

    WikiData wikiData = wikiDataService.getData(wikiDataLink);

    String wikipediaTitle =
        wikiData.getEntities().entrySet().stream()
            .findFirst()
            .map(Entry::getValue)
            .map(Entity::getSitelinks)
            .map(s -> s.getEnwiki().getTitle())
            .orElse(null);

    if (Objects.isNull(wikipediaTitle) || wikipediaTitle.isEmpty()) {
      throw new WikiDataException("No details found for Wikipedia!!!");
    }

    WikipediaData wikipediaData = wikipediaService.getData(wikipediaTitle);
    String extract = wikipediaData.getExtract_html();

    /***
     * Below commented code was working but was taking a lot of time to complete,
     * hence increasing the response time of our service
     *
     */
    //        List<Album> albums = new ArrayList<>();
    //        artistData.getReleaseGroups().forEach(
    //                releaseGroup -> {
    //                    albums.add(new Album(releaseGroup.getId(), releaseGroup.getTitle(),
    //
    // coverArtArchiveService.getCover(releaseGroup.getId()).getImages().stream().filter(Image::isFront).findFirst().map(Image::getImage).orElse(null)));
    //                }
    //        );

    return ArtistDetail.builder()
        .mbid(artistData.getId())
        .gender(artistData.getGender())
        .country(artistData.getCountry())
        .name(artistData.getName())
        .disambiguation(artistData.getDisambiguation())
        .description(extract)
        .albums(albums)
        .build();
  }

  private List<Album> getAlbums(MusicBrainzDetails artistData)
      throws InterruptedException, ExecutionException {
    List<CompletableFuture<Album>> completableFuture =
        artistData.getReleaseGroups().stream()
            .map(this::invokeSearch)
            .filter(Objects::nonNull)
            .collect(toList());

    return waitForCompletion(completableFuture).get();
  }

  private CompletableFuture<Album> invokeSearch(AlbumDetail releaseGroup) {
    return CompletableFuture.supplyAsync(
            () ->
                Album.createAlbum(
                    releaseGroup, coverArtArchiveService.getData(releaseGroup.getId())))
        .exceptionally(ex -> null);
  }

  private static <T> CompletableFuture<List<T>> waitForCompletion(
      List<CompletableFuture<T>> futures) {
    CompletableFuture<Void> allDoneFuture =
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
    return allDoneFuture
        .thenApply(v -> futures.stream().map(CompletableFuture::join).collect(Collectors.toList()))
        .thenApply(
            completeJourneys ->
                completeJourneys.stream().filter(Objects::nonNull).collect(toList()));
  }
}
