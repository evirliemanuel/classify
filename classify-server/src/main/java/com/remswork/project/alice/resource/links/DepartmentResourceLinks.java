package com.remswork.project.alice.resource.links;

import com.remswork.project.alice.model.support.Link;
import com.remswork.project.alice.resource.DepartmentResource;

import javax.ws.rs.core.UriInfo;

public class DepartmentResourceLinks {

    private UriInfo uriInfo;

    public DepartmentResourceLinks(UriInfo uriInfo){
        this.uriInfo = uriInfo;
    }

    public Link self(final long departmentId){
        String rel =  "self";
        String uri = uriInfo.getBaseUriBuilder()
                .path(DepartmentResource.class)
                .path(Long.toString(departmentId))
                .build().toString();
        return new Link(uri, rel);
    }
}
