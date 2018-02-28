package com.remswork.project.alice.resource.links;

import com.remswork.project.alice.model.support.Link;
import com.remswork.project.alice.resource.SectionResource;

import javax.ws.rs.core.UriInfo;

public class SectionResourceLinks {

    private UriInfo uriInfo;

    public SectionResourceLinks(UriInfo uriInfo) {
        this.uriInfo = uriInfo;
    }

    public Link self(final long sectionId){
        String rel =  "self";
        String uri = uriInfo.getBaseUriBuilder()
                .path(SectionResource.class)
                .path(Long.toString(sectionId))
                .build().toString();
        return new Link(uri, rel);
    }
}
