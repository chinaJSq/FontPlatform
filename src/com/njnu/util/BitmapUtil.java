package com.njnu.util;

import java.io.IOException;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;

/**
 */
public class BitmapUtil {
	
	/**
	 * ��ȡͼƬ�ļ�����Ϣ���Ƿ���ת��90�ȣ��������ת
	 * @param bitmap ��Ҫ��ת��ͼƬ
	 * @param path   ͼƬ��·��
	 */
	public static Bitmap reviewPicRotate(Bitmap bitmap,String path){
		int degree = getPicRotate(path);
		if(degree!=0){
			Matrix m = new Matrix();  
			int width = bitmap.getWidth();  
			int height = bitmap.getHeight();  
			m.setRotate(degree); // ��תangle��  
			bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height,m, true);// ��������ͼƬ  
		}
		return bitmap;
	}
	
	/**
	 * ��ȡͼƬ�ļ���ת�ĽǶ�
	 * @param path ͼƬ����·��
	 * @return ͼƬ��ת�ĽǶ�
	 */
	public static int getPicRotate(String path) {
		int degree  = 0;
		try {
			ExifInterface exifInterface = new ExifInterface(path);
			int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
			switch (orientation) {
			case ExifInterface.ORIENTATION_ROTATE_90:
				degree = 90;
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				degree = 180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_270:
				degree = 270;
				break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return degree;
	}
	
	
	private static int calculateInSampleSize(BitmapFactory.Options options,
            int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    // ����ǷŴ�ͼƬ��filter�����Ƿ�ƽ�����������СͼƬ��filter��Ӱ��
    private static Bitmap createScaleBitmap(Bitmap src, int dstWidth,
            int dstHeight) {
        Bitmap dst = Bitmap.createScaledBitmap(src, dstWidth, dstHeight, false);
        if (src != dst) { // ���û�����ţ���ô������
            src.recycle(); // �ͷ�Bitmap��native��������
        }
        return dst;
    }

    // ��Resources�м���ͼƬ
    public static Bitmap decodeSampledBitmapFromResource(Resources res,
            int resId, int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options); // ��ȡͼƬ����
        options.inSampleSize = calculateInSampleSize(options, reqWidth,
                reqHeight); // ����inSampleSize
        options.inJustDecodeBounds = false;
        Bitmap src = BitmapFactory.decodeResource(res, resId, options); // ����һ���Դ������ͼ
        return createScaleBitmap(src, reqWidth, reqHeight); // ��һ���õ�Ŀ���С������ͼ
    }

    // ��sd���ϼ���ͼƬ
    public static Bitmap decodeSampledBitmapFromFd(String pathName,
            int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(pathName, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        Bitmap src = BitmapFactory.decodeFile(pathName, options);
        return createScaleBitmap(src, reqWidth, reqHeight);
    }

}
