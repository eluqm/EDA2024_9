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
    public String getArtist_name() {
        return artist_name;
    }
    public void setArtist_name(String artist_name) {
        this.artist_name = artist_name;
    }
    public String getTrack_name() {
        return track_name;
    }
    public void setTrack_name(String track_name) {
        this.track_name = track_name;
    }
    public String getTrack_id() {
        return track_id;
    }
    public void setTrack_id(String track_id) {
        this.track_id = track_id;
    }
    public int getPopularity() {
        return popularity;
    }
    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }
    public int getYear() {
        return year;
    }
    public void setYear(int year) {
        this.year = year;
    }
    public String getGenre() {
        return genre;
    }
    public void setGenre(String genre) {
        this.genre = genre;
    }
    public double getDanceability() {
        return danceability;
    }
    public void setDanceability(double danceability) {
        this.danceability = danceability;
    }
    public double getEnergy() {
        return energy;
    }
    public void setEnergy(double energy) {
        this.energy = energy;
    }
    public int getKey() {
        return key;
    }
    public void setKey(int key) {
        this.key = key;
    }
    public double getLoudness() {
        return loudness;
    }
    public void setLoudness(double loudness) {
        this.loudness = loudness;
    }
    public int getMode() {
        return mode;
    }
    public void setMode(int mode) {
        this.mode = mode;
    }
    public double getSpeechiness() {
        return speechiness;
    }
    public void setSpeechiness(double speechiness) {
        this.speechiness = speechiness;
    }
    public double getAcousticness() {
        return acousticness;
    }
    public void setAcousticness(double acousticness) {
        this.acousticness = acousticness;
    }
    public String getInstrumentalness() {
        return instrumentalness;
    }
    public void setInstrumentalness(String instrumentalness) {
        this.instrumentalness = instrumentalness;
    }
    public double getLiveness() {
        return liveness;
    }
    public void setLiveness(double liveness) {
        this.liveness = liveness;
    }
    public double getValence() {
        return valence;
    }
    public void setValence(double valence) {
        this.valence = valence;
    }
    public double getTempo() {
        return tempo;
    }
    public void setTempo(double tempo) {
        this.tempo = tempo;
    }
    public int getDuration_ms() {
        return duration_ms;
    }
    public void setDuration_ms(int duration_ms) {
        this.duration_ms = duration_ms;
    }
    public int getTime_signature() {
        return time_signature;
    }
    public void setTime_signature(int time_signature) {
        this.time_signature = time_signature;
    }
}