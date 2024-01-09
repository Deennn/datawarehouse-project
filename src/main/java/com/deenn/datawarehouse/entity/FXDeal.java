package com.deenn.datawarehouse.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table(name="fx_deals")
public class FXDeal  extends BaseModel implements Serializable {

    @Column(name = "deal_unique_id",unique = true, nullable=false)
    private String dealUniqueId;

    @Column(name = "from_currency_code", nullable=false)
    private Currency fromCurrencyCode;

    @Column(name = "ordering_currency", nullable=false)
    private Currency orderingCurrency;

    @Column(name = "to_currency_code", nullable=false)
    private Currency toCurrencyCode;

    @Column(name = "amount_ordering_currency", nullable = false)
    private BigDecimal dealAmountInOrderingCurrency;

    @Column(name = "deal_timestamp", nullable = false)
    private LocalDateTime dealTimestamp;
}
