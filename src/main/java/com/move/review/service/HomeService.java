package com.move.review.service;

import com.move.review.controller.response.ResponseDto;
import com.move.review.domain.UserDetailsImpl;
import com.move.review.repository.HeartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class HomeService {

    private final HeartRepository heartRepository;

    public Long getMain() {
        Long maxReivewId = heartRepository.heartMaxCount();

        if(maxReivewId == null) {
            return Long.valueOf(1);
        }
         return maxReivewId;
    }
}
