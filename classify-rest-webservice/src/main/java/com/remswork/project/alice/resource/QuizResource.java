package com.remswork.project.alice.resource;

import com.remswork.project.alice.exception.GradingFactorException;
import com.remswork.project.alice.model.Quiz;
import com.remswork.project.alice.model.support.Message;
import com.remswork.project.alice.resource.links.QuizResourceLinks;
import com.remswork.project.alice.resource.links.ClassResourceLinks;
import com.remswork.project.alice.service.impl.QuizServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;

@Component
@Produces(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Path("quiz")
public class QuizResource {

    @Autowired
    private QuizServiceImpl quizService;
    @Autowired
    private QuizResultResource quizResultResource;
    @Context
    private UriInfo uriInfo;
    @QueryParam("classId")
    private long classId;
    @QueryParam("termId")
    private long termId;
    @QueryParam("studentId")
    private long studentId;

    @GET
    @Path("{quizId}")
    public Response getQuizById(@PathParam("quizId") long id) {
        try {
            QuizResourceLinks resourceLinks = new QuizResourceLinks(uriInfo);
            ClassResourceLinks classResourceLinks = new ClassResourceLinks(uriInfo);

            Quiz quiz = quizService.getQuizById(id);
            quiz.addLink(resourceLinks.self(id));
            if(quiz.get_class() != null)
                quiz.get_class().addLink(classResourceLinks.self(quiz.get_class().getId()));
            return Response.status(Response.Status.OK).entity(quiz).build();
        }catch (GradingFactorException e) {
            e.printStackTrace();
            Message message = new Message(404, "Not Found", e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(message).build();
        }
    }

    @GET
    public Response getQuizList() {
        try {
            List<Quiz> quizList;
            QuizResourceLinks resourceLinks = new QuizResourceLinks(uriInfo);
            ClassResourceLinks classResourceLinks = new ClassResourceLinks(uriInfo);

            if(classId != 0 && termId != 0)
                quizList = quizService.getQuizListByClassId(classId, termId);
            else if(classId != 0)
                quizList = quizService.getQuizListByClassId(classId);
            else if(studentId != 0 && termId != 0)
                quizList = quizService.getQuizListByStudentId(studentId, termId);
            else if(studentId != 0)
                quizList = quizService.getQuizListByStudentId(studentId);
            else
                quizList = quizService.getQuizList();
            for(Quiz quiz : quizList) {
                quiz.addLink(resourceLinks.self(quiz.getId()));
                if(quiz.get_class() != null)
                    quiz.get_class().addLink(classResourceLinks.self(quiz.get_class().getId()));
            }
            GenericEntity<List<Quiz>> entity = new GenericEntity<List<Quiz>>(quizList){};
            return Response.status(Response.Status.OK).entity(entity).build();
        }catch (GradingFactorException e) {
            e.printStackTrace();
            Message message = new Message(404, "Not Found", e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(message).build();
        }
    }

    @POST
    public Response addQuiz(Quiz quiz) {
        try {
            QuizResourceLinks resourceLinks = new QuizResourceLinks(uriInfo);
            ClassResourceLinks classResourceLinks = new ClassResourceLinks(uriInfo);

            if(termId > 0)
                quiz = quizService.addQuiz(quiz, classId, termId);
            else
                quiz = quizService.addQuiz(quiz, classId);
            quiz.addLink(resourceLinks.self(quiz.getId()));
            if(quiz.get_class() != null)
                quiz.get_class().addLink(classResourceLinks.self(quiz.get_class().getId()));
            return Response.status(Response.Status.CREATED).entity(quiz).build();
        }catch (GradingFactorException e) {
            e.printStackTrace();
            Message message = new Message(400, "Bad Request", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
        }
    }

    @PUT
    @Path("{quizId}")
    public Response updateQuizById(@PathParam("quizId") long id, Quiz newQuiz) {
        try {
            QuizResourceLinks resourceLinks = new QuizResourceLinks(uriInfo);
            ClassResourceLinks classResourceLinks = new ClassResourceLinks(uriInfo);
            Quiz quiz;
            if(termId > 0)
                quiz = quizService.updateQuizById(id, newQuiz, classId, termId);
            else
                quiz = quizService.updateQuizById(id, newQuiz, classId);
            quiz.addLink(resourceLinks.self(quiz.getId()));
            if(quiz.get_class() != null)
                quiz.get_class().addLink(classResourceLinks.self(quiz.get_class().getId()));
            return Response.status(Response.Status.OK).entity(quiz).build();
        }catch (GradingFactorException e) {
            e.printStackTrace();
            Message message = new Message(400, "Bad Request", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
        }
    }

    @DELETE
    @Path("{quizId}")
    public Response deleteQuizById(@PathParam("quizId") long id) {
        try {
            QuizResourceLinks resourceLinks = new QuizResourceLinks(uriInfo);
            ClassResourceLinks classResourceLinks = new ClassResourceLinks(uriInfo);

            Quiz quiz = quizService.deleteQuizById(id);
            quiz.addLink(resourceLinks.self(quiz.getId()));
            if(quiz.get_class() != null)
                quiz.get_class().addLink(classResourceLinks.self(quiz.get_class().getId()));
            return Response.status(Response.Status.OK).entity(quiz).build();
        }catch (GradingFactorException e) {
            e.printStackTrace();
            Message message = new Message(400, "Bad Request", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
        }
    }

    @Path("{quizId}/result")
    public QuizResultResource quizResultResource() {
        quizResultResource.setStudentId(studentId);
        quizResultResource.setUriInfo(uriInfo);
        return quizResultResource;
    }
}
