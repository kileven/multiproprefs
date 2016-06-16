package com.lib.multiproprefs_demo.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hujun.common.IPluginCommand;
import com.hujun.common.PluginCommand;
import com.lib.multiproprefs.MPSharedPrefs;
import com.lib.multiproprefs_demo.R;
import com.lib.multiproprefs_demo.model.HelloPluginInfo;
import com.lib.multiproprefs_demo.model.WorldPluginInfo;
import com.lib.multiproprefs_demo.ndk.NdkFunc;
import com.lib.multiproprefs_demo.vo.PluginInfo;

import org.acdd.framework.ACDD;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Method;

public class PluginFragment extends BaseFragment {

    private NdkFunc mNdkFunc = new NdkFunc();
    private Handler mHandler = new Handler();

    public PluginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mMainView = inflater.inflate(R.layout.activity_plugin, container, false);
        init();

        TextView tv = (TextView) mMainView.findViewById(R.id.tvHaHa);
        tv.setText(MPSharedPrefs.VERSION);

        return mMainView;
    }

    private void init() {
        installPlugin(HelloPluginInfo.getPluginInfo());
        installPlugin(WorldPluginInfo.getPluginInfo());

        try {
            Drawable drawable = PluginCommand.getCommand(PluginCommand.CMD_HELLO).getTestDrawable(getContext());
            BitmapDrawable bd = (BitmapDrawable) drawable;
            Bitmap in = bd.getBitmap();
            Bitmap out = Bitmap.createBitmap(in.getWidth(), in.getHeight(), Bitmap.Config.ALPHA_8);
            mNdkFunc.grayPhoto(in, out);

            ImageView imageView = (ImageView) mMainView.findViewById(com.lib.multiproprefs_demo.R.id.imageView);
            imageView.setImageBitmap(out);

            LinearLayout linearLayout = (LinearLayout) mMainView.findViewById(com.lib.multiproprefs_demo.R.id.layout);
            View view = PluginCommand.getCommand(PluginCommand.CMD_HELLO).getHelloView(getContext());
            linearLayout.addView(view);
        } catch (Exception ex) {
            ex.printStackTrace();
        }


        Button btn = (Button) mMainView.findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intPlugin = new Intent();
                    intPlugin.setClassName(getActivity(), "com.hujun.helloplugin.MainActivity");
                    startActivity(intPlugin);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        Button btnWorld = (Button) mMainView.findViewById(R.id.buttonWorld);
        btnWorld.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intPlugin = new Intent();
                    intPlugin.setClassName(getActivity(), "com.hujun.worldplugin.MainActivity");
                    startActivity(intPlugin);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    private void installPlugin(PluginInfo info) {
        FileInputStream ins = null;
        try {
            File deployFile = new File(info.getFilePath());
            ins = new FileInputStream(new File(deployFile.getAbsolutePath()));
            ACDD.getInstance().installBundle(info.getPkgName(), ins);
        } catch (Exception exe) {
            exe.printStackTrace();
        } finally {
            try {
                if (ins != null) ins.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        initPlugin(info);
    }

    private void initPlugin(PluginInfo info) {
        try {
            String appClzz = info.getAppClzz();
            if (TextUtils.isEmpty(appClzz)) {
                return;
            }

            Class<?> clazz = Class.forName(info.getAppClzz(),
                    false, ACDD.getInstance().getBundleClassLoader(info.getPkgName()));
            Method initMethod = clazz.getMethod("init");
            IPluginCommand cmd = (IPluginCommand)initMethod.invoke(null);

            String str =  (String) cmd.invoke(123);
            TextView tv_1 = (TextView) mMainView.findViewById(R.id.txt_1);
            tv_1.setText(str);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
