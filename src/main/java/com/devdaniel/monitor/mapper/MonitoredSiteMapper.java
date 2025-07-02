package com.devdaniel.monitor.mapper;

import com.devdaniel.monitor.dto.request.MonitoredSiteRequest;
import com.devdaniel.monitor.dto.response.MonitoredSiteResponse;
import com.devdaniel.monitor.model.MonitoredSite;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MonitoredSiteMapper {

    MonitoredSite toModel(MonitoredSiteRequest request);

    @Mapping(source = "user.username", target = "username")
    MonitoredSiteResponse toResponse(MonitoredSite model);
}