package com.arcis.vgil.apiconnectivity;



import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface ApiInterface {
    //@Headers("Content-Type: application/json")
   // @POST("App/GetTemplateDetails")
   // Observable<LeadModelBean> getLeadResponce(@Body String body);
    //insertimage(int quesId,string tag, int uid)


/*

    @POST("api/update-image")
    Observable<Succcess> postInsertReponse(@Header("Token") String token,
                                           @Header("CompanyId") int companyId,
                                           @Header("UserId") int userId,
                                           @Body ImagePostRequest imagePostRequest

    );

    @GET("api/response-image")
    Observable<ImageResponseAfterPostBean> getImageReviewResponse(@Header("Token") String token,
                                                                  @Header("CompanyId") int companyId,
                                                                  @Header("UserId") int userId,
                                                                  @Query("InspectionId") int inspectionId,
                                                                  @Query("QuestionId") int questionId);

    //deleteImage(int responseId,int pictureId)
    @POST("api/remove-image")
    Single<Error> detelePostImage(@Header("Token") String token,
                                  @Header("CompanyId") int companyId,
                                  @Header("UserId") int userId,
                                  @Query("InspectionId") int inspectionId,
                                  @Query("QuestionId") int questionId,
                                  @Query("ImageId") int imageId
    );


    //insertUpdateReponse(int quesId, string ans, string remarks, int uid)
    @POST("home/insertUpdateReponse")
    Single<Succcess> postinsertUpdateReponse(@Query("quesId") int questionID,
                                             @Query("ans") String answer,
                                             @Query("remarks") String remarks,
                                             @Query("uid") int uid);

   */
/* @GET("App/userlogin")
    Single<LoginResponse> postLoginUser(@Query("empcode")String empcode,
                                        @Query("password")String password);*//*

   @GET("api/authorization")
   Single<LoginResponse> postLoginUser(@Query("UserId") String empcode,
                                       @Query("Password") String password);

   */
/* @GET("App/surveystatus")
    Single<SurveyStatusResponse> getTastStatusUser(@Query("empid")int empid);*//*


    @GET("api/inspection-status")
    Single<SurveyStatusResponse> getTastStatusUser(@Header("Token") String token, @Header("CompanyId") int companyId, @Header("UserId") int userId);

    @POST("api/accept-reject-inspection")
    Single<Succcess> getInboxAction(@Header("Token") String token, @Header("CompanyId") int companyId, @Header("UserId") int userId,
                                    @Query("StatusId") int statusId, @Query("InspectionId") int inspectionId, @Query("Remark") String remark);


    @GET("App/getsurveylist")
    Single<SurveyListResponce>   getSurveyListResponce(@Query("empId") int empid,
                                                       @Query("status") int status);

    @GET("api/inspection-template")
    Single<SectionListResponse>   getSectionListResponcse(@Header("Token") String token,
                                                          @Header("CompanyId") int companyId,
                                                          @Header("UserId") int userId,
                                                          @Query("InspectionId") int surveyId);

    @POST("api/accept-reject-inspection")
    Single<Succcess> postFinalSubmission(@Header("Token") String token, @Header("CompanyId") int companyId, @Header("UserId") int userId,
                                         @Query("StatusId") int statusId, @Query("InspectionId") int inspectionId, @Query("Remark") String remark);



    @GET("App/getQuestion")
    Observable<QuestionListResponce>   getQuestionListResponce(@Query("empId") int empid,
                                                               @Query("surveyId") int surveyId,
                                                               @Query("sectionId") int sectionId);


    @POST("api/update-answer")
    Observable<Succcess>   postInsertAnswerResponce(@Header("Token") String token,
                                                    @Header("CompanyId") int companyId,
                                                    @Header("UserId") int userId,
                                                    @Query("InspectionId") int surveyId,
                                                    @Query("QuestionId") String quesId,
                                                    @Query("Answer") String answer

    );


*/


   /*
    getReponse(int quesId,int empId) - get*/

}
