package com.remswork.project.alice.resource;

import com.remswork.project.alice.exception.GradingFactorException;
import com.remswork.project.alice.model.Assignment;
import com.remswork.project.alice.model.support.Message;
import com.remswork.project.alice.resource.links.AssignmentResourceLinks;
import com.remswork.project.alice.resource.links.ClassResourceLinks;
import com.remswork.project.alice.service.impl.AssignmentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;

@Component
@Produces(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Path("assignment")
public class AssignmentResource {

    @Autowired
    private AssignmentServiceImpl assignmentService;
    @Autowired
    private AssignmentResultResource assignmentResultResource;
    @Context
    private UriInfo uriInfo;
    @QueryParam("classId")
    private long classId;
    @QueryParam("termId")
    private long termId;
    @QueryParam("studentId")
    private long studentId;

    @GET
    @Path("{assignmentId}")
    public Response getAssignmentById(@PathParam("assignmentId") long id) {
        try {
            AssignmentResourceLinks resourceLinks = new AssignmentResourceLinks(uriInfo);
            ClassResourceLinks classResourceLinks = new ClassResourceLinks(uriInfo);

            Assignment assignment = assignmentService.getAssignmentById(id);
            assignment.addLink(resourceLinks.self(id));
            if(assignment.get_class() != null)
                assignment.get_class().addLink(classResourceLinks.self(assignment.get_class().getId()));
            return Response.status(Response.Status.OK).entity(assignment).build();
        }catch (GradingFactorException e) {
            e.printStackTrace();
            Message message = new Message(404, "Not Found", e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(message).build();
        }
    }

    @GET
    public Response getAssignmentList() {
        try {
            List<Assignment> assignmentList;
            AssignmentResourceLinks resourceLinks = new AssignmentResourceLinks(uriInfo);
            ClassResourceLinks classResourceLinks = new ClassResourceLinks(uriInfo);

            if(classId != 0 && termId != 0)
                assignmentList = assignmentService.getAssignmentListByClassId(classId, termId);
            else if(classId != 0)
                assignmentList = assignmentService.getAssignmentListByClassId(classId);
            else if(studentId != 0 && termId != 0)
                assignmentList = assignmentService.getAssignmentListByStudentId(studentId, termId);
            else if(studentId != 0)
                assignmentList = assignmentService.getAssignmentListByStudentId(studentId);
            else
                assignmentList = assignmentService.getAssignmentList();
            for(Assignment assignment : assignmentList) {
                assignment.addLink(resourceLinks.self(assignment.getId()));
                if(assignment.get_class() != null)
                    assignment.get_class().addLink(classResourceLinks.self(assignment.get_class().getId()));
            }
            GenericEntity<List<Assignment>> entity = new GenericEntity<List<Assignment>>(assignmentList){};
            return Response.status(Response.Status.OK).entity(entity).build();
        }catch (GradingFactorException e) {
            e.printStackTrace();
            Message message = new Message(404, "Not Found", e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(message).build();
        }
    }

    @POST
    public Response addAssignment(Assignment assignment) {
        try {
            AssignmentResourceLinks resourceLinks = new AssignmentResourceLinks(uriInfo);
            ClassResourceLinks classResourceLinks = new ClassResourceLinks(uriInfo);

            if(termId > 0)
                assignment = assignmentService.addAssignment(assignment, classId, termId);
            else
                assignment = assignmentService.addAssignment(assignment, classId);
            assignment.addLink(resourceLinks.self(assignment.getId()));
            if(assignment.get_class() != null)
                assignment.get_class().addLink(classResourceLinks.self(assignment.get_class().getId()));
            return Response.status(Response.Status.CREATED).entity(assignment).build();
        }catch (GradingFactorException e) {
            e.printStackTrace();
            Message message = new Message(400, "Bad Request", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
        }
    }

    @PUT
    @Path("{assignmentId}")
    public Response updateAssignmentById(@PathParam("assignmentId") long id, Assignment newAssignment) {
        try {
            AssignmentResourceLinks resourceLinks = new AssignmentResourceLinks(uriInfo);
            ClassResourceLinks classResourceLinks = new ClassResourceLinks(uriInfo);
            Assignment assignment;
            if(termId > 0)
                assignment = assignmentService.updateAssignmentById(id, newAssignment, classId, termId);
            else
                assignment = assignmentService.updateAssignmentById(id, newAssignment, classId);
            assignment.addLink(resourceLinks.self(assignment.getId()));
            if(assignment.get_class() != null)
                assignment.get_class().addLink(classResourceLinks.self(assignment.get_class().getId()));
            return Response.status(Response.Status.OK).entity(assignment).build();
        }catch (GradingFactorException e) {
            e.printStackTrace();
            Message message = new Message(400, "Bad Request", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
        }
    }

    @DELETE
    @Path("{assignmentId}")
    public Response deleteAssignmentById(@PathParam("assignmentId") long id) {
        try {
            AssignmentResourceLinks resourceLinks = new AssignmentResourceLinks(uriInfo);
            ClassResourceLinks classResourceLinks = new ClassResourceLinks(uriInfo);

            Assignment assignment = assignmentService.deleteAssignmentById(id);
            assignment.addLink(resourceLinks.self(assignment.getId()));
            if(assignment.get_class() != null)
                assignment.get_class().addLink(classResourceLinks.self(assignment.get_class().getId()));
            return Response.status(Response.Status.OK).entity(assignment).build();
        }catch (GradingFactorException e) {
            e.printStackTrace();
            Message message = new Message(400, "Bad Request", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
        }
    }

    @Path("{assignmentId}/result")
    public AssignmentResultResource assignmentResultResource() {
        assignmentResultResource.setStudentId(studentId);
        assignmentResultResource.setUriInfo(uriInfo);
        return assignmentResultResource;
    }
}
