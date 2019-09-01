package com.example.instahappy.utils;

import java.util.Set;
import okhttp3.Headers;
import retrofit2.Response;

class NetworkUtil {
    public static String onPhotoResponseError(Response response){
        if(response.code() == 403)
        {
            Headers headers = response.headers();
            Set<String> headerNames =  headers.names();
            long rateLimit = 0;
            for (String headerName:
            headerNames){
                String headerValue = headers.get(headerName);
                if(headerValue == null){
                    continue;
                }

                if(headerName.equals("X-Ratelimit-Remaining")){
                    rateLimit = Long.valueOf(headers.get(headerName));
                    break;
                }

                if(rateLimit == 50){
                    return "Oh oh, it looks like you exceeded your API rate limit";
                }
            }

        }
        return response.message();
    }
}
