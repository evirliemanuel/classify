package com.remswork.project.alice.resource.links;

import com.remswork.project.alice.model.support.Link;
import com.remswork.project.alice.resource.StudentResource;

import javax.ws.rs.core.UriInfo;

public class StudentResourceLinks {

    private UriInfo uriInfo;

    public StudentResourceLinks(UriInfo uriInfo) {
        this.uriInfo = uriInfo;
    }

    public Link self(final long studentId){
        String rel =  "self";
        String uri = uriInfo.getBaseUriBuilder()
                .path(StudentResource.class)
                .path(Long.toString(studentId))
                .build().toString();
        return new Link(uri, rel);
    }

}
