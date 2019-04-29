package com.angcyo.uikitdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.angcyo.http.Func;
import com.angcyo.http.HttpSubscriber;
import com.angcyo.http.Rx;
import com.angcyo.lib.L;
import com.angcyo.uiview.less.utils.RDialog;
import com.qihoo360.replugin.RePlugin;
import com.qihoo360.replugin.model.PluginInfo;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import java.util.List;

/**
 * Email:angcyo@126.com
 *
 * @author angcyo
 * @date 2019/01/02
 */
public class RHost {
    public static void init(Context context, boolean debug) {
        RePlugin.enableDebugger(context, debug);
        RePlugin.registerInstalledReceiver(context.getApplicationContext(), new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                L.d(intent.toString());
                if (intent.hasExtra("plugin_info")) {
                    PluginInfo pluginInfo = intent.getParcelableExtra("plugin_info");
                    L.d(pluginInfo.toString());
                }
            }
        });
    }

    /**
     * 优先安装/更新插件
     * 如果安装失败, 则返回已经安装过的插件信息
     */
    public static PluginInfo getPlugin(String pluginName, String path) {
        PluginInfo pluginInfo = null;
        if (!TextUtils.isEmpty(path)) {
            pluginInfo = RePlugin.install(path);
        }
        if (pluginInfo != null) {
            RePlugin.preload(pluginInfo);
        } else {
            pluginInfo = RePlugin.getPluginInfo(pluginName);
        }
        return pluginInfo;
    }

    public static boolean install(String path) {
        PluginInfo pluginInfo = RePlugin.install(path);
        if (pluginInfo != null) {
            return RePlugin.preload(pluginInfo);
        }
        return false;
    }

    public static boolean uninstall(String pluginName) {
        return RePlugin.uninstall(pluginName);
    }

    public static void start(Context context, String pluginName, String activity) {
        start(context, pluginName, activity, new Intent());
    }

    public static void start(Context context, String pluginName, String activity, Intent intent) {
        RePlugin.startActivity(context, intent, pluginName, activity);
    }

    /**
     * 插件是否正在运行
     */
    public static boolean isPluginRunning(String pluginName) {
        return RePlugin.isPluginRunning(pluginName);
    }


    /**
     * 获取当前插件的版本号
     */
    public static int getPluginVersion(String pluginName) {
        return RePlugin.getPluginVersion(pluginName);
    }

    /**
     * 获取任意插件的信息
     */
    public static PluginInfo getPluginInfo(String pluginName) {
        return RePlugin.getPluginInfo(pluginName);
    }

    /**
     * 获取所有已安装（包括内置插件）的信息
     */
    public static List<PluginInfo> getPluginInfoList() {
        return RePlugin.getPluginInfoList();
    }

    /**
     * 获取最新插件的信息, 如果插件正在运行, 此时更新了插件. 返回的就是更新后的插件信息.虽然还没有升级.
     */
    public static PluginInfo getPendingUpdateInfo(String pluginName) {
        PluginInfo pi = RePlugin.getPluginInfo(pluginName);
        if (pi != null) {
            PluginInfo newPi = pi.getPendingUpdate();
            if (newPi != null) {
                pi = newPi;
            } else {
            }
        }
        return pi;
    }

    /**
     * 启动插件中的Activity
     * 如果需要释放dex, 会有 Loading 对话框
     */
    public static Observable<PluginInfo> startPlugin(@NonNull final Context context,
                                                     @Nullable final String pluginName,
                                                     @Nullable final String activity) {
        return startPlugin(context, null, pluginName, activity);
    }

    /**
     * 启动插件中的Activity
     * 如果需要释放dex, 会有 Loading 对话框
     *
     * @param pluginPath 如果插件未安装, 会先安装. 如果插件已安装, 会触发下一次的升级
     */
    public static Observable<PluginInfo> startPlugin(@NonNull final Context context,
                                                     @Nullable final String pluginPath,
                                                     @Nullable final String pluginName,
                                                     @Nullable final String activity) {
        return Rx
                .create(new Func<PluginInfo>() {
                    @Override
                    public PluginInfo call(Observer observer) {
                        if (TextUtils.isEmpty(pluginName) || TextUtils.isEmpty(activity)) {
                            return null;
                        }
                        PluginInfo pluginInfo = RePlugin.getPluginInfo(pluginName);
                        if (pluginInfo == null) {
                            //未安装插件
                            if (TextUtils.isEmpty(pluginPath)) {
                                return null;
                            }
                            pluginInfo = RePlugin.install(pluginPath);
                        }
                        if (pluginInfo.isNeedUpdate()) {
                            //插件需要升级
                        }
                        if (pluginInfo.isUsed()) {
                            //已经释放过dex
                        } else {
                            //需要释放dex
                        }
                        return pluginInfo;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<PluginInfo, PluginInfo>() {
                    @Override
                    public PluginInfo call(PluginInfo pluginInfo) {
                        if (pluginInfo != null && !pluginInfo.isUsed()) {
                            //插件未被使用过, 需要弹出loading, 并释放dex
                            RDialog.flow(context);
                        }
                        return pluginInfo;
                    }
                })
                .observeOn(Schedulers.newThread())
                .map(new Func1<PluginInfo, PluginInfo>() {
                    @Override
                    public PluginInfo call(PluginInfo pluginInfo) {
                        if (pluginInfo != null) {
                            if (!pluginInfo.isUsed()) {
                                //释放dex
                                RePlugin.preload(pluginInfo);
                            }

                            //可以直接在子线程中启动插件
                            RePlugin.startActivity(context, new Intent(), pluginName, activity);
                        }
                        return pluginInfo;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<PluginInfo, PluginInfo>() {
                    @Override
                    public PluginInfo call(PluginInfo pluginInfo) {
                        if (pluginInfo != null) {
                            RDialog.hide();
                        }
                        return pluginInfo;
                    }
                })
                ;
    }

    /**
     * 立即启动插件
     */
    public static void justStartPlugin(@NonNull final Context context,
                                       @Nullable final String pluginName,
                                       @Nullable final String activity) {
        startPlugin(context, pluginName, activity).subscribe(new HttpSubscriber<PluginInfo>());
    }
}
