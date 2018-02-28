package com.remswork.project.alice.resource;

import com.remswork.project.alice.exception.GradingFactorException;
import com.remswork.project.alice.model.ActivityResult;
import com.remswork.project.alice.model.support.Message;
import com.remswork.project.alice.resource.links.ActivityResultResourceLinks;
import com.remswork.project.alice.service.impl.ActivityServiceImpl;
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
public class ActivityResultResource {

    @Autowired
    private ActivityServiceImpl activityService;
    private long studentId;
    private UriInfo uriInfo;

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    public void setUriInfo(UriInfo uriInfo) {
        this.uriInfo = uriInfo;
    }

    @GET
    public Response getActivityResult(@PathParam("activityId") long activityId) {
        try {
            ActivityResultResourceLinks resultResourceLinks = new ActivityResultResourceLinks(uriInfo);
            ActivityResult result = activityService.getActivityResultByActivityAndStudentId(activityId, studentId);
            result.addLink(resultResourceLinks.self(result.getId()));
            return Response.status(Response.Status.OK).entity(result).build();
        } catch (GradingFactorException e) {
            e.printStackTrace();
            Message message = new Message(404, "Not Found", e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(message).build();
        }
    }

    @POST
    public Response addActivityResult(@QueryParam("score") int score, @PathParam("activityId") long activityId) {
        try {
            ActivityResultResourceLinks resultResourceLinks = new ActivityResultResourceLinks(uriInfo);
            ActivityResult result = activityService.addActivityResult(score, activityId, studentId);
            result.addLink(resultResourceLinks.self(result.getId()));
            return Response.status(Response.Status.OK).entity(result).build();
        } catch (GradingFactorException e) {
            e.printStackTrace();
            Message message = new Message(404, "Not Found", e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(message).build();
        }
    }

    @PUT
    public Response updateActivityResult(@PathParam("activityId") long activityId, @QueryParam("score") int score) {
        try {
            ActivityResultResourceLinks resultResourceLinks = new ActivityResultResourceLinks(uriInfo);
            ActivityResult result =
                    activityService.updateActivityResultByActivityAndStudentId(score, activityId, studentId);
            result.addLink(resultResourceLinks.self(result.getId()));
            return Response.status(Response.Status.OK).entity(result).build();
        } catch (GradingFactorException e) {
            e.printStackTrace();
            Message message = new Message(400, "Bad Request", e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(message).build();
        }
    }

    @DELETE
    public Response deleteActivityResult(@PathParam("activityId") long activityId) {
        try {
            ActivityResultResourceLinks resultResourceLinks = new ActivityResultResourceLinks(uriInfo);
            ActivityResult result = activityService.deleteActivityResultByActivityAndStudentId(activityId, studentId);
            result.addLink(resultResourceLinks.self(result.getId()));
            return Response.status(Response.Status.OK).entity(result).build();
        } catch (GradingFactorException e) {
            e.printStackTrace();
            Message message = new Message(400, "Bad Request", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
        }
    }
}
