package com.kangwon.fino.domain; // 패키지명 확인 (com.kangwon.fino.domain으로 생성했다면 그대로)

import jakarta.persistence.*; // JPA 관련 어노테이션 임포트
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity // 1. 이 클래스가 JPA 엔티티임을 나타냅니다. 데이터베이스 테이블과 매핑됩니다.
@Table(name = "users") // 2. 이 엔티티가 매핑될 데이터베이스 테이블 이름을 지정합니다. (관례상 복수형)
@Getter // Lombok: 모든 필드의 Getter 메서드를 자동으로 생성합니다.
@Setter // Lombok: 모든 필드의 Setter 메서드를 자동으로 생성합니다.
@NoArgsConstructor // Lombok: 기본 생성자를 자동으로 생성합니다. (JPA 필수)
public class User {

    @Id // 3. 이 필드가 테이블의 기본 키(Primary Key)임을 나타냅니다.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 4. 기본 키의 생성 전략을 지정합니다. (MySQL의 AUTO_INCREMENT)
    private Long id; // 사용자 고유 ID

    @Column(nullable = false, unique = true, length = 100) // 5. 테이블의 컬럼 속성을 지정합니다. (NULL 불가능, 유니크, 최대 길이)
    private String email; // 사용자 이메일 (로그인 ID로 사용 가능)

    @Column(nullable = false, length = 255)
    private String password; // 사용자 비밀번호 (암호화 필요)

    @Column(nullable = false, length = 50)
    private String nickname; // 사용자 닉네임

    // 생성자 (필요에 따라 추가)
    public User(String email, String password, String nickname) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }
}