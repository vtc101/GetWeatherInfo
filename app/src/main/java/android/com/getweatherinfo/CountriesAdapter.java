package android.com.getweatherinfo;

import android.com.getweatherinfo.SearchWeatherModels.SearchWeather;
import android.com.getweatherinfo.Utils.ApiServiceForWeather;
import android.com.getweatherinfo.Utils.ApiUtilsSearchWeather;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CountriesAdapter extends RecyclerView.Adapter<CountriesAdapter.MyViewHolder> {

    private List<String> listCountries;

    private Context context;
    private ApiServiceForWeather serviceForWeather;
    private SearchWeather searchWeather;
    private static final String KEY = "78ab2b14f50bae7e4e0196f1b13b5c7a";

    public CountriesAdapter(List<String> listCountries, Context context) {
        this.listCountries = listCountries;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_countries_item, parent, false);
        return new CountriesAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        String temp = listCountries.get(position);
        String[] array = temp.split(",");
        String result = array[0];
        serviceForWeather = ApiUtilsSearchWeather.getWeather();
        holder.tvCountries.setText(temp);
        holder.tvCountries.setOnClickListener(v -> getWeatherInfo(result, KEY));
    }

    private void getWeatherInfo(String city, String key) {
        Call<SearchWeather> call = serviceForWeather.getWeatherInfo(city, key);
        call.enqueue(new Callback<SearchWeather>() {
            @Override
            public void onResponse(Call<SearchWeather> call, Response<SearchWeather> response) {
                if (response.message().equals("OK") && response.body() != null) {
                    searchWeather = response.body();
                    ((MainActivity) context).
                            getSupportFragmentManager().
                            beginTransaction().
                            replace(R.id.fl_container, new ShowWeatherFragment(searchWeather)).commit();

                }else Toast.makeText(context, "Sorry,but i can't find data :)", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<SearchWeather> call, Throwable t) {
                Toast.makeText(context, "Sorry,but i can't find data :)", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listCountries.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvCountries;

        MyViewHolder(View itemView) {
            super(itemView);
            tvCountries = itemView.findViewById(R.id.tv_countries);
        }
    }
}
