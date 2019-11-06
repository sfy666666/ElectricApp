package com.electric.app.manager;

import android.app.Activity;
import android.content.Intent;


import androidx.fragment.app.FragmentActivity;

import com.electric.app.ui.activity.HomeActivity;
import com.electric.app.utils.LogUtils;

import java.util.Iterator;
import java.util.Stack;


public class ActManager {
    public static Stack<Activity> getActivityStack() {
        return activityStack;
    }

    private static Stack<Activity> activityStack;
    private static ActManager instance;

    private ActManager() {
    }

    /**
     * 单一实例
     */
    public static ActManager getInstance() {
        if (instance == null) {
            instance = new ActManager();
        }
        return instance;
    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<>();
        }
        activityStack.add(activity);
    }

    /**
     * 获取栈顶Activity（堆栈中最后一个压入的）
     */
    public Activity getTopActivity() {
        return activityStack.lastElement();
    }

    /**
     * 结束栈顶Activity（堆栈中最后一个压入的）
     */
    public void finishTopActivity() {
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * 结束指定类名的Activity
     *
     * @param cls
     */
    public void finishActivity(Class<?> cls) {
        Iterator iterator = activityStack.iterator();
        while (iterator.hasNext()) {
            Activity activity = (Activity) iterator.next();
            if (activity.getClass().equals(cls)) {
                iterator.remove();
                activity.finish();
            }
        }
    }

    /**
     * 结束所有Activity
     */
    @SuppressWarnings("WeakerAccess")
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    /**
     * 退出应用程序
     */
    public void appExit() {
        try {
            finishAllActivity();
            System.exit(0);
            android.os.Process.killProcess(android.os.Process.myPid());

        } catch (Exception e) {
        }
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 得到指定类名的Activity
     */
    public Activity getActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                return activity;
            }
        }
        return null;
    }

    /**
     * 回到首页
     */
    public void backMainActivity() {
        for (Activity activity : activityStack) {
            if (!activity.getClass().equals(HomeActivity.class)) {
                LogUtils.e(activity.getPackageName());
                finishActivity(activity);
            }
        }
    }

    /**
     * 首页
     *
     * @param activity
     * @param //identity       用户身份 1消费者 2经销商
     * @param fragmentPosition 首页fragment索引
     */
    public static void startMainAct(FragmentActivity activity, int fragmentPosition) {
        getInstance().addActivity(activity);
        Intent intent = new Intent(activity, HomeActivity.class);
//        intent.putExtra(Constants.FRAGMENT_POSITION, fragmentPosition);
        activity.startActivity(intent);

    }

    /**
     * 登录注册
     *
     * @param context
     */
    public static void startRegistAct(FragmentActivity context) {
        getInstance().addActivity(context);
//        Intent intent = new Intent(context, LoginByVerifyCodeActivity.class);
//        context.startActivity(intent);
    }

    /**
     * 密码登录
     *
     * @param identity 身份
     * @param context
     */
    public static void startLoginAct(FragmentActivity context, int identity) {
        getInstance().addActivity(context);
//        Intent intent = new Intent(context, LoginByPasswordActivity.class);
//        intent.putExtra(Constants.IDENTITY, identity);
//        context.startActivity(intent);
    }


    /**
     * 设置密码
     *
     * @param isNewUser 0正常用户 1,第一次注册的新用户   2,有用户 没有设置密码
     * @param context
     */
    public static void startSetPasswordAct(FragmentActivity context, int isNewUser) {
        getInstance().addActivity(context);
//        Intent intent = new Intent(context, SetPasswordActivity.class);
//        intent.putExtra("isNewUser", isNewUser);
//        context.startActivity(intent);
    }

    /**
     * 修改密码
     *
     * @param context
     */
    public static void startChangePasswordAct(FragmentActivity context) {
//        getInstance().addActivity(context);
//        Intent intent = new Intent(context, ChangePasswordActivity.class);
//        context.startActivity(intent);
    }


    /**
     * 找回密码
     *
     * @param context
     */
    public static void startForgetPasswordAct(FragmentActivity context) {
//        getInstance().addActivity(context);
//        Intent intent = new Intent(context, ForgetPasswordActivity.class);
//        context.startActivity(intent);
    }

    /**
     * 完善资料
     *
     * @param context
     */
    public static void startImproveDataAct(FragmentActivity context) {
        getInstance().addActivity(context);
//        Intent intent = new Intent(context, ImproveDataActivity.class);
//        context.startActivity(intent);
    }

    /**
     * 绑定产品
     *
     * @param context
     */
    public static void startBindingProductsAct(FragmentActivity context) {
        getInstance().addActivity(context);
//        Intent intent = new Intent(context, BindProductsActivity.class);
//        context.startActivity(intent);

    }

    /**
     * 搜索产品
     *
     * @param context
     * @param type    city product
     */
    public static void startSearchAct(FragmentActivity context, String type) {
        getInstance().addActivity(context);
//        Intent intent = new Intent(context, SearchActivity.class);
//        intent.putExtra(Constants.SEARCH_TYPE, type);
//        context.startActivity(intent);

    }

    /**
     * 消费者扫码绑定
     * 经销商扫码添加
     *
     * @param context
     */
    public static void startScanBindAndAddAct(FragmentActivity context) {
        getInstance().addActivity(context);
//        Intent intent = new Intent(context, ScanBindAndAddActivity.class);
//        context.startActivity(intent);
    }

    /**
     * 经销商扫码销售
     *
     * @param context
     */
    public static void startScanSaleAct(FragmentActivity context) {
        getInstance().addActivity(context);
//        Intent intent = new Intent(context, ScanSaleActivity.class);
//        context.startActivity(intent);
    }

    /**
     * 经销商扫码撤销
     *
     * @param context
     */
    public static void startScanRevokeAct(FragmentActivity context) {
        getInstance().addActivity(context);
//        Intent intent = new Intent(context, ScanRevokeActivity.class);
//        context.startActivity(intent);
    }

    /**
     * 扫码返回
     *
     * @param context
     * @param resultCode
     */
    public static void startScanBindAndAddAct(FragmentActivity context, int resultCode) {
        getInstance().addActivity(context);
//        Intent intent = new Intent(context, ScanBindAndAddActivity.class);
//        context.startActivityForResult(intent, resultCode);
    }

    /**
     * 确认绑定
     *
     * @param context
     * @param scanResult
     */
    public static void startConfirmeBindAndAddAct(FragmentActivity context, String scanResult) {
        getInstance().addActivity(context);
//        Intent intent = new Intent(context, ConfirmeBindAndAddActivity.class);
//        intent.putExtra(Constants.SCAN_RESULT, scanResult);
//        context.startActivity(intent);
    }

    /**
     * 确认销售/手动输入销售
     *
     * @param context
     * @param scanResult
     */
    public static void startConfirmeSaleAct(FragmentActivity context, String scanResult) {
        getInstance().addActivity(context);
//        Intent intent = new Intent(context, ConfirmeSaleActivity.class);
//        intent.putExtra(Constants.SCAN_RESULT, scanResult);
//        context.startActivity(intent);
    }

    /**
     * 确认撤销
     *
     * @param context
     * @param scanResult
     */
    public static void startConfirmeRevokeAct(FragmentActivity context, String scanResult) {
        getInstance().addActivity(context);
//        Intent intent = new Intent(context, ConfirmeRevokeActivity.class);
//        intent.putExtra(Constants.SCAN_RESULT, scanResult);
//        context.startActivity(intent);
    }


    /**
     * 关于我们
     *
     * @param context
     */
    public static void startAboutUsAct(FragmentActivity context) {
        getInstance().addActivity(context);
//        Intent intent = new Intent(context, AboutUsActivity.class);
//        context.startActivity(intent);
    }

    /**
     * 个人信息
     *
     * @param context
     */
    public static void startPersonalInfoAct(FragmentActivity context) {
        getInstance().addActivity(context);
//        Intent intent = new Intent(context, PersonalInfoActivity.class);
//        context.startActivity(intent);
    }

    //消息页面
    public static void startMessageAct(FragmentActivity context) {
        getInstance().addActivity(context);
//        Intent intent = new Intent(context, MessageActivity.class);
//        context.startActivity(intent);
    }

    /**
     * 切换角色
     *
     * @param context
     */
    public static void startChangeRoleAct(FragmentActivity context) {
        getInstance().addActivity(context);
//        Intent intent = new Intent(context, ChangeRoleActivity.class);
//        context.startActivity(intent);
    }

    /**
     * 修改手机号
     *
     * @param context
     */
    public static void startChangePhoneNumAct(FragmentActivity context) {
        getInstance().addActivity(context);
//        Intent intent = new Intent(context, ChangePhoneNumActivity.class);
//        context.startActivity(intent);
    }



    /**
     * web
     *
     * @param context
     * @param
     */
    public static void startWebAct(FragmentActivity context, String title, String url) {
        getInstance().addActivity(context);
//        Intent intent = new Intent(context, WebActivity.class);
//        intent.putExtra("title", title);
//        intent.putExtra("url", url);
//        context.startActivity(intent);
    }

    public static void startWebActByKey(FragmentActivity context, String key) {
        getInstance().addActivity(context);
//        Intent intent = new Intent(context, WebActivity.class);
//        switch (key) {
//            case "agreement":
//                intent.putExtra("title", "用户协议");
//                intent.putExtra("url", UrlConstant.SKIP_AGREEMENT);
//                break;
//
//            default:
//                break;
//
//
//        }
//
//        context.startActivity(intent);
    }

    /**
     * 引导页
     *
     * @param context
     */
    public static void startWelcomeAct(FragmentActivity context) {
        getInstance().addActivity(context);
//        Intent intent = new Intent(context, WelcomeActivity.class);
//        context.startActivity(intent);
    }

    /**
     * 我的业绩
     *
     * @param context
     */
    public static void startPerformanceAct(FragmentActivity context) {
        getInstance().addActivity(context);
//        Intent intent = new Intent(context, PerformanceActivity.class);
//        context.startActivity(intent);
    }

    /**
     * 提现记录
     *
     * @param context
     */
    public static void startWithdrawalRecord(FragmentActivity context) {
        getInstance().addActivity(context);
//        Intent intent = new Intent(context, WithdrawalRecordActivity.class);
//        context.startActivity(intent);
    }

    /**
     * 提现
     *
     * @param context
     */
    public static void startWithdrawal(FragmentActivity context) {
        getInstance().addActivity(context);
//        Intent intent = new Intent(context, WithdrawalActivity.class);
//        context.startActivity(intent);
    }

    /**
     * 账户设置
     *
     * @param context
     */
    public static void startAccountSettingsAct(FragmentActivity context) {
        getInstance().addActivity(context);
//        Intent intent = new Intent(context, AccountSettingsActivity.class);
//        context.startActivity(intent);
    }

    /**
     * 定位页面
     *
     * @param context
     * @param snCode
     */
    public static void startCellularProductPositionAct(FragmentActivity context, String snCode) {
        getInstance().addActivity(context);
//        Intent intent = new Intent(context, CellularProductPositionActivity.class);
//        intent.putExtra(Constants.SN_CODE, snCode);
//        context.startActivity(intent);
    }

    /**
     * 消息-电池详情
     *
     * @param context
     * @param snCode
     * @param buyTime
     */
    public static void startGpsProductDetailAct(FragmentActivity context, String snCode, String buyTime) {
        getInstance().addActivity(context);
//        Intent intent = new Intent(context, GpsProductDetailActivity.class);
//        intent.putExtra(Constants.SN_CODE, snCode);
//        intent.putExtra("buyTime", buyTime);
//        context.startActivity(intent);
    }


    /**
     * 网点-选择城市
     *
     * @param activity
     */
    public static void startSelectCityAct(FragmentActivity activity) {
        getInstance().addActivity(activity);
//        Intent intent = new Intent(activity, SelectCityActivity.class);
//        activity.startActivity(intent);
    }

    /**
     * 网点-选择城市
     *
     * @param activity
     */
    public static void startTextViewAct(FragmentActivity activity, String title, String content) {
        getInstance().addActivity(activity);
//        Intent intent = new Intent(activity, TextViewActivity.class);
//        intent.putExtra("title", title);
//        intent.putExtra("content", content);
//        activity.startActivity(intent);
    }


    /**
     * 我的收货地址
     *
     * @param activity
     */
    public static void startMyReceivAddressAct(FragmentActivity activity) {
//        getInstance().addActivity(activity);
//        Intent intent = new Intent(activity, MyReceivAddressActivity.class);
//        activity.startActivity(intent);
    }

    /**
     * 帮助与反馈
     *
     * @param activity
     */
    public static void startHelpAct(FragmentActivity activity) {
//        getInstance().addActivity(activity);
//        Intent intent = new Intent(activity,HelpFeedbackActivity.class);
//        activity.startActivity(intent);
    }
}
