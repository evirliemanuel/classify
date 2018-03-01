package com.remswork.project.alice.resource.links;

import com.remswork.project.alice.model.support.Link;
import com.remswork.project.alice.resource.ActivityResource;
import com.remswork.project.alice.resource.AssignmentResource;

import javax.ws.rs.core.UriInfo;

public class AssignmentResourceLinks {

    private UriInfo uriInfo;

    public AssignmentResourceLinks(UriInfo uriInfo) {
        this.uriInfo = uriInfo;
    }

    public Link self(final long activityId){
        String rel =  "self";
        String uri = uriInfo.getBaseUriBuilder()
                .path(AssignmentResource.class)
                .path(Long.toString(activityId))
                .build().toString();
        return new Link(uri, rel);
    }
}
