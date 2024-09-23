class Video implements Media {
    // 비디오 파일을 표현하는 클래스.
    private final String title;
    private final String artist;
    private int rating;

    public Video(String title, String artist, int rating) {
        this.title = title;
        this.artist = artist;
        this.rating = rating;
    }

    @Override
    public void play() {
        System.out.println(title + " - " + artist + " ..보는 중..");
    }

    @Override
    public void volumeUp() {
        MediaLibrary.CURRENT_VOLUME += 10;
        if (MediaLibrary.CURRENT_VOLUME > MediaLibrary.MAX_VOLUME){
            System.out.println("최대 볼륨 초과했습니다. " + MediaLibrary.MAX_VOLUME + " 으로 조정합니다.");
            MediaLibrary.CURRENT_VOLUME = MediaLibrary.MAX_VOLUME;
        }
        else {
            System.out.println("볼륨 10 증가: " + MediaLibrary.CURRENT_VOLUME);
        }
    }

    @Override
    public void volumeDown() {
        MediaLibrary.CURRENT_VOLUME -= 10;
        if (MediaLibrary.CURRENT_VOLUME < MediaLibrary.MIN_VOLUME){
            System.out.println("이미 최저 볼륨입니다. " + MediaLibrary.MIN_VOLUME + " 으로 조정합니다.");
        }
        else {
            System.out.println("볼륨 10 감소: " + MediaLibrary.CURRENT_VOLUME);
        }
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public int getRating() {
        return rating;
    }

    @Override
    public void setRating(int rating) {
        this.rating = rating;
    }

    @Override
    public String getArtist() {
        return artist;
    }
}