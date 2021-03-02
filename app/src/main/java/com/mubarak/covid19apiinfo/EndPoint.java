package com.mubarak.covid19apiinfo;

import retrofit2.Call;
import retrofit2.http.GET;

public interface EndPoint {
    @GET("/dayone/country/IN")
    Call<String> getData();
}
