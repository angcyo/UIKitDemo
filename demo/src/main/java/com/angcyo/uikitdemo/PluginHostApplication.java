package com.angcyo.uikitdemo;

import android.content.Context;
import android.content.res.Configuration;
import com.angcyo.uiview.less.RApplication;
import com.qihoo360.replugin.RePlugin;
import com.qihoo360.replugin.RePluginCallbacks;
import com.qihoo360.replugin.RePluginConfig;
import com.qihoo360.replugin.RePluginEventCallbacks;

/**
 * @author angcyo
 */
public class PluginHostApplication extends RApplication {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

        RePluginConfig config = new RePluginConfig();
        config.setCallbacks(new RePluginCallbacks(this));
        config.setEventCallbacks(new RePluginEventCallbacks(this));
        config.setVerifySign(false/*!BuildConfig.DEBUG*/);

        //插件类不存在时读取宿主
        config.setUseHostClassIfNotFound(true);

        RePlugin.App.attachBaseContext(this, config);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        RePlugin.App.onCreate();

        RHost.init(this, BuildConfig.DEBUG);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();

        /* Not need to be called if your application's minSdkVersion > = 14 */
        RePlugin.App.onLowMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);

        /* Not need to be called if your application's minSdkVersion > = 14 */
        RePlugin.App.onTrimMemory(level);
    }

    @Override
    public void onConfigurationChanged(Configuration config) {
        super.onConfigurationChanged(config);

        /* Not need to be called if your application's minSdkVersion > = 14 */
        RePlugin.App.onConfigurationChanged(config);
    }

}