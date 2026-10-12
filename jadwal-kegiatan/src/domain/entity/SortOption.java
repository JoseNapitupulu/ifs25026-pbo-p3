package domain.entity;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
public enum SortOption {
    /**
     * Mengurutkan berdasarkan hari dari Senin sampai Minggu.
     * Jika hari sama, kegiatan diurutkan berdasarkan waktu.
     */
    DAY(
            Comparator.comparingInt((Activity activity) -> dayIndex(activity.getDay()))
                    .thenComparing(Activity::getTime)
    ),

    TIME(
            Comparator.comparing(Activity::getTime)
    ),
    /**
     * Mengurutkan judul kegiatan dari A sampai Z.
     */
    TITLE_ASC(
            Comparator.comparing(Activity::getTitle, String.CASE_INSENSITIVE_ORDER)
    ),

    /**
     * Mengurutkan judul kegiatan dari Z sampai A.
     */
    TITLE_DESC(
            Comparator.comparing(Activity::getTitle, String.CASE_INSENSITIVE_ORDER)
                    .reversed()
    );

    private final Comparator<Activity> comparator;

    SortOption(Comparator<Activity> comparator) {
        this.comparator = comparator;
    }

    public Comparator<Activity> getComparator() {
        return comparator;
    }

    private static int dayIndex(String day) {
        List<String> days = Arrays.asList(
                "senin", "selasa", "rabu", "kamis",
                "jumat", "sabtu", "minggu"
        );
        int index = days.indexOf(day.toLowerCase());
        return index < 0 ? days.size() : index;
    }
}
