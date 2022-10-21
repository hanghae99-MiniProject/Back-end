package com.move.review.service;

import com.move.review.controller.response.CommentResponseDto;
import com.move.review.controller.response.ReviewResponseDto;
import com.move.review.domain.Comment;
import com.move.review.domain.Member;
import com.move.review.domain.Review;
import com.move.review.controller.request.ReviewRequestDto;
import com.move.review.controller.response.ResponseDto;
import com.move.review.jwt.TokenProvider;
import com.move.review.repository.CommentRepository;
import com.move.review.repository.ReviewRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewService {

  private final ReviewRepository reviewRepository;
  private final CommentRepository commentRepository;

  private final TokenProvider tokenProvider;

  @Transactional
  public ResponseDto<?> createPost(ReviewRequestDto requestDto, HttpServletRequest request) {
    if (null == request.getHeader("Refresh-Token")) {
      return ResponseDto.fail("MEMBER_NOT_FOUND",
          "로그인이 필요합니다.");
    }

    if (null == request.getHeader("Authorization")) {
      return ResponseDto.fail("MEMBER_NOT_FOUND",
          "로그인이 필요합니다.");
    }

    Member member = validateMember(request);
    if (null == member) {
      return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
    }

    Review review = Review.builder()
        .reviewTitle(requestDto.getReviewTitle())
        .reviewContent(requestDto.getReviewContent())
        .member(member)
        .build();
    reviewRepository.save(review);
    return ResponseDto.success(
        ReviewResponseDto.builder()
            .reviewId(review.getReviewId())
            .reviewTitle(review.getReviewTitle())
            .reviewContent(review.getReviewContent())
            .memberName(review.getMember().getMemberName())
            .createdAt(review.getCreatedAt())
            .modifiedAt(review.getModifiedAt())
            .build()
    );
  }

  @Transactional(readOnly = true)
  public ResponseDto<?> getPost(Long id) {
    Review review = isPresentPost(id);
    if (null == review) {
      return ResponseDto.fail("NOT_FOUND", "존재하지 않는 게시글 id 입니다.");
    }

    List<Comment> commentList = commentRepository.findAllByReview(review);
    List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();

    for (Comment comment : commentList) {
      commentResponseDtoList.add(
          CommentResponseDto.builder()
              .commentId(comment.getCommentId())
              .memberName(comment.getMember().getMemberName())
              .comment(comment.getComment())
              .createdAt(comment.getCreatedAt())
              .modifiedAt(comment.getModifiedAt())
              .build()
      );
    }

    return ResponseDto.success(
        ReviewResponseDto.builder()
            .reviewId(review.getReviewId())
            .reviewTitle(review.getReviewTitle())
            .reviewContent(review.getReviewContent())
            .commentResponseDtoList(commentResponseDtoList)
            .memberName(review.getMember().getMemberName())
            .createdAt(review.getCreatedAt())
            .modifiedAt(review.getModifiedAt())
            .build()
    );
  }

  @Transactional(readOnly = true)
  public ResponseDto<?> getAllPost() {
    return ResponseDto.success(reviewRepository.findAllByOrderByModifiedAtDesc());
  }

  @Transactional
  public ResponseDto<Review> updatePost(Long id, ReviewRequestDto requestDto, HttpServletRequest request) {
    if (null == request.getHeader("Refresh-Token")) {
      return ResponseDto.fail("MEMBER_NOT_FOUND",
          "로그인이 필요합니다.");
    }

    if (null == request.getHeader("Authorization")) {
      return ResponseDto.fail("MEMBER_NOT_FOUND",
          "로그인이 필요합니다.");
    }

    Member member = validateMember(request);
    if (null == member) {
      return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
    }

    Review review = isPresentPost(id);
    if (null == review) {
      return ResponseDto.fail("NOT_FOUND", "존재하지 않는 게시글 id 입니다.");
    }

    if (review.validateMember(member)) {
      return ResponseDto.fail("BAD_REQUEST", "작성자만 수정할 수 있습니다.");
    }

    review.update(requestDto);
    return ResponseDto.success(review);
  }

  @Transactional
  public ResponseDto<?> deletePost(Long id, HttpServletRequest request) {
    if (null == request.getHeader("Refresh-Token")) {
      return ResponseDto.fail("MEMBER_NOT_FOUND",
          "로그인이 필요합니다.");
    }

    if (null == request.getHeader("Authorization")) {
      return ResponseDto.fail("MEMBER_NOT_FOUND",
          "로그인이 필요합니다.");
    }

    Member member = validateMember(request);
    if (null == member) {
      return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
    }

    Review review = isPresentPost(id);
    if (null == review) {
      return ResponseDto.fail("NOT_FOUND", "존재하지 않는 게시글 id 입니다.");
    }

    if (review.validateMember(member)) {
      return ResponseDto.fail("BAD_REQUEST", "작성자만 삭제할 수 있습니다.");
    }

    reviewRepository.delete(review);
    return ResponseDto.success("delete success");
  }

  @Transactional(readOnly = true)
  public Review isPresentPost(Long id) {
    Optional<Review> optionalPost = reviewRepository.findById(id);
    return optionalPost.orElse(null);
  }

  @Transactional
  public Member validateMember(HttpServletRequest request) {
    if (!tokenProvider.validateToken(request.getHeader("Refresh-Token"))) {
      return null;
    }
    return tokenProvider.getMemberFromAuthentication();
  }

}
