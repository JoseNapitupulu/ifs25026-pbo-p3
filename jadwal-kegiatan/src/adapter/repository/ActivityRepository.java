package adapter.repository;
import domain.entity.Activity;
import domain.repository.IActivityRepository;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
public class ActivityRepository extends InMemoryRepository<Activity, Integer> implements IActivityRepository {
    private final AtomicInteger nextId = new AtomicInteger(1);
    public Activity save(Activity activity) { int id = nextId.getAndIncrement(); return saveWithId(id, new Activity(id, activity.getTitle(), activity.getDay(), activity.getTime())); }
    public List<Activity> findBy(Predicate<Activity> predicate) { return super.findBy(predicate); }
    public List<Activity> findByTitle(String title) { String query = title.toLowerCase(); return findBy(activity -> activity.getTitle().toLowerCase().contains(query)); }
}