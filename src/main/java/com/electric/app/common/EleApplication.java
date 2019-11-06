package com.electric.app.common;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.hjq.image.ImageLoader;
import com.hjq.toast.ToastInterceptor;
import com.hjq.toast.ToastUtils;


/**
 *  Application
 */
public  final class EleApplication extends Application {


    public static final String TAG = EleApplication.class.getSimpleName();

    public static Context sApplicationContext;
    private static EleApplication instance;
    private static Context mContext;
    private static Handler mMainThreadHandler  ;
    private static int mMainThreadId;
    public static int lastConnectionPowerPercentage;

    public static EleApplication getInstance() {
        return instance;
    }

    public static Handler getmMainThreadHandler() {
        return mMainThreadHandler;
    }



    @Override
    public void onCreate() {
        super.onCreate();

        sApplicationContext = this;
        mContext = this;
        instance = this;
        mMainThreadHandler = new Handler();
        mMainThreadId = android.os.Process.myTid();


        initSdk();

    }

    private void initSdk() {
        //扫描二维码
//        ZXingLibrary.initDisplayOpinion(this);
        //蓝牙

        //个推
        initGeTui();
        initUMeng();


        // 设置 Toast 拦截器
        ToastUtils.setToastInterceptor(new ToastInterceptor() {
            @Override
            public boolean intercept(Toast toast, CharSequence text) {
                boolean intercept = super.intercept(toast, text);
                if (intercept) {
                    Log.e("Toast", "空 Toast");
                } else {
                    Log.i("Toast", text.toString());
                }
                return intercept;
            }
        });
        // 吐司工具类
        ToastUtils.init(this);
        // 图片加载器
        ImageLoader.init(this);


    }

    private void initUMeng() {
        /**
         * 注意: 即使您已经在AndroidManifest.xml中配置过appkey和channel值，也需要在App代码中调
         * 用初始化接口（如需要使用AndroidManifest.xml中配置好的appkey和channel值，
         * UMConfigure.init调用中appkey和channel参数请置为null）。
         * App Master Secret  vl3qm1jwlswkwn7jen2hdvc6j3sotnrs
         * Umeng Message Secret     9cb8d92445180bbf9172a3b6f1b3b764
         * AppKey   5d3d1076570df3c6f2000027
         *channel_wandoujia
         *channel_xiaomi
         *channel_oppo
         *channel_huawei
         *channel_lssz
         *
         */
//        UMConfigure.init(this,"5d3d1076570df3c6f2000027","channel_lssz", UMConfigure.DEVICE_TYPE_PHONE, "9cb8d92445180bbf9172a3b6f1b3b764");
//        //获取消息推送代理示例
//        PushAgent mPushAgent = PushAgent.getInstance(this);
//        //注册推送服务，每次调用register方法都会回调该接口
//        mPushAgent.register(new IUmengRegisterCallback() {
//            @Override
//            public void onSuccess(String deviceToken) {
//                //注册成功会返回deviceToken deviceToken是推送消息的唯一标志
//                LogUtils.i("友盟注册成功：deviceToken：-------->  " + deviceToken);
//            }
//            @Override
//            public void onFailure(String s, String s1) {
//                LogUtils.e("注册失败：-------->  " + "s:" + s + ",s1:" + s1);
//            }
//        });
    }

    public Context getApplicationContext() {
        return sApplicationContext;
    }

    public static Context getContext() {
        return mContext;
    }


    private void initGeTui() {
//        PushManager.getInstance().initialize(this.getApplicationContext(), DemoPushService.class);
//        PushManager.getInstance().registerPushIntentService(getApplicationContext(), DemoIntentService.class);
    }

}
