package com.keven.kubi.weight;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.keven.kubi.utils.ScreenUtil;

public class InteraptorLinearLayout extends LinearLayout implements ValueAnimator.AnimatorUpdateListener
{

  private ValueAnimator showAnimator ;
  private int mScreenHeight;


  public InteraptorLinearLayout(Context paramContext)
  {
    super(paramContext);
    init();
  }

  public InteraptorLinearLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    init();
  }

  public InteraptorLinearLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    init();
  }

  public void init(){
    mScreenHeight = ScreenUtil.getScreenHeight(getContext());
  }

  public void close(){

    startAnimator(0, -mScreenHeight);
  }

  public void show(){
    startAnimator( -mScreenHeight, 0);
  }

  private void startAnimator(int from, int to){
    if(showAnimator == null){
      showAnimator = new ValueAnimator();

      showAnimator.addUpdateListener(this);
      showAnimator.setDuration(300);
    }

    showAnimator.setIntValues(from, to);
    showAnimator.start();
  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {
    super.onTouchEvent(event);
    return true;
  }


  @Override
  public void onAnimationUpdate(ValueAnimator animation) {
    int height = (int)animation.getAnimatedValue();
    setY(height);
  }
}