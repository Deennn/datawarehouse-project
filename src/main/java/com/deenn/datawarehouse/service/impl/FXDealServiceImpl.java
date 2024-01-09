package com.deenn.datawarehouse.service.impl;

import com.deenn.datawarehouse.dtos.request.FXDealRequest;
import com.deenn.datawarehouse.dtos.response.APIResponse;
import com.deenn.datawarehouse.dtos.response.FXDealResponse;
import com.deenn.datawarehouse.dtos.response.PaginatedResponse;
import com.deenn.datawarehouse.entity.FXDeal;
import com.deenn.datawarehouse.exception.DealNotFoundException;
import com.deenn.datawarehouse.exception.InvalidDealException;
import com.deenn.datawarehouse.repository.FXDealRepository;
import com.deenn.datawarehouse.service.FXDealService;
import com.deenn.datawarehouse.utils.FXValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Currency;

import static com.deenn.datawarehouse.constants.AppConstants.ONE;

@Service
@RequiredArgsConstructor
@Slf4j
public class FXDealServiceImpl implements FXDealService {

    private final FXDealRepository repository;


    private final FXValidator FXValidator;



    @Override
    public ResponseEntity<APIResponse<FXDealResponse>> saveFXDeal(FXDealRequest request) {

        FXValidator.validateFxDealRequest(request);
        log.info("Saving FXDeal with unique Id");


        FXDeal fxDeal = repository.findByDealUniqueId(request.getDealUniqueId()).orElse(null);

        if (fxDeal != null)
            throw new InvalidDealException("FXDeal with " + request.getDealUniqueId() + " already exist" );


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime parsedDateTime = LocalDateTime.parse(request.getDealTimestamp(), formatter);

        FXDeal fx = FXDeal.builder()
                .dealUniqueId(request.getDealUniqueId())
                .fromCurrencyCode(Currency.getInstance(request.getFromCurrencyCode()))
                .toCurrencyCode(Currency.getInstance(request.getToCurrencyCode()))
                .orderingCurrency(Currency.getInstance(request.getOrderingCurrency()))
                .dealAmountInOrderingCurrency(request.getDealAmountInOrderingCurrency())
                .dealTimestamp(parsedDateTime).build();

        repository.save(fx);
        FXDealResponse response = new FXDealResponse();
        BeanUtils.copyProperties(fx, response);
        return ResponseEntity.ok(new APIResponse<>("FXDeal save successfully", true , response));
    }

    @Override
    public ResponseEntity<APIResponse<FXDealResponse>> getFXDeal(String request) {

        log.info("fetching FXDeal with unique Id");

        FXDeal fxDeal = repository.findByDealUniqueId(request).orElseThrow(() ->  new DealNotFoundException("FXDeal with " + request + " is not found" ));

        FXDealResponse response = new FXDealResponse();

        BeanUtils.copyProperties(fxDeal, response);

        return ResponseEntity.ok(new APIResponse<>("success", true , response));
    }

    @Override
    public ResponseEntity<APIResponse<PaginatedResponse>> getAllFXDeal(int pageNo, int pageSize, String sortBy, String sortDir) {

        log.info("fetching all FXDeal");

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Collection<FXDealResponse> fxDealResponses = new ArrayList<>();
        Page<FXDeal> activityLogs = repository.findAll(pageable);
        Collection<FXDeal> contents = activityLogs.getContent();

        contents.forEach(x-> {
            FXDealResponse fxDealResponse = new FXDealResponse();
            BeanUtils.copyProperties(x, fxDealResponse);
            fxDealResponses.add(fxDealResponse);
        });
        PaginatedResponse paginatedResponse = new PaginatedResponse();
        paginatedResponse.setContent(fxDealResponses);
        paginatedResponse.setPageNo(activityLogs.getNumber() + ONE);
        paginatedResponse.setPageSize(activityLogs.getSize());
        paginatedResponse.setTotalElements(activityLogs.getTotalElements());
        paginatedResponse.setTotalPages(activityLogs.getTotalPages());
        paginatedResponse.setLast(activityLogs.isLast());
        return ResponseEntity.ok(new APIResponse<>("success", true , paginatedResponse));
    }


}
