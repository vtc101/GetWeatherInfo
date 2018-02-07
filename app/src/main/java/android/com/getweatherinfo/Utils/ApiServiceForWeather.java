package android.com.getweatherinfo.Utils;

import android.com.getweatherinfo.SearchWeatherModels.Main;
import android.com.getweatherinfo.SearchWeatherModels.SearchWeather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiServiceForWeather {

    @GET("data/2.5/weather")
    Call<SearchWeather> getWeatherInfo(@Query("q") String city, @Query("APPID") String key);
}
