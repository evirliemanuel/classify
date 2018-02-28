package com.remswork.project.alice.resource;

import com.remswork.project.alice.exception.GradingFactorException;
import com.remswork.project.alice.model.Project;
import com.remswork.project.alice.model.support.Message;
import com.remswork.project.alice.resource.links.ClassResourceLinks;
import com.remswork.project.alice.resource.links.ProjectResourceLinks;
import com.remswork.project.alice.service.impl.ProjectServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;

@Component
@Produces(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Path("project")
public class ProjectResource {

    @Autowired
    private ProjectServiceImpl projectService;
    @Autowired
    private ProjectResultResource projectResultResource;
    @Context
    private UriInfo uriInfo;
    @QueryParam("classId")
    private long classId;
    @QueryParam("termId")
    private long termId;
    @QueryParam("studentId")
    private long studentId;

    @GET
    @Path("{projectId}")
    public Response getProjectById(@PathParam("projectId") long id) {
        try {
            ProjectResourceLinks resourceLinks = new ProjectResourceLinks(uriInfo);
            ClassResourceLinks classResourceLinks = new ClassResourceLinks(uriInfo);

            Project project = projectService.getProjectById(id);
            project.addLink(resourceLinks.self(id));
            if(project.get_class() != null)
                project.get_class().addLink(classResourceLinks.self(project.get_class().getId()));
            return Response.status(Response.Status.OK).entity(project).build();
        }catch (GradingFactorException e) {
            e.printStackTrace();
            Message message = new Message(404, "Not Found", e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(message).build();
        }
    }

    @GET
    public Response getProjectList() {
        try {
            List<Project> projectList;
            ProjectResourceLinks resourceLinks = new ProjectResourceLinks(uriInfo);
            ClassResourceLinks classResourceLinks = new ClassResourceLinks(uriInfo);

            if(classId != 0 && termId != 0)
                projectList = projectService.getProjectListByClassId(classId, termId);
            else if(classId != 0)
                projectList = projectService.getProjectListByClassId(classId);
            else if(studentId != 0 && termId != 0)
                projectList = projectService.getProjectListByStudentId(studentId, termId);
            else if(studentId != 0)
                projectList = projectService.getProjectListByStudentId(studentId);
            else
                projectList = projectService.getProjectList();
            for(Project project : projectList) {
                project.addLink(resourceLinks.self(project.getId()));
                if(project.get_class() != null)
                    project.get_class().addLink(classResourceLinks.self(project.get_class().getId()));
            }
            GenericEntity<List<Project>> entity = new GenericEntity<List<Project>>(projectList){};
            return Response.status(Response.Status.OK).entity(entity).build();
        }catch (GradingFactorException e) {
            e.printStackTrace();
            Message message = new Message(404, "Not Found", e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(message).build();
        }
    }

    @POST
    public Response addProject(Project project) {
        try {
            ProjectResourceLinks resourceLinks = new ProjectResourceLinks(uriInfo);
            ClassResourceLinks classResourceLinks = new ClassResourceLinks(uriInfo);

            if(termId > 0)
                project = projectService.addProject(project, classId, termId);
            else
                project = projectService.addProject(project, classId);
            project.addLink(resourceLinks.self(project.getId()));
            if(project.get_class() != null)
                project.get_class().addLink(classResourceLinks.self(project.get_class().getId()));
            return Response.status(Response.Status.CREATED).entity(project).build();
        }catch (GradingFactorException e) {
            e.printStackTrace();
            Message message = new Message(400, "Bad Request", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
        }
    }

    @PUT
    @Path("{projectId}")
    public Response updateProjectById(@PathParam("projectId") long id, Project newProject) {
        try {
            ProjectResourceLinks resourceLinks = new ProjectResourceLinks(uriInfo);
            ClassResourceLinks classResourceLinks = new ClassResourceLinks(uriInfo);
            Project project;
            if(termId > 0)
                project = projectService.updateProjectById(id, newProject, classId, termId);
            else
                project = projectService.updateProjectById(id, newProject, classId);
            project.addLink(resourceLinks.self(project.getId()));
            if(project.get_class() != null)
                project.get_class().addLink(classResourceLinks.self(project.get_class().getId()));
            return Response.status(Response.Status.OK).entity(project).build();
        }catch (GradingFactorException e) {
            e.printStackTrace();
            Message message = new Message(400, "Bad Request", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
        }
    }

    @DELETE
    @Path("{projectId}")
    public Response deleteProjectById(@PathParam("projectId") long id) {
        try {
            ProjectResourceLinks resourceLinks = new ProjectResourceLinks(uriInfo);
            ClassResourceLinks classResourceLinks = new ClassResourceLinks(uriInfo);

            Project project = projectService.deleteProjectById(id);
            project.addLink(resourceLinks.self(project.getId()));
            if(project.get_class() != null)
                project.get_class().addLink(classResourceLinks.self(project.get_class().getId()));
            return Response.status(Response.Status.OK).entity(project).build();
        }catch (GradingFactorException e) {
            e.printStackTrace();
            Message message = new Message(400, "Bad Request", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
        }
    }

    @Path("{projectId}/result")
    public ProjectResultResource projectResultResource() {
        projectResultResource.setStudentId(studentId);
        projectResultResource.setUriInfo(uriInfo);
        return projectResultResource;
    }
}
