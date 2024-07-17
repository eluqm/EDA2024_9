public class Cancion {
    String artist_name;
    String track_name;
    String track_id;
    int popularity;
    int year;
    String genre;
    double danceability;
    double energy;
    int key;
    double loudness;
    int mode;
    double speechiness;
    double acousticness;
    String instrumentalness;
    double liveness;
    double valence;
    double tempo;
    int duration_ms;
    int time_signature;
    public Cancion(String artist_name, String track_name, String track_id, int popularity, int year, String genre,
            double danceability, double energy, int key, double loudness, int mode, double speechiness,
            double acousticness, String instrumentalness, double liveness, double valence, double tempo,
            int duration_ms, int time_signature) {
        this.artist_name = artist_name;
        this.track_name = track_name;
        this.track_id = track_id;
        this.popularity = popularity;
        this.year = year;
        this.genre = genre;
        this.danceability = danceability;
        this.energy = energy;
        this.key = key;
        this.loudness = loudness;
        this.mode = mode;
        this.speechiness = speechiness;
        this.acousticness = acousticness;
        this.instrumentalness = instrumentalness;
        this.liveness = liveness;
        this.valence = valence;
        this.tempo = tempo;
        this.duration_ms = duration_ms;
        this.time_signature = time_signature;
    }
    public Cancion(String artist_name, String track_name, String track_id, int popularity, int year, int duration_ms) {
        this.artist_name = artist_name;
        this.track_name = track_name;
        this.track_id = track_id;
        this.popularity = popularity;
        this.year = year;
        this.duration_ms = duration_ms;
    }
    public Cancion(String artist_name, String track_name) {
        this.artist_name = artist_name;
        this.track_name = track_name;
    }
    
}