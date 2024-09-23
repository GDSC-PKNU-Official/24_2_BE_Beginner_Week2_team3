public class MediaTypeChecker {
    public static void validateMediaType(String mediaType) throws InvalidMediaTypeException {
        if (!mediaType.equalsIgnoreCase("song") && !mediaType.equalsIgnoreCase("video")) {
            throw new InvalidMediaTypeException("잘못된 미디어 타입입니다: " + mediaType + ". song 또는 video만 입력하세요.");
        }
    }
}