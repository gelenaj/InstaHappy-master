package com.example.instahappy.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.ViewGroup;



public class AdaptiveImageView extends androidx.appcompat.widget.AppCompatImageView {
    private int defaultWidth = 0;
    private int defaultHeight = 0;


    public AdaptiveImageView(Context context) {
        super(context);
    }


    public AdaptiveImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public AdaptiveImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    @SuppressWarnings("SpellCheckingInspection")
    @Override
    protected void onDraw(Canvas canvas) {
        Drawable drawable = getDrawable();
        Bitmap bitmap = null;
        if (drawable == null) {
            return;
        }
        if (getWidth() == 0 || getHeight() == 0) {
            return;
        }

        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();

        //checking width and height
        //Snackbar.make(this, "width is:"+ w+ "height is: " + h, Snackbar.LENGTH_LONG).show();

        Bitmap.Config config =
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                        : Bitmap.Config.RGB_565;
        bitmap = Bitmap.createBitmap(w, h, config);
        if (bitmap.getWidth() == 0 || bitmap.getHeight() == 0) {
            return;
        }

        if (defaultWidth == 0) {
            defaultWidth = getWidth();
        }
        if (defaultHeight == 0) {
            defaultHeight = getHeight();
        }


        setMinimumHeight(getWidth());
        float scale = (float) defaultWidth / (float) bitmap.getWidth();
        defaultHeight = Math.round(bitmap.getHeight() * scale);
        ViewGroup.LayoutParams params = this.getLayoutParams();
        params.width = defaultWidth;
        params.height = defaultHeight;
        this.setLayoutParams(params);
        super.onDraw(canvas);
    }
}
