package com.kangwon.fino.user.dto.request;

import lombok.*;

@Setter
@Getter
public class MemberRequest {
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Setter
    @Getter
    public static class SignInDto {
        private String username;
        private String password;
        //private String phone;
        //private Integer gender;
        //private Double latitude;
        //private Double longtitude;

    }
}
