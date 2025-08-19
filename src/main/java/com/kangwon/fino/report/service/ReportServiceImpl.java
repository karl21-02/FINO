// ReportServiceImpl.java
package com.kangwon.fino.report.service;

import com.kangwon.fino.global.domain.TblReport;
import com.kangwon.fino.global.domain.TblUser;
import com.kangwon.fino.global.exception.BadRequestException;
import com.kangwon.fino.report.domain.ReportCategorySpending;
import com.kangwon.fino.report.dto.response.ReportResponse;
import com.kangwon.fino.report.repository.ReportRepository;
import com.kangwon.fino.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static com.kangwon.fino.global.exception.ExceptionCode.USER_NOT_FOUND;
import static com.kangwon.fino.global.exception.ExceptionCode.REPORT_NOT_FOUND;
import static com.kangwon.fino.global.exception.ExceptionCode.FORBIDDEN_ACCESS;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public void createMonthlyReport(Long userId) {
        TblUser user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
                // .orElseThrow(() -> new BadRequestException(USER_NOT_FOUND));

        // TODO: 실제 소비 데이터 분석 및 보고서 데이터 생성 로직 구현
        String reportMonth = "2025-07"; // 예시 데이터

        // 임시 데이터로 보고서 생성
        TblReport newReport = TblReport.builder()
                .user(user)
                .reportMonth(reportMonth)
                .totalSpending(new BigDecimal("150000.00"))
                .previousMonthTotalSpending(new BigDecimal("120000.00"))
                .spendingChangeRate(0.25)
                // 나머지 필드들
                .build();

        // 카테고리별 소비 데이터 (TODO: 실제 데이터 분석 결과로 대체)
        List<ReportCategorySpending> spendings = List.of(
                ReportCategorySpending.builder().categoryName("식비").amount(new BigDecimal("150000")).report(newReport).build(),
                ReportCategorySpending.builder().categoryName("교통").amount(new BigDecimal("50000")).report(newReport).build(),
                ReportCategorySpending.builder().categoryName("쇼핑").amount(new BigDecimal("50000")).report(newReport).build()
        );

        newReport.getCategorySpendings().addAll(spendings);

        reportRepository.save(newReport);
    }

    @Override
    public List<ReportResponse> getReports(Long userId) {
        TblUser user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
                // .orElseThrow(() -> new BadRequestException(USER_NOT_FOUND));

        List<TblReport> reports = reportRepository.findAllByUserOrderByReportMonthDesc(user);
        return reports.stream()
                .map(ReportResponse::from)
                .collect(Collectors.toList());
    }

    @Override
    public ReportResponse getReport(Long userId, String reportMonth) {
        TblUser user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
                // .orElseThrow(() -> new BadRequestException(USER_NOT_FOUND));


        TblReport report = reportRepository.findByUserAndReportMonth(user, reportMonth)
                .orElseThrow(() -> new EntityNotFoundException("Report not found for month: " + reportMonth));
                // .orElseThrow(() -> new BadRequestException(REPORT_NOT_FOUND));

        return ReportResponse.from(report);
    }

    @Override
    @Transactional
    public void deleteReport(Long reportId, Long userId) {
        TblReport report = reportRepository.findById(reportId)
                .orElseThrow(() -> new EntityNotFoundException("Report not found with id: " + reportId));
                // .orElseThrow(() -> new BadRequestException(REPORT_NOT_FOUND));

        if (!report.getUser().getId().equals(userId)) {
            throw new BadRequestException(FORBIDDEN_ACCESS);
        }

        reportRepository.delete(report);
    }
}