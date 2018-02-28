package com.remswork.project.alice.resource;

import com.remswork.project.alice.exception.GradingFactorException;
import com.remswork.project.alice.model.ExamResult;
import com.remswork.project.alice.model.support.Message;
import com.remswork.project.alice.resource.links.ExamResultResourceLinks;
import com.remswork.project.alice.service.impl.ExamServiceImpl;
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
public class ExamResultResource {

    @Autowired
    private ExamServiceImpl examService;
    private long studentId;
    private UriInfo uriInfo;

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    public void setUriInfo(UriInfo uriInfo) {
        this.uriInfo = uriInfo;
    }

    @GET
    public Response getExamResult(@PathParam("examId") long examId) {
        try {
            ExamResultResourceLinks resultResourceLinks = new ExamResultResourceLinks(uriInfo);
            ExamResult result = examService.getExamResultByExamAndStudentId(examId, studentId);
            result.addLink(resultResourceLinks.self(result.getId()));
            return Response.status(Response.Status.OK).entity(result).build();
        } catch (GradingFactorException e) {
            e.printStackTrace();
            Message message = new Message(404, "Not Found", e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(message).build();
        }
    }

    @POST
    public Response addExamResult(@QueryParam("score") int score, @PathParam("examId") long examId) {
        try {
            ExamResultResourceLinks resultResourceLinks = new ExamResultResourceLinks(uriInfo);
            ExamResult result = examService.addExamResult(score, examId, studentId);
            result.addLink(resultResourceLinks.self(result.getId()));
            return Response.status(Response.Status.OK).entity(result).build();
        } catch (GradingFactorException e) {
            e.printStackTrace();
            Message message = new Message(404, "Not Found", e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(message).build();
        }
    }

    @PUT
    public Response updateExamResult(@PathParam("examId") long examId, @QueryParam("score") int score) {
        try {
            ExamResultResourceLinks resultResourceLinks = new ExamResultResourceLinks(uriInfo);
            ExamResult result =
                    examService.updateExamResultByExamAndStudentId(score, examId, studentId);
            result.addLink(resultResourceLinks.self(result.getId()));
            return Response.status(Response.Status.OK).entity(result).build();
        } catch (GradingFactorException e) {
            e.printStackTrace();
            Message message = new Message(400, "Bad Request", e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(message).build();
        }
    }

    @DELETE
    public Response deleteExamResult(@PathParam("examId") long examId) {
        try {
            ExamResultResourceLinks resultResourceLinks = new ExamResultResourceLinks(uriInfo);
            ExamResult result = examService.deleteExamResultByExamAndStudentId(examId, studentId);
            result.addLink(resultResourceLinks.self(result.getId()));
            return Response.status(Response.Status.OK).entity(result).build();
        } catch (GradingFactorException e) {
            e.printStackTrace();
            Message message = new Message(400, "Bad Request", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
        }
    }
}
