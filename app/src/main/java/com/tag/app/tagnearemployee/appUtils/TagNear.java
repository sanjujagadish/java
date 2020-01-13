package com.tag.app.tagnearemployee.appUtils;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.multidex.MultiDex;

import dagger.ObjectGraph;

/**
 * created by anjum on 14-10-2019
 */

public class TagNear extends Application
{
    private static TagNear sInstance;
    private ObjectGraph mApplicationGraph;

    /**
     * Inject modules.
     * @param object  the object
     * @param modules the modules
     */
    public static void injectModules(@NonNull final Object object, final Object... modules)
    { sInstance.mApplicationGraph.plus(modules).inject(object); }

    @Override
    protected void attachBaseContext(Context base)
    { super.attachBaseContext(base);
      MultiDex.install(base); }

    @Override
    public void onCreate()
    {   super.onCreate();
        sInstance = this;
        initApplicationGraph(); }

    private void initApplicationGraph()
    { mApplicationGraph = ObjectGraph.create(new ApplicationModule());
    }
}
