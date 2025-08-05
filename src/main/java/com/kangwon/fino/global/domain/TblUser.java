package com.kangwon.fino.global.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
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

    @Column(name = "user_latitude")
    @Comment("유저의 등록된 위도 (기준 위치)")
    private Double latitude;

    @Column(name = "user_longitude")
    @Comment("유저의 등록된 경도 (기준 위치)")
    private Double longitude;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

}
