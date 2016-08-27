package com.androidxx.yangjw.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by yangjw on 2016/8/5.
 */
public class ImageLoader {

    private static ImageLoader imageLoader;
    //线程池
    private static ExecutorService executorService;
    public static ImageLoader init(Context context) {
        executorService = Executors.newFixedThreadPool(4);
        DiskLruCacheTool.init(context);
        if (imageLoader == null) {
            imageLoader = new ImageLoader();
        }
        return imageLoader;
    }
    //入口:开始加载图片
    public void load(String url, ImageView imageView, ProgressBar mPb) {
        //加载内存缓存
        Bitmap memoryCahche = MemoryCacheTool.readMemoryCahche(url);
        if (memoryCahche != null) {
            mPb.setVisibility(ProgressBar.GONE);
            imageView.setImageBitmap(memoryCahche);
        } else {
            //开启线程
            executorService.execute(new ImageThread(url,imageView,mPb));
        }
        DiskLruCacheTool.flush();
    }


//    //出口：获取加载成功后的图片
//    public void into(ImageView imageview) {
//        this.imageView = imageview;
//    }

}
