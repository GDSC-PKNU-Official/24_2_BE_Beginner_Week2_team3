interface Media {
    // Song, Video 등 다양한 미디어 유형들이 공통으로 구현해야 할 메소드를 정의한 인터페이스.
    void play();
    void volumeUp();
    void volumeDown();
    String getTitle();
    int getRating();
    void setRating(int rating);
    String getArtist();
}