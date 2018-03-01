package com.remswork.project.alice.resource.links;

import com.remswork.project.alice.model.support.Link;
import com.remswork.project.alice.resource.ExamResultResource;

import javax.ws.rs.core.UriInfo;

public class ExamResultResourceLinks {

    private UriInfo uriInfo;

    public ExamResultResourceLinks(UriInfo uriInfo) {
        this.uriInfo = uriInfo;
    }

    public Link self(final long resultId){
        String rel =  "self";
        String uri = uriInfo.getBaseUriBuilder()
                .path(ExamResultResource.class)
                .path(Long.toString(resultId))
                .build().toString();
        return new Link(uri, rel);
    }
}
