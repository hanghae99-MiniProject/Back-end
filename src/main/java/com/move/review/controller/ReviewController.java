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
  @PostMapping(value = "/api/auth/post")
  public ResponseDto<?> createPost(@RequestBody ReviewRequestDto requestDto,
                                   HttpServletRequest request) {
    return reviewService.createPost(requestDto, request);
  }

  @GetMapping(value = "/api/post/{id}")
  public ResponseDto<?> getPost(@PathVariable Long id) {
    return reviewService.getPost(id);
  }

  @GetMapping(value = "/api/post")
  public ResponseDto<?> getAllPosts() {
    return reviewService.getAllPost();
  }

  @PutMapping(value = "/api/auth/post/{id}")
  public ResponseDto<?> updatePost(@PathVariable Long id, @RequestBody ReviewRequestDto reviewRequestDto,
      HttpServletRequest request) {
    return reviewService.updatePost(id, reviewRequestDto, request);
  }

  @DeleteMapping(value = "/api/auth/post/{id}")
  public ResponseDto<?> deletePost(@PathVariable Long id,
      HttpServletRequest request) {
    return reviewService.deletePost(id, request);
  }

}
