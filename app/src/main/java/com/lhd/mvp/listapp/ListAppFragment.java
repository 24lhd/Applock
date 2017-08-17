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
import android.widget.ListView;

import com.lhd.adaptor.AdaptorApp;
import com.lhd.applock.R;
import com.lhd.module.ItemApp;

import java.util.ArrayList;
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
        initView(viewContent);
        return viewContent;

    }

    private void initView(View viewContent) {
        rcvLocked = (ListView) viewContent.findViewById(R.id.item_list_app_rcv_list_locked_app);
        rcvUnLock = (ListView) viewContent.findViewById(R.id.item_list_app_rcv_list_unlock_app);
        rcvUnLock.setAdapter(new AdaptorApp(getContext(),android.R.layout.activity_list_item,getAllListApp(getContext())));
        rcvLocked.setAdapter(new AdaptorApp(getContext(),android.R.layout.activity_list_item,getAllListApp(getContext())));
//        new ListAppModelImpl().putAllApp(getContext());
//        rcvLocked.setAdapter(new AdaptorListApp(getContext(), new ListAppModelImpl().getListAppUnLock(getContext())));
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
                ItemApp itemApp = new ItemApp(ri.activityInfo.loadIcon(mPm) , ri.activityInfo.packageName, ri.loadLabel(mPm).toString(), false);
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
