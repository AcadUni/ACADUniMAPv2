package com.elshaikh.mano.acadunimap;

/**
 * Created by Mano on 1/30/2018.
 */


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Mano on 26/07/2017.
 */

public class WeatherAdapter extends ArrayAdapter<RssItem> {

    Context context;
    int layoutResourceId;
    List<RssItem> data = null;

    public WeatherAdapter(Context context, int layoutResourceId, List<RssItem> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        WeatherHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new WeatherHolder();
            ImageView xx = (ImageView)row.findViewById(R.id.imgIcon);
            if(data.get(0).getFragment_name().equalsIgnoreCase("rssitem"))
                xx.setImageResource(R.drawable.locat);
            else xx.setImageResource(R.drawable.icon);
            holder.imgIcon = xx;//(ImageView)row.findViewById(R.id.imgIcon);
            holder.txtTitle = (TextView)row.findViewById(R.id.txtTitle);

            row.setTag(holder);
        }
        else
        {
            holder = (WeatherHolder)row.getTag();
        }

        List<RssItem> weather = data;//data[position];
        holder.txtTitle.setText(weather.get(position).getTitle().toString());
        // holder.imgIcon.setImageResource(weather.icon);

        return row;
    }

    static class WeatherHolder
    {
        ImageView imgIcon;
        TextView txtTitle;
    }
}

