package android.com.getweatherinfo.Utils;

public class ApiUtilsSearchCity {
    private static final String BASE_URL_SEARCH_CITIES = "https://maps.googleapis.com/";
    public static ApiServiceForSearchCities getCities() {
        return RetrofitClientCities.getClient(BASE_URL_SEARCH_CITIES).create(ApiServiceForSearchCities.class);
    }
}
