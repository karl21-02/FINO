// ReportController.java
package com.kangwon.fino.report.controller;

import com.kangwon.fino.report.dto.response.ReportResponse;
import com.kangwon.fino.report.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    // 모든 보고서 목록 조회
    @GetMapping("/users/{userId}")
    public ResponseEntity<List<ReportResponse>> getReports(@PathVariable Long userId) {
        List<ReportResponse> reports = reportService.getReports(userId);
        return ResponseEntity.ok(reports);
    }

    // 특정 월의 보고서 조회
    @GetMapping("/users/{userId}/month/{reportMonth}")
    public ResponseEntity<ReportResponse> getReportByMonth(
            @PathVariable Long userId,
            @PathVariable String reportMonth) {
        ReportResponse report = reportService.getReport(userId, reportMonth);
        return ResponseEntity.ok(report);
    }


    // 보고서 생성
    @PostMapping("/users/{userId}")
    public ResponseEntity<Void> createReport(@PathVariable Long userId) {
        reportService.createMonthlyReport(userId);
        return ResponseEntity.status(201).build();
    }

    // 보고서 삭제
    @DeleteMapping("/{reportId}")
    public ResponseEntity<Void> deleteReport(@PathVariable Long reportId) {
        reportService.deleteReport(reportId);
        return ResponseEntity.noContent().build();
    }
}