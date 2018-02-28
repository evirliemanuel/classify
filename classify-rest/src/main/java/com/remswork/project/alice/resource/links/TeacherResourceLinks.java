package com.remswork.project.alice.resource.links;

import com.remswork.project.alice.model.support.Link;
import com.remswork.project.alice.resource.TeacherResource;

import javax.ws.rs.core.UriInfo;

public class TeacherResourceLinks {

    private UriInfo uriInfo;

    public TeacherResourceLinks(UriInfo uriInfo) {
        this.uriInfo = uriInfo;
    }

    public Link self(final long teacherId){
        String rel =  "self";
        String uri = uriInfo.getBaseUriBuilder()
                .path(TeacherResource.class)
                .path(Long.toString(teacherId))
                .build().toString();
        return new Link(uri, rel);
    }
}
