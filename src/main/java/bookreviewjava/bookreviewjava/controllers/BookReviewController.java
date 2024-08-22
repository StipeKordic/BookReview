package bookreviewjava.bookreviewjava.controllers;

import bookreviewjava.bookreviewjava.models.BookReview;
import bookreviewjava.bookreviewjava.models.User;
import bookreviewjava.bookreviewjava.models.UserDetails;
import bookreviewjava.bookreviewjava.repositories.BookReviewRepository;
import bookreviewjava.bookreviewjava.repositories.UserRepository;
import bookreviewjava.bookreviewjava.services.BookReviewService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;


import java.util.List;

    @Controller
    public class BookReviewController {

        @Autowired
        private BookReviewRepository reviewRepository;
        @Autowired
        private BookReviewService bookReviewService;

        @Autowired
        private UserRepository userRepository;


        @GetMapping("/home")
        public String getAllBookReviews(
                @RequestParam(value = "page", defaultValue = "0") Integer page,
                @RequestParam(value = "size", defaultValue = "5") Integer size,
                Model model) {

            if (page < 0) {
                page = 0;
            }
            if (size <= 0) {
                size = 5;
            }

            Page<BookReview> bookReviewPage = bookReviewService.findPaginated(page, size);

            model.addAttribute("book_reviews", bookReviewPage.getContent());
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", bookReviewPage.getTotalPages());
            model.addAttribute("size", size);

            return "home";
        }

        @GetMapping("/profile")
        public String getCurrentUserReviews(@AuthenticationPrincipal UserDetails userDetails, Model model) {
            model.addAttribute("bookReview", new BookReview());
            List<BookReview> userReviews = reviewRepository.findByUserId(userDetails.getId());
            model.addAttribute("user_reviews", userReviews);
            return "profile";
        }


        @PostMapping("/book_reviews/add")
        public String addReview(@Valid BookReview review, BindingResult bindingResult, Model model, @AuthenticationPrincipal UserDetails userDetails) {
            if (bindingResult.hasErrors()) {
                model.addAttribute("book_reviews", reviewRepository.findAll());
                return "profile";
            }
            User user = userRepository.findById(userDetails.getId()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
            review.setUser(user);
            reviewRepository.save(review);
            return "redirect:/profile";
        }
        @PostMapping("/book_reviews/delete/{id}")
        public String deleteReview(@PathVariable Long id) {
            reviewRepository.deleteById(id);
            return "redirect:/profile";
        }

        @PostMapping("/book_reviews/edit")
        public String editReview(@ModelAttribute BookReview bookReview, @AuthenticationPrincipal UserDetails userDetails) {
            User user = userRepository.findById(userDetails.getId()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
            BookReview existingReview = reviewRepository.findByIdAndUser(bookReview.getId(), user)
                    .orElseThrow(() -> new IllegalArgumentException("Review not found or not authorized"));

            existingReview.setBookTitle(bookReview.getBookTitle());
            existingReview.setBookAuthor(bookReview.getBookAuthor());
            existingReview.setReview(bookReview.getReview());
            existingReview.setReviewText(bookReview.getReviewText());

            reviewRepository.save(existingReview);
            return "redirect:/profile";
        }
    }


