package com.devdaniel.monitor.mapper;

import com.devdaniel.monitor.dto.request.MonitorTaskRequest;
import com.devdaniel.monitor.dto.response.MonitorTaskResponse;
import com.devdaniel.monitor.model.MonitorTask;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { MonitoredSiteMapper.class })
public interface MonitorTaskMapper {

    @Mapping(source = "siteId", target = "site.id")
    MonitorTask toModel(MonitorTaskRequest request);

    MonitorTaskResponse toResponse(MonitorTask model);
}