package usecase;
import domain.entity.Activity;
import domain.entity.SortOption;
import domain.exception.EntityNotFoundException;
import domain.exception.ValidationException;
import domain.repository.IActivityRepository;
import java.util.ArrayList;
import java.util.List;
public class ActivityUseCase {
    private final IActivityRepository repository;
    public ActivityUseCase(IActivityRepository repository) { this.repository = repository; }
    public Activity add(String title, String day, String time) throws ValidationException { validate(title, "Judul"); validate(day, "Hari"); validate(time, "Waktu"); return repository.save(new Activity(null, title.trim(), day.trim(), time.trim())); }
    public Activity update(int id, String title, String day, String time) throws ValidationException, EntityNotFoundException {
        Activity activity = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Activity dengan ID " + id + " tidak ditemukan."));
        if (title != null && !title.trim().isEmpty()) { validate(title, "Judul"); activity.changeTitle(title.trim()); }
        if (day != null && !day.trim().isEmpty()) { validate(day, "Hari"); activity.changeDay(day.trim()); }
        if (time != null && !time.trim().isEmpty()) { validate(time, "Waktu"); activity.changeTime(time.trim()); }
        return activity;
    }
    public List<Activity> search(String title) throws ValidationException { validate(title, "Judul"); return repository.findByTitle(title.trim()); }
    public List<Activity> sort(SortOption option) { List<Activity> result = new ArrayList<>(repository.findAll()); result.sort(option.getComparator().thenComparing(Activity::getId)); return result; }
    public List<Activity> list() { return repository.findAll(); }
    public void delete(int id) throws EntityNotFoundException { repository.deleteById(id); }
    private void validate(String value, String label) throws ValidationException { if (value == null || value.trim().isEmpty()) throw new ValidationException(label + " tidak boleh kosong."); }
}