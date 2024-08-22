package bookreviewjava.bookreviewjava.services;

import bookreviewjava.bookreviewjava.models.BookReview;
import bookreviewjava.bookreviewjava.repositories.BookReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BookReviewService {

    @Autowired
    private BookReviewRepository bookReviewRepository;

    public Page<BookReview> findPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return bookReviewRepository.findAll(pageable);
    }

}
