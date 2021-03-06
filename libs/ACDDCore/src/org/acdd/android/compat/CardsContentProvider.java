package org.acdd.android.compat;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

/**
 * Created by zhoukaifeng on 2016/1/8 21:53.
 * email: zhoukaifeng@conew.com.
 * 用于兼容CardsContentProvider因multidex找不到Class
 */
public class CardsContentProvider extends ContentProvider {
    private ProviderProxy mProviderProxy;
    public CardsContentProvider() {
        mProviderProxy = new ProviderProxy("ks.cm.antivirus.scan.result.timeline.data.CardsContentProvider");
    }

    @Override
    public boolean onCreate() {
        return mProviderProxy.onCreate();
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return mProviderProxy.query(uri, projection, selection, selectionArgs, sortOrder);
    }

    @Override
    public String getType(Uri uri) {
        return mProviderProxy.getType(uri);
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return mProviderProxy.insert(uri, values);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return mProviderProxy.delete(uri, selection, selectionArgs);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return mProviderProxy.update(uri, values, selection, selectionArgs);
    }
}
