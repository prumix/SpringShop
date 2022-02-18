package com.prumi.web.recommendations.controllers;

import com.prumi.web.api.statistic.StatisticDto;
import com.prumi.web.recommendations.converters.StatisticConverter;
import com.prumi.web.recommendations.service.StatisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/statistic")
public class StatisticController {
    private final StatisticService statisticService;
    private final StatisticConverter statisticConverter;

    @GetMapping("/cart")
    public StatisticDto getCartPerDay(){
        return statisticConverter.modelToDto(statisticService.getCurrentStatistic("Cart"));
    }

    @GetMapping("/order")
    public StatisticDto getOrder(){
        return statisticConverter.modelToDto(statisticService.getCurrentStatistic("Order"));
    }
}
