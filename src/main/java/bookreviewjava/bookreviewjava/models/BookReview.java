package bookreviewjava.bookreviewjava.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;


@Entity
@Table(name="book_reviews")
public class BookReview {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Size(min = 2, max = 50, message = "Book Title needs to be between 2 and 50 characters.") // Anotator za provjeru duljine unesenog teksta.
        @Column(nullable = false, length = 50)
        private String bookTitle;

        @Size(min = 2, max = 50, message = "Book Author needs to be between 2 and 50 characters.") // Anotator za provjeru duljine unesenog teksta.
        @Column(nullable = false, length = 50)
        private String bookAuthor;


        @Size(min = 10, max = 5000, message = "Review text needs to have between 10 and 5000 characters.") // Anotator za provjeru duljine unesenog teksta.
        @Column(nullable = false, length = 5000)
        private String reviewText;

        @DecimalMin(value = "1.00", message = "Rating can not be less than 1.")
        @DecimalMax(value = "5.00", message = "Rating can not be more than 5")
        @Digits(integer = 1, fraction = 2, message = "Rating can have no more than 2 decimal places.")
        private BigDecimal review;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "user_id", nullable = false)
        private User user;


        public BookReview(String bookTitle, String bookAuthor, String reviewText, BigDecimal review) {
                this.bookTitle = bookTitle;
                this.bookAuthor = bookAuthor;
                this.reviewText = reviewText;
                this.review = review;
        }

        public BookReview() {
        }
        public Long getId() {
                return id;
        }
        public void setId(Long id) {
                this.id = id;
        }
        public String getBookTitle() {
                return bookTitle;
        }
        public void setBookTitle(String bookTitle) {
                this.bookTitle = bookTitle;
        }
        public String getBookAuthor() {
                return bookAuthor;
        }
        public void setBookAuthor(String bookAuthor) {
                this.bookAuthor = bookAuthor;
        }
        public String getReviewText() {
                return reviewText;
        }
        public void setReviewText(String reviewText) {
                this.reviewText = reviewText;
        }
        public BigDecimal getReview() {
                return review;
        }
        public void setReview(BigDecimal review) {
                this.review = review;
        }
        public void setUser(User user) {
                this.user = user;
        }
        public User getUser() {
                return user;
        }


}
