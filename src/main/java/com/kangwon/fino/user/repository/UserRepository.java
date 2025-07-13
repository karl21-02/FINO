package com.kangwon.fino.user.repository;

import com.kangwon.fino.domain.TblUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<TblUser, Long> {
}
