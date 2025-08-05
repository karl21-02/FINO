// ReportResponse.java
package com.kangwon.fino.report.dto.response;

import com.kangwon.fino.global.domain.TblReport;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
public class ReportResponse {

    private Long reportId;
    private Long userId;
    private String reportMonth;
    private BigDecimal totalSpending;
    private String categorySpending;
    private String previousMonthComparison;
    private String localBusinessIndex;
    private String householdComparisonIndex;
    private LocalDateTime createdAt;

    public static ReportResponse from(TblReport report) {
        return ReportResponse.builder()
                .reportId(report.getReportId())
                .userId(report.getUser().getId())
                .reportMonth(report.getReportMonth())
                .totalSpending(report.getTotalSpending())
                .categorySpending(report.getCategorySpending())
                .previousMonthComparison(report.getPreviousMonthComparison())
                .localBusinessIndex(report.getLocalBusinessIndex())
                .householdComparisonIndex(report.getHouseholdComparisonIndex())
                .build();
    }
}