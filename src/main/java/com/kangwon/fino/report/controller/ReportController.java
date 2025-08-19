// ReportController.java
package com.kangwon.fino.report.controller;

import com.kangwon.fino.report.dto.response.ReportResponse;
import com.kangwon.fino.report.service.ReportService;
import com.kangwon.fino.user.dto.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    // 모든 보고서 목록 조회
    @GetMapping
    public ResponseEntity<List<ReportResponse>> getReports(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        List<ReportResponse> reports = reportService.getReports(userDetails.getId());
        return ResponseEntity.ok(reports);
    }


    // 특정 월의 보고서 조회
    @GetMapping("/{reportMonth}")
    public ResponseEntity<ReportResponse> getMyReportByMonth(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable String reportMonth) {
        ReportResponse report = reportService.getReport(userDetails.getId(), reportMonth);
        return ResponseEntity.ok(report);
    }

    // 보고서 생성
    @PostMapping
    public ResponseEntity<Void> createReport(@AuthenticationPrincipal CustomUserDetails userDetails) {
        reportService.createMonthlyReport(userDetails.getId());
        return ResponseEntity.status(201).build();
    }

    // 보고서 삭제
    @DeleteMapping("/{reportId}")
    public ResponseEntity<Void> deleteReport(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long reportId) {
        reportService.deleteReport(reportId, userDetails.getId());
        return ResponseEntity.noContent().build();
    }
}