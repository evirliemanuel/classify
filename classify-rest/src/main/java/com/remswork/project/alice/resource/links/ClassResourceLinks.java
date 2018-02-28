package com.remswork.project.alice.resource.links;

import com.remswork.project.alice.model.support.Link;
import com.remswork.project.alice.resource.ClassResource;
import com.remswork.project.alice.resource.ClassScheduleListResource;
import com.remswork.project.alice.resource.ClassStudentListResource;

import javax.ws.rs.core.UriInfo;

public class ClassResourceLinks {

    private UriInfo uriInfo;

    public ClassResourceLinks(UriInfo uriInfo) {
        this.uriInfo = uriInfo;
    }

    public Link self(final long classId){
        String rel =  "self";
        String uri = uriInfo.getBaseUriBuilder()
                .path(ClassResource.class)
                .path(Long.toString(classId))
                .build().toString();
        return new Link(uri, rel);
    }

    public Link schedule(final long classId) {
        String rel =  "schedule";
        String uri = uriInfo.getBaseUriBuilder()
                .path(ClassResource.class)
                .path(Long.toString(classId))
                .path(ClassScheduleListResource.class)
                .build().toString();
        return new Link(uri, rel);
    }

    public Link student(final long classId) {
        String rel =  "student";
        String uri = uriInfo.getBaseUriBuilder()
                .path(ClassResource.class)
                .path(Long.toString(classId))
                .path(ClassStudentListResource.class)
                .build().toString();
        return new Link(uri, rel);
    }
}
