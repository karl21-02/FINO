package com.kangwon.fino.user.service;

import com.kangwon.fino.global.domain.TblUser;
import com.kangwon.fino.user.dto.request.UserRequest;
import com.kangwon.fino.user.dto.response.UserResponse;
import com.kangwon.fino.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserLocationServiceImpl implements UserLocationService {

    private final UserRepository userRepository;

    @Value("${location.auth.allowed-distance-meters:150.0}") // 기본값 150m 설정
    private double ALLOWED_DISTANCE_METERS;

    @Override
    public boolean updateUserLocation(UserRequest.UserLocationUpdateDto requestDto) {
        TblUser user = userRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("사용자 ID " + requestDto.getUserId() + "를 찾을 수 없습니다."));

        user.setLatitude(requestDto.getLatitude());
        user.setLongitude(requestDto.getLongitude());

        return true;
    }

    @Override
    public UserResponse.LocationAuthResultDto authenticateByLocation(UserRequest.LocationAuthDto requestDto) {
        TblUser user = userRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("사용자 ID " + requestDto.getUserId() + "를 찾을 수 없습니다."));

        if (user.getLatitude() == null || user.getLongitude() == null) {
            return UserResponse.LocationAuthResultDto.builder()
                    .isAuthenticated(false)
                    .message("사용자의 등록된 기준 위치 정보가 없습니다.")
                    .build();
        }

        double distance = calculateDistance(
                user.getLatitude(), user.getLongitude(),
                requestDto.getLatitude(), requestDto.getLongitude()
        );

        if (distance <= ALLOWED_DISTANCE_METERS) {
            return UserResponse.LocationAuthResultDto.builder()
                    .isAuthenticated(true)
                    .message("위치 인증 성공! 허용 범위 내에 있습니다. (거리: " + String.format("%.2f", distance) + "m)")
                    .build();
        } else {
            return UserResponse.LocationAuthResultDto.builder()
                    .isAuthenticated(false)
                    .message("위치 인증 실패. 허용 범위를 벗어났습니다. (거리: " + String.format("%.2f", distance) + "m)")
                    .build();
        }
    }

    /**
     * 두 위경도 좌표 간의 거리를 미터 단위로 계산하는 하버사인 공식.
     *
     * @param lat1 첫 번째 지점 위도
     * @param lon1 첫 번째 지점 경도
     * @param lat2 두 번째 지점 위도
     * @param lon2 두 번째 지점 경도
     * @return 두 지점 간의 거리 (미터)
     */
    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371000; // 지구 반지름 (미터)

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c; // 미터 단위 거리
    }
}

