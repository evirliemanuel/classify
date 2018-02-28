package com.remswork.project.alice.resource.links;

import com.remswork.project.alice.model.support.Link;
import com.remswork.project.alice.resource.SubjectResource;

import javax.ws.rs.core.UriInfo;

public class SubjectResourceLinks {

    private UriInfo uriInfo;

    public SubjectResourceLinks(UriInfo uriInfo) {
        this.uriInfo = uriInfo;
    }

    public Link self(final long subjectId){
        String rel =  "self";
        String uri = uriInfo.getBaseUriBuilder()
                .path(SubjectResource.class)
                .path(Long.toString(subjectId))
                .build().toString();
        return new Link(uri, rel);
    }

}
