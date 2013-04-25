package com.eevoskos.overlayimageview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.ImageView;

public class OverlayImageView extends ImageView {

    private enum Gravity {
        center(0), top_left(1), top_right(2), bottom_left(3), bottom_right(4);

        public final int value;

        private Gravity(int value) {
            this.value = value;
        }

        public static Gravity fromValue(int value) {
            for (Gravity gravity : Gravity.values()) {
                if (gravity.value == value) {
                    return gravity;
                }
            }
            return null;
        }
    }

    private Paint mPaint;
    private int mOverlayPadding;
    private Bitmap mOverlayBitmap;
    private Gravity mOverlayGravity;

    private RectF mBounds;
    private float mOverlayWidth;
    private float mOverlayHeight;

    private boolean mIsPrepared = false;
    private boolean mShowOverlay = true;

    public OverlayImageView(Context context) {
        this(context, null);
    }

    public OverlayImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OverlayImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setDither(true);
        mPaint.setFilterBitmap(true);

        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.OverlayImageView,
                defStyle, 0);
        try {
            int overlayResId = a.getResourceId(R.styleable.OverlayImageView_overlay_src, 0);
            if (overlayResId != 0) {
                mOverlayBitmap = BitmapFactory.decodeResource(getResources(), overlayResId);
            }
            mOverlayPadding = a.getDimensionPixelSize(R.styleable.OverlayImageView_overlay_padding,
                    0);
            mOverlayGravity = Gravity.fromValue(a.getInteger(
                    R.styleable.OverlayImageView_overlay_gravity, Gravity.center.value));

        } finally {
            a.recycle();
        }
    }

    public void setOverlayBitmap(Bitmap bitmap) {
        this.mOverlayBitmap = bitmap;
        postInvalidate();
    }

    public void setOverlayResource(int resource) {
        this.mOverlayBitmap = BitmapFactory.decodeResource(getResources(), resource);
        postInvalidate();
    }

    private void prepare() {
        if (mOverlayBitmap == null) {
            return;
        }

        // Calculate width and height
        mOverlayHeight = mOverlayBitmap.getHeight();
        mOverlayWidth = mOverlayBitmap.getWidth();

        // Calculate bounds
        if (mOverlayGravity == Gravity.top_left) {
            mBounds = new RectF(mOverlayPadding, 
                                mOverlayPadding, 
                                mOverlayPadding + mOverlayHeight, 
                                mOverlayPadding + mOverlayHeight);
            
        } else if (mOverlayGravity == Gravity.top_right) {
            mBounds = new RectF(getMeasuredWidth() - mOverlayWidth - mOverlayPadding, 
                                mOverlayPadding, 
                                getMeasuredWidth() - mOverlayPadding, 
                                mOverlayPadding + mOverlayHeight);
            
        } else if (mOverlayGravity == Gravity.bottom_left) {
            mBounds = new RectF(mOverlayPadding, 
                                getMeasuredHeight() - mOverlayHeight - mOverlayPadding, 
                                mOverlayHeight + mOverlayPadding,
                                getMeasuredHeight() - mOverlayPadding);
            
        } else if (mOverlayGravity == Gravity.bottom_right) {
            mBounds = new RectF(getMeasuredWidth() - mOverlayWidth - mOverlayPadding, 
                                getMeasuredHeight() - mOverlayHeight - mOverlayPadding, 
                                getMeasuredWidth() - mOverlayPadding, 
                                getMeasuredHeight() - mOverlayPadding);
            
        } else {
            mBounds = new RectF(getMeasuredWidth() / 2 - mOverlayWidth / 2, 
                                getMeasuredHeight() / 2 - mOverlayHeight / 2, 
                                getMeasuredWidth() / 2 + mOverlayWidth / 2, 
                                getMeasuredHeight() / 2 + mOverlayHeight / 2);
        }

        mIsPrepared = true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (!mIsPrepared) {
            prepare();
        }

        // Draw overlay bitmap on top of image
        if (mShowOverlay && mOverlayBitmap != null) {
            canvas.drawBitmap(mOverlayBitmap, null, mBounds, mPaint);
        }
    }
    
    public void showOverlay() {
        mShowOverlay = true;
        postInvalidate();
    }
    
    public void hideOverlay() {
        mShowOverlay = false;
        postInvalidate();
    }
    
    public void setShowOverlay(boolean show) {
        mShowOverlay = show;
        postInvalidate();
    }
    
    public void setOverlayGravity(Gravity gravity) {
        this.mOverlayGravity = gravity;
        mIsPrepared = false;
        postInvalidate();
    }

}
