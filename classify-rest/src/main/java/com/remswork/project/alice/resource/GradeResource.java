package com.remswork.project.alice.resource;

import com.remswork.project.alice.exception.GradingFactorException;
import com.remswork.project.alice.model.Grade;
import com.remswork.project.alice.model.support.Message;
import com.remswork.project.alice.service.impl.GradeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;

@Component
@Produces(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Path("grade")
public class GradeResource {

    @Autowired
    private GradeServiceImpl gradeService;
    @Context
    private UriInfo uriInfo;
    @QueryParam("studentId")
    private long studentId;
    @QueryParam("classId")
    private long classId;
    @QueryParam("termId")
    private long termId;

    @GET
    @Path("{gradeId}")
    public Response getGradeById(@PathParam("gradeId") long id) {
        try {
            Grade grade = gradeService.getGradeById(id);
            return Response.status(Response.Status.OK).entity(grade).build();
        } catch (GradingFactorException e) {
            e.printStackTrace();
            Message message = new Message(404, "Not Found", e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(message).build();
        }
    }

    @GET
    public Response getGradeList() {
        try {
            List<Grade> gradeList;
            if (classId != 0 && studentId != 0 && termId != 0)
                gradeList = gradeService.getGradeListByClass(classId, studentId, termId);
            else if (classId != 0 && studentId != 0)
                gradeList = gradeService.getGradeListByClass(classId, studentId);
            else if (studentId != 0 && termId != 0)
                gradeList = gradeService.getGradeListByStudentId(studentId, termId);
            else if (studentId != 0)
                gradeList = gradeService.getGradeListByStudentId(studentId);
            else
                gradeList = gradeService.getGradeList();
            GenericEntity<List<Grade>> entity = new GenericEntity<List<Grade>>(gradeList) {
            };
            return Response.status(Response.Status.OK).entity(entity).build();
        } catch (GradingFactorException e) {
            e.printStackTrace();
            Message message = new Message(404, "Not Found", e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(message).build();
        }
    }

    @POST
    public Response addGrade(Grade grade) {
        try {
            grade = gradeService.addGrade(grade, classId, studentId, termId);
            return Response.status(Response.Status.CREATED).entity(grade).build();
        } catch (GradingFactorException e) {
            e.printStackTrace();
            Message message = new Message(400, "Bad Request", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
        }
    }

    @PUT
    @Path("{gradeId}")
    public Response updateGradeById(@PathParam("gradeId") long gradeId, Grade newGrade) {
        try {
            Grade grade;
            if (termId != 0)
                grade = gradeService.updateGrade(gradeId, newGrade, classId, studentId, termId);
            else if (classId != 0 || studentId != 0)
                grade = gradeService.updateGrade(gradeId, newGrade, classId, studentId);
            else
                grade = gradeService.updateGradeById(gradeId, newGrade);
            return Response.status(Response.Status.OK).entity(grade).build();
        } catch (GradingFactorException e) {
            e.printStackTrace();
            Message message = new Message(400, "Bad Request", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
        }
    }

    @DELETE
    @Path("{gradeId}")
    public Response deleteGradeById(@PathParam("gradeId") long gradeId) {
        try {
            Grade grade = gradeService.deleteGradeById(gradeId);
            return Response.status(Response.Status.OK).entity(grade).build();
        } catch (GradingFactorException e) {
            e.printStackTrace();
            Message message = new Message(400, "Bad Request", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
        }
    }
}
