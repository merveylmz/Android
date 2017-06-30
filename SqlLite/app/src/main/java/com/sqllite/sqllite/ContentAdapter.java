package com.sqllite.sqllite;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.sqllite.sqllite.model.City;
import com.sqllite.sqllite.model.Main;

import java.util.List;


/**
 * Created by ss on 25.2.2017.
 */

public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.MyViewHolder> {

    private Context mContext;
    private List<City> cityList;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, count;
        public ImageView overflow;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            count = (TextView) view.findViewById(R.id.count);
            overflow = (ImageView) view.findViewById(R.id.overflow);
        }
    }

    public ContentAdapter(Context mContext, List<City> cityList) {
        this.mContext = mContext;
        this.cityList = cityList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.album_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        City city = cityList.get(position);
        holder.title.setText(city.getName()); // Cityname i ekrana bas.

        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(mContext, "Şehir Pozsition"+position, Toast.LENGTH_SHORT).show();

                showPopupMenu(holder.overflow, position);
            }

        });
    }

    private void showPopupMenu(View view, int position) {

        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_album, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener(position));
        popup.show();
    }

    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {
        private int mypoostion;
        public MyMenuItemClickListener(int pos) {
            this.mypoostion = pos;
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            Intent intent = new Intent(mContext, ActivityDetail.class);
            intent.putExtra(ActivityDetail.EXTRA_POSITION, this.mypoostion);
            switch (menuItem.getItemId()) {
                case R.id.action_clouds:
                    intent.putExtra("clouds", "CLOUDS");
                    mContext.startActivity(intent);
                    return true;
                case R.id.action_coord:
                    intent.putExtra("coord", "COORD");
                    mContext.startActivity(intent);
                    return true;
                case R.id.action_main:
                    intent.putExtra("main", "MAİN");
                    mContext.startActivity(intent);
                    return true;
                case R.id.action_weather:
                    intent.putExtra("weather", "WEATHER");
                    mContext.startActivity(intent);
                    return true;
                case R.id.action_wind:
                    intent.putExtra("wind", "WİND");
                    mContext.startActivity(intent);
                    return true;
                default:
            }
            return false;
        }
    }

    @Override
    public int getItemCount() {
        return cityList.size();
    }
}
