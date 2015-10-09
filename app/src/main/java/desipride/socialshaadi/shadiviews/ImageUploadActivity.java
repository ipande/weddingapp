package desipride.socialshaadi.shadiviews;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;

import desipride.socialshaadi.R;

public class ImageUploadActivity extends ActionBarActivity implements View.OnClickListener{
    private static final String TAG = ImageUploadActivity.class.getSimpleName();
    ImageView imageToUpload;
    EditText caption;
    ImageView uploadButton;
    Bitmap imageBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_upload_activity);
        Intent intent = getIntent();
        Uri imageUri = (Uri)intent.getData();
        Log.d(TAG,"The image uri is " + imageUri.toString());
        imageToUpload = (ImageView)findViewById(R.id.image_to_upload);
        caption = (EditText)findViewById(R.id.caption);
        uploadButton = (ImageView)findViewById(R.id.upload_image_icon);

        uploadButton.setOnClickListener(this);

        try {
            imageBitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
        } catch (FileNotFoundException e) {
            Log.e(TAG,"",e);
        }
        if(imageBitmap != null) {
            imageToUpload.setImageBitmap(imageBitmap);
        }

    }


    @Override
    public void onClick(View v) {
        String captionString  = caption.getText().toString();

        ImageUploadTask task = new ImageUploadTask(this,imageBitmap,captionString);
        task.execute();
    }

    public class ImageUploadTask extends AsyncTask<Void, Void, Integer> {
        private static final int CONNECTION_TIMEOUT_MS = 5000;
        private static final String SAMPLE_FILE_NAME = "sample.jpg";
        private static final String IMAGE_UPLOAD_URL = "http://10.0.3.2:5000/upload";
        private Bitmap imageBitmap;
        private ProgressDialog progressDialog;
        private static final int RESULT_SUCCESS = 1;
        private static final int RESULT_FAILURE = 0;
        private static final int RESULT_ABORTED = 2;
        private static final String RESPONSE_SUCCESS = "{file_uploaded}";
        private HttpClient httpClient;
        private HttpPost postRequest;
        private boolean requestAborted = false;
        private String caption;


        public ImageUploadTask(Context context, Bitmap imageBitmap, String caption) {
            this.imageBitmap = imageBitmap;
            this.caption = caption;

            final HttpParams httpParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParams, CONNECTION_TIMEOUT_MS);
            httpClient = new DefaultHttpClient(httpParams);

            progressDialog = new ProgressDialog(context);
            progressDialog.setCancelable(true);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

                @Override
                public void onCancel(DialogInterface dialog) {
                    Log.d(TAG, "Request is cancelled");
                    if (postRequest.isAborted() == false) {
                        postRequest.abort();
                        requestAborted = true;
                    }

                }
            });
        }

        protected void onPreExecute() {
            Log.d(TAG, "Executing Task");
            this.progressDialog.setMessage("Uploading Image to Server...");
            this.progressDialog.show();
        }
        @Override
        protected Integer doInBackground(Void... params) {
            return uploadImage();
        }

        @Override
        protected void onPostExecute(Integer result) {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            Intent resultIntent = new Intent();
            if(result == RESULT_SUCCESS) {
                Log.d(TAG, "Image is uploaded, sending result back to actvity");
                setResult(Activity.RESULT_OK, resultIntent);
            } else if(result == RESULT_ABORTED) {
                Log.d(TAG, "Image could not be uploaded, Task Cancelled");
                setResult(NewsFeedFragment.TASK_ABORTED, resultIntent);
            } else {
                Log.d(TAG, "Image could not be uploaded, sending result back to actvity");
                setResult(NewsFeedFragment.CONNECTION_ERR, resultIntent);
            }

            finish();
        }

        private int uploadImage() {
            try {
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                imageBitmap.compress(Bitmap.CompressFormat.JPEG, 75, bos);
                byte[] data = bos.toByteArray();

                postRequest = new HttpPost(IMAGE_UPLOAD_URL);
                ByteArrayBody bab = new ByteArrayBody(data, SAMPLE_FILE_NAME);
                MultipartEntity reqEntity = new MultipartEntity(
                        HttpMultipartMode.BROWSER_COMPATIBLE);
                reqEntity.addPart("file", bab);
                reqEntity.addPart("filename",new StringBody(SAMPLE_FILE_NAME));
                reqEntity.addPart("caption", new StringBody(caption));
                postRequest.setEntity(reqEntity);
                HttpResponse response = httpClient.execute(postRequest);
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        response.getEntity().getContent(), "UTF-8"));
                String sResponse;
                StringBuilder s = new StringBuilder();

                while ((sResponse = reader.readLine()) != null) {
                    s = s.append(sResponse);
                }
                String responseData = s.toString();
                if(responseData.equals(RESPONSE_SUCCESS)) {
                    return RESULT_SUCCESS;
                } else {
                    return RESULT_FAILURE;
                }
            } catch (Exception e) {

                Log.e(e.getClass().getName(), e.getMessage());
                if(requestAborted) {
                    return RESULT_ABORTED;
                } else {
                    return RESULT_FAILURE;
                }

            }

        }

    }

}
