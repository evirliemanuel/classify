package io.classify.service;

import io.classify.model.MarkDto;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GradeService {

    @GET("test/total/class/{classId}/term/{termId}/teacher/{teacherId}/subject/{subjectId}/student/{studentId}")
    Observable<String> findTotal(@Path("classId") long classId,
                                @Path("termId") long termId,
                                @Path("teacherId") long teacherId,
                                @Path("subjectId") long subjectId,
                                @Path("studentId") long studentId);

    @GET("test/all/class/{classId}/teacher/{teacherId}/subject/{subjectId}/student/{studentId}")
    Observable<MarkDto> findAll(@Path("classId") long classId,
                                  @Path("teacherId") long teacherId,
                                  @Path("subjectId") long subjectId,
                                  @Path("studentId") long studentId);
}
