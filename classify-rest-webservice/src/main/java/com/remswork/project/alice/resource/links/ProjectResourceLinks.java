package com.remswork.project.alice.resource.links;

import com.remswork.project.alice.model.support.Link;
import com.remswork.project.alice.resource.ProjectResource;

import javax.ws.rs.core.UriInfo;

public class ProjectResourceLinks {

    private UriInfo uriInfo;

    public ProjectResourceLinks(UriInfo uriInfo) {
        this.uriInfo = uriInfo;
    }

    public Link self(final long activityId){
        String rel =  "self";
        String uri = uriInfo.getBaseUriBuilder()
                .path(ProjectResource.class)
                .path(Long.toString(activityId))
                .build().toString();
        return new Link(uri, rel);
    }
}
