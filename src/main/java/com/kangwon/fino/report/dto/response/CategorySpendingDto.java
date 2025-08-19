// CategorySpendingDto.java
package com.kangwon.fino.report.dto.response;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class CategorySpendingDto {
    private String categoryName;
    private BigDecimal amount;
}