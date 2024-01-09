package com.deenn.datawarehouse.dtos.response;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

@Getter @Setter
@AllArgsConstructor @RequiredArgsConstructor
@Builder
public class FXDealResponse {

    private String dealUniqueId;

    private Currency fromCurrencyCode;

    private Currency orderingCurrency;

    private Currency toCurrencyCode;

    private BigDecimal dealAmountInOrderingCurrency;

    private LocalDateTime dealTimestamp;


}
