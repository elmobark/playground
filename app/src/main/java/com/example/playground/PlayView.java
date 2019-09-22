package com.example.playground;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class PlayView extends View {
    Paint myp;
    RectF rectF;
    public PlayView(Context context) {
        super(context);
        Paint myp = new Paint();
        myp.setAntiAlias(true);
        myp.setColor(Color.BLACK);
        myp.setStrokeWidth(3);
        myp.setStrokeCap(Paint.Cap.ROUND);
        RectF rectF = new RectF();
        rectF.set(2,2,2,2);
        myp.setStrokeJoin(Paint.Join.ROUND);
    }

    public PlayView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        Paint myp = new Paint();
        myp.setAntiAlias(true);
        myp.setColor(Color.BLACK);
        myp.setStrokeWidth(3);
        myp.setStrokeCap(Paint.Cap.ROUND);
        RectF rectF = new RectF();
        rectF.set(2,2,2,2);
        myp.setStrokeJoin(Paint.Join.ROUND);
    }

    public PlayView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Paint myp = new Paint();
        myp.setAntiAlias(true);
        myp.setColor(Color.BLACK);
        myp.setStrokeWidth(3);
        myp.setStrokeCap(Paint.Cap.ROUND);
        RectF rectF = new RectF();
        rectF.set(2,2,2,2);
        myp.setStrokeJoin(Paint.Join.ROUND);
    }

    public PlayView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


//        canvas.drawOval(rectF,myp);
//        canvas.drawArc(rectF,30,20,true,myp);

    }
}
