package com.remswork.project.alice.resource;

import com.remswork.project.alice.exception.GradingFactorException;
import com.remswork.project.alice.model.Term;
import com.remswork.project.alice.model.support.Message;
import com.remswork.project.alice.resource.links.TermResourceLinks;
import com.remswork.project.alice.service.impl.TermServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;

@Component
@Produces(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Path("term")
public class TermResource {

    @Autowired
    private TermServiceImpl termService;
    @Context
    private UriInfo uriInfo;

    @GET
    @Path("{termId}")
    public Response getTermById(@PathParam("termId") long id) {
        try {
            TermResourceLinks resourceLinks = new TermResourceLinks(uriInfo);
            Term term = termService.getTermById(id);
            term.addLink(resourceLinks.self(id));
            return Response.status(Response.Status.OK).entity(term).build();
        }catch (GradingFactorException e) {
            e.printStackTrace();
            Message message = new Message(404, "Not Found", e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(message).build();
        }
    }

    @GET
    public Response getTermList() {
        try {
            TermResourceLinks resourceLinks = new TermResourceLinks(uriInfo);
            List<Term> termList = termService.getTermList();
            for(Term term : termList)
                term.addLink(resourceLinks.self(term.getId()));
            GenericEntity<List<Term>> entity = new GenericEntity<List<Term>>(termList){};
            return Response.status(Response.Status.OK).entity(entity).build();
        }catch (GradingFactorException e) {
            e.printStackTrace();
            Message message = new Message(404, "Not Found", e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(message).build();
        }
    }

    @POST
    public Response addTerm(Term term) {
        try {
            TermResourceLinks resourceLinks = new TermResourceLinks(uriInfo);
            term = termService.addTerm(term);
            term.addLink(resourceLinks.self(term.getId()));
            return Response.status(Response.Status.CREATED).entity(term).build();
        }catch (GradingFactorException e) {
            e.printStackTrace();
            Message message = new Message(400, "Bad Request", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
        }
    }

    @PUT
    @Path("{termId}")
    public Response updateTermById(@PathParam("termId") long id, Term newTerm) {
        try {
            TermResourceLinks resourceLinks = new TermResourceLinks(uriInfo);
            Term term = termService.updateTermById(id, newTerm);
            term.addLink(resourceLinks.self(id));
            return Response.status(Response.Status.OK).entity(term).build();
        }catch (GradingFactorException e) {
            e.printStackTrace();
            Message message = new Message(400, "Bad Request", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
        }
    }

    @DELETE
    @Path("{termId}")
    public Response deleteTermById(@PathParam("termId") long id) {
        try {
            TermResourceLinks resourceLinks = new TermResourceLinks(uriInfo);
            Term term = termService.deleteTermById(id);
            term.addLink(resourceLinks.self(id));
            return Response.status(Response.Status.OK).entity(term).build();
        }catch (GradingFactorException e) {
            e.printStackTrace();
            Message message = new Message(400, "Bad Request", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
        }
    }
}
