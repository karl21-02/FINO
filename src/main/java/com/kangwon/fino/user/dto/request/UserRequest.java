package com.kangwon.fino.user.dto.request;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

public class UserRequest {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    public static class LocationAuthDto {
        @NotNull(message = "사용자 ID는 필수입니다.")
        private Long userId;

        @NotNull(message = "위도는 필수입니다.")
        @DecimalMin(value = "-90.0", message = "위도는 -90.0보다 작을 수 없습니다.")
        @DecimalMax(value = "90.0", message = "위도는 90.0보다 클 수 없습니다.")
        private Double latitude;

        @NotNull(message = "경도는 필수입니다.")
        @DecimalMin(value = "-180.0", message = "경도는 -180.0보다 작을 수 없습니다.")
        @DecimalMax(value = "180.0", message = "경도는 180.0보다 클 수 없습니다.")
        private Double longitude;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    public static class UserLocationUpdateDto {
        @NotNull(message = "사용자 ID는 필수입니다.")
        private Long userId;

        @NotNull(message = "위도는 필수입니다.")
        @DecimalMin(value = "-90.0", message = "위도는 -90.0보다 작을 수 없습니다.")
        @DecimalMax(value = "90.0", message = "위도는 90.0보다 클 수 없습니다.")
        private Double latitude;

        @NotNull(message = "경도는 필수입니다.")
        @DecimalMin(value = "-180.0", message = "경도는 -180.0보다 작을 수 없습니다.")
        @DecimalMax(value = "180.0", message = "경도는 180.0보다 클 수 없습니다.")
        private Double longitude;
    }
}