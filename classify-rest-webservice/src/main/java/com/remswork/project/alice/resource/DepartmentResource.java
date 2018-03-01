package com.remswork.project.alice.resource;

import com.remswork.project.alice.exception.DepartmentException;
import com.remswork.project.alice.model.Department;
import com.remswork.project.alice.model.support.Message;
import com.remswork.project.alice.resource.links.DepartmentResourceLinks;
import com.remswork.project.alice.service.impl.DepartmentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;


@Component
@Produces(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Path("department")
public class DepartmentResource {

    @Autowired
    private DepartmentServiceImpl departmentService;
    @Context
    private UriInfo uriInfo;

    @GET
    @Path("{departmentId}")
    public Response getDepartmentById(@PathParam("departmentId") long id){
        try{
            DepartmentResourceLinks resourceLinks = new DepartmentResourceLinks(uriInfo);
            Department department = departmentService.getDepartmentById(id);
            department.addLink(resourceLinks.self(id));
            return Response
                    .status(Response.Status.OK)
                    .entity(department)
                    .build();
        }catch (DepartmentException e){
            e.printStackTrace();
            Message message = new Message(404, "Not Found", e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(message).build();
        }
    }

    @GET
    public Response getDepartmentList(){
        try{
            DepartmentResourceLinks resourceLinks = new DepartmentResourceLinks(uriInfo);
            List<Department> departmentList = departmentService.getDepartmentList();
            for(Department d : departmentList)
                d.addLink(resourceLinks.self(d.getId()));
            GenericEntity<List<Department>> entity = new GenericEntity<List<Department>>(departmentList){};
            return Response
                    .status(Response.Status.OK)
                    .entity(entity)
                    .build();
        }catch (DepartmentException e){
            e.printStackTrace();
            Message message = new Message(404, "Not Found", e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(message).build();
        }
    }

    @POST
    public Response addDepartment(Department department){
        try{
            DepartmentResourceLinks resourceLinks = new DepartmentResourceLinks(uriInfo);
            department = departmentService.addDepartment(department);
            department.addLink(resourceLinks.self(department.getId()));
            return Response
                    .status(Response.Status.CREATED)
                    .entity(department)
                    .build();
        }catch(DepartmentException e){
            e.printStackTrace();
            Message message = new Message(400, "Bad Request", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
        }
    }

    @PUT
    @Path("{departmentId}")
    public Response updateDepartmentById(@PathParam("departmentId") long id, Department newDepartment){
        try{
            DepartmentResourceLinks resourceLinks = new DepartmentResourceLinks(uriInfo);
            Department department = departmentService.updateDepartmentById(id, newDepartment);
            department.addLink(resourceLinks.self(id));
            return Response
                    .status(Response.Status.OK)
                    .entity(department)
                    .build();
        }catch (DepartmentException e){
            e.printStackTrace();
            Message message = new Message(400, "Bad Request", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
        }
    }

    @DELETE
    @Path("{departmentId}")
    public Response deleteDepartmentById(@PathParam("departmentId") long id){
        try{
            DepartmentResourceLinks resourceLinks = new DepartmentResourceLinks(uriInfo);
            Department department = departmentService.deleteDepartmentById(id);
            department.addLink(resourceLinks.self(id));
            return Response
                    .status(Response.Status.OK)
                    .entity(department)
                    .build();
        }catch (DepartmentException e){
            e.printStackTrace();
            Message message = new Message(400, "Bad Request", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
        }
    }

}
