package com.example.instahappy.api;

import com.example.instahappy.model.Photo;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface IUnsplashService {
    String URL_UNSPLASH_BASE_URL = "https://api.unsplash.com/";
    String CLIENT_ID = "1f2d4a594b34880dbeabfd74d9ca94f0d1428f41697b3fd35d03c98ddf6a23ac";
    String PORTRAIT_ORIENTATION = "portrait";
    @Headers("Authorization: Client-ID 1f2d4a594b34880dbeabfd74d9ca94f0d1428f41697b3fd35d03c98ddf6a23ac")
    @GET("collections/{id}/photos")
    Call<List<Photo>> getPhotosFromService(@Path("id") int nId);

}
