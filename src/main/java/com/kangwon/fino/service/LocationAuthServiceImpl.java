package com.kangwon.fino.service;

import com.kangwon.fino.domain.TblUser;
import com.kangwon.fino.dto.LocationAuthRequest;
import com.kangwon.fino.dto.LocationAuthResponse;
import com.kangwon.fino.repository.TblUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LocationAuthServiceImpl implements LocationAuthService {

    private final TblUserRepository tblUserRepository; // UserRepository 주입

    // 허용 오차 범위 (미터 단위)
    private static final double ALLOWED_DISTANCE_METERS = 150.0;

    @Override
    public LocationAuthResponse authenticateByLocation(LocationAuthRequest request) {
        // 1. 사용자 ID로 TblUser 조회
        Optional<TblUser> userOptional = tblUserRepository.findById(request.getUserId());

        if (userOptional.isEmpty()) {
            return LocationAuthResponse.builder()
                    .isAuthenticated(false)
                    .message("사용자를 찾을 수 없습니다.")
                    .build();
        }

        TblUser user = userOptional.get();

        // 사용자의 등록된 기준 위치가 없으면 인증 불가
        if (user.getLatitude() == null || user.getLongitude() == null) {
            return LocationAuthResponse.builder()
                    .isAuthenticated(false)
                    .message("사용자의 등록된 기준 위치 정보가 없습니다.")
                    .build();
        }

        // 2. 클라이언트 현재 위치와 사용자 등록 위치 간의 거리 계산
        double distance = calculateDistance(
                user.getLatitude(), user.getLongitude(),
                request.getLatitude(), request.getLongitude()
        );

        // 3. 거리 비교 및 인증 판단
        if (distance <= ALLOWED_DISTANCE_METERS) {
            return LocationAuthResponse.builder()
                    .isAuthenticated(true)
                    .message("위치 인증 성공! 허용 범위 내에 있습니다. (거리: " + String.format("%.2f", distance) + "m)")
                    .build();
        } else {
            return LocationAuthResponse.builder()
                    .isAuthenticated(false)
                    .message("위치 인증 실패. 허용 범위를 벗어났습니다. (거리: " + String.format("%.2f", distance) + "m)")
                    .build();
        }
    }

    /**
     * 두 위경도 지점 간의 거리를 미터 단위로 계산합니다. (하버사인 공식)
     * 위도/경도는 도(degree) 단위로 입력되어야 합니다.
     *
     * @param lat1 첫 번째 지점의 위도
     * @param lon1 첫 번째 지점의 경도
     * @param lat2 두 번째 지점의 위도
     * @param lon2 두 번째 지점의 경도
     * @return 두 지점 간의 거리 (미터)
     */
    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371000; // 지구의 반지름 (미터)

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c; // 미터 단위 거리
    }
}