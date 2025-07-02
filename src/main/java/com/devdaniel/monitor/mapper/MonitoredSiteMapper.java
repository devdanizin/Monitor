package com.devdaniel.monitor.mapper;

import com.devdaniel.monitor.dto.request.MonitoredSiteRequest;
import com.devdaniel.monitor.dto.response.MonitoredSiteResponse;
import com.devdaniel.monitor.model.MonitoredSite;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MonitoredSiteMapper {

    MonitoredSite toModel(MonitoredSiteRequest request);

    MonitoredSiteResponse toResponse(MonitoredSite model);
}