package com.kangwon.fino.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LocationAuthRequest {
    private Long userId;        // 사용자의 ID (user_seq)
    private double latitude;    // 클라이언트가 전송하는 현재 위치의 위도
    private double longitude;   // 클라이언트가 전송하는 현재 위치의 경도

}