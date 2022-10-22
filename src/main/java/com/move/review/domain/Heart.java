package com.move.review.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Heart {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long heartId;

    @ManyToOne
    @JoinColumn(name="memberId")
    private Member member;

    @ManyToOne
    @JoinColumn(name="reviewId")
    private Review review;

}
