package com.electric.app.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class CircleView extends View {

    private PointF circle;

    public CircleView(Context context) {
        super(context);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        //圆心位置
        circle = new PointF(getWidth() / 2f, getHeight() / 2f);
        //间隙大小
        float circleRadio = getWidth()/2f-10f;
        //绘制
        drawCir(canvas,circleRadio);

        //平移画布  角度90 210 330  平移距离circleRadio






    }

    private void drawCir(Canvas canvas, float circleRadio) {

        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setAntiAlias(true);

        RectF rectF = new RectF(100,100,getWidth()-100,getHeight()-100);
//        RectF rectF = new RectF(circle.x-circleRadio, circle.y-circleRadio, circle.x+circleRadio, circle.y+circleRadio);
        canvas.drawArc(rectF, 30, 120, true, paint);

        paint.setColor(Color.YELLOW);
        canvas.drawArc(rectF, 150, 120, true, paint);

        paint.setColor(Color.GREEN);
        canvas.drawArc(rectF, 270, 120, true, paint);

//        canvas.translate((float) Math.sin(30)*2,(float) Math.cos(30));
//        canvas.restore();
//        canvas.save();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }
}
