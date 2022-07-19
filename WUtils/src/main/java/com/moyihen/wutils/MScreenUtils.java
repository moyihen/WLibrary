package com.moyihen.wutils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.LruCache;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

/**
 * 创建日期：2021/4/25 11:38
 *
 * @author moyihen
 * 包名： com.moyihen.informationcollection.utils
 * 类说明：
 */
public class MScreenUtils {
    private static final String TAG = "MScreenUtils";

    /**
     * 获取控件截图（黑色背景）
     *
     * @param view view
     * @return Bitmap
     */
    public static Bitmap getViewBitmapNoBg(View view) {
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache(true);
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
        // clear drawing cache
        view.setDrawingCacheEnabled(false);
        return bitmap;
    }

    /**
     * @param view 需要截取图片的view（含有底色）
     * @return Bitmap
     */
    public static Bitmap getViewBitmap(Activity activity, View view) {
        View screenView = activity.getWindow().getDecorView();
        screenView.setDrawingCacheEnabled(true);
        screenView.buildDrawingCache();

        //获取屏幕整张图片
        Bitmap bitmap = screenView.getDrawingCache();

        if (bitmap != null) {
            //需要截取的长和宽
            int outWidth = view.getWidth();
            int outHeight = view.getHeight();

            //获取需要截图部分的在屏幕上的坐标(view的左上角坐标）
            int[] viewLocationArray = new int[2];
            view.getLocationOnScreen(viewLocationArray);

            //从屏幕整张图片中截取指定区域
            bitmap = Bitmap.createBitmap(bitmap, viewLocationArray[0], viewLocationArray[1], outWidth, outHeight);
        }


        /*Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);*/

        return bitmap;
    }


    /**------------------------------------------获取ViewGroup截图-------------------------------**/
    /**
     * @param viewGroup viewGroup
     * @return Bitmap
     */
    public static Bitmap getViewGroupBitmapNoBg(ViewGroup viewGroup) {
        // 创建对应大小的bitmap(重点)
        Bitmap bitmap = Bitmap.createBitmap(viewGroup.getWidth(), viewGroup.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        viewGroup.draw(canvas);
        return bitmap;
    }

    /**
     * @param viewGroup viewGroup
     * @return Bitmap
     */
    public static Bitmap getViewGroupBitmap(Activity activity, ViewGroup viewGroup) {
        View screenView = activity.getWindow().getDecorView();
        screenView.setDrawingCacheEnabled(true);
        screenView.buildDrawingCache();

        //获取屏幕整张图片
        Bitmap bitmap = screenView.getDrawingCache();

        if (bitmap != null) {
            //需要截取的长和宽
            int outWidth = viewGroup.getWidth();
            int outHeight = viewGroup.getHeight();

            //获取需要截图部分的在屏幕上的坐标(view的左上角坐标）
            int[] viewLocationArray = new int[2];
            viewGroup.getLocationOnScreen(viewLocationArray);

            //从屏幕整张图片中截取指定区域
            bitmap = Bitmap.createBitmap(bitmap, viewLocationArray[0], viewLocationArray[1], outWidth, outHeight);
        }
        return bitmap;
    }

    /**------------------------------------------获取Activity截图-------------------------------**/
    /**
     * 根据指定的Activity截图（带空白的状态栏）
     *
     * @param activity 要截图的Activity
     * @return Bitmap
     */
    public static Bitmap shotActivity(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache(), 0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.setDrawingCacheEnabled(false);
        view.destroyDrawingCache();
        return bitmap;
    }

    /**
     * 根据指定的Activity截图（去除状态栏）
     *
     * @param activity 要截图的Activity
     * @return Bitmap
     */
    public static Bitmap shotActivityNoStatusBar(Activity activity) {
        // 获取windows中最顶层的view
        View view = activity.getWindow().getDecorView();
        view.buildDrawingCache();
        // 获取状态栏高度
        Rect rect = new Rect();
        view.getWindowVisibleDisplayFrame(rect);
        int statusBarHeights = rect.top;

        DisplayMetrics displayMetrics = activity.getResources().getDisplayMetrics();
        // 获取屏幕长和高
        int widths = displayMetrics.widthPixels;
        int heights = displayMetrics.heightPixels;

//        Display display = activity.getWindowManager().getDefaultDisplay();
//        // 获取屏幕宽和高
//        int widths = display.getWidth();
//        int heights = display.getHeight();
        // 允许当前窗口保存缓存信息
        view.setDrawingCacheEnabled(true);
        // 去掉状态栏
        Bitmap bmp = Bitmap.createBitmap(view.getDrawingCache(), 0,
                statusBarHeights, widths, heights - statusBarHeights);
        // 销毁缓存信息
        view.destroyDrawingCache();
        return bmp;
    }


    /**
     * 截取scrollview的屏幕
     *
     * @param scrollView .
     * @return .
     */
    public static Bitmap getBitmapByView(NestedScrollView scrollView) {
        int h = 0;
        Bitmap bitmap = null;
        // 获取scrollview实际高度
        for (int i = 0; i < scrollView.getChildCount(); i++) {
            h += scrollView.getChildAt(i).getHeight();//获取scrollView的屏幕高度
            scrollView.getChildAt(i).setBackgroundColor(
                    Color.parseColor("#ffffff"));
        }
        //如果传的参数不是NestedScrollView，则不需要循环遍历高度
//        h += scrollView.getHeight();//获取scrollView的屏幕高度
//        scrollView.setBackgroundColor(
//                    Color.parseColor("#ffffff"));
        // 创建对应大小的bitmap
        bitmap = Bitmap.createBitmap(scrollView.getWidth(), h,
                Bitmap.Config.RGB_565);
        final Canvas canvas = new Canvas(bitmap);//把创建的bitmap放到画布中去
        scrollView.draw(canvas);//绘制canvas 画布
        return compressImage(bitmap);
    }

    /**
     * 压缩图片
     *
     * @param image .
     * @return .
     */
    public static Bitmap compressImage(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        image.compress(Bitmap.CompressFormat.JPEG, 80, baos);
        int options = 100;
        // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
        while (baos.toByteArray().length / 1024 > 300) {
            // 重置baos
            baos.reset();
            // 这里压缩options%，把压缩后的数据存放到baos中
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);
            // 每次都减少10
            options -= 10;
        }
        // 把压缩后的数据baos存放到ByteArrayInputStream中
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        // 把ByteArrayInputStream数据生成图片
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);
        return bitmap;
    }

    /**  recycleView 截图
     * https://gist.github.com/PrashamTrivedi/809d2541776c8c141d9a
     */
    public static Bitmap shotRecyclerView(RecyclerView view) {
        RecyclerView.Adapter adapter = view.getAdapter();
        Bitmap bigBitmap = null;
        if (adapter != null) {
            int size = adapter.getItemCount();
            int height = 0;
            Paint paint = new Paint();
            int iHeight = 0;
            final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

            // Use 1/8th of the available memory for this memory cache.
            final int cacheSize = maxMemory / 8;
            LruCache<String, Bitmap> bitmaCache = new LruCache<>(cacheSize);
            for (int i = 0; i < size; i++) {
                RecyclerView.ViewHolder holder = adapter.createViewHolder(view, adapter.getItemViewType(i));
                adapter.onBindViewHolder(holder, i);
                holder.itemView.measure(
                        View.MeasureSpec.makeMeasureSpec(view.getWidth(), View.MeasureSpec.EXACTLY),
                        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                holder.itemView.layout(0, 0, holder.itemView.getMeasuredWidth(),
                        holder.itemView.getMeasuredHeight());
                holder.itemView.setDrawingCacheEnabled(true);
                holder.itemView.buildDrawingCache();
                Bitmap drawingCache = holder.itemView.getDrawingCache();
                if (drawingCache != null) {

                    bitmaCache.put(String.valueOf(i), drawingCache);
                }
                height += holder.itemView.getMeasuredHeight();
            }

            bigBitmap = Bitmap.createBitmap(view.getMeasuredWidth(), height, Bitmap.Config.ARGB_8888);
            Canvas bigCanvas = new Canvas(bigBitmap);

            Drawable lBackground = view.getBackground();
            if (lBackground instanceof ColorDrawable) {
                ColorDrawable lColorDrawable = (ColorDrawable) lBackground;
                int lColor = lColorDrawable.getColor();
                bigCanvas.drawColor(lColor);
            }

            for (int i = 0; i < size; i++) {
                Bitmap bitmap = bitmaCache.get(String.valueOf(i));
                bigCanvas.drawBitmap(bitmap, 0f, iHeight, paint);
                iHeight += bitmap.getHeight();
                bitmap.recycle();
            }
        }
        return bigBitmap;
    }

    /**
     * 将bitmap集合上下拼接,纵向(多个)
     * @param bgColor #4088F0
     * @param bitmaps
     * @return
     */
    public static Bitmap drawMultiV(Context context ,String bgColor, ArrayList<Bitmap> bitmaps) {
        int width = bitmaps.get(0).getWidth();
        int height = bitmaps.get(0).getHeight();
        for (int i = 1;i<bitmaps.size();i++) {
            if (width < bitmaps.get(i).getWidth()) {
                width = bitmaps.get(i).getWidth();
            }
            height = height+bitmaps.get(i).getHeight()+100;
        }
        Bitmap result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);

        if (bgColor!=null && !bgColor.isEmpty())
            canvas.drawColor(Color.parseColor(bgColor));
        Paint paint = new Paint();
        paint.setDither(true);
        //设置文字对齐方式，取值：align.CENTER、align.LEFT或align.RIGHT
        //paint.setTextAlign(Paint.Align.CENTER);(会改变基线y轴坐标)
        //文字
        canvas.drawColor(Color.parseColor("#f5f5f5"));
        drawCenterText(context,canvas,paint,bitmaps.get(0));
        int h = 100;
        for (int j = 0;j<bitmaps.size();j++) {
            Log.i(TAG, "drawMultiV: ----------------------------------------------------h="+h);
            canvas.drawBitmap(bitmaps.get(j), 0,h, null);
            h = bitmaps.get(j).getHeight()+h;
        }

        return result;
    }

    /**
     * 绘制文字
     */
    private static void drawCenterText(Context context,Canvas canvas, Paint paint, Bitmap bitmap) {
        String str ="本招聘信息由折木鸟平台提供";
        int width = bitmap.getWidth();
        // 绘制居中文字
        paint.setTextSize(sptopx(context,15));
        paint.setColor(Color.GRAY);
        // 文字宽
        //int textWidth = (int)paint.measureText(str);
        Rect rect = new Rect();
        paint.getTextBounds(str,0,str.length(),rect);
        // 文字baseline在y轴方向的位置
        int textw = rect.right -rect.left;
        int texth = rect.bottom-rect.top;
        Log.i(TAG, "drawCenterText: texth:"+texth+"textw:"+textw+"bitmap.getWidth:"+bitmap.getWidth());

        canvas.drawText(str, (bitmap.getWidth()- textw)/2, 50+texth/2, paint);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @param
     *            （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int sptopx(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

}
