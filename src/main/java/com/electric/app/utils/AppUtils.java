package com.electric.app.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AppOpsManager;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;


import androidx.core.app.ActivityCompat;

import com.electric.app.common.EleApplication;
import com.electric.app.R;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * Created by zhangzc on 2017/4/28.
 */
public class AppUtils {
    /**
     * 得到Context
     */
    public static Context getContext() {
        return EleApplication.getContext();
    }

    /**
     * 得到Resource对象
     */
    public static Resources getResources() {
        return getContext().getResources();
    }

    /**
     * 得到String.xml中的字符串信息
     */
    public static String getString(int resId) {
        return getResources().getString(resId);
    }

    /**
     * 得到String.xml中的字符串数组信息
     */
    public static String[] getStrings(int resId) {
        return getResources().getStringArray(resId);
    }

    /**
     * 得到Color.xml中的颜色信息
     */
    public static int getColor(int resId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return getResources().getColor(resId, AppUtils.getContext().getTheme());
        } else {
            return getResources().getColor(resId);
        }
    }

    /**
     *
     */
    public static Drawable getDrawable(int resId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return getResources().getDrawable(resId, AppUtils.getContext().getTheme());
        } else {
            return getResources().getDrawable(resId);
        }
    }

    public static Bitmap getDrawableBitamp(int resId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            BitmapDrawable drawable = (BitmapDrawable) getResources().getDrawable(resId, AppUtils.getContext().getTheme());
            return drawable.getBitmap();
        } else {
            BitmapDrawable drawable = (BitmapDrawable) getResources().getDrawable(resId);
            return drawable.getBitmap();
        }
    }

    /**
     * 得到应用程序包名
     *
     * @return
     */
    public static String getPackageName() {
        return getContext().getPackageName();
    }

    /**
     * 得到版本名
     *
     * @return
     */
    public static String getVersionName() {

        PackageManager manager = getContext().getPackageManager();
        try {
            PackageInfo packageInfo = manager.getPackageInfo(getContext().getPackageName(), 0);
            String version = packageInfo.versionName;
            return version;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 得到版本号
     *
     * @return
     */
    public static int getVersionCode() {

        PackageManager manager = getContext().getPackageManager();
        try {
            PackageInfo packageInfo = manager.getPackageInfo(getContext().getPackageName(), 0);
            int version = packageInfo.versionCode;
            return version;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }

    /**
     * 判断用户登录状态
     *
     * @return
     */
    public static boolean isLogin() {
//        if (TextUtils.isEmpty(PreferenceUtil.getInstance().getSession())) {
//            return false;
//        }
        return true;
    }


    /**
     * 隐藏软键盘
     *
     * @param activity
     */
    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (activity.getCurrentFocus() != null) {
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
    }


    //白色可以替换成其他浅色系
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void whiteStatusBar(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (MIUISetStatusBarLightMode(activity.getWindow(), true)) {//MIUI
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0
                    activity.getWindow().setStatusBarColor(getColor(R.color.main_blue));
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4
                    activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//                    SystemBarTintManager tintManager = new SystemBarTintManager(activity);
//                    tintManager.setStatusBarTintEnabled(true);
//                    tintManager.setStatusBarTintResource(android.R.color.white);
                }
            } else if (FlymeSetStatusBarLightMode(activity.getWindow(), true)) {//Flyme
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0
                    activity.getWindow().setStatusBarColor(getColor(R.color.main_blue));
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4
                    activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//                    SystemBarTintManager tintManager = new SystemBarTintManager(activity);
//                    tintManager.setStatusBarTintEnabled(true);
//                    tintManager.setStatusBarTintResource(android.R.color.white);
                }
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//6.0
                activity.getWindow().setStatusBarColor(getColor(R.color.main_blue));
                activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            }

        }
    }

    /**
     * 设置状态栏字体图标为深色，需要MIUIV6以上
     *
     * @param window 需要设置的窗口
     * @param dark   是否把状态栏字体及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    public static boolean MIUISetStatusBarLightMode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            Class clazz = window.getClass();
            try {
                int darkModeFlag = 0;
                Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                darkModeFlag = field.getInt(layoutParams);
                Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
                if (dark) {
                    extraFlagField.invoke(window, darkModeFlag, darkModeFlag);//状态栏透明且黑色字体
                } else {
                    extraFlagField.invoke(window, 0, darkModeFlag);//清除黑色字体
                }
                result = true;
            } catch (Exception e) {

            }
        }
        return result;
    }

    /**
     * 设置状态栏图标为深色和魅族特定的文字风格
     * 可以用来判断是否为Flyme用户
     *
     * @param window 需要设置的窗口
     * @param dark   是否把状态栏字体及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    public static boolean FlymeSetStatusBarLightMode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            try {
                WindowManager.LayoutParams lp = window.getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class.getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(lp);
                if (dark) {
                    value |= bit;
                } else {
                    value &= ~bit;
                }
                meizuFlags.setInt(lp, value);
                window.setAttributes(lp);
                result = true;
            } catch (Exception e) {

            }
        }
        return result;
    }


    /**
     * 打开三方app
     *
     * @param context
     * @param TripartiteApp
     */
    public static void toTripartiteApp(Context context, String TripartiteApp) {

        // 通过包名获取此APP详细信息，包括Activities、services、versioncode、name等等
        PackageInfo packageinfo = null;
        try {
            packageinfo = context.getPackageManager().getPackageInfo(TripartiteApp, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageinfo == null) {
//            ToastUtil.show("未安装该应用");
        } else {
            PackageManager packageManager = context.getPackageManager();
            Intent intent = packageManager.getLaunchIntentForPackage(TripartiteApp);
            context.startActivity(intent);
        }

    }

    public static boolean isAppInstalled(Context context, String uri) {
        PackageManager pm = context.getPackageManager();
        boolean installed = false;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            installed = false;
        }
        return installed;
    }


    public static void copy(String text) {
        ClipboardManager cm = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        cm.setText(text);
//        ToastUtil.show("复制成功");

    }

    public static void Paste(TextView tv) {
        ClipboardManager cm = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        //判断剪切版时候有内容
        if (!cm.hasPrimaryClip()) {
            return;
        }
        String s = cm.getPrimaryClip().getItemAt(0).getText().toString();
        tv.setText(s);
    }


    public static String getDeviceId() {
        String deviceId = "";
        try {
            final TelephonyManager manager = (TelephonyManager) getContext().getSystemService(Context.TELEPHONY_SERVICE);
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {

            }
            if (manager.getDeviceId() == null || manager.getDeviceId().equals("")) {
                if (Build.VERSION.SDK_INT >= 23) {
                    deviceId = manager.getDeviceId(0);
                }
            } else {
                deviceId = manager.getDeviceId();
            }
        } catch (Exception e) {
            return deviceId;
        }

        return deviceId;
    }

    public static int[] getScreenWH(Context context) {
        int[] strings = new int[2];
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics de = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(de);
        strings[0] = de.widthPixels;
        strings[1] = de.heightPixels;
        return strings;
    }


//    public static void setRefreshLayoutColor(SwipeRefreshLayout refreshLayoutColor) {
//        refreshLayoutColor.setColorSchemeColors(Color.parseColor("#FF7830"), Color.parseColor("#FF0000"), Color.parseColor("#FF7F00"), Color.parseColor("#FFFF00"), Color.parseColor("#00FF00"), Color.parseColor("#00FFFF"), Color.parseColor("#0000FF"), Color.parseColor("#8B00FF"));
//    }


    /*
     * 毫秒转化时分秒毫秒
     */
    public static String formatTime(Long ms) {
        Integer ss = 1000;
        Integer mi = ss * 60;
        Integer hh = mi * 60;
        Integer dd = hh * 24;

        Long day = ms / dd;
        Long hour = (ms - day * dd) / hh;
        Long minute = (ms - day * dd - hour * hh) / mi;
        Long second = (ms - day * dd - hour * hh - minute * mi) / ss;
        Long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;

        StringBuffer sb = new StringBuffer();
        if (day > 0) {
            sb.append(day + "天");
        }
        if (hour > 0) {
            sb.append(hour + "小时");
        }
        if (minute > 0) {
            sb.append(minute + "分");
        }
        if (second > 0) {
            sb.append(second + "秒");
        }
//        if (milliSecond > 0) {
//            sb.append(milliSecond + "毫秒");
//        }
        return sb.toString();
    }

    public static String formatTimeToMinute(Long ms) {
        Integer ss = 1000;
        Integer mi = ss * 60;
        Long minute = ms / mi;
        StringBuffer sb = new StringBuffer();

        if (minute > 0) {
            sb.append(minute + "分钟");
        }

        return sb.toString();
    }


    public static String getHelloString() {
        Calendar calendar = Calendar.getInstance();

        int d = calendar.get(Calendar.HOUR_OF_DAY);
        if (d < 11) {
            return "早上好,";
        } else if (d < 13) {
            return "中午好,";
        } else if (d < 18) {
            return "下午好,";
        } else if (d < 24) {
            return "晚上好,";
        }
        return "";
    }

    public static String formatDate(Long ms) {

        Date d = new Date(ms);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String s = sdf.format(d);
        return s;
    }


    public static boolean isConnect(Context context) {
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        try {
            ConnectivityManager connectivity = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                // 获取网络连接管理的对象
                NetworkInfo info = connectivity.getActiveNetworkInfo();

                if (info != null && info.isConnected()) {
                    // 判断当前网络是否已经连接
                    if (info.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            LogUtils.v("error", e.toString());

        }
        return false;
    }

    /**
     * dp转px
     **/
    public int dp2px(int dipValue) {
        float reSize = getContext().getResources().getDisplayMetrics().density;
        return (int) ((dipValue * reSize) + 0.5);
    }

    /**
     * px转换成dp
     */
    public static int px2dp(float pxValue) {
        float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

//    public static void setColorSchemeResources(SwipeRefreshLayout swipeRefreshLayout) {
//        swipeRefreshLayout.setColorSchemeResources(R.color.main_blue, R.color._FFA400, R.color._FFE400, R.color._6DE417);
//    }

    public static boolean checkVersionCode(String serverVersion) {
        String[] currnt = getVersionName().split(".");
        String[] server = serverVersion.split(".");

        if (Integer.valueOf(server[0]) > Integer.valueOf(currnt[0])) {
            return true;
        }
        if (Integer.valueOf(server[0]) == Integer.valueOf(currnt[0])) {
            if (Integer.valueOf(server[1]) > Integer.valueOf(currnt[1])) {
                return true;
            }
        }

        return false;

    }


    public static void startLocaion() {

//        AMapLocationClient mLocationClient = new AMapLocationClient(getContext());
//        mLocationClient.setLocationListener(mLocationListener);
//
//        AMapLocationClientOption mLocationOption = new AMapLocationClientOption();
//        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
//        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
//        //设置是否返回地址信息（默认返回地址信息）
//        mLocationOption.setNeedAddress(true);
//        //获取一次定位结果：
//        //该方法默认为false。
//        mLocationOption.setOnceLocation(true);
//        //设置是否允许模拟位置,默认为false，不允许模拟位置
//        mLocationOption.setMockEnable(false);
//
//        //给定位客户端对象设置定位参数
//        mLocationClient.setLocationOption(mLocationOption);
//        //启动定位
//        mLocationClient.startLocation();
    }

    //声明定位回调监听器
//    public static AMapLocationListener mLocationListener = new AMapLocationListener() {
//        @Override
//        public void onLocationChanged(AMapLocation amapLocation) {
//            if (amapLocation != null) {
//                if (amapLocation.getErrorCode() == 0) {
//                    //定位成功回调信息，设置相关消息
//                    LogUtils.i("当前定位结果来源-----" + amapLocation.getLocationType());//获取当前定位结果来源，如网络定位结果，详见定位类型表
//                    LogUtils.i("纬度 ----------------" + amapLocation.getLatitude());//获取纬度
//                    LogUtils.i("经度-----------------" + amapLocation.getLongitude());//获取经度
//                    LogUtils.i("精度信息-------------" + amapLocation.getAccuracy());//获取精度信息
//                    LogUtils.i("地址-----------------" + amapLocation.getAddress());//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
//                    LogUtils.i("国家信息-------------" + amapLocation.getCountry());//国家信息
//                    LogUtils.i("省信息---------------" + amapLocation.getProvince());//省信息
//                    LogUtils.i("城市信息-------------" + amapLocation.getCity());//城市信息
//                    LogUtils.i("城区信息-------------" + amapLocation.getDistrict());//城区信息
//                    LogUtils.i("街道信息-------------" + amapLocation.getStreet());//街道信息
//                    LogUtils.i("街道门牌号信息-------" + amapLocation.getStreetNum());//街道门牌号信息
//                    LogUtils.i("城市编码-------------" + amapLocation.getCityCode());//城市编码
//                    LogUtils.i("地区编码-------------" + amapLocation.getAdCode());//地区编码
//                    LogUtils.i("当前定位点的信息-----" + amapLocation.getAoiName());//获取当前定位点的AOI信息
//
//                    CDLApplication.setLocation(amapLocation);
//                } else {
//                    //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
//                    LogUtils.e("AmapError", "location Error, ErrCode:"
//                            + amapLocation.getErrorCode() + ", errInfo:"
//                            + amapLocation.getErrorInfo());
//                }
//            }
//        }
//    };


    /**
     * 是否开启通知权限
     */
    private static final String CHECK_OP_NO_THROW = "checkOpNoThrow";
    private static final String OP_POST_NOTIFICATION = "OP_POST_NOTIFICATION";

    @SuppressLint("NewApi")
    public static boolean isNotificationEnabled(Context context) {
        AppOpsManager mAppOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
        ApplicationInfo appInfo = context.getApplicationInfo();
        String pkg = context.getApplicationContext().getPackageName();
        int uid = appInfo.uid;
        Class appOpsClass = null;
        /* Context.APP_OPS_MANAGER */
        try {
            appOpsClass = Class.forName(AppOpsManager.class.getName());
            Method checkOpNoThrowMethod = appOpsClass.getMethod(CHECK_OP_NO_THROW, Integer.TYPE, Integer.TYPE, String.class);
            Field opPostNotificationValue = appOpsClass.getDeclaredField(OP_POST_NOTIFICATION);
            int value = (Integer) opPostNotificationValue.get(Integer.class);
            return ((Integer) checkOpNoThrowMethod.invoke(mAppOps, value, uid, pkg) == AppOpsManager.MODE_ALLOWED);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 跳转打电话界面
     *
     * @param activity
     * @param phoneNum
     */
    public static void callPhone(Activity activity, String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        activity.startActivity(intent);
    }

}
