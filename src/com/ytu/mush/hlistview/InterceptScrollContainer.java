package com.ytu.mush.hlistview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;


public class InterceptScrollContainer extends LinearLayout {
	private static final String TAG = "InterceptScrollContainer";

	public InterceptScrollContainer(Context context, AttributeSet attrs) {
		super(context, attrs);
		
	}

	public InterceptScrollContainer(Context context) {
		super(context);
		
	}

	/* (non-Javadoc)
	 * @see android.view.ViewGroup#onInterceptTouchEvent(android.view.MotionEvent)
	 * ����TouchEvent
	 */
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		
		Log.i(TAG,"onInterceptTouchEvent");
		return true;
		//return super.onInterceptTouchEvent(ev);
	}
}
