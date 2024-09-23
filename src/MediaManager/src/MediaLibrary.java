import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Scanner;

class MediaLibrary {
    // 전체 미디어 라이브러리를 관리하며, 사용자 명령을 처리하는 로직을 실행함.
    private final PlayList<Media> mediaPlayList;
    private Media currentMedia;
    public static int CURRENT_VOLUME = 50;
    public static final int MAX_VOLUME = 100;
    public static final int MIN_VOLUME = 0;

    public MediaLibrary() {
        this.mediaPlayList = new PlayList<>();
    }

    public void setCurrentMedia(Media media) {
        this.currentMedia = media;
    }

    public Media getCurrentMedia() {
        return this.currentMedia;
    }

    void startLogic() throws IllegalArgumentException {
        // 미리 10개의 미디어를 추가함
        mediaPlayList.addMedia(MediaFactory.createMedia("Song", "HAPPY", "DAY6 (데이식스)", 4));
        mediaPlayList.addMedia(MediaFactory.createMedia("Song", "Supernova", "aespa", 3));
        mediaPlayList.addMedia(MediaFactory.createMedia("Song", "Klaxon", "(여자)아이들", 3));
        mediaPlayList.addMedia(MediaFactory.createMedia("Song", "How Sweet", "NewJeans", 5));
        mediaPlayList.addMedia(MediaFactory.createMedia("Song", "Love wins all", "아이유", 2));

        mediaPlayList.addMedia(MediaFactory.createMedia("Video", "Inception", "Christopher Nolan", 5));
        mediaPlayList.addMedia(MediaFactory.createMedia("Video", "Whiplash", "Damien Chazelle", 5));
        mediaPlayList.addMedia(MediaFactory.createMedia("Video", "Blade Runner", "Ridley Scott", 2));
        mediaPlayList.addMedia(MediaFactory.createMedia("Video", "Joker", "Todd Phillips", 3));
        mediaPlayList.addMedia(MediaFactory.createMedia("Video", "Her", "Spike Jonze", 4));

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("명령어를 입력하세요 (add, delete, find, list, play, volumeup, volumedown, get_rating, set_rating): ");
            String command = scanner.nextLine();
            String mediaType = "";

            switch (command.toLowerCase()) {
                case "add":
                    try {
                        System.out.print("미디어 타입을 입력하세요(song, video): ");
                        mediaType = scanner.nextLine();
                        // 올바른 미디어 타입인지 검증
                        MediaTypeChecker.validateMediaType(mediaType);
                        break;
                    } catch (InvalidMediaTypeException e) {
                        System.out.println(e.getMessage());
                    }
                    System.out.print("미디어 제목을 입력하세요: ");
                    String title = scanner.nextLine();
                    System.out.print("아티스트 이름을 입력하세요: ");
                    String artist = scanner.nextLine();
                    System.out.print("별점을 입력하세요: ");
                    int rating = Integer.parseInt(scanner.nextLine());

                    Media media = MediaFactory.createMedia(mediaType, title, artist, rating);

                    mediaPlayList.addMedia(media);

                    break;

                case "delete":
                    try {
                        System.out.print("미디어 타입을 입력하세요(song, video): ");
                        mediaType = scanner.nextLine();
                        // 올바른 미디어 타입인지 검증
                        MediaTypeChecker.validateMediaType(mediaType);
                        break;
                    } catch (InvalidMediaTypeException e) {
                        System.out.println(e.getMessage());
                    }
                    System.out.print("삭제할 미디어 제목을 입력하세요: ");
                    String titleToDelete = scanner.nextLine();
                    mediaPlayList.deleteMedia(titleToDelete, mediaType);
                    break;

                case "find":
                    try {
                        System.out.print("미디어 타입을 입력하세요(song, video): ");
                        mediaType = scanner.nextLine();
                        // 올바른 미디어 타입인지 검증
                        MediaTypeChecker.validateMediaType(mediaType);
                        break;
                    } catch (InvalidMediaTypeException e) {
                        System.out.println(e.getMessage());
                    }
                    System.out.print("찾을 미디어 제목을 입력하세요: ");
                    String titleToFind = scanner.nextLine();
                    Optional<Media> foundMedia = mediaPlayList.findMedia(titleToFind, mediaType);
                    // foundMedia의 값이 있을 경우에만 미디어 정보를 출력한다.
                    foundMedia.ifPresentOrElse(
                            m -> System.out.println("미디어 찾음: " + m.getTitle() + " - " + m.getArtist()),
                            () -> System.out.println("해당 제목의 미디어가 없습니다.")
                    );
                    break;

                case "list":
                    try {
                        System.out.print("미디어 타입을 입력하세요(song, video): ");
                        mediaType = scanner.nextLine();
                        // 올바른 미디어 타입인지 검증
                        MediaTypeChecker.validateMediaType(mediaType);
                        break;
                    } catch (InvalidMediaTypeException e) {
                        System.out.println(e.getMessage());
                    }
                    if (mediaType.equalsIgnoreCase("song")){
                        mediaPlayList.listMedium(Song.class);
                    } else if (mediaType.equalsIgnoreCase("video")) {
                        mediaPlayList.listMedium(Video.class);
                    } else {
                        System.out.println("지원하지 않는 미디어 타입입니다.");
                    }
                    break;

                case "play":
                    try {
                        System.out.print("미디어 타입을 입력하세요(song, video): ");
                        mediaType = scanner.nextLine();
                        // 올바른 미디어 타입인지 검증
                        MediaTypeChecker.validateMediaType(mediaType);
                        break;
                    } catch (InvalidMediaTypeException e) {
                        System.out.println(e.getMessage());
                    }
                    System.out.print("재생할 미디어 제목을 입력하세요: ");
                    String titleToPlay = scanner.nextLine();
                    try {
                        Optional<Media> mediaToPlay = mediaPlayList.findMedia(titleToPlay, mediaType);
                        Media findMedia = mediaToPlay.get();  // 여기서 Optional의 get()을 호출
                        setCurrentMedia(findMedia);
                        findMedia.play();
                    } catch (NoSuchElementException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                
                    // 볼륨 증가, 감소 로직 생각하기
                case "volumeup":
                    // 미디어 play()할 때마다 currentMedia를 저장하도록 구현해야 한다.
                    if (getCurrentMedia() != null) {
                        getCurrentMedia().volumeUp();  // 현재 재생 중인 미디어의 볼륨 증가
                    } else {
                        System.out.println("재생 중인 미디어가 없습니다.");
                    }
                    break;

                case "volumedown":
                    if (getCurrentMedia() != null) {
                        getCurrentMedia().volumeDown();  // 현재 재생 중인 미디어의 볼륨 증가
                    } else {
                        System.out.println("재생 중인 미디어가 없습니다.");
                    }
                    break;

                case "get_rating":
                    try {
                        System.out.print("미디어 타입을 입력하세요(song, video): ");
                        mediaType = scanner.nextLine();
                        // 올바른 미디어 타입인지 검증
                        MediaTypeChecker.validateMediaType(mediaType);
                        break;
                    } catch (InvalidMediaTypeException e) {
                        System.out.println(e.getMessage());
                    }
                    System.out.print("평점을 알고 싶은 미디어 제목을 입력하세요: ");
                    String titleToFind_0 = scanner.nextLine();
                    try {
                        // 사용자가 입력한 타입의 미디어를 찾고 평점을 가져옴
                        Optional<Media> mediaOp = mediaPlayList.findMedia(titleToFind_0, mediaType);
                        mediaOp.ifPresent(value -> System.out.println("평점: " + value.getRating()));
                    }
                    catch (NoSuchElementException e) {
                        System.out.println("해당 미디어를 찾을 수 없습니다.");
                    }
                    break;

                case "set_rating":
                    try {
                        System.out.print("미디어 타입을 입력하세요(song, video): ");
                        mediaType = scanner.nextLine();
                        // 올바른 미디어 타입인지 검증
                        MediaTypeChecker.validateMediaType(mediaType);
                        break;
                    } catch (InvalidMediaTypeException e) {
                        System.out.println(e.getMessage());
                    }
                    System.out.print("평점을 수정하고 싶은 미디어 제목을 입력하세요: ");
                    String titleToFind_01 = scanner.nextLine();
                    try {
                        // 사용자가 입력한 타입의 미디어를 찾고 평점을 가져옴
                        Optional<Media> mediaOp = mediaPlayList.findMedia(titleToFind_01, mediaType);
                        mediaOp.ifPresent(value -> System.out.println("현재 평점: " + value.getRating()));
                    }
                    catch (NoSuchElementException e) {
                        System.out.println("해당 미디어를 찾을 수 없습니다.");
                    }
                    System.out.print("수정할 평점을 입력하세요 ");
                    String rate = scanner.nextLine();
                    int rates = Integer.parseInt(rate);

                    Optional<Media> mediaOp = mediaPlayList.findMedia(titleToFind_01, mediaType);
                    mediaOp.get().setRating(rates);
                    System.out.println("수정된 평점: " + mediaOp.get().getRating());

                    break;

                case "exit":
                    System.out.println("종료합니다...");
                    scanner.close();
                    return;

                default:
                    System.out.println("알 수 없는 명령어입니다. 다시 시도하세요.");
            }
        }
    }
}
