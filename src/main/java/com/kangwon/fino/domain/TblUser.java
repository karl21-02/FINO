package com.kangwon.fino.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

@Entity
// PROTECTED --> PUBLIC 으로 변경
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Builder
@Getter
@Setter
public class TblUser {

    @Id
    //@Column(name = "user_id")
    //@Comment("유저 구분자")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@Column(name = "user_name", length = 10)
    //@Comment("유저 이름")
    private String username;

    //@Column(name = "user_pwd", length = 100)
    //@Comment("유저 비밀번호")
    private String password;

    private String role;

    //@Column(name = "user_tel", length = 13)
    //@Comment("유저 연락처")
    //private String phone;

    //@Column(name = "user_gender")
    //@Comment("유저 성별")
    //private Integer gender;

    //@Column(name = "user_latitude")
    //@Comment("유저의 등록된 위도 (기준 위치)")
    private Double latitude;

    //@Column(name = "user_longitude")
    //@Comment("유저의 등록된 경도 (기준 위치)")
    private Double longitude;

}
