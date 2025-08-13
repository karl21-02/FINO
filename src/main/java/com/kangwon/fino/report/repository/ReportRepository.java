// ReportRepository.java
package com.kangwon.fino.report.repository;

import com.kangwon.fino.global.domain.TblReport;
import com.kangwon.fino.global.domain.TblUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReportRepository extends JpaRepository<TblReport, Long> {

    // 특정 사용자의 모든 보고서를 월별로 정렬하여 조회
    List<TblReport> findAllByUserOrderByReportMonthDesc(TblUser user);

    // 특정 사용자의 특정 월 보고서 조회
    Optional<TblReport> findByUserAndReportMonth(TblUser user, String reportMonth);
}