package com.telesgabriel.previsaotempo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.telesgabriel.previsaotempo.ApiClient;
import com.telesgabriel.previsaotempo.R;
import com.telesgabriel.previsaotempo.Weather;
import com.telesgabriel.previsaotempo.WeatherAdapter;
import com.telesgabriel.previsaotempo.WeatherApi;
import com.telesgabriel.previsaotempo.WeatherResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.List;

public class WeatherFragment extends Fragment {
    private RecyclerView recyclerView;
    private WeatherAdapter weatherAdapter;
    private List<Weather> weatherList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Chama a função para carregar os dados da API
        loadWeatherData();

        return view;
    }

    private void loadWeatherData() {
        // Substitua "YOUR_API_KEY" pela sua chave da API
        String apiKey = "fbe2781b";
        String city = "Toledo"; // Você pode alterar a cidade conforme necessário

        WeatherApi weatherApi = ApiClient.getClient().create(WeatherApi.class);
        Call<WeatherResponse> call = weatherApi.getWeather(apiKey, city);

        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    weatherList = response.body().getResults();
                    weatherAdapter = new WeatherAdapter(weatherList);
                    recyclerView.setAdapter(weatherAdapter);
                } else {
                    Toast.makeText(getContext(), "Erro ao obter dados", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Falha na chamada: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}