package com.remswork.project.alice.resource.links;

import com.remswork.project.alice.model.support.Link;
import com.remswork.project.alice.resource.AssignmentResultResource;

import javax.ws.rs.core.UriInfo;

public class AssignmentResultResourceLinks {

    private UriInfo uriInfo;

    public AssignmentResultResourceLinks(UriInfo uriInfo) {
        this.uriInfo = uriInfo;
    }

    public Link self(final long resultId){
        String rel =  "self";
        String uri = uriInfo.getBaseUriBuilder()
                .path(AssignmentResultResource.class)
                .path(Long.toString(resultId))
                .build().toString();
        return new Link(uri, rel);
    }
}
