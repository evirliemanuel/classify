package com.remswork.project.alice.resource;

import com.remswork.project.alice.exception.SubjectException;
import com.remswork.project.alice.model.Subject;
import com.remswork.project.alice.model.support.Message;
import com.remswork.project.alice.resource.links.SubjectResourceLinks;
import com.remswork.project.alice.service.impl.SubjectServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;

@Component
@Produces(value = {MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Consumes(value = {MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Path("subject")
public class SubjectResource {

    @Autowired
    private SubjectServiceImpl subjectService;
    @Context
    private UriInfo uriInfo;

    @GET
    @Path("0")
    public Response getSubject(@QueryParam("classId") long classId, @QueryParam("scheduleId") long scheduleId,
                               @QueryParam("teacherId") long teacherId) {
        try {
            if (classId != 0 && teacherId != 0) {
                SubjectResourceLinks resourceLinks = new SubjectResourceLinks(uriInfo);
                Subject subject = subjectService.getSubjectByClassAndTeacherId(classId, teacherId);
                subject.addLink(resourceLinks.self(subject.getId()));
                return Response.status(Response.Status.OK).entity(subject).build();
            } else if(scheduleId != 0){
                SubjectResourceLinks resourceLinks = new SubjectResourceLinks(uriInfo);
                Subject subject = subjectService.getSubjectByScheduleId(scheduleId);
                subject.addLink(resourceLinks.self(subject.getId()));
                return Response.status(Response.Status.OK).entity(subject).build();
            } else
                throw new SubjectException("No subject found");
        } catch (SubjectException e) {
            e.printStackTrace();
            Message message = new Message(404, "Not Found", e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(message).build();
        }
    }

    @GET
    @Path("{subjectId}")
    public Response getSubjectById(@PathParam("subjectId") long id) {
        try {
            SubjectResourceLinks resourceLinks = new SubjectResourceLinks(uriInfo);
            Subject subject = subjectService.getSubjectById(id);
            subject.addLink(resourceLinks.self(id));
            return Response.status(Response.Status.OK).entity(subject).build();
        } catch (SubjectException e) {
            e.printStackTrace();
            Message message = new Message(404, "Not Found", e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(message).build();
        }
    }

    @GET
    public Response getSubjectList(@QueryParam("teacherId") long teacherId, @QueryParam("studentId") long studentId) {
        try {
            List<Subject> subjectList;
            SubjectResourceLinks resourceLinks = new SubjectResourceLinks(uriInfo);

            if(teacherId != 0)
                subjectList = subjectService.getSubjectListByTeacherId(teacherId);
            else if(studentId != 0)
                subjectList = subjectService.getSubjectListByStudentId(studentId);
            else
                subjectList = subjectService.getSubjectList();
            for (Subject subject : subjectList)
                subject.addLink(resourceLinks.self(subject.getId()));
            GenericEntity<List<Subject>> entity = new GenericEntity<List<Subject>>(subjectList) {
            };
            return Response.status(Response.Status.OK).entity(entity).build();
        } catch (SubjectException e) {
            e.printStackTrace();
            Message message = new Message(404, "Not Found", e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(message).build();
        }
    }

    @GET
    @Path("1")
    @Deprecated
    public Response getSubjectListByTeacherId(@QueryParam("teacherId") long teacherId) {
        try {
            SubjectResourceLinks resourceLinks = new SubjectResourceLinks(uriInfo);
            List<Subject> subjectList = subjectService.getSubjectListByTeacherId(teacherId);
            for (Subject subject : subjectList)
                subject.addLink(resourceLinks.self(subject.getId()));
            GenericEntity<List<Subject>> entity = new GenericEntity<List<Subject>>(subjectList) {
            };
            return Response.status(Response.Status.OK).entity(entity).build();
        } catch (SubjectException e) {
            e.printStackTrace();
            Message message = new Message(404, "Not Found", e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(message).build();
        }
    }

    @GET
    @Path("1/unique")
    @Deprecated
    public Response getSubjectListByTeacherIdUnique(@QueryParam("teacherId") long teacherId) {
        try {
            SubjectResourceLinks resourceLinks = new SubjectResourceLinks(uriInfo);
            List<Subject> subjectList = subjectService.getSubjectListByTeacherIdUnique(teacherId);
            for (Subject subject : subjectList)
                subject.addLink(resourceLinks.self(subject.getId()));
            GenericEntity<List<Subject>> entity = new GenericEntity<List<Subject>>(subjectList) {
            };
            return Response.status(Response.Status.OK).entity(entity).build();
        } catch (SubjectException e) {
            e.printStackTrace();
            Message message = new Message(404, "Not Found", e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(message).build();
        }
    }

    @POST
    public Response addSubject(Subject subject) {
        try {
            SubjectResourceLinks resourceLinks = new SubjectResourceLinks(uriInfo);
            subject = subjectService.addSubject(subject);
            subject.addLink(resourceLinks.self(subject.getId()));
            return Response.status(Response.Status.CREATED).entity(subject).build();
        } catch (SubjectException e) {
            e.printStackTrace();
            Message message = new Message(400, "Bad Request", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
        }
    }

    @PUT
    @Path("{subjectId}")
    public Response updateSubjectById(@PathParam("subjectId") long id, Subject newSubject) {
        try {
            SubjectResourceLinks resourceLinks = new SubjectResourceLinks(uriInfo);
            Subject subject = subjectService.updateSubjectById(id, newSubject);
            subject.addLink(resourceLinks.self(id));
            return Response.status(Response.Status.OK).entity(subject).build();
        } catch (SubjectException e) {
            e.printStackTrace();
            Message message = new Message(400, "Bad Request", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
        }
    }

    @DELETE
    @Path("{subjectId}")
    public Response deleteSubjectById(@PathParam("subjectId") long id) {
        try {
            SubjectResourceLinks resourceLinks = new SubjectResourceLinks(uriInfo);
            Subject subject = subjectService.deleteSubjectById(id);
            subject.addLink(resourceLinks.self(id));
            return Response.status(Response.Status.OK).entity(subject).build();
        } catch (SubjectException e) {
            e.printStackTrace();
            Message message = new Message(400, "Bad Request", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
        }
    }

}
