package com.remswork.project.alice.resource;

import com.remswork.project.alice.dao.exception.ClassDaoException;
import com.remswork.project.alice.exception.ClassException;
import com.remswork.project.alice.model.Student;
import com.remswork.project.alice.model.support.Message;
import com.remswork.project.alice.resource.links.ClassStudentListResourceLinks;
import com.remswork.project.alice.service.impl.ClassServiceImpl;
import com.remswork.project.alice.service.impl.ScheduleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.Set;

@Component
@Produces(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Path("student")
public class ClassStudentListResource {

    @Autowired
    private ClassServiceImpl classService;
    @Autowired
    private ScheduleServiceImpl scheduleService;
    private UriInfo uriInfo;
    private long studentId;

    void setUriInfo(UriInfo uriInfo) {
        this.uriInfo = uriInfo;
    }

    void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    @GET
    @Path("{studentId}")
    public Response getStudentById(@PathParam("classId") long classId, @PathParam("studentId") long id) {
        try {
            ClassStudentListResourceLinks resourceLinks = new ClassStudentListResourceLinks(uriInfo);
            Student student = classService.getStudentById(classId, id);
            student.addLink(resourceLinks.self(classId, id));
            return Response.status(Response.Status.OK).entity(student).build();
        } catch (ClassException e) {
            e.printStackTrace();
            Message message = new Message(404, "Not Found", e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(message).build();
        }
    }

    @GET
    public Response getStudentList(@PathParam("classId") long classId) {
        try {
            ClassStudentListResourceLinks resourceLinks = new ClassStudentListResourceLinks(uriInfo);
            Set<Student> studentSet = classService.getStudentList(classId);
            for (Student student : studentSet)
                student.addLink(resourceLinks.self(classId, student.getId()));
            GenericEntity<Set<Student>> entity = new GenericEntity<Set<Student>>(studentSet) {
            };
            return Response.status(Response.Status.OK).entity(entity).build();
        } catch (ClassException e) {
            e.printStackTrace();
            Message message = new Message(404, "Not Found", e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(message).build();
        }
    }

    @POST
    public Response addStudentById(@PathParam("classId") long classId) {
        try {
            ClassStudentListResourceLinks resourceLinks = new ClassStudentListResourceLinks(uriInfo);
            if (studentId == 0)
                throw new ClassDaoException("Query param : studentId is required");
            Student student = classService.addStudentById(classId, studentId);
            student.addLink(resourceLinks.self(classId, studentId));
            return Response.status(Response.Status.OK).entity(student).build();
        } catch (ClassException | ClassDaoException e) {
            e.printStackTrace();
            Message message = new Message(400, "Bad Request", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
        }
    }

    @DELETE
    @Path("{studentId}")
    public Response deleteStudentById(@PathParam("classId") long classId, @PathParam("studentId") long id) {
        try {
            ClassStudentListResourceLinks resourceLinks = new ClassStudentListResourceLinks(uriInfo);
            Student student = classService.deleteStudentById(classId, id);
            student.addLink(resourceLinks.self(classId, id));
            return Response.status(Response.Status.OK).entity(student).build();
        } catch (ClassException e) {
            e.printStackTrace();
            Message message = new Message(400, "Bad Request", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
        }
    }
}
