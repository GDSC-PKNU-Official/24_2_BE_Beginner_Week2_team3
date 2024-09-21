import java.util.LinkedList;
import java.util.List;
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
        System.out.println("미디어 추가됨: " + media.getTitle() + " - " + media.getArtist());
    }

    // 재생 목록에서 미디어 객체를 제목으로 검색하여 삭제
    //public void deleteMedia(String title) {
        // todo
    //    System.out.println("미디어 삭제됨: " + title);
    //}

    // 재생 목록에서 제목으로 미디어를 검색
    //public Optional<T> findMedia(String title) {
        //todo
    //    return
    //}

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