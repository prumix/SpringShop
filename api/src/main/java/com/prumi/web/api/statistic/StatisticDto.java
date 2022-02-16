package com.prumi.web.api.statistic;

import java.util.List;

public class StatisticDto {
   private List<StatisticItemDto> statisticItemDtos;

    public List<StatisticItemDto> getStatisticItemDtos() {
        return statisticItemDtos;
    }

    public void setStatisticItemDtos(List<StatisticItemDto> statisticItemDtos) {
        this.statisticItemDtos = statisticItemDtos;
    }

    public StatisticDto(List<StatisticItemDto> statisticItemDtos) {
        this.statisticItemDtos = statisticItemDtos;
    }
}