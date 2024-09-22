import java.util.*;

interface Media {
    void play();
    void volumeUp();
    void volumeDown();
    String getTitle();
    int getRating();
    void setRating(int rating);
    String getArtist();
}

class Song implements Media {
    private final String title;
    private final String artist;
    private int rating;

    public Song(String title, String artist, int rating) {
        this.title = title;
        this.artist = artist;
        this.rating = rating;
    }

    @Override
    public void play() {
        System.out.println(title + " - " + artist + "..듣는 중..");
    }

    @Override
    public void volumeUp() {
        MediaLibrary.CURRENT_VOLUME++;
        System.out.println("볼륨 증가: " + MediaLibrary.CURRENT_VOLUME);
    }

    @Override
    public void volumeDown() {
        MediaLibrary.CURRENT_VOLUME--;
        System.out.println("볼륨 감소: " + MediaLibrary.CURRENT_VOLUME);
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

class Video implements Media {
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
        System.out.println(title + " - " + artist + "..보는 중..");
    }

    @Override
    public void volumeUp() {
        MediaLibrary.CURRENT_VOLUME++;
        System.out.println("볼륨 증가: " + MediaLibrary.CURRENT_VOLUME);
    }

    @Override
    public void volumeDown() {
        MediaLibrary.CURRENT_VOLUME--;
        System.out.println("볼륨 감소: " + MediaLibrary.CURRENT_VOLUME);
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

class PlayList<T extends Media> {
    private final List<T> medium;

    public PlayList() {
        this.medium = new LinkedList<>();
    }

    public void addMedia(T media) {
        medium.add(media);
        System.out.println("미디어 추가됨: " + media.getTitle());
    }

    public void deleteMedia(String title,String mediaType) {
        try {
            Optional<T> mediaOptional = findMedia(title, mediaType);

            if (mediaOptional.isPresent()) {
                medium.remove(mediaOptional.get());
                System.out.println("미디어 삭제됨: " + title);
            }
        }
        catch (NoSuchElementException e) {
            System.out.println("해당 제목의 미디어가 없어 삭제가 불가능합니다.");
        }

    }

    public Optional<T> findMedia(String title,String mediaType) {
        for (T media : medium) {
            if (media.getTitle().equalsIgnoreCase(title) && media.getClass().getSimpleName().equalsIgnoreCase(mediaType)) {
                return Optional.of(media); //media가 null이 아님을 보장될때 Optional 객체 생성됨
            }
        }
        // 미디어가 없을 경우 예외 발생 java 라이브러리에서 제공하는 예외 사용
        throw new NoSuchElementException("해당 제목의 미디어가 없습니다");
    }


    public void listMedias() {
        if (medium.isEmpty()) {
            System.out.println("재생 목록에 미디어가 없습니다.");
        } else {
            System.out.println("재생 목록:");
            for (T media : medium) {
                System.out.println("- " + media.getTitle());
            }
        }
    }
}

class MediaFactory {         //media factory에서 명령프롬포트에 입력한 media타입을 받아 객체를 생성해줌 만약에 새로운 타입의 객체가 구현됐다면 ex>) 3d video 그르면 case에 추가만하면됨
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

class MediaLibrary {
    private PlayList<Media> mediaPlayList;

    public static int CURRENT_VOLUME = 50;
    public static final int MAX_VOLUME = 100;
    public static final int MIN_VOLUME = 0;

    public MediaLibrary() {
        this.mediaPlayList = new PlayList<>();
    }

    void startLogic() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("명령어를 입력하세요 (예: Song add, Video find): "); ///만약에 새로운 media 타입이 추가되어도 ex) 3D video 그래도 명령프롬포트에서는 유동적이게 받기 가능
            String input = scanner.nextLine();
            String[] tokens = input.split(" ");         //왜냐하면 토크나이징해서 media타입 문자를 따로 받아 저장가능

            if (tokens.length < 2) {
                System.out.println("잘못된 명령어 형식입니다.");
                continue;
            }

            String mediaType = tokens[0];
            String command = tokens[1];

            switch (command.toLowerCase()) {
                case "add":
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
                    System.out.print("삭제할 미디어 제목을 입력하세요: ");
                    String titleToDelete = scanner.nextLine();
                    mediaPlayList.deleteMedia(titleToDelete,mediaType);
                    break;

                case "find":
                    System.out.print("찾을 미디어 제목을 입력하세요: ");
                    String finidTitle = scanner.nextLine();
                    try {
                        Optional<Media> foundMedia = mediaPlayList.findMedia(finidTitle,mediaType);
                        Media findMedia = foundMedia.get();  // 여기서 Optional의 get()을 호출합니다.
                        System.out.println("미디어 찾기 완료: " + findMedia.getTitle() + " - " + findMedia.getArtist());
                    } catch (NoSuchElementException e) {
                        System.out.println(e.getMessage());
                    }
                    break;


                case "list":
                    mediaPlayList.listMedias();
                    break;

                case "play":
                    System.out.print("재생할 미디어 제목을 입력하세요: ");
                    String titleToPlay = scanner.nextLine();
                    Optional<Media> mediaToPlay = mediaPlayList.findMedia(titleToPlay,mediaType);
                    break;

                case "volumeup":
//
                    break;

                case "volumedown":
//
                    break;


                case "get_rating":
                    System.out.print("평점을 알고 싶은 미디어 제목을 입력하세요: ");
                    String titleToFind_0 = scanner.nextLine();
                    try {
                        // 사용자가 입력한 타입의 미디어를 찾고 평점을 가져옴
                        Optional<Media> mediaOp = mediaPlayList.findMedia(titleToFind_0, mediaType);
                        if (mediaOp.isPresent()) {
                            System.out.println("평점: " + mediaOp.get().getRating());
                        }
                    }
                    catch (NoSuchElementException e) {
                        System.out.println("해당 미디어를 찾을 수 없습니다.");
                    }
                    break;

                case "set_rating":
                    System.out.print("평점을 수정하고 싶은 미디어 제목을 입력하세요: ");
                    String titleToFind_01 = scanner.nextLine();
                    try {
                        // 사용자가 입력한 타입의 미디어를 찾고 평점을 가져옴
                        Optional<Media> mediaOp = mediaPlayList.findMedia(titleToFind_01, mediaType);
                        if (mediaOp.isPresent()) {
                            System.out.println("현재 평점: " + mediaOp.get().getRating());
                        }
                    }
                    catch (NoSuchElementException e) {
                        System.out.println("해당 미디어를 찾을 수 없습니다.");
                    }
                    System.out.print("수정할 평점을 입력하세요");
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

public class Main {
    public static void main(String[] args) {
        MediaLibrary mediaLibrary = new MediaLibrary();
        mediaLibrary.startLogic();
    }
}