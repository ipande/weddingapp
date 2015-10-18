package desipride.socialshaadi.desipride.socialshaadi.utils;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by parth.mehta on 10/15/15.
 */
public class ImageLoader {
    private static final String TAG = ImageLoader.class.getSimpleName();
    private static final int ROTATION_90 = 90;
    private static final int ROTATION_270 = 270;

    public static Bitmap loadImage(ImageView imageView, Uri imageUri, Context context,int reqHeight, int reqWidth)
    {
        Bitmap imageBitmap = null;
        int imageOrientation;
        InputStream imageInputStream = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        try {
            imageInputStream = context.getContentResolver().openInputStream(imageUri);
            BitmapFactory.decodeStream(imageInputStream, null, options);
            imageInputStream.close();
        } catch (FileNotFoundException e) {
            Log.e(TAG, "Image input stream is null", e);
            return null;
        } catch (IOException e) {
            Log.e(TAG, "Could not close input stream", e);
            return null;
        }

        int rotatedWidth, rotatedHeight;
        imageOrientation = getOrientation(context, imageUri);

        if (imageOrientation == ROTATION_90 || imageOrientation == ROTATION_270) {
            rotatedWidth = options.outHeight;
            rotatedHeight = options.outWidth;
        } else {
            rotatedWidth = options.outWidth;
            rotatedHeight = options.outHeight;
        }

        options.inSampleSize = calculateInSampleSize(rotatedHeight,rotatedWidth,reqHeight,reqWidth);

        Log.d(TAG, "Dimentions of original image are H:" + rotatedHeight + " W:" + rotatedWidth + " insample size:" +options.inSampleSize);
        try {
            imageInputStream = context.getContentResolver().openInputStream(imageUri);
            options.inJustDecodeBounds = false;
            imageBitmap = BitmapFactory.decodeStream(imageInputStream, null, options);
            imageInputStream.close();
        } catch (IOException e) {
            Log.e(TAG,"",e);
        }

        if (imageOrientation > 0) {
            Matrix matrix = new Matrix();
            matrix.postRotate(imageOrientation);

            imageBitmap = Bitmap.createBitmap(imageBitmap, 0, 0, imageBitmap.getWidth(),
                    imageBitmap.getHeight(), matrix, true);
        }


        Log.d(TAG,"Dimentions of displayed image are H:" + imageBitmap.getHeight() + " W:" + imageBitmap.getWidth());
        if(imageBitmap != null) {
            imageView.setImageBitmap(imageBitmap);
        } else {
            Log.d(TAG,"Bitmap returned is null");
        }
        return imageBitmap;
    }


    private static int calculateInSampleSize(
            int imageHeight, int imageWidth, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = imageHeight;
        final int width = imageWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public static int getOrientation(Context context, Uri photoUri) {
    /* it's on the external media. */
        Cursor cursor = context.getContentResolver().query(photoUri,
                new String[] { MediaStore.Images.ImageColumns.ORIENTATION }, null, null, null);

        if (cursor.getCount() != 1) {
            return -1;
        }

        cursor.moveToFirst();
        int orientation = cursor.getInt(0);
        cursor.close();
        return orientation;
    }
}
