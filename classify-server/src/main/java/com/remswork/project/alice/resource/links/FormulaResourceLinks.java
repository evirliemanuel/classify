package com.remswork.project.alice.resource.links;

import com.remswork.project.alice.model.support.Link;
import com.remswork.project.alice.resource.FormulaResource;

import javax.ws.rs.core.UriInfo;

public class FormulaResourceLinks {

    private UriInfo uriInfo;

    public FormulaResourceLinks(UriInfo uriInfo) {
        this.uriInfo = uriInfo;
    }

    public Link self(final long formulaId){
        String rel =  "self";
        String uri = uriInfo.getBaseUriBuilder()
                .path(FormulaResource.class)
                .path(Long.toString(formulaId))
                .build().toString();
        return new Link(uri, rel);
    }
}
