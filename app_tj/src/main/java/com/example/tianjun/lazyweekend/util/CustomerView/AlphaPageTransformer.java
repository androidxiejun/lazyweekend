package com.example.tianjun.lazyweekend.util.CustomerView;

import android.support.v4.view.ViewPager;
import android.view.View;

import com.nineoldandroids.view.ViewHelper;

/**
 * Created by xx on 2016/8/23.
 */
public class AlphaPageTransformer implements ViewPager.PageTransformer {
    private static Float MAX_SIZE = 1.2F;
    @Override
    public void transformPage(View page, float position) {
        int pageWidth = page.getWidth();
        //从左滑出屏幕
        if (position < -1){
//            ViewHelper.setAlpha(page,0);
        }

        //将要消失的View
        else if (position <= 0){
            ViewHelper.setAlpha(page,1 + position);
            ViewHelper.setTranslationX(page,-pageWidth * position);

        }
        //需要显示的view
        else if (position <= 1){
            ViewHelper.setAlpha(page,1 - position);
            ViewHelper.setTranslationX(page,pageWidth);
            ViewHelper.setTranslationX(page,-pageWidth * position);


            float scaleFactor = MAX_SIZE
                    - (MAX_SIZE - 1) * (1 - Math.abs(position));
            ViewHelper.setScaleX(page,scaleFactor);
            ViewHelper.setScaleY(page,scaleFactor);
        }
        //从右滑出屏幕
        else{
            ViewHelper.setAlpha(page,0);
        }
    }
}
