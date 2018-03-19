package com.remswork.project.alice.resource;

import com.remswork.project.alice.exception.StudentException;
import com.remswork.project.alice.model.Section;
import com.remswork.project.alice.model.Student;
import com.remswork.project.alice.model.support.Message;
import com.remswork.project.alice.resource.links.DepartmentResourceLinks;
import com.remswork.project.alice.resource.links.SectionResourceLinks;
import com.remswork.project.alice.resource.links.StudentResourceLinks;
import com.remswork.project.alice.service.impl.StudentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;

@Component
@Produces(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Path("student")
public class StudentResource {

    @Autowired
    private StudentServiceImpl studentService;
    @Context
    private UriInfo uriInfo;
    @QueryParam("sectionId")
    private long sectionId;

    @GET
    @Path("{studentId}")
    public Response getStudentById(@PathParam("studentId") long id) {
       try{
           StudentResourceLinks resourceLinks = new StudentResourceLinks(uriInfo);
           SectionResourceLinks sectionResourceLinks = new SectionResourceLinks(uriInfo);
           DepartmentResourceLinks departmentResourceLinks = new DepartmentResourceLinks(uriInfo);
           Student student = studentService.getStudentById(id);
           student.addLink(resourceLinks.self(id));
           if(student.getSection() != null) {
               Section section = student.getSection();
               section.addLink(sectionResourceLinks.self(section.getId()));
               if(section.getDepartment() != null)
                   section.getDepartment().addLink(departmentResourceLinks.self(section.getDepartment().getId()));
           }
           return Response.status(Response.Status.OK).entity(student).build();
       }catch (StudentException e) {
           e.printStackTrace();
           Message message = new Message(404, "Not Found", e.getMessage());
           return Response.status(Response.Status.NOT_FOUND).entity(message).build();
       }
    }

    @QueryParam("sn") Long sn;

    @GET
    public Response getStudentList() {
        try {
            StudentResourceLinks resourceLinks = new StudentResourceLinks(uriInfo);
            SectionResourceLinks sectionResourceLinks = new SectionResourceLinks(uriInfo);
            DepartmentResourceLinks departmentResourceLinks = new DepartmentResourceLinks(uriInfo);
            List<Student> studentList = studentService.getStudentList();
            if (sn == null) {
                for (Student student : studentList) {
                    student.addLink(resourceLinks.self(student.getId()));
                    if (student.getSection() != null) {
                        Section section = student.getSection();
                        section.addLink(sectionResourceLinks.self(section.getId()));
                        if (section.getDepartment() != null)
                            section.getDepartment().addLink(departmentResourceLinks.self(section.getDepartment().getId()));
                    }
                }
                GenericEntity<List<Student>> entity = new GenericEntity<List<Student>>(studentList) {
                };
                return Response.status(Response.Status.OK).entity(entity).build();
            } else {
                Student student = studentService.getStudentBySN(sn);
                if (student.getSection() != null) {
                    Section section = student.getSection();
                    section.addLink(sectionResourceLinks.self(section.getId()));
                    if (section.getDepartment() != null) {
                        section.getDepartment().addLink(departmentResourceLinks.self(section.getDepartment().getId()));
                    }
                }
                return Response.status(Response.Status.OK).entity(student).build();
            }
        }catch (StudentException e) {
            e.printStackTrace();
            Message message = new Message(404, "Not Found", e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(message).build();
        }
    }

    @POST
    public Response addStudent(Student student) {
        try {
            StudentResourceLinks resourceLinks = new StudentResourceLinks(uriInfo);
            SectionResourceLinks sectionResourceLinks = new SectionResourceLinks(uriInfo);
            DepartmentResourceLinks departmentResourceLinks = new DepartmentResourceLinks(uriInfo);
            student = studentService.addStudent(student, sectionId);
            student.addLink(resourceLinks.self(student.getId()));
            if(student.getSection() != null) {
                Section section = student.getSection();
                section.addLink(sectionResourceLinks.self(section.getId()));
                if(section.getDepartment() != null)
                    section.getDepartment().addLink(departmentResourceLinks.self(section.getDepartment().getId()));
            }
            return Response.status(Response.Status.CREATED).entity(student).build();
        }catch (StudentException e) {
            e.printStackTrace();
            Message message = new Message(400, "Bad Request", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
        }
    }

    @PUT
    @Path("{studentId}")
    public Response updateStudentById(@PathParam("studentId") long id, Student newStudent) {
        try {
            StudentResourceLinks resourceLinks = new StudentResourceLinks(uriInfo);
            SectionResourceLinks sectionResourceLinks = new SectionResourceLinks(uriInfo);
            DepartmentResourceLinks departmentResourceLinks = new DepartmentResourceLinks(uriInfo);
            Student student = studentService.updateStudentById(id, newStudent, sectionId);
            student.addLink(resourceLinks.self(id));
            if(student.getSection() != null) {
                Section section = student.getSection();
                section.addLink(sectionResourceLinks.self(section.getId()));
                if(section.getDepartment() != null)
                    section.getDepartment().addLink(departmentResourceLinks.self(section.getDepartment().getId()));
            }
            return Response.status(Response.Status.OK).entity(student).build();
        }catch (StudentException e) {
            e.printStackTrace();
            Message message = new Message(400, "Bad Request", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
        }
    }

    @DELETE
    @Path("{studentId}")
    public Response deleteStudentById(@PathParam("studentId") long id) {
        try {
            StudentResourceLinks resourceLinks = new StudentResourceLinks(uriInfo);
            SectionResourceLinks sectionResourceLinks = new SectionResourceLinks(uriInfo);
            DepartmentResourceLinks departmentResourceLinks = new DepartmentResourceLinks(uriInfo);
            Student student = studentService.deleteStudentById(id);
            student.addLink(resourceLinks.self(id));
            if(student.getSection() != null) {
                Section section = student.getSection();
                section.addLink(sectionResourceLinks.self(section.getId()));
                if(section.getDepartment() != null)
                    section.getDepartment().addLink(departmentResourceLinks.self(section.getDepartment().getId()));
            }
            return Response.status(Response.Status.OK).entity(student).build();
        }catch (StudentException e) {
            e.printStackTrace();
            Message message = new Message(400, "Bad Request", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
        }
    }
}
