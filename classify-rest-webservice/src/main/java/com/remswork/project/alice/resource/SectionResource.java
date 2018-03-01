package com.remswork.project.alice.resource;

import com.remswork.project.alice.exception.SectionException;
import com.remswork.project.alice.model.Section;
import com.remswork.project.alice.model.support.Message;
import com.remswork.project.alice.resource.links.DepartmentResourceLinks;
import com.remswork.project.alice.resource.links.SectionResourceLinks;
import com.remswork.project.alice.service.impl.SectionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;

@Component
@Produces(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Path("section")
public class SectionResource {

    @Autowired
    private SectionServiceImpl sectionService;
    @Context
    private UriInfo uriInfo;
    @QueryParam("departmentId")
    private long departmentId;

    @GET
    @Path("{sectionId}")
    public Response getSectionById(@PathParam("sectionId") long id) {
        try {
            SectionResourceLinks resourceLinks = new SectionResourceLinks(uriInfo);
            DepartmentResourceLinks departmentResourceLinks = new DepartmentResourceLinks(uriInfo);
            Section section = sectionService.getSectionById(id);
            section.addLink(resourceLinks.self(id));
            if(section.getDepartment() != null)
                section.getDepartment().addLink(departmentResourceLinks.self(section.getDepartment().getId()));
            return Response.status(Response.Status.OK).entity(section).build();
        }catch (SectionException e) {
            e.printStackTrace();
            Message message = new Message(404, "Not Found", e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(message).build();
        }
    }

    @GET
    public Response getSectionList() {
        try {
            SectionResourceLinks resourceLinks = new SectionResourceLinks(uriInfo);
            DepartmentResourceLinks departmentResourceLinks = new DepartmentResourceLinks(uriInfo);
            List<Section> sectionList = sectionService.getSectionList();
            for(Section section : sectionList) {
                section.addLink(resourceLinks.self(section.getId()));
                if(section.getDepartment() != null)
                    section.getDepartment().addLink(departmentResourceLinks.self(section.getDepartment().getId()));
            }
            GenericEntity<List<Section>> entity = new GenericEntity<List<Section>>(sectionList){};
            return Response.status(Response.Status.OK).entity(entity).build();
        }catch (SectionException e) {
            e.printStackTrace();
            Message message = new Message(404, "Not Found", e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(message).build();
        }
    }

    @POST
    public Response addSection(Section section) {
        try {
            SectionResourceLinks resourceLinks = new SectionResourceLinks(uriInfo);
            DepartmentResourceLinks departmentResourceLinks = new DepartmentResourceLinks(uriInfo);
            section = sectionService.addSection(section, departmentId);
            section.addLink(resourceLinks.self(section.getId()));
            if(section.getDepartment() != null)
                section.getDepartment().addLink(departmentResourceLinks.self(section.getDepartment().getId()));
            return Response.status(Response.Status.CREATED).entity(section).build();
        }catch (SectionException e) {
            e.printStackTrace();
            Message message = new Message(400, "Bad Request", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
        }
    }

    @PUT
    @Path("{sectionId}")
    public Response updateSectionById(@PathParam("sectionId") long id, Section newSection) {
        try {
            SectionResourceLinks resourceLinks = new SectionResourceLinks(uriInfo);
            DepartmentResourceLinks departmentResourceLinks = new DepartmentResourceLinks(uriInfo);
            Section section = sectionService.updateSectionById(id, newSection, departmentId);
            section.addLink(resourceLinks.self(id));
            if(section.getDepartment() != null)
                section.getDepartment().addLink(departmentResourceLinks.self(section.getDepartment().getId()));
            return Response.status(Response.Status.OK).entity(section).build();
        }catch (SectionException e) {
            e.printStackTrace();
            Message message = new Message(400, "Bad Request", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
        }
    }

    @DELETE
    @Path("{sectionId}")
    public Response deleteSectionById(@PathParam("sectionId") long id) {
        try {
            SectionResourceLinks resourceLinks = new SectionResourceLinks(uriInfo);
            DepartmentResourceLinks departmentResourceLinks = new DepartmentResourceLinks(uriInfo);
            Section section = sectionService.deleteSectionById(id);
            section.addLink(resourceLinks.self(id));
            if(section.getDepartment() != null)
                section.getDepartment().addLink(departmentResourceLinks.self(section.getDepartment().getId()));
            return Response.status(Response.Status.OK).entity(section).build();
        }catch (SectionException e) {
            e.printStackTrace();
            Message message = new Message(400, "Bad Request", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
        }
    }
}
