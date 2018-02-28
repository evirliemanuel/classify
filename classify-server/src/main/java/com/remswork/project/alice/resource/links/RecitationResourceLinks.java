package com.remswork.project.alice.resource.links;

import com.remswork.project.alice.model.support.Link;
import com.remswork.project.alice.resource.ActivityResource;
import com.remswork.project.alice.resource.RecitationResource;

import javax.ws.rs.core.UriInfo;

public class RecitationResourceLinks {

    private UriInfo uriInfo;

    public RecitationResourceLinks(UriInfo uriInfo) {
        this.uriInfo = uriInfo;
    }

    public Link self(final long activityId){
        String rel =  "self";
        String uri = uriInfo.getBaseUriBuilder()
                .path(RecitationResource.class)
                .path(Long.toString(activityId))
                .build().toString();
        return new Link(uri, rel);
    }
}
