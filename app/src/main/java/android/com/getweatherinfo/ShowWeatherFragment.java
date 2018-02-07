package android.com.getweatherinfo;


import android.annotation.SuppressLint;
import android.com.getweatherinfo.SearchWeatherModels.SearchWeather;
import android.com.getweatherinfo.Utils.CallbackForVisibility;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ShowWeatherFragment extends Fragment {

    private TextView cityText;
    private ImageView imgView;
    private TextView temp;
    private TextView pressure;
    private TextView humidity;
    private TextView wind;
    private SearchWeather searchWeather;
    private LinearLayout linearLayout;

    @SuppressLint("ValidFragment")
    public ShowWeatherFragment(SearchWeather searchWeather) {
        this.searchWeather = searchWeather;
    }

    public ShowWeatherFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CallbackForVisibility callbackForVisibility = (CallbackForVisibility) getActivity();
        callbackForVisibility.frameAndRecyclerVisibility();
        if (savedInstanceState != null){
            searchWeather = (SearchWeather) savedInstanceState.getSerializable("searchWeather");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show_weater, container, false);
        findViewsById(view);
        getData();
        return view;
    }

    private void findViewsById(View view) {
        cityText = view.findViewById(R.id.cityText);
        imgView = view.findViewById(R.id.img_icon);
        temp = view.findViewById(R.id.tv_temp);
        pressure = view.findViewById(R.id.tv_pressure);
        humidity = view.findViewById(R.id.tv_humidity);
        wind = view.findViewById(R.id.tv_wind);
        linearLayout = view.findViewById(R.id.ll_layout);
    }

    @SuppressLint("SetTextI18n")
    private void getData() {
        if (searchWeather != null){
            double kelvin = searchWeather.getMain().getTemp();
            int celsius = (int)kelvin - 274;
            cityText.setText(searchWeather.getName());
            temp.setText("Temperature  " + celsius + "\u00b0" + "C");
            temp.setTextColor(getResources().getColor(R.color.colorBlue));
            if (searchWeather.getMain().getPressure() != null) {
                pressure.setText("Pressure  " + searchWeather.getMain().getPressure().toString() + " P");
                pressure.setTextColor(getResources().getColor(R.color.colorGreen));
            }
            humidity.setText("Humidity  " + searchWeather.getMain().getHumidity().toString() + "%");
            humidity.setTextColor(getResources().getColor(R.color.colorGreen));
            setImage(searchWeather.getWeather().get(0).getDescription());

            if (searchWeather.getWind().getSpeed() != null) {
                wind.setText("Wind " + searchWeather.getWind().getSpeed().toString() + " mps "  + "\u00b0");
                wind.setTextColor(getResources().getColor(R.color.colorGreen));
            }
        }
    }

    private void setImage(String text){
        switch (text){
            case "broken clouds":{
                imgView.setBackground(getResources().getDrawable(R.drawable.sunny));
                linearLayout.setBackground(getResources().getDrawable(R.drawable.cludly));
                break;
            }
            case "overcast clouds":{
                imgView.setBackground(getResources().getDrawable(R.drawable.clouds));
                linearLayout.setBackground(getResources().getDrawable(R.drawable.cloud));
                break;
            }
            case "clear sky":{
                imgView.setBackground(getResources().getDrawable(R.drawable.sun));
                linearLayout.setBackground(getResources().getDrawable(R.drawable.back));
                break;
            }
            case "scattered clouds":{
                imgView.setBackground(getResources().getDrawable(R.drawable.scatteredclouds));
                linearLayout.setBackground(getResources().getDrawable(R.drawable.cludly));
                break;
            }
            case "light snow":{
                linearLayout.setBackground(getResources().getDrawable(R.drawable.winter));
                imgView.setBackground(getResources().getDrawable(R.drawable.snowflake));
            }
        }
    }
}

