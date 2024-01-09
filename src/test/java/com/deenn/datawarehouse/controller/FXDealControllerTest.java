package com.deenn.datawarehouse.controller;

import com.deenn.datawarehouse.dtos.request.FXDealRequest;
import com.deenn.datawarehouse.dtos.response.APIResponse;
import com.deenn.datawarehouse.dtos.response.FXDealResponse;
import com.deenn.datawarehouse.dtos.response.PaginatedResponse;
import com.deenn.datawarehouse.service.FXDealService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.mockito.Mockito.when;


@WebMvcTest(FXDealController.class)
class FXDealControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FXDealService fxDealService;


    private final ObjectMapper objectMapper = new ObjectMapper();



    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void saveFxDeal() throws Exception {

        FXDealRequest request = createSampleFXDealRequest();
        when(fxDealService.saveFXDeal(request)).thenReturn(createSampleResponseEntity());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/fx-deal/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    void getFxDeal() throws Exception {

        String dealId = "123456";
        when(fxDealService.getFXDeal(dealId)).thenReturn(createSampleResponseEntity());
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/fx-deal/{id}", dealId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("success"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.payload.dealUniqueId").value("123456"));
    }

    @Test
    void getAllFxDeals() throws Exception {

        when(fxDealService.getAllFXDeal(0, 10, "id", "asc"))
                .thenReturn(createSampleResponseEntitys());

        // Test
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/fx-deals")
                        .param("pageNo", "0")
                        .param("pageSize", "10")
                        .param("sortBy", "id")
                        .param("sortDir", "asc")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("success"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.payload.content").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.payload.pageNo").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.payload.pageSize").value(10))
                .andExpect(MockMvcResultMatchers.jsonPath("$.payload.totalElements").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.payload.totalPages").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.payload.last").exists());
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


    private ResponseEntity<APIResponse<FXDealResponse>> createSampleResponseEntity() {
        FXDealResponse response = new FXDealResponse();

        response.setDealUniqueId("123456");
        response.setFromCurrencyCode(Currency.getInstance("USD"));
        response.setToCurrencyCode(Currency.getInstance("EUR"));
        response.setOrderingCurrency(Currency.getInstance("GBP"));
        response.setDealAmountInOrderingCurrency(BigDecimal.valueOf(100.0));
        response.setDealTimestamp(LocalDateTime.now());

        APIResponse<FXDealResponse> apiResponse = new APIResponse<>("success", true, response);

        return ResponseEntity.ok(apiResponse);
    }


    private ResponseEntity<APIResponse<PaginatedResponse>> createSampleResponseEntitys() {
        List<FXDealResponse> fxDealResponses = IntStream.rangeClosed(0, 10)
                .mapToObj(i -> createSampleFXDealResponse("123456" + i))
                .collect(Collectors.toList());

        PaginatedResponse paginatedResponse = new PaginatedResponse();
        paginatedResponse.setContent(fxDealResponses);
        paginatedResponse.setPageNo(0);
        paginatedResponse.setPageSize(10);
        paginatedResponse.setTotalElements(fxDealResponses.size());
        paginatedResponse.setTotalPages(10);
        paginatedResponse.setLast(true);

        APIResponse<PaginatedResponse> apiResponse = new APIResponse<>("success", true, paginatedResponse);

        return ResponseEntity.ok(apiResponse);
    }

    private FXDealResponse createSampleFXDealResponse(String dealIdSuffix) {
        FXDealResponse response = new FXDealResponse();
        response.setDealUniqueId("123456" + dealIdSuffix);
        response.setFromCurrencyCode(Currency.getInstance("USD"));
        response.setToCurrencyCode(Currency.getInstance("EUR"));
        response.setOrderingCurrency(Currency.getInstance("GBP"));
        response.setDealAmountInOrderingCurrency(BigDecimal.valueOf(100.0));
        response.setDealTimestamp(LocalDateTime.now());
        return response;
    }
}