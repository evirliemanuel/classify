package com.remswork.project.alice.resource.links;

import com.remswork.project.alice.model.support.Link;
import com.remswork.project.alice.resource.TermResource;

import javax.ws.rs.core.UriInfo;

public class TermResourceLinks {

    private UriInfo uriInfo;

    public TermResourceLinks(UriInfo uriInfo) {
        this.uriInfo = uriInfo;
    }

    public Link self(final long termId){
        String rel =  "self";
        String uri = uriInfo.getBaseUriBuilder()
                .path(TermResource.class)
                .path(Long.toString(termId))
                .build().toString();
        return new Link(uri, rel);
    }
}
