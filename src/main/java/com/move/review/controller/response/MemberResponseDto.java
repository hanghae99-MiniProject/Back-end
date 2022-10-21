package com.move.review.controller.response;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberResponseDto {
  private Long memberId;
  private String memberName;
  private LocalDateTime createdAt;
  private LocalDateTime modifiedAt;
}
