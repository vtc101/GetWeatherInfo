package android.com.getweatherinfo.Utils;

public class ApiUtilsSearchWeather {
    public static final String BASE_URL_SEARCH_WEATHER = "http://api.openweathermap.org/";

    public static ApiServiceForWeather getWeather() {
        return RetrofitClientWeather.getClient(BASE_URL_SEARCH_WEATHER).create(ApiServiceForWeather.class);
    }
}
