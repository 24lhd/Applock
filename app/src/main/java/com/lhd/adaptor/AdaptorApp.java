package com.lhd.adaptor;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lhd.applock.R;
import com.lhd.module.ItemApp;

import java.util.ArrayList;

/**
 * Created by D on 8/17/2017.
 */

public class AdaptorApp extends ArrayAdapter<ItemApp> {

    private Context context;
    private ArrayList<ItemApp> objects;

    public AdaptorApp(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<ItemApp> objects) {
        super(context, resource, objects);
        this.objects = objects;
        this.context = context;
    }

    @Nullable
    @Override
    public ItemApp getItem(int position) {
        return super.getItem(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View viewItemApp = View.inflate(context, R.layout.item_app, null);
        ImageView imIconApp = (ImageView) viewItemApp.findViewById(R.id.item_app_id_im_ic_app);
        ImageView imStateLockApp = (ImageView) viewItemApp.findViewById(R.id.item_app_id_im_state_app_lock);
        TextView tvTitle = (TextView) viewItemApp.findViewById(R.id.item_app_id_txv_title_app);
        imIconApp.setImageDrawable(objects.get(position).getIconApp());
        imStateLockApp.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_lock_outline_white_48dp));
        tvTitle.setText(objects.get(position).getNameApp());
        return viewItemApp;
    }
}
