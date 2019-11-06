package com.electric.app.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.ImageView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.electric.app.R;
import com.electric.app.common.EleApplication;

/**
 * Description：Glide加载图片封装类
 *
 * @author SGF
 * date:
 */
public class GlideUtils {

    private static GlideUtils utils;
    private Context context;

    private GlideUtils() {
        context = EleApplication.getContext();
    }

    public static synchronized GlideUtils getInstance() {
        if (utils == null) {
            utils = new GlideUtils();
        }
        return utils;
    }

    public int dip2px(float dipValue) {
        float reSize = context.getResources().getDisplayMetrics().density;
        return (int) ((dipValue * reSize) + 0.5);
    }

    /**
     * dp转px
     **/
    public int dip2px(int dipValue) {
        float reSize = context.getResources().getDisplayMetrics().density;
        return (int) ((dipValue * reSize) + 0.5);
    }

    /**
     * px转换成dp
     */
    public static int px2dp(Context context, float pxValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * dp转px
     *
     * @param dp
     * @return
     */
    public static int dip2px(Context context, int dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5);
    }

    /**
     * 加载圆形图片
     *
     * @param context
     * @param url
     * @param imageView
     */
    public static void displayRound(Context context, String url, ImageView imageView) {

        //Glide 4.5.0写法：
        RequestOptions requestOptions = RequestOptions.circleCropTransform();
        requestOptions.placeholder(R.mipmap.ic_launcher_round)//设置等待时的图片
                .error(R.mipmap.ic_launcher_round)//设置加载失败后的图片显示
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        Glide.with(context).load(url)
                .apply(requestOptions)
                .into(imageView);

    }

    /**
     * 加载圆形图片
     *
     * @param context
     * @param url
     * @param imageView
     */
    public static void displayRound(Context context, int url, ImageView imageView) {

        /**
         * DiskCacheStrategy.NONE： 表示不缓存任何内容。
         * DiskCacheStrategy.DATA： 表示只缓存原始图片。
         * DiskCacheStrategy.RESOURCE： 表示只缓存转换过后的图片。
         * DiskCacheStrategy.ALL ： 表示既缓存原始图片，也缓存转换过后的图片。
         * DiskCacheStrategy.AUTOMATIC： 表示让Glide根据图片资源智能地选择使用哪一种缓存策略（默认选项）。
         */
        RequestOptions requestOptions = RequestOptions.circleCropTransform();
        requestOptions.placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        Glide.with(context).load(url)
                .apply(requestOptions)
                .into(imageView);

    }
    /**
     * 欢迎页加载普通图片
     *
     * @param context
     * @param drawableId
     * @param imageView
     */
    public static void ordinaryWelcome(Context context, int  drawableId, ImageView imageView) {
        //Glide 4.5.0写法：
        RequestOptions options = new RequestOptions();
        options.placeholder(R.mipmap.ic_launcher_round);
        Glide.with(context).load(drawableId).apply(options).into(imageView);
    }

    /**
     * 加载普通图片
     *
     * @param context
     * @param url
     * @param imageView
     */
    public static void ordinary(Context context, String url, ImageView imageView) {
        //Glide 4.5.0写法：
        RequestOptions options = new RequestOptions();
        options.placeholder(R.mipmap.ic_launcher_round);
        Glide.with(context).load(url).apply(options).into(imageView);


    }

    /**
     * 加载GIF动态图片
     *
     * @param context
     * @param url
     * @param imageView
     */
    public static void ordinaryGif(Context context, int url, ImageView imageView) {
        //Glide 4.5.0写法：
        RequestOptions options = new RequestOptions();
        options.placeholder(R.mipmap.ic_launcher_round);
        options.diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        Glide.with(context).load(url).apply(options).into(imageView);
    }

    /**
     * 加载普通图片
     *
     * @param context
     * @param url
     * @param imageView
     */
    @SuppressLint("CheckResult")
    public static void ordinary(Context context, int url, ImageView imageView) {

        //Glide 4.5.0写法：
        RequestOptions options = new RequestOptions();
        options.placeholder(R.mipmap.ic_launcher_round)//设置等待时的图片
                .error(R.mipmap.ic_launcher_round)//设置加载失败后的图片显示
                .centerCrop()
                //默认淡入淡出动画
                .centerCrop()
                //缓存策略,跳过内存缓存【此处应该设置为false，否则列表刷新时会闪一下】
                .skipMemoryCache(false)
                //缓存策略,硬盘缓存-仅仅缓存最终的图像，即降低分辨率后的（或者是转换后的）
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                //设置图片加载的优先级
                .priority(Priority.HIGH);

        Glide.with(context).load(url).apply(options).into(imageView);

    }
}
