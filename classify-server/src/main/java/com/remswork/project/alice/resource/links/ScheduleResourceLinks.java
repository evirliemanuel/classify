package com.remswork.project.alice.resource.links;

import com.remswork.project.alice.model.support.Link;
import com.remswork.project.alice.resource.ScheduleResource;

import javax.ws.rs.core.UriInfo;

public class ScheduleResourceLinks {

    private UriInfo uriInfo;

    public ScheduleResourceLinks(UriInfo uriInfo) {
        this.uriInfo = uriInfo;
    }

    public Link self(final long scheduleId){
        String rel =  "self";
        String uri = uriInfo.getBaseUriBuilder()
                .path(ScheduleResource.class)
                .path(Long.toString(scheduleId))
                .build().toString();
        return new Link(uri, rel);
    }
}
