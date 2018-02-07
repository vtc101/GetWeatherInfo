package android.com.getweatherinfo;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.com.getweatherinfo.SearchCitiesModels.SearchCities;
import android.com.getweatherinfo.Utils.ApiServiceForSearchCities;
import android.com.getweatherinfo.Utils.ApiUtilsSearchCity;
import android.com.getweatherinfo.Utils.CallbackForVisibility;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, View.OnClickListener, CallbackForVisibility {

    private List<String> listCountries;
    private SearchView searchView;
    private FrameLayout frameLayout;
    private RecyclerView recyclerView;
    private ApiServiceForSearchCities apiServiceForSearchCities;
    private CountriesAdapter countriesAdapter;
    private static final String KEY = "AIzaSyAlPAWLVgxYcs6XO2D7I1271-dr9KNwig0";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!isNetworkOnline()) {
            Toast.makeText(this, "connect to internet", Toast.LENGTH_LONG).show();
        }
        findViewsAndClickListeners();
        apiServiceForSearchCities = ApiUtilsSearchCity.getCities();
    }

    private void findViewsAndClickListeners(){
        frameLayout = findViewById(R.id.fl_container);
        searchView = findViewById(R.id.search_View);
        recyclerView = findViewById(R.id.rv_countries);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        if (listCountries == null) {
            listCountries = new ArrayList<>();
        }

        searchView.setOnQueryTextListener(this);
        searchView.setOnClickListener(this);
    }

    private void getCountries(String text, String key) {
        if (!isNetworkOnline()) {
            Toast.makeText(this, "connect to internet", Toast.LENGTH_LONG).show();
        }
        Call<SearchCities> call = apiServiceForSearchCities.getNearbyPlacesByText(text, "(cities)", key);
        call.enqueue(new Callback<SearchCities>() {
            @Override
            public void onResponse(Call<SearchCities> call, Response<SearchCities> response) {

                if (response.body().getPredictions() != null){
                    for (int i = 0; i < response.body().getPredictions().size(); i++) {
                        String text = response.body().getPredictions().get(i).getDescription();
                        listCountries.add(text);
                    }
                countriesAdapter = new CountriesAdapter(listCountries, MainActivity.this);
                recyclerView.setAdapter(countriesAdapter);}

            }
            @Override
            public void onFailure(Call<SearchCities> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        frameLayout.setVisibility(View.GONE);
        listCountries.clear();
        if (countriesAdapter != null) {
            countriesAdapter.notifyDataSetChanged();
        }
        if (newText.length() >= 1) {
            recyclerView.setVisibility(View.VISIBLE);
            searchView.setIconifiedByDefault(false);
            getCountries(newText, KEY);
        } else {
            recyclerView.setVisibility(View.INVISIBLE);
        }
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public void onClick(View v) {
        searchView.setIconified(false);
    }

    @Override
    public void frameAndRecyclerVisibility() {
        if (recyclerView != null && frameLayout != null) {
            recyclerView.setVisibility(View.GONE);
            frameLayout.setVisibility(View.VISIBLE);
        }
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public boolean isNetworkOnline() {
        boolean status = false;
        try {
            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getNetworkInfo(0);
            if (netInfo != null && netInfo.getState() == NetworkInfo.State.CONNECTED) {
                status = true;
            } else {
                netInfo = cm.getNetworkInfo(1);
                if (netInfo != null && netInfo.getState() == NetworkInfo.State.CONNECTED)
                    status = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return status;

    }
}
