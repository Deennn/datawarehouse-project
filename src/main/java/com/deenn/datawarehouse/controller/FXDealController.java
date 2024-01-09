package com.deenn.datawarehouse.controller;

import com.deenn.datawarehouse.dtos.request.FXDealRequest;
import com.deenn.datawarehouse.dtos.response.APIResponse;
import com.deenn.datawarehouse.dtos.response.FXDealResponse;
import com.deenn.datawarehouse.dtos.response.PaginatedResponse;
import com.deenn.datawarehouse.service.FXDealService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.deenn.datawarehouse.constants.AppConstants.*;

@RestController
@RequestMapping(path = "api/v1")
@RequiredArgsConstructor
public class FXDealController {

    private final FXDealService fxDealService;


    @PostMapping("fx-deal/save")
    public ResponseEntity<APIResponse<FXDealResponse>> saveFxDeal(@RequestBody @Valid FXDealRequest request){
       return fxDealService.saveFXDeal(request);
    }


    @GetMapping("fx-deal/{id}")
    public ResponseEntity<APIResponse<FXDealResponse>> getFxDeal(@PathVariable String id) {
       return fxDealService.getFXDeal(id);
    }

    @GetMapping("fx-deals")
    public ResponseEntity<APIResponse<PaginatedResponse>>  getAllFxDeals(
            @RequestParam(value = "pageNo", defaultValue = DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = DEFAULT_SORT_DIRECTION, required = false) String sortDir){
        return fxDealService.getAllFXDeal(pageNo, pageSize, sortBy, sortDir);
    }
}
