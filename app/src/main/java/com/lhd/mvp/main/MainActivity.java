package com.lhd.mvp.main;

import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.lhd.applock.R;
import com.lhd.module.ItemApp;
import com.lhd.mvp.listapp.ListAppFragment;
import com.lhd.mvp.setpin.SetPinFragment;
import com.lhd.mvp.toprunapp.StateDeviceService;
import com.lhd.mvp.wellcome.WellcomeFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by D on 8/8/2017.
 */

public class MainActivity extends AppCompatActivity implements MainView {
    private static final String TAG = "MainActivity";
    private MainPresenter mainPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        mainPresenter = new MainPresenterImpl(this);
        initView();
        startLogService();
    }

    private PackageManager mPm;

    public ArrayList<ItemApp> loadAppsIntoList(Context mContext) {
        final Intent i = new Intent(Intent.ACTION_MAIN);
        i.addCategory(Intent.CATEGORY_LAUNCHER);
        mPm = mContext.getPackageManager();
        ArrayList<ItemApp> itemApps = new ArrayList<>();
        final List<ResolveInfo> ris = mPm.queryIntentActivities(i, 0);
        List<ApplicationInfo> list = mPm.getInstalledApplications(0);
//        for (ApplicationInfo applicationInfo:list) {
//            Log.e("duongapp", applicationInfo.packageName);
//            Log.e("duongapp", applicationInfo.loadLabel(mPm).toString());
//        }
        for (ResolveInfo ri : ris) {
            if (!mContext.getPackageName().equals(ri.activityInfo.packageName)) {
//                final AppListElement ah = new AppListElement(ri.loadLabel(mPm).toString(), ri.activityInfo, AppListElement.PRIORITY_NORMAL_APPS);
//                Log.e("duongapp", ri.loadLabel(mPm).toString());
//                Log.e("duongapp", ri.activityInfo.packageName);
                ItemApp itemApp = new ItemApp(ri.activityInfo.loadIcon(mPm), ri.activityInfo.packageName, ri.loadLabel(mPm).toString(), false);
                itemApps.add(itemApp);
            }
        }
        return itemApps;
    }

    private LinearLayout itemNotiDrawOverApp;
    private LinearLayout itemNotiDrawUsagerAccess;
    private LinearLayout itemNotiDrawAccessibility;

    private void initView() {
        itemNotiDrawOverApp = (LinearLayout) findViewById(R.id.main_id_permisstion_draw_over);
        itemNotiDrawUsagerAccess = (LinearLayout) findViewById(R.id.main_id_permisstion_usager_access);
        itemNotiDrawAccessibility = (LinearLayout) findViewById(R.id.main_id_permisstion_accesssibility);
        mainPresenter.checkStartWellcomeFragment();
    }

    @Override
    public void startWellcomeFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_view, new WellcomeFragment()).commit();
    }

    @Override
    public void startListAppFragment() {
        showBar();
        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_view, new ListAppFragment()).commit();
//        Intent intent = new Intent(this, ListAppActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        startActivity(intent);

    }

    @Override
    public void startLogService() {
        Log.e(TAG, "Servide đang chạy " + StateDeviceService.isRunning(this));
        if (!StateDeviceService.isRunning(this)) {
            startService(new Intent(this, StateDeviceService.class));
        }
    }

    @Override
    public void showPermisstionDrawOverApp() {
        itemNotiDrawOverApp.setVisibility(View.VISIBLE);
    }


    @Override
    public void requestPermisstionDrawOverApp() {

    }

    @Override
    public boolean isPermisstionDrawOverApp() {
        return false;
    }

    @Override
    public void hidePermisstionDrawOverApp() {
        itemNotiDrawOverApp.setVisibility(View.GONE);
    }

    @Override
    public void showPermisstionUsagerAccess() {
        itemNotiDrawOverApp.setVisibility(View.VISIBLE);
    }


    @Override
    public void requestPermisstionUsagerAccess() {
        if (isGragUsageAccess()==false){
            Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
            startActivity(intent);
        }

    }

    @Override
    public boolean isPermisstionUsagerAccess() {
        return false;
    }

    @Override
    public void hidePermisstionUsagerAccess() {
        itemNotiDrawUsagerAccess.setVisibility(View.GONE);
    }

    @Override
    public void showPermisstionAccesiblity() {
        itemNotiDrawAccessibility.setVisibility(View.VISIBLE);
    }

    @Override
    public void requestPermisstionAccesiblity() {

    }

    @Override
    public boolean isPermisstionAccesiblity() {
        return false;
    }

    @Override
    public void hidePermisstionAccesiblity() {
        itemNotiDrawAccessibility.setVisibility(View.GONE);
    }

    @Override
    public void showAllPermisstion() {
        itemNotiDrawAccessibility.setVisibility(View.VISIBLE);
        itemNotiDrawUsagerAccess.setVisibility(View.VISIBLE);
        itemNotiDrawOverApp.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideAllPermisstion() {
        itemNotiDrawAccessibility.setVisibility(View.GONE);
        itemNotiDrawUsagerAccess.setVisibility(View.GONE);
        itemNotiDrawOverApp.setVisibility(View.GONE);
    }

    @Override
    public void startSetPinFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_view, new SetPinFragment()).commit();
    }

    @Override
    public void startSettingFragment() {
        Toast.makeText(this, "startSettingFragment", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideBar() {
        getSupportActionBar().hide();
    }

    @Override
    public void showBar() {
        getSupportActionBar().show();
    }

    public boolean isGragUsageAccess() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            try {
                PackageManager packageManager = this.getPackageManager();
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo(this.getPackageName(), 0);
                AppOpsManager appOpsManager = (AppOpsManager) this.getSystemService(Context.APP_OPS_SERVICE);
                int mode = 0;

                mode = appOpsManager.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, applicationInfo.uid, applicationInfo.packageName);

                return (mode == AppOpsManager.MODE_ALLOWED);

            } catch (PackageManager.NameNotFoundException e) {
                return false;
            }
        }
        return false;
    }
}
