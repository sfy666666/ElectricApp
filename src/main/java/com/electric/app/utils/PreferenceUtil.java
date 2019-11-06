package com.electric.app.utils;

import android.content.SharedPreferences;
import android.util.Base64;

import androidx.appcompat.app.AppCompatActivity;


import com.electric.app.common.EleApplication;
import com.electric.app.bean.UserInfo;
import com.electric.app.constants.Constants;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *
 * des: Preference管理工具类.
 */
public class PreferenceUtil {

    /**
     * 单例.
     */
    public static PreferenceUtil sInstance;


    public static final String SHARE_PREFERENCE_NAME = "CDL_Preference";


    private SharedPreferences mSharedPreferences;

    private PreferenceUtil() {
        mSharedPreferences = EleApplication.sApplicationContext.getSharedPreferences(SHARE_PREFERENCE_NAME, AppCompatActivity.MODE_PRIVATE);
    }

    public static PreferenceUtil getInstance() {
        if (sInstance == null) {
            synchronized (PreferenceUtil.class) {
                if (sInstance == null) {
                    sInstance = new PreferenceUtil();
                }
            }
        }
        return sInstance;
    }

    public void setStringValue(String key, String value) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public String getStringValue(String key, String defaultValue) {
        return mSharedPreferences.getString(key, defaultValue);
    }

    public void setBooleanValue(String key, boolean value) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }


    public boolean getBooleanValue(String key, boolean defaultValue) {
        return mSharedPreferences.getBoolean(key, defaultValue);
    }

    public void setIntValue(String key, int value) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public int getIntValue(String key, int defaultValue) {
        return mSharedPreferences.getInt(key, defaultValue);
    }

    public void setLongValue(String key, long value) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putLong(key, value);
        editor.commit();
    }

    public long getLongValue(String key, long defaultValue) {
        return mSharedPreferences.getLong(key, defaultValue);
    }

    public Object getObjectFromString(String key) {
        Object value = null;
        try {
            byte[] bytes = Base64.decode(PreferenceUtil.getInstance().getStringValue(key, ""), Base64.DEFAULT);
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            ObjectInputStream oisArray = new ObjectInputStream(bais);
            value = oisArray.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return value;
    }

    public void setObjectToString(String key, Object value) {

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos;
            oos = new ObjectOutputStream(baos);
            oos.writeObject(value);
            String data = new String(Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT));
            PreferenceUtil.getInstance().setStringValue(key, data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


//    public UserInfo getUserInfo() {
//        String userInfo = getStringValue(Constants.USER_INFO, "");
//    if (userInfo.isEmpty()) {
//        return new UserInfo(0, "", 1, "", "", 1, "", "","");
//    } else {
//        return JsonUtils.toEntity(userInfo, UserInfo.class);
//    }
//    }

    public void saveUserInfo(UserInfo userInfo) {
        if (userInfo == null) {
            setStringValue(Constants.USER_INFO, "");
        }else {
//            setStringValue(Constants.USER_INFO, JsonUtils.toJson(userInfo));
        }
    }
// public void saveCompanyInfo(SettingEntity.CompanyInfo companyInfo) {
//        if (companyInfo == null) {
//            setStringValue(Constants.COMPANY_INFO, "");
//        }else {
//            setStringValue(Constants.COMPANY_INFO, JsonUtils.toJson(companyInfo));
//        }
//    }
// public SettingEntity.CompanyInfo getCompanyInfo( ) {
//     String companyInfo = getStringValue(Constants.COMPANY_INFO, "");
//     if (companyInfo.isEmpty()) {
//         return new SettingEntity.CompanyInfo( );
//     } else {
//         return JsonUtils.toEntity(companyInfo, SettingEntity.CompanyInfo.class);
//     }
//    }


    public void setIdentity(int identity) {
//        UserInfo userInfo = getUserInfo();
//        userInfo.setCurrentRole(identity);
//        saveUserInfo(userInfo);
    }

//    public int getIdentity() {
//        return getUserInfo().getCurrentRole();
//    }

//    public String getSession() {
//        return getUserInfo().getUniqueCode();
//    }

    public String getLastConnectionMac() {
        return getStringValue("LAST_MAC", "");
    }

    public void setLastConnectionMac(String mac) {
        setStringValue("LAST_MAC", mac);
    }

    public void setInitialInstallation(boolean isFrist) {
        setBooleanValue(Constants.INITIAL_INSTALLATION, isFrist);
    }

    public boolean isInitialInstallation() {
        return getBooleanValue(Constants.INITIAL_INSTALLATION, true);
    }

    public void registerOnChangeListener(OnChangeListener listener) {
        mSharedPreferences.registerOnSharedPreferenceChangeListener(listener);
    }

    public void unregisterOnChangeListener(OnChangeListener listener) {
        mSharedPreferences.unregisterOnSharedPreferenceChangeListener(listener);
    }




    public interface OnChangeListener extends SharedPreferences.OnSharedPreferenceChangeListener {

    }
}
