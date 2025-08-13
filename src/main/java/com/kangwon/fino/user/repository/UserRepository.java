package com.kangwon.fino.user.repository;

import com.kangwon.fino.global.domain.TblUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface UserRepository extends JpaRepository<TblUser, Long> {

    Boolean existsByUsername(String username);

    // username으로 tbluser에서 회원 조회용 메소드
    TblUser findByUsername(String username);
}
