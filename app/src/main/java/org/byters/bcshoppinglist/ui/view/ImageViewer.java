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
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.OnScaleGestureListener;
import android.view.View;
import android.widget.Scroller;

import java.util.ArrayList;


public class ImageViewer extends View {
    private GestureDetector gestureDetector;
    private ScaleGestureDetector scaleGestureDetector;
    private Scroller scroller;

    private Paint paint;
    @Nullable
    private Bitmap image;

    private float scaleFactor;

    private Rect dst;

    public ImageViewer(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, 0);
        init(context);
    }

    public ImageViewer(Context context) {
        super(context);

        init(context);
    }

    private void init(Context context) {
        gestureDetector = new GestureDetector(context, new MyGestureListener());
        scaleGestureDetector = new ScaleGestureDetector(context, new MyScaleGestureListener());

        setHorizontalScrollBarEnabled(true);
        setVerticalScrollBarEnabled(true);

        scroller = new Scroller(context);

        paint = new Paint();
        paint.setFilterBitmap(true);
        paint.setDither(false);

        image = null;
        scaleFactor = 1;

        dst = new Rect(0, 0, 0, 0);

    }

    private int getScaledWidth() {
        return image == null ? 0 : (int) (image.getWidth() * scaleFactor);
    }

    private int getScaledHeight() {
        return image == null ? 0 : (int) (image.getHeight() * scaleFactor);
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
    public void onDraw(Canvas canvas) {
        if (image == null) return;
        //todo: set scale and translate based on matrix
        dst.set(-getScaledWidth() / 2, -getScaledHeight() / 2, getScaledWidth() / 2, getScaledHeight() / 2);
        canvas.drawBitmap(image, null, dst, paint);
    }

    @Override
    protected int computeHorizontalScrollRange() {
        return getScaledWidth();
    }

    @Override
    protected int computeVerticalScrollRange() {
        return getScaledHeight();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (image == null) return false;

        // check for tap and cancel fling
        if ((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_DOWN) {
            if (!scroller.isFinished()) scroller.abortAnimation();
        }
        // handle pinch zoom gesture
        // don't check return value since it is always true
        scaleGestureDetector.onTouchEvent(event);

        // check for scroll gesture


        if (gestureDetector.onTouchEvent(event)) return true;

        // check for pointer release
        if ((event.getPointerCount() == 1) && ((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_UP)) {
            int newScrollX = getScrollX();
            if (getScrollX() < 0) newScrollX = 0;
            else if (getScrollX() > image.getWidth() - getWidth())
                newScrollX = image.getWidth() - getWidth();

            int newScrollY = getScrollY();
            if (getScrollY() < 0) newScrollY = 0;
            else if (getScrollY() > image.getHeight() - getHeight())
                newScrollY = image.getHeight() - getHeight();

            if ((newScrollX != getScrollX()) || (newScrollY != getScrollY())) {
                scroller.startScroll(getScrollX(), getScrollY(), newScrollX - getScrollX(), newScrollY - getScrollY());
                awakenScrollBars(scroller.getDuration());
            }
        }

        return true;
    }

    @Override
    public void computeScroll() {
        if (scroller.computeScrollOffset()) {
            int oldX = getScrollX();
            int oldY = getScrollY();
            int x = scroller.getCurrX();
            int y = scroller.getCurrY();
            scrollTo(x, y);
            if (oldX != getScrollX() || oldY != getScrollY()) {
                onScrollChanged(getScrollX(), getScrollY(), oldX, oldY);
            }

            postInvalidate();
        }
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldX, int oldY) {
    }

    @Override
    protected void onSizeChanged(int width, int height, int oldWidth, int oldHeight) {
        int scrollX = (getScaledWidth() < width ? -(width - getScaledWidth()) / 2 : getScaledWidth() / 2);
        int scrollY = (getScaledHeight() < height ? -(height - getScaledHeight()) / 2 : getScaledHeight() / 2);
        scrollTo(scrollX, scrollY);
    }

    public void loadImage(Bitmap source) {
        image = source.copy(source.getConfig(), true);

        if (image == null)
            return;

        DisplayMetrics displaymetrics = getResources().getDisplayMetrics();

        if ((float) displaymetrics.heightPixels / (float) image.getHeight() > (float) displaymetrics.widthPixels / (float) image.getWidth())
            scaleFactor = (float) displaymetrics.widthPixels / (float) image.getWidth();
        else
            scaleFactor = (float) displaymetrics.heightPixels / (float) image.getHeight();

        invalidate();
    }

    private class MyGestureListener extends SimpleOnGestureListener {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

            scrollBy((int) distanceX, (int) distanceY);
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            int fixedScrollX = 0, fixedScrollY = 0;
            int maxScrollX = getScaledWidth(), maxScrollY = getScaledHeight();

            if (getScaledWidth() < getWidth()) {
                fixedScrollX = -(getWidth() - getScaledWidth()) / 2;
                maxScrollX = fixedScrollX + getScaledWidth();
            }

            if (getScaledHeight() < getHeight()) {
                fixedScrollY = -(getHeight() - getScaledHeight()) / 2;
                maxScrollY = fixedScrollY + getScaledHeight();
            }

            boolean scrollBeyondImage = (fixedScrollX < 0) || (fixedScrollX > maxScrollX) || (fixedScrollY < 0) || (fixedScrollY > maxScrollY);
            if (scrollBeyondImage) return false;

            scroller.fling(getScrollX(), getScrollY(), -(int) velocityX, -(int) velocityY, 0, getScaledWidth() - getWidth(), 0, getScaledHeight() - getHeight());
            awakenScrollBars(scroller.getDuration());

            return true;
        }
    }

    private class MyScaleGestureListener implements OnScaleGestureListener {
        public boolean onScale(ScaleGestureDetector detector) {
            scaleFactor *= detector.getScaleFactor();

            int newScrollX = (int) ((getScrollX() + detector.getFocusX()) * detector.getScaleFactor() - detector.getFocusX());
            int newScrollY = (int) ((getScrollY() + detector.getFocusY()) * detector.getScaleFactor() - detector.getFocusY());
            scrollTo(newScrollX, newScrollY);

            invalidate();

            return true;
        }

        public boolean onScaleBegin(ScaleGestureDetector detector) {
            return true;
        }

        public void onScaleEnd(ScaleGestureDetector detector) {
        }
    }
}
