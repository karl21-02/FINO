package com.kangwon.fino.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Setter
public class TblUser {

    @Id
    @Column(name = "user_seq")
    @Comment("유저 구분자")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name", length = 10)
    @Comment("유저 이름")
    private String name;

    @Column(name = "user_tel", length = 13)
    @Comment("유저 연락처")
    private String phone;

    @Column(name = "user_gender")
    @Comment("유저 성별")
    private Integer gender;

}
