package com.remswork.project.alice.resource.links;

import com.remswork.project.alice.model.support.Link;
import com.remswork.project.alice.resource.ClassResource;
import com.remswork.project.alice.resource.ClassScheduleListResource;
import com.remswork.project.alice.resource.ClassStudentListResource;

import javax.ws.rs.core.UriInfo;

public class ClassStudentListResourceLinks {

    private UriInfo uriInfo;

    public ClassStudentListResourceLinks(UriInfo uriInfo) {
        this.uriInfo = uriInfo;
    }

    public Link self(final long classId, final long studentId){
        String rel =  "self";
        String uri = uriInfo.getBaseUriBuilder()
                .path(ClassResource.class)
                .path(Long.toString(classId))
                .path(ClassStudentListResource.class)
                .path(Long.toString(studentId))
                .build().toString();
        return new Link(uri, rel);
    }
}
