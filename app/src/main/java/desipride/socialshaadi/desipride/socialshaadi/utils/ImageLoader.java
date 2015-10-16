package desipride.socialshaadi.desipride.socialshaadi.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
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
    public static Bitmap loadImage(ImageView imageView, Uri imageUri, Context context,int reqHeight, int reqWidth)
    {
        Bitmap imageBitmap = null;
        InputStream imageInputStream = null;
        try {
            imageInputStream = context.getContentResolver().openInputStream(imageUri);
        } catch (FileNotFoundException e) {
            Log.e(TAG, "Image input stream is null", e);
            return null;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(imageInputStream,null,options);
        Log.d(TAG, "Dimentions of origina image are H:" + options.outHeight + " W:" + options.outWidth);
        options.inSampleSize = calculateInSampleSize(options,reqHeight,reqWidth);
        try {
            imageInputStream.close();
            imageInputStream = context.getContentResolver().openInputStream(imageUri);
        } catch (IOException e) {
            Log.e(TAG,"",e);
        }
        options.inJustDecodeBounds = false;
        imageBitmap = BitmapFactory.decodeStream(imageInputStream,null,options);
        if(imageBitmap != null) {
            imageView.setImageBitmap(imageBitmap);
        } else {
            Log.d(TAG,"Bitmap returned is null");
        }
        return imageBitmap;
    }

    private static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
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
}
