package com.sqllite.sqllite;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.sqllite.sqllite.model.WeatherData;

public class ActivityDetail extends AppCompatActivity {

    public static final String EXTRA_POSITION = "position";
    TextView txtTitle, txtInfo;
    String cloudsData, coordData, mainData, weatherData, windData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        txtTitle = (TextView) findViewById(R.id.txtTitle);
        txtInfo = (TextView) findViewById(R.id.txtInfo);

        int position = getIntent().getIntExtra(EXTRA_POSITION, 0);

        try {

            if(getIntent().getStringExtra("clouds") != null){
                cloudsData = "\nAll : " + MainActivity.weatherDataArrayList.get(position).getClouds().getAll();
                txtTitle.setText("CLOUDS : ");
                txtInfo.setText(cloudsData);
            }
            else if(getIntent().getStringExtra("coord") != null){
                coordData = "\nLat : " + MainActivity.weatherDataArrayList.get(position).getCity().getCoord().getLat() +
                "\nLon : " + MainActivity.weatherDataArrayList.get(position).getCity().getCoord().getLon();
                txtTitle.setText("COORD : ");
                txtInfo.setText(coordData);
            }
            else if(getIntent().getStringExtra("main") != null){
                mainData = "\nTemp : " + MainActivity.weatherDataArrayList.get(position).getMain().getTemp() +
                        "\nPressure : " + MainActivity.weatherDataArrayList.get(position).getMain().getPressure() +
                        "\nTempMax : " + MainActivity.weatherDataArrayList.get(position).getMain().getTempMin() +
                        "\nTempMin : " + MainActivity.weatherDataArrayList.get(position).getMain().getTempMax() +
                        "\nHumidity : " + MainActivity.weatherDataArrayList.get(position).getMain().getHumidity();
                txtTitle.setText("MAIN : ");
                txtInfo.setText(mainData);
            }
            else if(getIntent().getStringExtra("weather") != null){
                weatherData =  "\nId : " + MainActivity.weatherDataArrayList.get(position).getWeather().get(0).getId() +
                        "\nMain : " + MainActivity.weatherDataArrayList.get(position).getWeather().get(0).getMain() +
                        "\nDescrition : " + MainActivity.weatherDataArrayList.get(position).getWeather().get(0).getDescription() +
                        "\nIcon : " + MainActivity.weatherDataArrayList.get(position).getWeather().get(0).getIcon();
                txtTitle.setText("WEATHER : ");
                txtInfo.setText(weatherData);
            }
            else if(getIntent().getStringExtra("wind") != null){
                windData = "\nSpeed : " + MainActivity.weatherDataArrayList.get(position).getWind().getSpeed() +
                        "\nGust : " + MainActivity.weatherDataArrayList.get(position).getWind().getGust() +
                        "\nDeg : " + MainActivity.weatherDataArrayList.get(position).getWind().getDeg();
                txtTitle.setText("WIND : ");
                txtInfo.setText(windData);
            }

        } catch (Exception e) {

        }



    }
}
