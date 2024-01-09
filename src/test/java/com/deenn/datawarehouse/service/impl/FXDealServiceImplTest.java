package com.deenn.datawarehouse.service.impl;

import com.deenn.datawarehouse.dtos.request.FXDealRequest;
import com.deenn.datawarehouse.dtos.response.APIResponse;
import com.deenn.datawarehouse.dtos.response.FXDealResponse;
import com.deenn.datawarehouse.dtos.response.PaginatedResponse;
import com.deenn.datawarehouse.entity.FXDeal;
import com.deenn.datawarehouse.exception.DealNotFoundException;
import com.deenn.datawarehouse.exception.InvalidDealException;
import com.deenn.datawarehouse.repository.FXDealRepository;
import com.deenn.datawarehouse.utils.FXValidator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.deenn.datawarehouse.constants.AppConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class FXDealServiceImplTest {


    private FXDealRepository repository;


    private FXValidator FXValidator;

    private FXDealServiceImpl fxDealService;

    @BeforeEach
    void setUp() {

        repository  = Mockito.mock(FXDealRepository.class);



        FXValidator = Mockito.mock(FXValidator.class);

        fxDealService = new FXDealServiceImpl(repository, FXValidator);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void saveFXDeal() {

        FXDealRequest request = createSampleFXDealRequest();
        FXDeal fxDeal = createSampleFXDeal();

        when(repository.findByDealUniqueId(request.getDealUniqueId())).thenReturn(Optional.empty());
        doNothing().when(FXValidator).validateFxDealRequest(request);
        when(repository.save(any(FXDeal.class))).thenReturn(fxDeal);


        ResponseEntity<APIResponse<FXDealResponse>> responseEntity = fxDealService.saveFXDeal(request);

        assertThat(responseEntity).isNotNull();
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals("FXDeal save successfully", Objects.requireNonNull(responseEntity.getBody()).getMessage());
        assertTrue(responseEntity.getBody().isSuccess());

        verify(repository, times(1)).save((any(FXDeal.class)));
    }

    @Test
    void saveFXDeal_DuplicateDeal() {
        // Mocking
        FXDealRequest request = createSampleFXDealRequest();
        FXDeal fxDeal = createSampleFXDeal();

        when(repository.findByDealUniqueId(request.getDealUniqueId())).thenReturn(Optional.of(fxDeal));
        doNothing().when(FXValidator).validateFxDealRequest(request);



        try {
            fxDealService.saveFXDeal(request);
        } catch (InvalidDealException e) {

            assertEquals("FXDeal with " + request.getDealUniqueId() + " already exist", e.getMessage());
        }
        verify(repository, times(1)).findByDealUniqueId((request.getDealUniqueId()));
        verify(repository, times(0)).save((any(FXDeal.class)));
    }



    @Test
    void getFXDeal() {

        String dealUniqueId = "123456";
        FXDeal fxDeal = createSampleFXDeal();

        when(repository.findByDealUniqueId(dealUniqueId)).thenReturn(Optional.of(fxDeal));


        ResponseEntity<APIResponse<FXDealResponse>> responseEntity = fxDealService.getFXDeal(dealUniqueId);

        assertThat(responseEntity).isNotNull();
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals("success", Objects.requireNonNull(responseEntity.getBody()).getMessage());
        assertTrue(responseEntity.getBody().isSuccess());
        assertEquals(dealUniqueId, responseEntity.getBody().getPayload().getDealUniqueId());

        verify(repository, times(1)).findByDealUniqueId((dealUniqueId));
        verify(repository, times(0)).save((any(FXDeal.class)));


    }

    @Test
    void getFXDeal_NotFound() {

        String dealUniqueId = "10dss01";

        when(repository.findByDealUniqueId(dealUniqueId)).thenReturn(Optional.empty());


        try {
            fxDealService.getFXDeal(dealUniqueId);
        } catch (DealNotFoundException e) {

            assertEquals("FXDeal with " + dealUniqueId + " is not found", e.getMessage());
        }

        verify(repository, times(1)).findByDealUniqueId((dealUniqueId));
        verify(repository, times(0)).save((any(FXDeal.class)));
    }

    @Test
    void getAllFXDeal() {

        Page<FXDeal> fxDealPage = createSampleFXDealPage();
        when(repository.findAll(any(Pageable.class))).thenReturn(fxDealPage);

        ResponseEntity<APIResponse<PaginatedResponse>> responseEntity = fxDealService.getAllFXDeal(0, 10, DEFAULT_SORT_BY, DEFAULT_SORT_DIRECTION);

        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals("success", Objects.requireNonNull(responseEntity.getBody()).getMessage());
        assertTrue(responseEntity.getBody().isSuccess());

        PaginatedResponse paginatedResponse = responseEntity.getBody().getPayload();
        assertEquals(  ONE, paginatedResponse.getPageNo() );
        assertEquals(10, paginatedResponse.getPageSize());
        assertEquals(fxDealPage.getTotalElements(), paginatedResponse.getTotalElements());
        assertEquals(fxDealPage.getTotalPages(), paginatedResponse.getTotalPages());
        assertEquals(fxDealPage.isLast(), paginatedResponse.isLast());

    }

    @Test
    void getAllFXDeal_SuccessDescending() {
        // Mocking
        Page<FXDeal> fxDealPage = createSampleFXDealPage();
        when(repository.findAll(any(Pageable.class))).thenReturn(fxDealPage);

        // Test
        ResponseEntity<APIResponse<PaginatedResponse>> responseEntity = fxDealService.getAllFXDeal(0, 10, DEFAULT_SORT_BY, "desc");

        // Assertions
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals("success", Objects.requireNonNull(responseEntity.getBody()).getMessage());
        assertTrue(responseEntity.getBody().isSuccess());

        PaginatedResponse paginatedResponse = responseEntity.getBody().getPayload();
        assertEquals(ONE, paginatedResponse.getPageNo());
        assertEquals(10, paginatedResponse.getPageSize());
        assertEquals(fxDealPage.getTotalElements(), paginatedResponse.getTotalElements());
        assertEquals(fxDealPage.getTotalPages(), paginatedResponse.getTotalPages());
        assertEquals(fxDealPage.isLast(), paginatedResponse.isLast());
    }


    private FXDealRequest createSampleFXDealRequest() {
        FXDealRequest request = new FXDealRequest();
        request.setDealUniqueId("123456");
        request.setFromCurrencyCode("USD");
        request.setToCurrencyCode("EUR");
        request.setOrderingCurrency("GBP");
        request.setDealAmountInOrderingCurrency(BigDecimal.valueOf(100.0));
        request.setDealTimestamp("2023-01-01 12:00:00");
        return request;
    }

    private FXDeal createSampleFXDeal() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime parsedDateTime = LocalDateTime.parse("2023-01-01 12:00:00", formatter);

        return FXDeal.builder()
                .dealUniqueId("123456")
                .fromCurrencyCode(Currency.getInstance("USD"))
                .toCurrencyCode(Currency.getInstance("EUR"))
                .orderingCurrency(Currency.getInstance("GBP"))
                .dealAmountInOrderingCurrency(BigDecimal.valueOf(100.0))
                .dealTimestamp(parsedDateTime)
                .build();
    }

    private Page<FXDeal> createSampleFXDealPage() {
        Collection<FXDeal> fxDeals = new ArrayList<>();


        return new PageImpl<>(new ArrayList<>(fxDeals), PageRequest.of(0, 10), fxDeals.size());
    }
}