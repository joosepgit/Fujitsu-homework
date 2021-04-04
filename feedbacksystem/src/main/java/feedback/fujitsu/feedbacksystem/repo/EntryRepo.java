package feedback.fujitsu.feedbacksystem.repo;

import feedback.fujitsu.feedbacksystem.model.Category;
import feedback.fujitsu.feedbacksystem.model.Entry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EntryRepo extends JpaRepository<Entry, Long> {
}
