# Music Service
Music service to provide artist data.

# Tech Stack
Java SE 11
Springboot v2.6.4
Maven
JUnit
Lombok

# Running the project
mvn spring-boot:run

# Third party Services used
1.) MusicBrainz Service to fetch basic data related to artist.
2.) Wikipedia Service to get detailed description.
3.) CoverArtArchive to get cover images for released albums.

# Endpoints

URL : /musify/music-artist/musicBrainzDetails/{mbid}

Method : GET

Ex : http://localhost:8080/musify/music-artist/musicBrainzDetails/f27ec8db-af05-4f36-916e-3d57f91ecf5e

Response :
{
"mbid": "f27ec8db-af05-4f36-916e-3d57f91ecf5e",
"name": "Michael Jackson",
"gender": "Male",
"country": "US",
"disambiguation": "“King of Pop”",
"description": "<b>Michael Joseph Jackson</b> (August 29, 1958 – June 25, 2009) was an American singer, songwriter, and dancer."
"albums": [
{
"id": "97e0014d-a267-33a0-a868-bb4e2552918a",
"title": "Got to Be There",
"imageUrl": "http://coverartarchive.org/release/7d65853b-d547-4885-86a6-51df4005768c/1619682960.jpg"
}
]
}


# Future Improvements
1.) Add caching to improve response time and resiliency.

2.) Add retry mechanism as well to improve the resiliency for our application.

3.) Improve test coverage.

4.) Add logger
