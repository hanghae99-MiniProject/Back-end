package com.move.review.controller;

import com.move.review.controller.request.ReviewRequestDto;
import com.move.review.controller.response.ResponseDto;
import com.move.review.service.ReviewService;
import javax.servlet.http.HttpServletRequest;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class ReviewController {

  private final ReviewService reviewService;

  @ApiImplicitParams({
          @ApiImplicitParam(
                  name = "Refresh-Token",
                  required = true,
                  dataType = "string",
                  paramType = "header"
          )
  })

  //Review 생성
  @PostMapping(value = "/api/reviews")
  public ResponseDto<?> createReview(@RequestBody ReviewRequestDto requestDto,
                                     HttpServletRequest request) {
    return reviewService.createReview(requestDto, request);
  }

  @ApiImplicitParams({
          @ApiImplicitParam(
                  name = "Refresh-Token",
                  required = true,
                  dataType = "string",
                  paramType = "header"
          )
  })
  //전체 Review 조회
  @GetMapping(value = "/api/reviews")
  public ResponseDto<?> getAllReview() {
    return reviewService.getAllReview();
  }

  @ApiImplicitParams({
          @ApiImplicitParam(
                  name = "Refresh-Token",
                  required = true,
                  dataType = "string",
                  paramType = "header"
          )
  })

  //Review 상세 조회
  @GetMapping(value = "/api/reviews/{reviewId}")
  public ResponseDto<?> getReview(@PathVariable Long reviewId) {
    return reviewService.getReview(reviewId);
  }

  @ApiImplicitParams({
          @ApiImplicitParam(
                  name = "Refresh-Token",
                  required = true,
                  dataType = "string",
                  paramType = "header"
          )
  })

  //Review 업데이트
  @PutMapping(value = "/api/reviews/{reviewId}")
  public ResponseDto<?> updateReview(@PathVariable Long id, @RequestBody ReviewRequestDto reviewRequestDto,
      HttpServletRequest request) {
    return reviewService.updateReview(id, reviewRequestDto, request);
  }

  @ApiImplicitParams({
          @ApiImplicitParam(
                  name = "Refresh-Token",
                  required = true,
                  dataType = "string",
                  paramType = "header"
          )
  })

  //Review 삭제
  @DeleteMapping(value = "/api/reviews/{reviewId}")
  public ResponseDto<?> deleteReview(@PathVariable Long id,
      HttpServletRequest request) {
    return reviewService.deleteReview(id, request);
  }

}
