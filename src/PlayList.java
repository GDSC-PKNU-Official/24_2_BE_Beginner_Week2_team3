import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

class PlayList<T extends Media> {
    // 다양한 미디어 객체들을 저장하고 관리하는 재생 목록 클래스.
    // Song 이나 Video 같은 미디어 객체들을 추가, 삭제, 검색하는 기능을 제공함.

    private final List<T> medium;

    public PlayList() {
        this.medium = new LinkedList<>();
    }

    // 미디어 객체를 재생 목록에 추가
    public void addMedia(T media) {
        medium.add(media);
        System.out.println("미디어 추가됨: " + media.getTitle() + " - " + media.getArtist() + "별점: " + media.getRating());

    }

    // 재생 목록에서 미디어 객체를 제목으로 검색하여 삭제
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


    // 재생 목록에서 제목으로 미디어를 검색
    public Optional<T> findMedia(String title,String mediaType) {
        for (T media : medium) {
            if (media.getTitle().equalsIgnoreCase(title) && media.getClass().getSimpleName().equalsIgnoreCase(mediaType)) {
                return Optional.of(media); //media가 null이 아님을 보장될때 Optional 객체 생성됨
            }
        }
        // 미디어가 없을 경우 예외 발생 java 라이브러리에서 제공하는 예외 사용
        throw new NoSuchElementException("해당 제목의 미디어가 없습니다");
    }


    // 재생 목록에 있는 모든 미디어를 나열
    public <M extends Media> void listMedium(Class<M> mediaClass) {
        if (medium.isEmpty()) {
            System.out.println("재생 목록에 미디어가 없습니다.");
        } else {
            System.out.printf("%-20s %-10s %-5s\n", "제목", "아티스트", "별점");
            for (T m : medium) {
                if (mediaClass.isInstance(m)){
                    System.out.printf("%-20s %-10s %-5d\n", m.getTitle(), m.getArtist(), m.getRating());
                }
            }
        }
    }
}