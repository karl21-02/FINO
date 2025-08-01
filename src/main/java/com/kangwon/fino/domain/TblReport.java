package com.kangwon.fino.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_report")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Setter
public class TblReport {

    @Id
    @Column(name = "report_seq")
    @Comment("보고서 구분자")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private TblUser user; // FK 관계 설정

    @Column(name = "report_month", length = 7, nullable = false)
    @Comment("보고서 생성 월")
    private String reportMonth; // 예: "2025-07"

    @Column(name = "report_total_spending", nullable = false)
    @Comment("총 소비 금액")
    private BigDecimal totalSpending;

    @Column(name = "report_category_spending", columnDefinition = "jsonb")
    @Comment("카테고리별 소비")
    private String categorySpending;

    @Column(name = "report_previous_month_comparison", columnDefinition = "jsonb")
    @Comment("전달 대비 비교")
    private String previousMonthComparison;

    @Column(name = "report_local_business_index", columnDefinition = "jsonb")
    @Comment("지역 상권 지표")
    private String localBusinessIndex;

    @Column(name = "report_household_comparison_index", columnDefinition = "jsonb")
    @Comment("타 가구 비교 지표")
    private String householdComparisonIndex;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}