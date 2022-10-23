package com.move.review.repository;

import com.move.review.domain.Heart;
import com.move.review.domain.Member;
import com.move.review.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HeartRepository extends JpaRepository<Heart, Long> {
    Optional<Heart> findByMemberAndReview(Member member, Review review);
    int countByReview(Review review);
}
