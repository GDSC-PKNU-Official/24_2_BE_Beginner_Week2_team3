class MediaFactory {
    // 미디어 타입(Song, Video 등)을 입력받아 해당 객체를 생성하고 반환함.
    // 팩토리 패턴
    public static Media createMedia(String type, String title, String artist, int rating) {
        switch (type.toLowerCase()) {
            case "song":
                return new Song(title, artist, rating);
            case "video":
                return new Video(title, artist, rating);
            default:
                throw new IllegalArgumentException("지원하지 않는 미디어 타입: " + type);
        }
    }
}