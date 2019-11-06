package com.electric.app.net;

import android.os.Handler;

import androidx.fragment.app.FragmentActivity;

import com.electric.app.common.EleApplication;
import com.electric.app.constants.Constants;
import com.electric.app.utils.LoadingDialogUtil;
import com.electric.app.utils.LogUtils;
import com.electric.app.utils.ToastUtil;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class HttpLoader {


    private static HttpLoader instance;

    private static final String REQUEST_GET = "GET";
    private static final String REQUEST_POST = "POST";
    private static final int Timeout = 5;
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");

    private static volatile HttpLoader mInstance;//单利引用
    private OkHttpClient mOkHttpClient;//okHttpClient 实例
    private Handler okHttpHandler;//全局处理子线程和M主线程通信

    private static FragmentActivity mContext;

    private HttpLoader(FragmentActivity context) {
        mContext = context;
        mOkHttpClient = new OkHttpClient()
                .newBuilder()
                .connectTimeout(Timeout, TimeUnit.SECONDS)//设置超时时间
                .readTimeout(Timeout, TimeUnit.SECONDS)//设置读取超时时间
                .writeTimeout(Timeout, TimeUnit.SECONDS)//设置写入超时时间
//                .addInterceptor(new GoddessInterceptor())
                .build();
        //初始化Handler
        okHttpHandler = new Handler(context.getMainLooper());

    }

    public static HttpLoader getInstance(FragmentActivity context) {
        HttpLoader instance = mInstance;
        mContext = context;
        if (instance == null) {
            synchronized (HttpLoader.class) {
                instance = mInstance;
                if (instance == null) {
                    instance = new HttpLoader(context);
                    mInstance = instance;
                }
            }
        }
        return instance;
    }


    public void post(String url, HttpParam param, int requestCode, HttpListener listener) {
        request(REQUEST_POST, url, param, requestCode, listener);
    }


    public void get(String url, HttpParam param, int requestCode, HttpListener listener) {
        request(REQUEST_GET, url, param, requestCode, listener);
    }

    public void upLoad(String url, HttpParam param, File file, int requestCode, HttpListener listener) {
        LoadingDialogUtil.getInstance().showLoadingDialog(mContext, "Loading...");//加载动画
        MultipartBody.Builder multipartBodyBuilder = new MultipartBody.Builder();
        multipartBodyBuilder.setType(MultipartBody.FORM);
        //遍历map中所有参数到builder
        param = addParameters(param);
        param.put("modelType", "CLIENT");
        if (param != null) {
            for (String key : param.keySet()) {
                multipartBodyBuilder.addFormDataPart(key, param.get(key));
            }
        }

        multipartBodyBuilder.addFormDataPart("file","firstImage", RequestBody.create(MEDIA_TYPE_PNG, file));


        //构建请求体
        RequestBody requestBody = multipartBodyBuilder.build();

        Request.Builder RequestBuilder = new Request.Builder();
        RequestBuilder.url(url);// 添加URL地址
//        RequestBuilder.post(requestBody);
        RequestBuilder.post(requestBody);
        Request request = RequestBuilder.build();
        mOkHttpClient.newCall(request).enqueue(new HttpCallBack(requestCode, listener));
    }


    private void request(String requestType, String url, HttpParam param, int requestCode, HttpListener listener) {
        LoadingDialogUtil.getInstance().showLoadingDialog(mContext, "Loading...");//加载动画
        try {
            param = addParameters(param);
            //post
            if (REQUEST_POST.equals(requestType)) {
                RequestBody body = assemblyParametersPost(param);
                assemblyParametersGet(url, param);//只是为了打印一下url
                Request request = new Request.Builder().url(url).post(body).build();
                mOkHttpClient.newCall(request).enqueue(new HttpCallBack(requestCode, listener));
            } else {
                //get
                url = assemblyParametersGet(url, param);
                Request request = new Request.Builder().url(url).build();
                mOkHttpClient.newCall(request).enqueue(new HttpCallBack(requestCode, listener));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * 添加公共参数
     *
     * @param param
     * @return
     */
    private HttpParam addParameters(HttpParam param) {
        if (param == null) {
            param = new HttpParam();
        }

//        if (!param.containsKey(Constants.MOBILE)) {
//            LogUtils.i("添加参数MOBILE:" + PreferenceUtil.getInstance().getUserInfo().getMobile());
//            param.put(Constants.MOBILE, PreferenceUtil.getInstance().getUserInfo().getMobile());
//        }
//        if (!param.containsKey(Constants.CLIENTID)) {
//            LogUtils.i("添加参数CLIENTID:" + PreferenceUtil.getInstance().getUserInfo().getClientId());
//            param.put(Constants.CLIENTID, PreferenceUtil.getInstance().getUserInfo().getClientId());
//        }
//        if (!param.containsKey(Constants.USER_ID)) {
//            LogUtils.i("添加参数USER_ID:" + PreferenceUtil.getInstance().getUserInfo().getUserId());
//            param.put(Constants.USER_ID, PreferenceUtil.getInstance().getUserInfo().getUserId());
//        }
        if (!param.containsKey(Constants.EQUIPMENT_TYPE)) {
            LogUtils.i("添加参数EQUIPMENT_TYPE:" + 1);
            param.put(Constants.EQUIPMENT_TYPE, "1");
        }

        return param;
    }

    /**
     * 拼装post请求参数
     *
     * @param param
     * @return
     */
    private RequestBody assemblyParametersPost(HttpParam param) {

        FormBody.Builder builder = new FormBody.Builder();
        for (String key : param.keySet()) {
            if (param.get(key) == null || "".equals(param.get(key))) {
                builder.add(key, "");
            } else {
                builder.add(key, param.get(key));
            }
        }
        RequestBody body = builder.build();
        return body;
    }

    /**
     * 拼装get请求参数
     *
     * @param url
     * @param param
     * @return
     */
    private String assemblyParametersGet(String url, HttpParam param) {
        url += "?";
        for (String key : param.keySet()) {
            url += key + "=" + param.get(key) + "&";
        }
        LogUtils.i(url);
        return url;
    }


    public interface HttpListener {

        void onSuccess(int resultCode, String result);

        void onfailed(int resultCode, String error);

    }

    class HttpCallBack implements Callback {
        private HttpListener listener;
        private int requestCode;

        public HttpCallBack(int requestCode, HttpListener listener) {
            this.listener = listener;
            this.requestCode = requestCode;
        }

        @Override
        public void onFailure(Call call, final IOException e) {

            EleApplication.getmMainThreadHandler().post(new Runnable() {
                @Override
                public void run() {
                    listener.onfailed(requestCode, e.toString());
                    ToastUtil.show("网络连接失败,请检查网络状态");
//            mContext.hintLoading();
                    LogUtils.i("连接失败,请检查网络状态", e.toString());
                    LoadingDialogUtil.getInstance().closeLoadingDialog();

                }
            });

        }

        @Override
        public void onResponse(Call call, final Response response) {
            String result = "{}";
            try {
                result = response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
                LogUtils.e("ioException" + e.toString());
            }
            final String finalResult = result;
            //发送到主线程
            EleApplication.getmMainThreadHandler().post(new Runnable() {
                @Override
                public void run() {
                    try {
                        LoadingDialogUtil.getInstance().closeLoadingDialog();
                        LogUtils.i(finalResult);
//                        BaseBean baseBean = JsonUtils.toEntity(finalResult, BaseBean.class);
//                        if (baseBean.getCode() != 0 && baseBean.getCode() == 500) {
//                            ToastUtil.show("服务器异常,请稍候再试");
//                        } else if (baseBean.getFlag() == 1) {//成功
//                            listener.onSuccess(requestCode, finalResult);
//                        } else if (baseBean.getFlag() == 0) {//失败
//                            ToastUtil.show("系统提示:" + baseBean.getMessage());
//                            listener.onSuccess(requestCode, finalResult);
////                            listener.onfailed(requestCode, finalResult);
//                        } else if (baseBean.getFlag() == -1) {//重新登录
//                            ActManager.startLoginAct(mContext,Constants.IDENTITY_CONSUMER);
//                        } else {//其他情况
//                            listener.onfailed(requestCode, finalResult);
//                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        LogUtils.i(e.toString());
                        listener.onfailed(requestCode, e.toString());
                        ToastUtil.show("网络连接失败");

                    }
                }
            });
        }
    }


}














