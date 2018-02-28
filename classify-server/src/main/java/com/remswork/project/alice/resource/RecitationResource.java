package com.remswork.project.alice.resource;

import com.remswork.project.alice.exception.GradingFactorException;
import com.remswork.project.alice.model.Recitation;
import com.remswork.project.alice.model.support.Message;
import com.remswork.project.alice.resource.links.ClassResourceLinks;
import com.remswork.project.alice.resource.links.RecitationResourceLinks;
import com.remswork.project.alice.service.impl.RecitationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;

@Component
@Produces(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Path("recitation")
public class RecitationResource {

    @Autowired
    private RecitationServiceImpl recitationService;
    @Autowired
    private RecitationResultResource recitationResultResource;
    @Context
    private UriInfo uriInfo;
    @QueryParam("classId")
    private long classId;
    @QueryParam("termId")
    private long termId;
    @QueryParam("studentId")
    private long studentId;

    @GET
    @Path("{recitationId}")
    public Response getRecitationById(@PathParam("recitationId") long id) {
        try {
            RecitationResourceLinks resourceLinks = new RecitationResourceLinks(uriInfo);
            ClassResourceLinks classResourceLinks = new ClassResourceLinks(uriInfo);

            Recitation recitation = recitationService.getRecitationById(id);
            recitation.addLink(resourceLinks.self(id));
            if(recitation.get_class() != null)
                recitation.get_class().addLink(classResourceLinks.self(recitation.get_class().getId()));
            return Response.status(Response.Status.OK).entity(recitation).build();
        }catch (GradingFactorException e) {
            e.printStackTrace();
            Message message = new Message(404, "Not Found", e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(message).build();
        }
    }

    @GET
    public Response getRecitationList() {
        try {
            List<Recitation> recitationList;
            RecitationResourceLinks resourceLinks = new RecitationResourceLinks(uriInfo);
            ClassResourceLinks classResourceLinks = new ClassResourceLinks(uriInfo);

            if(classId != 0 && termId != 0)
                recitationList = recitationService.getRecitationListByClassId(classId, termId);
            else if(classId != 0)
                recitationList = recitationService.getRecitationListByClassId(classId);
            else if(studentId != 0 && termId != 0)
                recitationList = recitationService.getRecitationListByStudentId(studentId, termId);
            else if(studentId != 0)
                recitationList = recitationService.getRecitationListByStudentId(studentId);
            else
                recitationList = recitationService.getRecitationList();
            for(Recitation recitation : recitationList) {
                recitation.addLink(resourceLinks.self(recitation.getId()));
                if(recitation.get_class() != null)
                    recitation.get_class().addLink(classResourceLinks.self(recitation.get_class().getId()));
            }
            GenericEntity<List<Recitation>> entity = new GenericEntity<List<Recitation>>(recitationList){};
            return Response.status(Response.Status.OK).entity(entity).build();
        }catch (GradingFactorException e) {
            e.printStackTrace();
            Message message = new Message(404, "Not Found", e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(message).build();
        }
    }

    @POST
    public Response addRecitation(Recitation recitation) {
        try {
            RecitationResourceLinks resourceLinks = new RecitationResourceLinks(uriInfo);
            ClassResourceLinks classResourceLinks = new ClassResourceLinks(uriInfo);

            if(termId > 0)
                recitation = recitationService.addRecitation(recitation, classId, termId);
            else
                recitation = recitationService.addRecitation(recitation, classId);
            recitation.addLink(resourceLinks.self(recitation.getId()));
            if(recitation.get_class() != null)
                recitation.get_class().addLink(classResourceLinks.self(recitation.get_class().getId()));
            return Response.status(Response.Status.CREATED).entity(recitation).build();
        }catch (GradingFactorException e) {
            e.printStackTrace();
            Message message = new Message(400, "Bad Request", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
        }
    }

    @PUT
    @Path("{recitationId}")
    public Response updateRecitationById(@PathParam("recitationId") long id, Recitation newRecitation) {
        try {
            RecitationResourceLinks resourceLinks = new RecitationResourceLinks(uriInfo);
            ClassResourceLinks classResourceLinks = new ClassResourceLinks(uriInfo);
            Recitation recitation;
            if(termId > 0)
                recitation = recitationService.updateRecitationById(id, newRecitation, classId, termId);
            else
                recitation = recitationService.updateRecitationById(id, newRecitation, classId);
            recitation.addLink(resourceLinks.self(recitation.getId()));
            if(recitation.get_class() != null)
                recitation.get_class().addLink(classResourceLinks.self(recitation.get_class().getId()));
            return Response.status(Response.Status.OK).entity(recitation).build();
        }catch (GradingFactorException e) {
            e.printStackTrace();
            Message message = new Message(400, "Bad Request", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
        }
    }

    @DELETE
    @Path("{recitationId}")
    public Response deleteRecitationById(@PathParam("recitationId") long id) {
        try {
            RecitationResourceLinks resourceLinks = new RecitationResourceLinks(uriInfo);
            ClassResourceLinks classResourceLinks = new ClassResourceLinks(uriInfo);

            Recitation recitation = recitationService.deleteRecitationById(id);
            recitation.addLink(resourceLinks.self(recitation.getId()));
            if(recitation.get_class() != null)
                recitation.get_class().addLink(classResourceLinks.self(recitation.get_class().getId()));
            return Response.status(Response.Status.OK).entity(recitation).build();
        }catch (GradingFactorException e) {
            e.printStackTrace();
            Message message = new Message(400, "Bad Request", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
        }
    }

    @Path("{recitationId}/result")
    public RecitationResultResource recitationResultResource() {
        recitationResultResource.setStudentId(studentId);
        recitationResultResource.setUriInfo(uriInfo);
        return recitationResultResource;
    }
}