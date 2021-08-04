package com.example.ethinkosorganismosemboliasmou.ui.statistics;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface JsonPlaceHolderApi {

    // The way the call must be made to be successful
    @Headers("Authorization: Token f71a0fa8c774fe0be4a00089ce07a5019aeb8f02")
    @GET("mdg_emvolio")
    Call<List<Record>> getRecords(@Query("date_from") String date_from,
                                  @Query("date_to") String date_to);
}
