package com.remswork.project.alice.resource;

import com.remswork.project.alice.exception.GradingFactorException;
import com.remswork.project.alice.model.ProjectResult;
import com.remswork.project.alice.model.support.Message;
import com.remswork.project.alice.resource.links.ProjectResultResourceLinks;
import com.remswork.project.alice.service.impl.ProjectServiceImpl;
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
public class ProjectResultResource {

    @Autowired
    private ProjectServiceImpl projectService;
    private long studentId;
    private UriInfo uriInfo;

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    public void setUriInfo(UriInfo uriInfo) {
        this.uriInfo = uriInfo;
    }

    @GET
    public Response getProjectResult(@PathParam("projectId") long projectId) {
        try {
            ProjectResultResourceLinks resultResourceLinks = new ProjectResultResourceLinks(uriInfo);
            ProjectResult result = projectService.getProjectResultByProjectAndStudentId(projectId, studentId);
            result.addLink(resultResourceLinks.self(result.getId()));
            return Response.status(Response.Status.OK).entity(result).build();
        } catch (GradingFactorException e) {
            e.printStackTrace();
            Message message = new Message(404, "Not Found", e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(message).build();
        }
    }

    @POST
    public Response addProjectResult(@QueryParam("score") int score, @PathParam("projectId") long projectId) {
        try {
            ProjectResultResourceLinks resultResourceLinks = new ProjectResultResourceLinks(uriInfo);
            ProjectResult result = projectService.addProjectResult(score, projectId, studentId);
            result.addLink(resultResourceLinks.self(result.getId()));
            return Response.status(Response.Status.OK).entity(result).build();
        } catch (GradingFactorException e) {
            e.printStackTrace();
            Message message = new Message(404, "Not Found", e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(message).build();
        }
    }

    @PUT
    public Response updateProjectResult(@PathParam("projectId") long projectId, @QueryParam("score") int score) {
        try {
            ProjectResultResourceLinks resultResourceLinks = new ProjectResultResourceLinks(uriInfo);
            ProjectResult result =
                    projectService.updateProjectResultByProjectAndStudentId(score, projectId, studentId);
            result.addLink(resultResourceLinks.self(result.getId()));
            return Response.status(Response.Status.OK).entity(result).build();
        } catch (GradingFactorException e) {
            e.printStackTrace();
            Message message = new Message(400, "Bad Request", e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(message).build();
        }
    }

    @DELETE
    public Response deleteProjectResult(@PathParam("projectId") long projectId) {
        try {
            ProjectResultResourceLinks resultResourceLinks = new ProjectResultResourceLinks(uriInfo);
            ProjectResult result = projectService.deleteProjectResultByProjectAndStudentId(projectId, studentId);
            result.addLink(resultResourceLinks.self(result.getId()));
            return Response.status(Response.Status.OK).entity(result).build();
        } catch (GradingFactorException e) {
            e.printStackTrace();
            Message message = new Message(400, "Bad Request", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
        }
    }
}
