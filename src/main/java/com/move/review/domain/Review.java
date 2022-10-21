package com.move.review.domain;

import com.move.review.controller.request.ReviewRequestDto;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Review extends Timestamped {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long reviewId;

  @JoinColumn(name = "memberID", nullable = false)
  @ManyToOne(fetch = FetchType.LAZY)
  private Member member;

  @Column(nullable = false)
  private String image;

  @Column(nullable = false)
  private String movieTitle; // 영화제목

  @Column(nullable = false)
  private String actor; // 배우

  @Column(nullable = false)
  private String rating; // 별점

  @Column(nullable = false)
  private String reviewTitle;

  @Column(nullable = false)
  private String reviewContent;

  @Column(nullable = false)
  private String memberName;

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Comment> comments;

  public void update(ReviewRequestDto reviewRequestDto) {
    this.reviewTitle = reviewRequestDto.getReviewTitle();
    this.reviewContent = reviewRequestDto.getReviewContent();
  }

  public boolean validateMember(Member member) {
    return !this.member.equals(member);
  }

}
