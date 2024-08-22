package bookreviewjava.bookreviewjava.repositories;

import bookreviewjava.bookreviewjava.models.BookReview;
import bookreviewjava.bookreviewjava.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookReviewRepository extends JpaRepository<BookReview, Long> {
    List<BookReview> findByUserId(Long userId);
    Optional<BookReview> findByIdAndUser(Long id, User user);
}