package com.remswork.project.alice.resource.links;

import com.remswork.project.alice.model.support.Link;
import com.remswork.project.alice.resource.ClassResource;
import com.remswork.project.alice.resource.ClassScheduleListResource;

import javax.ws.rs.core.UriInfo;

public class ClassScheduleListResourceLinks {

    private UriInfo uriInfo;

    public ClassScheduleListResourceLinks(UriInfo uriInfo) {
        this.uriInfo = uriInfo;
    }

    public Link self(final long classId, final long scheduleId){
        String rel =  "self";
        String uri = uriInfo.getBaseUriBuilder()
                .path(ClassResource.class)
                .path(Long.toString(classId))
                .path(ClassScheduleListResource.class)
                .path(Long.toString(scheduleId))
                .build().toString();
        return new Link(uri, rel);
    }
}
