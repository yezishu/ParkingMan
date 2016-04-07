package com.parkingman.views;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Rect;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;

import io.codetail.animation.SupportAnimator;
import io.codetail.animation.ViewAnimationUtils;

/**
 * 项目名称：ParkingMan
 * 类描述：
 * 创建人：Yiming.Gan
 * 创建时间：2016/3/23 10:18
 * 修改人：Yiming.Gan
 * 修改时间：2016/3/23 10:18
 * 修改备注：
 */
public class CircularRevealFab extends FloatingActionButton {

    private ViewTreeObserver observer;
    private Activity context;
    public final Rect fabRect = new Rect();

    public CircularRevealFab(Context context) {
        super(context);
    }

    public CircularRevealFab(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CircularRevealFab(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void initCicularAni(Activity context) throws Exception {

//        setViewTreeObserver(context, (ViewTreeObserver.OnGlobalLayoutListener) context);
    }

    @Override
    public void getHitRect(Rect outRect) {
        if (outRect == null)
            super.getHitRect(fabRect);
        else
            super.getHitRect(outRect);
    }

    public void setBgColorFcicular(int color) {
        setBackgroundTintList(new ColorStateList(new  int[][]{new int[]{0}},new int[]{color}));
        SupportAnimator cla=next().create(this,fabRect);
        cla.setDuration(1000);
        cla.setInterpolator(new AccelerateDecelerateInterpolator());
        cla.start();
    }

    /**
     * 移除全局监听
     */
    public void removeOnGlobaLayListener() {
        if (Build.VERSION.SDK_INT >= 16) {
            observer.removeOnGlobalLayoutListener((ViewTreeObserver.OnGlobalLayoutListener) context);
        } else {
            observer.removeGlobalOnLayoutListener((ViewTreeObserver.OnGlobalLayoutListener) context);
        }
    }

    private void setViewTreeObserver(Activity context, ViewTreeObserver.OnGlobalLayoutListener gbListener) {

        observer = context.getWindow().getDecorView().getViewTreeObserver();
        observer.addOnGlobalLayoutListener(gbListener);
    }

    private int mCreationIndex;

    private static final Creation[] REVEAL_CREATORS = new Creation[]{
            new LeftTopSide(), new RightTopSide(), new RightBottomSide(), new LeftBottomSide()
    };

    private Creation next(){
        if(mCreationIndex == REVEAL_CREATORS.length){
            mCreationIndex = 0;
        }
        final Creation creation = REVEAL_CREATORS[mCreationIndex];
        mCreationIndex += 1;
        return creation;
    }
    interface Creation{
        SupportAnimator create(View view, Rect bounds);
    }

    static class LeftTopSide implements Creation{
        @Override
        public SupportAnimator create(View view, Rect bounds) {
            return ViewAnimationUtils.createCircularReveal(view, bounds.left, bounds.top, 0,
                    hypo(bounds.width(), bounds.height()));
        }
    }

    static class RightTopSide implements Creation{
        @Override
        public SupportAnimator create(View view, Rect bounds) {
            return ViewAnimationUtils.createCircularReveal(view, bounds.right, bounds.top, 0,
                    hypo(bounds.width(), bounds.height()));
        }
    }

    static class RightBottomSide implements Creation{
        @Override
        public SupportAnimator create(View view, Rect bounds) {
            return ViewAnimationUtils.createCircularReveal(view, bounds.right, bounds.bottom, 0,
                    hypo(bounds.width(), bounds.height()));
        }
    }

    static class LeftBottomSide implements Creation{
        @Override
        public SupportAnimator create(View view, Rect bounds) {
            return ViewAnimationUtils.createCircularReveal(view, bounds.left, bounds.bottom, 0,
                    hypo(bounds.width(), bounds.height()));
        }
    }

    public static float hypo(float a, float b){
        return (float) Math.sqrt( Math.pow(a, 2) + Math.pow(b, 2) );
    }




}
