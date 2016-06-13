package org.byters.bcshoppinglist.ui.view;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import org.byters.bcshoppinglist.controllers.utils.OnTapListener;

import java.util.ArrayList;


public class ImageViewer extends View {
    private GestureDetector gestureDetector;
    private ScaleGestureDetector scaleGestureDetector;
    private ArrayList<OnTapListener> tapListeners;
    private float scaleFactor;

    private Paint paint;
    @Nullable
    private Bitmap image;

    private Rect frameScaled, frameOriginal;
    private int imageWidth, imageHeight;

    public ImageViewer(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, 0);
        init(context);
    }

    /*    public void setPoint(Point point) {
            this.point = point;
            Bitmap pt;
            pt = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_map_marker_circle);
            int[] px = new int[pt.getWidth()*pt.getHeight()];
            pt.getPixels(px, 0, pt.getWidth(), 0, 0, pt.getWidth(), pt.getHeight());
            image.setPixels(px, 0, pt.getWidth(), point.x - (pt.getHeight() / 2), point.y - (pt.getWidth() / 2), pt.getWidth(), pt.getHeight());
            pt.recycle();

            invalidate();

        }*/

    public ImageViewer(Context context) {
        super(context);

        init(context);
    }

    private void init(Context context) {
        MyGestureListener listener = new MyGestureListener();
        gestureDetector = new GestureDetector(context, listener);
        scaleGestureDetector = new ScaleGestureDetector(context, listener);

        paint = new Paint();
        paint.setFilterBitmap(true);
        paint.setDither(false);

        image = null;

        frameScaled = new Rect(0, 0, 0, 0);
        frameOriginal = new Rect(0, 0, 0, 0);

        scaleFactor = 1;
    }

    public void loadImage(Bitmap source) {
        image = source.copy(source.getConfig(), true);
        if (image == null) return;

        imageHeight = image.getHeight();
        imageWidth = image.getWidth();
        initRects();

        invalidate();
    }

    private void initRects() {
        if (imageWidth == 0 || imageHeight == 0
                || frameOriginal.bottom == 0 || frameOriginal.right == 0) return;
        int calcHeight = (int) (imageHeight / ((float) imageWidth / frameOriginal.right));
        frameScaled.bottom = calcHeight;
        frameOriginal.bottom = calcHeight;
    }

    public void addPolygon(ArrayList<Point> points) {
        if (image == null || points == null || points.size() < 2) return;

        Canvas can = new Canvas(image);
        Path path = new Path();

        path.moveTo(points.get(0).x, points.get(0).y);
        for (Point point : points)
            path.lineTo(point.x, point.y);

        path.close();

        Paint brush = new Paint();
        brush.setColor(Color.RED);
        brush.setStyle(Paint.Style.STROKE);
        brush.setStrokeWidth(3);
        brush.setDither(true);

        can.drawPath(path, brush);

        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        frameScaled.set(0, 0, width, height);
        frameOriginal.set(0, 0, width, height);
        scaleFactor = 1;
        initRects();
    }

    @Override
    public void onDraw(Canvas canvas) {
        if (image == null) return;
        canvas.drawBitmap(image, null, frameScaled, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (image == null) return false;

        scaleGestureDetector.onTouchEvent(event);
        if (gestureDetector.onTouchEvent(event)) return true;
        return true;
    }

    public void addTapListener(OnTapListener listener) {
        if (tapListeners == null) tapListeners = new ArrayList<>();
        tapListeners.add(listener);
    }

    public void removeTapListener(OnTapListener listener) {
        if (tapListeners == null) return;
        tapListeners.remove(listener);
    }

    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener
            implements ScaleGestureDetector.OnScaleGestureListener {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            int x = (int) distanceX;
            int y = (int) distanceY;
            x = x < 0 ? Math.max(x, -getScrollX()) : Math.min(x, frameScaled.right - (getScrollX() + frameOriginal.right));
            y = y < 0 ? Math.max(y, -getScrollY()) : Math.min(y, frameScaled.bottom - (getScrollY() + frameOriginal.bottom));
            scrollBy(x, y);
            return true;
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {

            float multiplier = imageHeight / (float) frameScaled.bottom;

            int x_image = getScrollX() + (int) (e.getX() * multiplier);
            int y_image = getScrollY() + (int) (e.getY() * multiplier);

            if (tapListeners != null)
                for (OnTapListener listener : tapListeners)
                    listener.onTap(x_image, y_image);
            return super.onSingleTapUp(e);
        }

        @Override
        public boolean onScale(ScaleGestureDetector detector) {

            scaleFactor *= detector.getScaleFactor();
            scaleFactor = Math.max(scaleFactor, 1);

            int right = (int) (frameOriginal.right * scaleFactor);
            int bottom = (int) (frameOriginal.bottom * scaleFactor);

            frameScaled.left = (int) Math.max(frameOriginal.left * scaleFactor, frameOriginal.left);
            frameScaled.top = (int) Math.max(frameOriginal.top * scaleFactor, frameOriginal.top);
            frameScaled.right = frameScaled.right == 0 ? right : Math.max(right, frameOriginal.right);
            frameScaled.bottom = frameScaled.bottom == 0 ? bottom : Math.max(bottom, frameOriginal.bottom);

            //todo add scroll to fingers pos
            /*int newScrollX = (int) ((getScrollX() + detector.getFocusX()) * detector.getScaleFactor() - detector.getFocusX());
            int newScrollY = (int) ((getScrollY() + detector.getFocusY()) * detector.getScaleFactor() - detector.getFocusY());
            scrollTo(newScrollX, newScrollY);*/

            invalidate();
            return true;
        }

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            return true;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {

        }
    }
}
