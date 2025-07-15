package com.kangwon.fino.repository;

import com.kangwon.fino.domain.TblUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface TblUserRepository extends JpaRepository<TblUser, Long> { }