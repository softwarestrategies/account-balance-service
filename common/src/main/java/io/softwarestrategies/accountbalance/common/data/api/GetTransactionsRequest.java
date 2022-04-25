package io.softwarestrategies.accountbalance.common.data.api;

import lombok.Data;

import java.time.LocalDate;

@Data
public class GetTransactionsRequest {

    private String type;

    // today, last7days, last30days, custom
    private String dateRangeDescriptor;

    private LocalDate startDate;
    private LocalDate endDate;
}
