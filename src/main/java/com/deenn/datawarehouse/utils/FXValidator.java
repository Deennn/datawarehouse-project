package com.deenn.datawarehouse.utils;

import com.deenn.datawarehouse.dtos.request.FXDealRequest;
import com.deenn.datawarehouse.exception.ValidationException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Currency;

@Component
public class FXValidator {

    public void validateFxDealRequest(FXDealRequest request) {
        validateUniqueId(request.getDealUniqueId());
        validateCurrencyCode(request.getOrderingCurrency(), "ordering_currency");
        validateCurrencyCode(request.getToCurrencyCode(), "to_currency_code");
        validateCurrencyCode(request.getFromCurrencyCode(), "from_currency_code");
        validateToAndFromCurrencyCode(request.getToCurrencyCode(),request.getFromCurrencyCode());
        validateAmount(request.getDealAmountInOrderingCurrency());
        validateTimestamp(request.getDealTimestamp());
    }

    public void validateCurrencyCode(String currency, String currencyType) {
        if(currency == null) throw new ValidationException(currencyType + " cannot be null");
        if(currency.isEmpty()) throw new ValidationException(currencyType + " cannot be empty");
        boolean isPresent = Currency.getAvailableCurrencies().stream()
                .anyMatch(v -> v.getCurrencyCode().equals(currency));
        if(!isPresent) throw new ValidationException(currencyType + " currency code does not exist");
    }

    public void validateAmount(BigDecimal bigDecimal){
        if(bigDecimal == null || bigDecimal.compareTo(BigDecimal.ZERO) <= 0) throw new ValidationException("deal_amount_in_ordering_currency must be greater than zero and cannot be null");
    }

    public void validateTimestamp(String timestamp){
        if(timestamp == null) throw new ValidationException("deal_timestamp must not be null");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        try {
            LocalDateTime parsedDateTime = LocalDateTime.parse(timestamp, formatter);
        } catch (DateTimeParseException e) {
            throw new ValidationException("deal_timestamp is not valid");
        }

    }

    public void validateUniqueId(String dealUniqueId) {
       if (dealUniqueId == null || dealUniqueId.isEmpty()) throw new ValidationException("dealUniqueId cannot be null or empty");
    }

    public void validateToAndFromCurrencyCode(String toCurrencyCode, String fromCurrencyCode) {
        if(fromCurrencyCode.equals(toCurrencyCode)){
            throw new ValidationException("from_currency_code and to_currency_code cannot be same");
        }
    }
}
