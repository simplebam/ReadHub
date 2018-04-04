package com.yueyue.readhub.common.utils;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.blankj.utilcode.util.CloseUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.yueyue.readhub.R;
import com.yueyue.readhub.component.PLog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * author : yueyue on 2018/4/4 17:53
 * desc   :
 */
public class BitmapUtil {
    private static final String TAG = BitmapUtil.class.getSimpleName();

    public static boolean saveViewAsImage(View view, String fileName) {
        Bitmap b = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        view.layout(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
        view.draw(c);
        return saveImage(view.getContext(), b, fileName);
    }

    public static boolean saveImage(Context context, Bitmap bmp, String fileName) {
        if (!(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                && (ContextCompat.checkSelfPermission(context, Manifest.permission_group.STORAGE)
                != PackageManager.PERMISSION_GRANTED))) {
            ToastUtils.showShort(R.string.open_storage_permission_in_setting);
            return false;
        }
        File parentDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File fileDir = new File(parentDir, "hubread");
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        File file = new File(fileDir, fileName + ".jpg");
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file));
            context.sendBroadcast(intent);
            return true;
        } catch (IOException e) {
            PLog.e(TAG, "saveImage: " + e.toString());
            e.printStackTrace();
        } finally {
            CloseUtils.closeIOQuietly(fos);
        }
        return false;
    }
}

