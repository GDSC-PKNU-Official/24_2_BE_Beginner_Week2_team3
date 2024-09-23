class Song implements Media {
    // 음악 파일을 표현하는 클래스
    private final String title;  // 음악 제목
    private final String artist;  // 아티스트
    private int rating;  // 평점

    public Song(String title, String artist, int rating) {
        this.title = title;
        this.artist = artist;
        this.rating = rating;
    }

    @Override
    public void play() {
        System.out.println(title + " - " + artist + " ..듣는 중..");
    }

    @Override
    public void volumeUp() {
        if (MediaLibrary.CURRENT_VOLUME == MediaLibrary.MAX_VOLUME){
            System.out.println("최대 볼륨입니다.");
        }
        else {
            MediaLibrary.CURRENT_VOLUME += 5;
            System.out.println("볼륨 5 증가: " + MediaLibrary.CURRENT_VOLUME);
        }
    }

    @Override
    public void volumeDown() {
        if (MediaLibrary.CURRENT_VOLUME == MediaLibrary.MIN_VOLUME){
            System.out.println("최저 볼륨입니다.");
        }
        else {
            MediaLibrary.CURRENT_VOLUME -= 5;
            System.out.println("볼륨 5 감소: " + MediaLibrary.CURRENT_VOLUME);
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