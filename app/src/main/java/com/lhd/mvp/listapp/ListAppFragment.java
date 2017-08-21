package com.lhd.mvp.listapp;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.lhd.adaptor.AdaptorApp;
import com.lhd.applock.R;
import com.lhd.module.ItemApp;
import com.lhd.sql.MySQL;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by D on 8/10/2017.
 */

public class ListAppFragment extends Fragment implements ListAppView {
    private ArrayList<ItemApp> itemApps;
    ListAppPresenterImpl listAppPresenter;
    private ArrayList<ItemApp> listAppUnlock;
    private ArrayList<ItemApp> listAppLocked;
    private ListView rcvLocked;
    private ListView rcvUnLock;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View viewContent = inflater.inflate(R.layout.list_app_layout, null);
        initDate();
        initView(viewContent);
        return viewContent;

    }

    MySQL mySQL;

    private void initDate() {
        mySQL = new MySQL(getContext());
        mySQL.getAllItemApp();
    }

    AdaptorApp adaptorApp;

    private void initView(View viewContent) {
//        rcvLocked = (ListView) viewContent.findViewById(R.id.item_list_app_rcv_list_locked_app);
        rcvUnLock = (ListView) viewContent.findViewById(R.id.item_list_app_rcv_list_unlock_app);
        itemApps = getAllListApp(getContext());
        sortList();
        adaptorApp = new AdaptorApp(getContext(), android.R.layout.activity_list_item, itemApps);
        rcvUnLock.setAdapter(adaptorApp);
        rcvUnLock.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ImageView imStateLockApp = (ImageView) view.findViewById(R.id.item_app_id_im_state_app_lock);
                Log.e("duong", "onClick");
                itemApps.get(i).setLock(!itemApps.get(i).isLock());
                if (itemApps.get(i).isLock()) {
                    mySQL.insertOneItemApp(itemApps.get(i).getNamePackage());
                    imStateLockApp.setImageDrawable(getResources().getDrawable(R.drawable.ic_lock_outline_light_green_a400_36dp));
                } else {
                    mySQL.removeOneItemApp(itemApps.get(i).getNamePackage());
                    imStateLockApp.setImageDrawable(getResources().getDrawable(R.drawable.ic_lock_open_white_36dp));
                }
                adaptorApp.notifyDataSetChanged();
            }
        });
//        rcvLocked.setAdapter(new AdaptorApp(getContext(),android.R.layout.activity_list_item,getAllListApp(getContext())));
//        new ListAppModelImpl().putAllApp(getContext());
//        rcvLocked.setAdapter(new AdaptorListApp(getContext(), new ListAppModelImpl().getListAppUnLock(getContext())));
    }

    private void sortList() {
//        Collections.sort(itemApps, new Comparator<ItemApp>() {
//            @Override
//            public int compare(ItemApp itemApp, ItemApp t1) {
//                if (itemApp.isLock()) return 1;
//                return -1;
//            }
//        });
        for (ItemApp itemApp : itemApps) {
            if (mySQL.isExistsItemApp(itemApp.getNamePackage(),  mySQL.getAllItemApp())) itemApp.setLock(true);
        }
        Collections.sort(itemApps, new Comparator<ItemApp>() {
            @Override
            public int compare(ItemApp itemApp, ItemApp t1) {
                return itemApp.getNameApp().compareTo(t1.getNameApp());
            }
        });
    }

    public ArrayList<ItemApp> getAllListApp(Context mContext) {
        Intent i = new Intent(Intent.ACTION_MAIN);
        i.addCategory(Intent.CATEGORY_LAUNCHER);
        PackageManager mPm = mContext.getPackageManager();
        ArrayList<ItemApp> itemApps = new ArrayList<>();
        List<ResolveInfo> ris = mPm.queryIntentActivities(i, 0);
//        List<ApplicationInfo> list = mPm.getInstalledApplications(0);
//        for (ApplicationInfo applicationInfo:list) {
//            Log.e("duongapp", applicationInfo.packageName);
//            Log.e("duongapp", applicationInfo.loadLabel(mPm).toString());
//        }
        for (ResolveInfo ri : ris) {
            if (!mContext.getPackageName().equals(ri.activityInfo.packageName)) {
//                final AppListElement ah = new AppListElement(ri.loadLabel(mPm).toString(), ri.activityInfo, AppListElement.PRIORITY_NORMAL_APPS);
//                Log.e("duongapp", ri.loadLabel(mPm).toString());
                Log.e("duongapp", ri.activityInfo.packageName);
//                Log.e("duongapp", ri.activityInfo.loadIcon(mPm) + "");
                ItemApp itemApp = new ItemApp(ri.activityInfo.loadIcon(mPm), ri.activityInfo.packageName, ri.loadLabel(mPm).toString(), false);
                itemApps.add(itemApp);
            }
        }
        return itemApps;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        listAppPresenter = new ListAppPresenterImpl(this);
    }

    @Override
    public void loadListAppToList(ArrayList<ItemApp> itemApps) {
        this.itemApps = itemApps;
    }

    @Override
    public void setListUnlock(ArrayList<ItemApp> itemApps) {
        this.listAppUnlock = itemApps;
        for (ItemApp itemApp : itemApps) {
            Log.e("itemApp", itemApp.toString());
        }
    }

    @Override
    public void setListLocked(ArrayList<ItemApp> itemApps) {
        this.listAppLocked = itemApps;
        for (ItemApp itemApp : itemApps) {
            Log.e("itemApp", itemApp.toString());
        }
    }

    @Override
    public void refeshList() {

    }

    @Override
    public void startSetting() {

    }

    @Override
    public void sreachApp() {

    }

    @Override
    public void onLockApp(ItemApp itemApp) {

    }

    @Override
    public void showListLocked() {
//        rcvLocked.setAdapter(new AdaptorListApp(getContext(), listAppLocked));
    }

    @Override
    public void showListUnlock() {
//        rcvUnLock.setAdapter(new AdaptorListApp(getContext(), listAppUnlock));
    }
}
