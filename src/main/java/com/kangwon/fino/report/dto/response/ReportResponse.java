// ReportResponse.java
package com.kangwon.fino.report.dto.response;

import com.kangwon.fino.global.domain.TblReport;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class ReportResponse {

    private Long reportId;
    private Long userId;
    private String reportMonth;
    private BigDecimal totalSpending;
    private List<CategorySpendingDto> categorySpendings;
    private BigDecimal previousMonthTotalSpending;
    private Double spendingChangeRate;
    private String localBusinessIndex;
    private String householdComparisonIndex;
    private LocalDateTime createdAt;

    public static ReportResponse from(TblReport report) {
        return ReportResponse.builder()
                .reportId(report.getReportId())
                .userId(report.getUser().getId())
                .reportMonth(report.getReportMonth())
                .totalSpending(report.getTotalSpending())
                .categorySpendings(
                        report.getCategorySpendings().stream()
                                .map(cs -> CategorySpendingDto.builder()
                                        .categoryName(cs.getCategoryName())
                                        .amount(cs.getAmount())
                                        .build())
                                .collect(Collectors.toList())
                )
                .previousMonthTotalSpending(report.getPreviousMonthTotalSpending())
                .spendingChangeRate(report.getSpendingChangeRate())
                .localBusinessIndex(report.getLocalBusinessIndex())
                .householdComparisonIndex(report.getHouseholdComparisonIndex())
                .build();
    }
}