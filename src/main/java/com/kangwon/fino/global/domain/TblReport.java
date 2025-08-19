package com.kangwon.fino.global.domain;

import com.kangwon.fino.report.domain.ReportCategorySpending;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "tbl_report")
@EntityListeners(AuditingEntityListener.class)
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

    //@Column(name = "report_category_spending")
    //@Comment("카테고리별 소비")
    @OneToMany(mappedBy = "report", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReportCategorySpending> categorySpendings = new ArrayList<>();

    // @Column(name = "report_previous_month_comparison", columnDefinition = "json")
    // @Comment("전달 대비 비교")
    // private String previousMonthComparison;

    @Column(name = "previous_month_total_spending", nullable = false)
    @Comment("전달 총 소비 금액")
    private BigDecimal previousMonthTotalSpending;

    @Column(name = "spending_change_rate", nullable = false)
    @Comment("소비 변화율")
    private Double spendingChangeRate;

    @Column(name = "report_local_business_index", columnDefinition = "json")
    @Comment("지역 상권 지표")
    private String localBusinessIndex;

    @Column(name = "report_household_comparison_index", columnDefinition = "json")
    @Comment("타 가구 비교 지표")
    private String householdComparisonIndex;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}