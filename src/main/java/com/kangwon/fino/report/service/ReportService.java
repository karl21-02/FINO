// ReportService.java
package com.kangwon.fino.report.service;

import com.kangwon.fino.report.dto.response.ReportResponse;

import java.util.List;

public interface ReportService {
    // 모든 보고서 조회
    List<ReportResponse> getReports(Long userId);

    // 특정 보고서 조회
    ReportResponse getReport(Long userId, String reportMonth);


    // 보고서 생성
    void createMonthlyReport(Long userId);

    // 보고서 삭제 
    void deleteReport(Long reportId, Long userId);
}