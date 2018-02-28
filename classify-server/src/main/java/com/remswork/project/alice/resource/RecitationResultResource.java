package com.remswork.project.alice.resource;

import com.remswork.project.alice.exception.GradingFactorException;
import com.remswork.project.alice.model.RecitationResult;
import com.remswork.project.alice.model.support.Message;
import com.remswork.project.alice.resource.links.RecitationResultResourceLinks;
import com.remswork.project.alice.service.impl.RecitationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Component
@Produces(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Path("result")
public class RecitationResultResource {

    @Autowired
    private RecitationServiceImpl recitationService;
    private long studentId;
    private UriInfo uriInfo;

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    public void setUriInfo(UriInfo uriInfo) {
        this.uriInfo = uriInfo;
    }

    @GET
    public Response getRecitationResult(@PathParam("recitationId") long recitationId) {
        try {
            RecitationResultResourceLinks resultResourceLinks = new RecitationResultResourceLinks(uriInfo);
            RecitationResult result = recitationService.getRecitationResultByRecitationAndStudentId(recitationId,
                    studentId);
            result.addLink(resultResourceLinks.self(result.getId()));
            return Response.status(Response.Status.OK).entity(result).build();
        } catch (GradingFactorException e) {
            e.printStackTrace();
            Message message = new Message(404, "Not Found", e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(message).build();
        }
    }

    @POST
    public Response addRecitationResult(@QueryParam("score") int score, @PathParam("recitationId") long recitationId) {
        try {
            RecitationResultResourceLinks resultResourceLinks = new RecitationResultResourceLinks(uriInfo);
            RecitationResult result = recitationService.addRecitationResult(score, recitationId, studentId);
            result.addLink(resultResourceLinks.self(result.getId()));
            return Response.status(Response.Status.OK).entity(result).build();
        } catch (GradingFactorException e) {
            e.printStackTrace();
            Message message = new Message(404, "Not Found", e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(message).build();
        }
    }

    @PUT
    public Response updateRecitationResult(@PathParam("recitationId") long recitationId,
                                           @QueryParam("score") int score) {
        try {
            RecitationResultResourceLinks resultResourceLinks = new RecitationResultResourceLinks(uriInfo);
            RecitationResult result =
                    recitationService.updateRecitationResultByRecitationAndStudentId(score, recitationId, studentId);
            result.addLink(resultResourceLinks.self(result.getId()));
            return Response.status(Response.Status.OK).entity(result).build();
        } catch (GradingFactorException e) {
            e.printStackTrace();
            Message message = new Message(400, "Bad Request", e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(message).build();
        }
    }

    @DELETE
    public Response deleteRecitationResult(@PathParam("recitationId") long recitationId) {
        try {
            RecitationResultResourceLinks resultResourceLinks = new RecitationResultResourceLinks(uriInfo);
            RecitationResult result = recitationService.deleteRecitationResultByRecitationAndStudentId(recitationId,
                    studentId);
            result.addLink(resultResourceLinks.self(result.getId()));
            return Response.status(Response.Status.OK).entity(result).build();
        } catch (GradingFactorException e) {
            e.printStackTrace();
            Message message = new Message(400, "Bad Request", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
        }
    }
}
