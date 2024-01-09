package com.deenn.datawarehouse.utils;

import com.deenn.datawarehouse.dtos.request.FXDealRequest;
import com.deenn.datawarehouse.exception.ValidationException;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FXValidatorTest {

    @InjectMocks
    private FXValidator validator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void validateAmount() {

        try {
            validator.validateAmount(new BigDecimal("100.00"));
        } catch (ValidationException e) {
            fail("Unexpected ValidationException: " + e.getMessage());
        }

        assertValidationException(null, "deal_amount_in_ordering_currency must be greater than zero and cannot be null");

        assertValidationException(BigDecimal.ZERO, "deal_amount_in_ordering_currency must be greater than zero and cannot be null");

        assertValidationException(new BigDecimal("-50.00"),  "deal_amount_in_ordering_currency must be greater than zero and cannot be null");

    }

    @Test
    void validateTimestamp() {

        try {
            validator.validateTimestamp("2022-01-08 12:30:45");
        } catch (ValidationException e) {
            fail("Unexpected ValidationException: " + e.getMessage());
        }
        String x = null;
        assertValidationException2(null, "deal_timestamp must not be null");

        assertValidationException2("2022-01-08 12:30:45x", "deal_timestamp is not valid");

    }

    @Test
    void validateFxDealRequest() {


    }

    @Test
    void validateCurrencyCode_ValidCurrency() {

        String validCurrency = "USD";
        String currencyType = "ordering_currency";

        assertDoesNotThrow(() -> validator.validateCurrencyCode(validCurrency, currencyType));
    }

    @Test
    void validateCurrencyCode_NullCurrency() {
        String nullCurrency = null;
        String currencyType = "ordering_currency";

        assertThrows(ValidationException.class, () -> validator.validateCurrencyCode(nullCurrency, currencyType));
    }

    @Test
    void validateCurrencyCode_EmptyCurrency() {

        String emptyCurrency = "";
        String currencyType = "ordering_currency";

        assertThrows(ValidationException.class, () -> validator.validateCurrencyCode(emptyCurrency, currencyType));
    }




    private void assertValidationException2(String timestamp,  String expectedErrorMessage) {
        try {
            validator.validateTimestamp(timestamp);
            fail("Expected ValidationException, but it was not thrown");
        } catch (ValidationException e) {
            assertEquals(expectedErrorMessage, e.getMessage());
        }
    }

    private void assertValidationException(BigDecimal amount, String expectedErrorMessage) {
        try {
            validator.validateAmount(amount);
            fail("Expected ValidationException, but it was not thrown");
        } catch (ValidationException e) {
            assertEquals(expectedErrorMessage, e.getMessage());
        }
    }

    private FXDealRequest createValidFXDealRequest() {
        FXDealRequest request = new FXDealRequest();
        request.setDealUniqueId("123456");
        request.setFromCurrencyCode("USD");
        request.setToCurrencyCode("EUR");
        request.setOrderingCurrency("GBP");
        request.setDealAmountInOrderingCurrency(BigDecimal.valueOf(100.0));
        request.setDealTimestamp("2023-01-01 12:00:00");
        return request;
    }
}