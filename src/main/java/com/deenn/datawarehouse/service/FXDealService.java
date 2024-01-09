package com.deenn.datawarehouse.service;

import com.deenn.datawarehouse.dtos.request.FXDealRequest;
import com.deenn.datawarehouse.dtos.response.APIResponse;
import com.deenn.datawarehouse.dtos.response.FXDealResponse;
import com.deenn.datawarehouse.dtos.response.PaginatedResponse;
import org.springframework.http.ResponseEntity;

public interface FXDealService {

    ResponseEntity<APIResponse<FXDealResponse>> saveFXDeal(FXDealRequest request);

    ResponseEntity<APIResponse<FXDealResponse>> getFXDeal(String request);

    ResponseEntity<APIResponse<PaginatedResponse>> getAllFXDeal(int pageNo, int pageSize, String sortBy, String sortDir);
}
