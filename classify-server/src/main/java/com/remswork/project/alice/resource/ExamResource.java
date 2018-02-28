package com.remswork.project.alice.resource;

import com.remswork.project.alice.exception.GradingFactorException;
import com.remswork.project.alice.model.Exam;
import com.remswork.project.alice.model.support.Message;
import com.remswork.project.alice.resource.links.ClassResourceLinks;
import com.remswork.project.alice.resource.links.ExamResourceLinks;
import com.remswork.project.alice.service.impl.ExamServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;

@Component
@Produces(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Path("exam")
public class ExamResource {

    @Autowired
    private ExamServiceImpl examService;
    @Autowired
    private ExamResultResource examResultResource;
    @Context
    private UriInfo uriInfo;
    @QueryParam("classId")
    private long classId;
    @QueryParam("termId")
    private long termId;
    @QueryParam("studentId")
    private long studentId;

    @GET
    @Path("{examId}")
    public Response getExamById(@PathParam("examId") long id) {
        try {
            ExamResourceLinks resourceLinks = new ExamResourceLinks(uriInfo);
            ClassResourceLinks classResourceLinks = new ClassResourceLinks(uriInfo);

            Exam exam = examService.getExamById(id);
            exam.addLink(resourceLinks.self(id));
            if(exam.get_class() != null)
                exam.get_class().addLink(classResourceLinks.self(exam.get_class().getId()));
            return Response.status(Response.Status.OK).entity(exam).build();
        }catch (GradingFactorException e) {
            e.printStackTrace();
            Message message = new Message(404, "Not Found", e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(message).build();
        }
    }

    @GET
    public Response getExamList() {
        try {
            List<Exam> examList;
            ExamResourceLinks resourceLinks = new ExamResourceLinks(uriInfo);
            ClassResourceLinks classResourceLinks = new ClassResourceLinks(uriInfo);

            if(classId != 0 && termId != 0)
                examList = examService.getExamListByClassId(classId, termId);
            else if(classId != 0)
                examList = examService.getExamListByClassId(classId);
            else if(studentId != 0 && termId != 0)
                examList = examService.getExamListByStudentId(studentId, termId);
            else if(studentId != 0)
                examList = examService.getExamListByStudentId(studentId);
            else
                examList = examService.getExamList();
            for(Exam exam : examList) {
                exam.addLink(resourceLinks.self(exam.getId()));
                if(exam.get_class() != null)
                    exam.get_class().addLink(classResourceLinks.self(exam.get_class().getId()));
            }
            GenericEntity<List<Exam>> entity = new GenericEntity<List<Exam>>(examList){};
            return Response.status(Response.Status.OK).entity(entity).build();
        }catch (GradingFactorException e) {
            e.printStackTrace();
            Message message = new Message(404, "Not Found", e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(message).build();
        }
    }

    @POST
    public Response addExam(Exam exam) {
        try {
            ExamResourceLinks resourceLinks = new ExamResourceLinks(uriInfo);
            ClassResourceLinks classResourceLinks = new ClassResourceLinks(uriInfo);

            if(termId > 0)
                exam = examService.addExam(exam, classId, termId);
            else
                exam = examService.addExam(exam, classId);
            exam.addLink(resourceLinks.self(exam.getId()));
            if(exam.get_class() != null)
                exam.get_class().addLink(classResourceLinks.self(exam.get_class().getId()));
            return Response.status(Response.Status.CREATED).entity(exam).build();
        }catch (GradingFactorException e) {
            e.printStackTrace();
            Message message = new Message(400, "Bad Request", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
        }
    }

    @PUT
    @Path("{examId}")
    public Response updateExamById(@PathParam("examId") long id, Exam newExam) {
        try {
            ExamResourceLinks resourceLinks = new ExamResourceLinks(uriInfo);
            ClassResourceLinks classResourceLinks = new ClassResourceLinks(uriInfo);
            Exam exam;
            if(termId > 0)
                exam = examService.updateExamById(id, newExam, classId, termId);
            else
                exam = examService.updateExamById(id, newExam, classId);
            exam.addLink(resourceLinks.self(exam.getId()));
            if(exam.get_class() != null)
                exam.get_class().addLink(classResourceLinks.self(exam.get_class().getId()));
            return Response.status(Response.Status.OK).entity(exam).build();
        }catch (GradingFactorException e) {
            e.printStackTrace();
            Message message = new Message(400, "Bad Request", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
        }
    }

    @DELETE
    @Path("{examId}")
    public Response deleteExamById(@PathParam("examId") long id) {
        try {
            ExamResourceLinks resourceLinks = new ExamResourceLinks(uriInfo);
            ClassResourceLinks classResourceLinks = new ClassResourceLinks(uriInfo);

            Exam exam = examService.deleteExamById(id);
            exam.addLink(resourceLinks.self(exam.getId()));
            if(exam.get_class() != null)
                exam.get_class().addLink(classResourceLinks.self(exam.get_class().getId()));
            return Response.status(Response.Status.OK).entity(exam).build();
        }catch (GradingFactorException e) {
            e.printStackTrace();
            Message message = new Message(400, "Bad Request", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
        }
    }

    @Path("{examId}/result")
    public ExamResultResource examResultResource() {
        examResultResource.setStudentId(studentId);
        examResultResource.setUriInfo(uriInfo);
        return examResultResource;
    }
}
