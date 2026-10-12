package domain.repository;
import domain.entity.Activity;
import java.util.List;
public interface IActivityRepository extends IRepository<Activity, Integer> { List<Activity> findByTitle(String title); }