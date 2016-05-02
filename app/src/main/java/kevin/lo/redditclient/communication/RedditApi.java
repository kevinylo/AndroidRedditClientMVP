package kevin.lo.redditclient.communication;


import kevin.lo.redditclient.reddis.TopListing;

import retrofit.http.GET;
import retrofit.http.Query;


public interface RedditApi {
    @GET("/top.json")
    TopListing getTop(@Query("limit") String number, @Query("after") String token);
}
