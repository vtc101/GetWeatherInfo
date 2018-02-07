package android.com.getweatherinfo.Utils;

import android.com.getweatherinfo.SearchCitiesModels.SearchCities;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiServiceForSearchCities {

    @GET("maps/api/place/autocomplete/json")
    Call<SearchCities> getNearbyPlacesByText(@Query("input") String city, @Query("types") String type, @Query("key") String key);
}
