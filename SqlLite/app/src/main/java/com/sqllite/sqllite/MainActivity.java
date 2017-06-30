package com.sqllite.sqllite;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sqllite.sqllite.model.City;
import com.sqllite.sqllite.model.WeatherData;

public class MainActivity extends AppCompatActivity {

    public static ArrayList<WeatherData> weatherDataArrayList;

    private RecyclerView recyclerView;
    private ContentAdapter adapter;
    private List<City> cityList;

    public static ArrayList<WeatherData> readFileFromRaw(int id, Context context){

        ObjectMapper mapper= new ObjectMapper();
        weatherDataArrayList = new ArrayList<>();
        WeatherData weatherData = new WeatherData();

        StringBuffer stringBuffer = new StringBuffer();
        try {

            InputStream fIn = context.getResources().openRawResource(id);
            InputStreamReader isr = new InputStreamReader ( fIn ) ;
            BufferedReader bufferedReader = new BufferedReader ( isr ) ;
            String readString = bufferedReader.readLine ( ) ;
            while ( readString != null ) {
                stringBuffer.append(readString);

                try {

                    JSONObject jsob = new JSONObject(readString.toString());
                    weatherData = mapper.readValue(jsob.toString(), WeatherData.class);
                    weatherDataArrayList.add(weatherData);

                } catch (JsonMappingException e) {
                    e.printStackTrace();
                } catch (JsonGenerationException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch(JSONException e){
                    e.printStackTrace();
                }

                readString = bufferedReader.readLine ( ) ;

            }
            isr.close ( ) ;
        } catch ( IOException ioe ) {
            ioe.printStackTrace ( ) ;
        }
        return weatherDataArrayList;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<WeatherData> weatherDataArrayList = new ArrayList<>();
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);

        weatherDataArrayList = readFileFromRaw(R.raw.json, getBaseContext());

        for(WeatherData weatherData:weatherDataArrayList){
            dbHandler.addCity(weatherData);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initCollapsingToolbar();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        cityList = new ArrayList<>();
        adapter = new ContentAdapter(this, cityList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        prepareCities();


    }
    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(getString(R.string.app_name));
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle("SqlLite");
                    isShow = false;
                }
            }
        });
    }

    private void prepareCities() {

        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);

        for(WeatherData weatherData:weatherDataArrayList){
            City city = dbHandler.findCity(weatherData.getCity().getName());
            cityList.add(city);
        }
    }
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }



}


