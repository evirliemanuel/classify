package com.remswork.project.alice.resource;

import com.remswork.project.alice.exception.GradingFactorException;
import com.remswork.project.alice.model.Formula;
import com.remswork.project.alice.model.support.Message;
import com.remswork.project.alice.resource.links.FormulaResourceLinks;
import com.remswork.project.alice.resource.links.SubjectResourceLinks;
import com.remswork.project.alice.resource.links.TeacherResourceLinks;
import com.remswork.project.alice.service.impl.FormulaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;

@Component
@Produces(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Path("formula")
public class FormulaResource {

    @Autowired
    private FormulaServiceImpl formulaService;
    @Context
    private UriInfo uriInfo;
    @QueryParam("subjectId")
    private long subjectId;
    @QueryParam("teacherId")
    private long teacherId;
    @QueryParam("termId")
    private long termId;

    @GET
    @Path("{formulaId}")
    public Response getFormulaById(@PathParam("formulaId") long id) {
        try {
            FormulaResourceLinks resourceLinks = new FormulaResourceLinks(uriInfo);
            SubjectResourceLinks subjectResourceLinks = new SubjectResourceLinks(uriInfo);
            TeacherResourceLinks teacherResourceLinks = new TeacherResourceLinks(uriInfo);

            Formula formula = formulaService.getFormulaById(id);
            formula.addLink(resourceLinks.self(id));
            if(formula.getSubject() != null)
                formula.getSubject().addLink(subjectResourceLinks.self(formula.getSubject().getId()));
            if(formula.getTeacher() != null)
                formula.getTeacher().addLink(teacherResourceLinks.self(formula.getTeacher().getId()));
            return Response.status(Response.Status.OK).entity(formula).build();
        }catch (GradingFactorException e) {
            e.printStackTrace();
            Message message = new Message(404, "Not Found", e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(message).build();
        }
    }

    @GET
    @Path("0")
    public Response getFormula() {
        try {
            FormulaResourceLinks resourceLinks = new FormulaResourceLinks(uriInfo);
            SubjectResourceLinks subjectResourceLinks = new SubjectResourceLinks(uriInfo);
            TeacherResourceLinks teacherResourceLinks = new TeacherResourceLinks(uriInfo);

            Formula formula = formulaService.getFormulaBySubjectAndTeacherId(subjectId, teacherId, termId);
            formula.addLink(resourceLinks.self(formula.getId()));
            if(formula.getSubject() != null)
                formula.getSubject().addLink(subjectResourceLinks.self(formula.getSubject().getId()));
            if(formula.getTeacher() != null)
                formula.getTeacher().addLink(teacherResourceLinks.self(formula.getTeacher().getId()));
            return Response.status(Response.Status.OK).entity(formula).build();
        }catch (GradingFactorException e) {
            e.printStackTrace();
            Message message = new Message(404, "Not Found", e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(message).build();
        }
    }

    @GET
    public Response getFormulaList() {
        try {
            List<Formula> formulaList;
            FormulaResourceLinks resourceLinks = new FormulaResourceLinks(uriInfo);
            SubjectResourceLinks subjectResourceLinks = new SubjectResourceLinks(uriInfo);
            TeacherResourceLinks teacherResourceLinks = new TeacherResourceLinks(uriInfo);

            if(teacherId != 0)
                formulaList = formulaService.getFormulaListByTeacherId(teacherId);
            else
                formulaList = formulaService.getFormulaList();
            for(Formula formula : formulaList) {
                formula.addLink(resourceLinks.self(formula.getId()));
                if(formula.getTeacher() != null)
                    formula.getTeacher().addLink(teacherResourceLinks.self(formula.getTeacher().getId()));
                if(formula.getSubject() != null)
                    formula.getSubject().addLink(subjectResourceLinks.self(formula.getSubject().getId()));
            }
            GenericEntity<List<Formula>> entity = new GenericEntity<List<Formula>>(formulaList){};
            return Response.status(Response.Status.OK).entity(entity).build();
        }catch (GradingFactorException e) {
            e.printStackTrace();
            Message message = new Message(404, "Not Found", e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(message).build();
        }
    }

    @POST
    public Response addFormula(Formula formula) {
        try {
            FormulaResourceLinks resourceLinks = new FormulaResourceLinks(uriInfo);
            SubjectResourceLinks subjectResourceLinks = new SubjectResourceLinks(uriInfo);
            TeacherResourceLinks teacherResourceLinks = new TeacherResourceLinks(uriInfo);
            if(termId > 0)
                formula = formulaService.addFormula(formula, subjectId, teacherId, termId);
            else
                formula = formulaService.addFormula(formula, subjectId, teacherId);
            formula.addLink(resourceLinks.self(formula.getId()));
            if(formula.getTeacher() != null)
                formula.getTeacher().addLink(teacherResourceLinks.self(formula.getTeacher().getId()));
            if(formula.getSubject() != null)
                formula.getSubject().addLink(subjectResourceLinks.self(formula.getSubject().getId()));
            return Response.status(Response.Status.CREATED).entity(formula).build();
        }catch (GradingFactorException e) {
            e.printStackTrace();
            Message message = new Message(400, "Bad Request", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
        }
    }

    @PUT
    @Path("{formulaId}")
    public Response updateFormulaById(@PathParam("formulaId") long id, Formula newFormula) {
        try {
            FormulaResourceLinks resourceLinks = new FormulaResourceLinks(uriInfo);
            SubjectResourceLinks subjectResourceLinks = new SubjectResourceLinks(uriInfo);
            TeacherResourceLinks teacherResourceLinks = new TeacherResourceLinks(uriInfo);
            Formula formula = formulaService.updateFormulaById(id, newFormula, subjectId, teacherId, termId);

            formula.addLink(resourceLinks.self(formula.getId()));
            if(formula.getTeacher() != null)
                formula.getTeacher().addLink(teacherResourceLinks.self(formula.getTeacher().getId()));
            if(formula.getSubject() != null)
                formula.getSubject().addLink(subjectResourceLinks.self(formula.getSubject().getId()));
            return Response.status(Response.Status.OK).entity(formula).build();
        }catch (GradingFactorException e) {
            e.printStackTrace();
            Message message = new Message(400, "Bad Request", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
        }
    }

    @DELETE
    @Path("{formulaId}")
    public Response deleteFormulaById(@PathParam("formulaId") long id) {
        try {
            FormulaResourceLinks resourceLinks = new FormulaResourceLinks(uriInfo);
            SubjectResourceLinks subjectResourceLinks = new SubjectResourceLinks(uriInfo);
            TeacherResourceLinks teacherResourceLinks = new TeacherResourceLinks(uriInfo);
            Formula formula = formulaService.deleteFormulaById(id);

            formula.addLink(resourceLinks.self(formula.getId()));
            if(formula.getTeacher() != null)
                formula.getTeacher().addLink(teacherResourceLinks.self(formula.getTeacher().getId()));
            if(formula.getSubject() != null)
                formula.getSubject().addLink(subjectResourceLinks.self(formula.getSubject().getId()));
            return Response.status(Response.Status.OK).entity(formula).build();
        }catch (GradingFactorException e) {
            e.printStackTrace();
            Message message = new Message(400, "Bad Request", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
        }
    }

}
