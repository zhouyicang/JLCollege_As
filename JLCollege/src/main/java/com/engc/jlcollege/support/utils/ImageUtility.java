package com.engc.jlcollege.support.utils;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.TypedValue;

/**
 * Created by Administrator on 14-2-26.
 * 图片、图像 相关操作帮助类
 */

public class ImageUtility {
    private ImageUtility(){

    }

    /**
     * 将dp单位转为像素px
     * @param dipValue
     * @return 返回值为int
     */
    public static int dip2px(int dipValue) {
        float reSize = GlobalContext.getInstance().getResources().getDisplayMetrics().density;
        return (int) ((dipValue * reSize) + 0.5);
    }

    /**
     *将像素px转换为dp
     * @param pxValue
     * @return返回值为int
     */
    public static int px2dip(int pxValue) {
        float reSize = GlobalContext.getInstance().getResources().getDisplayMetrics().density;
        return (int) ((pxValue / reSize) + 0.5);
    }

    /**
     *将sp 转化为像素
     * @param spValue
     * @return 返回值为int
     */
    public static float sp2px(int spValue) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue,
                GlobalContext.getInstance().getResources().getDisplayMetrics());
    }

    /**
     * 从URI 中获取图片的地址
     * @param uri
     * @param activity
     * @return
     */
    public static String getPicPathFromUri(Uri uri, Activity activity) {
        String value = uri.getPath();

        if (value.startsWith("/external")) {
            String[] proj = {MediaStore.Images.Media.DATA};
            Cursor cursor = activity.managedQuery(uri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else {
            return value;
        }
    }

    /**
     * 将图片圆角显示的函数,返回Bitmap
      */

    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        // 根据原来图片大小画一个矩形
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        // 圆角弧度参数,数值越大圆角越大,甚至可以画圆形
        final float roundPx = 12;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        // 画出一个圆角的矩形
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        // 取两层绘制交集,显示上层
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        // 显示图片
        canvas.drawBitmap(bitmap, rect, rect, paint);
        // 返回Bitmap对象
        return output;
    }

}
