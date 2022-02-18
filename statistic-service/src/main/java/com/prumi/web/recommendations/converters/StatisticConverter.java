package com.prumi.web.recommendations.converters;


import com.prumi.web.api.statistic.StatisticDto;
import com.prumi.web.api.statistic.StatisticItemDto;
import com.prumi.web.recommendations.model.Statistic;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class StatisticConverter {
    public StatisticDto modelToDto(Statistic statistic) {
        List<StatisticItemDto> statisticItemDtos = statistic.getItemList().stream().map(it ->
                new StatisticItemDto(it.getTitle(), it.getFrom(), it.getQuantity())
        ).collect(Collectors.toList());
        StatisticDto statisticDto = new StatisticDto(statisticItemDtos);
        return statisticDto;
    }
}
