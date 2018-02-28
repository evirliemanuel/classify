package com.remswork.project.alice.resource;

import com.remswork.project.alice.exception.GradingFactorException;
import com.remswork.project.alice.model.AssignmentResult;
import com.remswork.project.alice.model.support.Message;
import com.remswork.project.alice.resource.links.AssignmentResultResourceLinks;
import com.remswork.project.alice.service.impl.AssignmentServiceImpl;
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
public class AssignmentResultResource {

    @Autowired
    private AssignmentServiceImpl assignmentService;
    private long studentId;
    private UriInfo uriInfo;

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    public void setUriInfo(UriInfo uriInfo) {
        this.uriInfo = uriInfo;
    }

    @GET
    public Response getAssignmentResult(@PathParam("assignmentId") long assignmentId) {
        try {
            AssignmentResultResourceLinks resultResourceLinks = new AssignmentResultResourceLinks(uriInfo);
            AssignmentResult result = assignmentService.getAssignmentResultByAssignmentAndStudentId(assignmentId,
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
    public Response addAssignmentResult(@QueryParam("score") int score, @PathParam("assignmentId") long assignmentId) {
        try {
            AssignmentResultResourceLinks resultResourceLinks = new AssignmentResultResourceLinks(uriInfo);
            AssignmentResult result = assignmentService.addAssignmentResult(score, assignmentId, studentId);
            result.addLink(resultResourceLinks.self(result.getId()));
            return Response.status(Response.Status.OK).entity(result).build();
        } catch (GradingFactorException e) {
            e.printStackTrace();
            Message message = new Message(404, "Not Found", e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(message).build();
        }
    }

    @PUT
    public Response updateAssignmentResult(@PathParam("assignmentId") long assignmentId,
                                           @QueryParam("score") int score) {
        try {
            AssignmentResultResourceLinks resultResourceLinks = new AssignmentResultResourceLinks(uriInfo);
            AssignmentResult result =
                    assignmentService.updateAssignmentResultByAssignmentAndStudentId(score, assignmentId, studentId);
            result.addLink(resultResourceLinks.self(result.getId()));
            return Response.status(Response.Status.OK).entity(result).build();
        } catch (GradingFactorException e) {
            e.printStackTrace();
            Message message = new Message(400, "Bad Request", e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(message).build();
        }
    }

    @DELETE
    public Response deleteAssignmentResult(@PathParam("assignmentId") long assignmentId) {
        try {
            AssignmentResultResourceLinks resultResourceLinks = new AssignmentResultResourceLinks(uriInfo);
            AssignmentResult result = assignmentService.deleteAssignmentResultByAssignmentAndStudentId(assignmentId,
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