package com.lhd.mvp.listapp;

import android.util.Log;

/**
 * Created by D on 8/10/2017.
 */

public class ListAppPresenterImpl implements ListAppPresenter {

    private ListAppModelImpl listAppModel;

    public ListAppPresenterImpl(ListAppFragment listAppFragment) {
        listAppModel = new ListAppModelImpl();
        listAppModel.putAllApp(listAppFragment.getContext());
        Log.e("ListAppPresenterImpl", "" + listAppModel.getListAppUnLock(listAppFragment.getContext()).size());
        Log.e("ListAppPresenterImpl", "" + listAppModel.getListAppLocked(listAppFragment.getContext()).size());
        listAppFragment.setListUnlock(listAppModel.getListAppUnLock(listAppFragment.getContext()));
        listAppFragment.setListLocked(listAppModel.getListAppLocked(listAppFragment.getContext()));
        listAppFragment.showListUnlock();
        listAppFragment.showListLocked();
//        listAppFragment.loadListAppToList(listAppModel.getListApp());
//        listAppModel.getAllListApp(listAppFragment.getContext());

    }
}
