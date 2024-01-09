package com.deenn.datawarehouse.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter
public class FXDealRequest {

    @JsonProperty("deal_unique_id")
    @NotNull(message = "deal_unique_id cannot be null")
    private String dealUniqueId;

    @JsonProperty("from_currency_code")
    @NotNull(message = "from_currency_code cannot be null")
    private String fromCurrencyCode;

    @JsonProperty("ordering_currency")
    @NotNull(message = "ordering_currency cannot be null")
    private String orderingCurrency;

    @JsonProperty("to_currency_code")
    @NotNull(message = "to_currency_code cannot be null")
    private String toCurrencyCode;

    @JsonProperty("deal_amount_in_ordering_currency")
    @NotNull(message = "deal_amount_in_ordering_currency cannot be null")
    private BigDecimal dealAmountInOrderingCurrency;

    @JsonProperty("deal_timestamp")
    @NotNull(message = "deal_timestamp cannot be null")
    private String dealTimestamp;
}
