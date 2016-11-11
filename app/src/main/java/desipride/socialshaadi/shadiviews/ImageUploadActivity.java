package desipride.socialshaadi.shadiviews;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

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
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;

import desipride.socialshaadi.R;
import desipride.socialshaadi.desipride.socialshaadi.utils.ConfigData;
import desipride.socialshaadi.desipride.socialshaadi.utils.ImageLoader;

import static desipride.socialshaadi.desipride.socialshaadi.utils.Constants.CONNECTION_ERR;
import static desipride.socialshaadi.desipride.socialshaadi.utils.Constants.CONNECTION_TIMEOUT_MS;
import static desipride.socialshaadi.desipride.socialshaadi.utils.Constants.HTTP_PREFIX;
import static desipride.socialshaadi.desipride.socialshaadi.utils.Constants.IMAGE_UPLOAD_URL;
import static desipride.socialshaadi.desipride.socialshaadi.utils.Constants.RESPONSE_SUCCESS;
import static desipride.socialshaadi.desipride.socialshaadi.utils.Constants.SAMPLE_FILE_NAME;
import static desipride.socialshaadi.desipride.socialshaadi.utils.Constants.TASK_ABORTED;
import static desipride.socialshaadi.desipride.socialshaadi.utils.Constants.UPLOAD_RESULT_ABORTED;
import static desipride.socialshaadi.desipride.socialshaadi.utils.Constants.UPLOAD_RESULT_FAILURE;
import static desipride.socialshaadi.desipride.socialshaadi.utils.Constants.UPLOAD_RESULT_SUCCESS;


public class ImageUploadActivity extends ActionBarActivity implements View.OnClickListener,ViewTreeObserver.OnGlobalLayoutListener{
    private static final String TAG = ImageUploadActivity.class.getSimpleName();
    ImageView imageToUpload;
    EditText caption;
    ImageView uploadButton;
    Bitmap imageBitmap;
    Uri imageUri;
    RelativeLayout relativeLayout;
    private StorageReference storageRef;
    private StorageReference imgRef;
    private ProgressBar pb;
    private static Long currTime;
    private String picURL;
    private static final String STORAGE_URL = "trial_images";
    private static final String SEPARATOR = "/";
    private static final String CURRENT_PIC = "currentpic_";
    private static final String JPEG_EXT = ".jpeg";
    private static final String CAPTION = "caption";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_upload_activity);
        Intent intent = getIntent();
        imageUri = (Uri)intent.getData();
        Log.d(TAG,"The image uri is " + imageUri.toString());
        imageToUpload = (ImageView)findViewById(R.id.image_to_upload);
        caption = (EditText)findViewById(R.id.caption);
        uploadButton = (ImageView)findViewById(R.id.upload_image_icon);
        uploadButton.setOnClickListener(this);

        pb = (ProgressBar) findViewById(R.id.pbUploading);
        relativeLayout = (RelativeLayout)findViewById(R.id.image_upload_activity_layout);

        ViewTreeObserver vto = relativeLayout.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(this);
        currTime = System.currentTimeMillis();

        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageRef = storage.getReferenceFromUrl("gs://imugweddingapp.appspot.com");
        picURL = STORAGE_URL + SEPARATOR + CURRENT_PIC + currTime + JPEG_EXT;
        imgRef = storageRef.child(picURL);

    }

    private void uploadToFirebase(Bitmap bitmap, final String caption) {
        if(bitmap!=null){
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
            byte[] data = baos.toByteArray();
            final Intent resultIntent = new Intent();
            if(imgRef !=null) {
                UploadTask uploadTask = imgRef.putBytes(data);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        Log.e(TAG, "File upload unsuccessful because: " + exception.getMessage());
                        Log.d(TAG, "Image could not be uploaded, sending result back to actvity");
                        pb.setVisibility(ProgressBar.INVISIBLE);
                        setResult(CONNECTION_ERR, resultIntent);
                        finish();
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        Log.d(TAG,"Image uploaded successfully");
                        pb.setVisibility(ProgressBar.INVISIBLE);
                        setResult(Activity.RESULT_OK, resultIntent);
                        StorageMetadata updateMetadata = new StorageMetadata.Builder()
                                                        .setCustomMetadata(CAPTION,caption)
                                                        .build();
                        // Update metadata properties
                        imgRef.updateMetadata(updateMetadata)
                                .addOnSuccessListener(new OnSuccessListener<StorageMetadata>() {
                                    @Override
                                    public void onSuccess(StorageMetadata storageMetadata) {
                                        // Updated metadata is in storageMetadata
                                        Log.d(TAG,"Updated image caption");
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {
                                        // Uh-oh, an error occurred!
                                        Log.e(TAG,"Could not update caption because: "+exception);
                                    }
                                });
                        resultIntent.putExtra("img_added", picURL);
                        finish();

                    }
                });
            } else {
                Log.e(TAG,"User not auth into firebase");
                Log.d(TAG, "Image could not be uploaded, sending result back to actvity");
                pb.setVisibility(ProgressBar.INVISIBLE);
                setResult(CONNECTION_ERR, resultIntent);
                finish();
            }
        }
    }




    @Override
    public void onClick(View v) {
        String captionString  = caption.getText().toString();

//        ImageUploadTask task = new ImageUploadTask(this,imageBitmap,captionString);
//        task.execute();
        pb.setVisibility(ProgressBar.VISIBLE);
        uploadToFirebase(imageBitmap, captionString);
    }


    @Override
    public void onGlobalLayout() {
        relativeLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
        int width = relativeLayout.getWidth();
        int height = relativeLayout.getHeight();
        Log.d(TAG, "onGlobalLayout W:" + width + ", H:" + height);
        imageBitmap = ImageLoader.loadImage(imageToUpload, imageUri, this.getContentResolver(), 0, width);

    }


    public class ImageUploadTask extends AsyncTask<Void, Void, Integer> {

        private Bitmap imageBitmap;
        private ProgressDialog progressDialog;

        private HttpClient httpClient;
        private HttpPost postRequest;
        private boolean requestAborted = false;
        private String caption;
        private Context context;


        public ImageUploadTask(Context context, Bitmap imageBitmap, String caption) {
            this.imageBitmap = imageBitmap;
            this.caption = caption;
            this.context = context;

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
            if(result == UPLOAD_RESULT_SUCCESS) {
                Log.d(TAG, "Image is uploaded, sending result back to actvity");
                setResult(Activity.RESULT_OK, resultIntent);
            } else if(result == UPLOAD_RESULT_ABORTED) {
                Log.d(TAG, "Image could not be uploaded, Task Cancelled");
                setResult(TASK_ABORTED, resultIntent);
            } else {
                Log.d(TAG, "Image could not be uploaded, sending result back to actvity");
                setResult(CONNECTION_ERR, resultIntent);
            }

            finish();
        }

        private int uploadImage() {
            try {
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                imageBitmap.compress(Bitmap.CompressFormat.JPEG, 50, bos);
                Log.d(TAG, "Image dimentions after compressing h x w:" + imageBitmap.getHeight() + " x " + imageBitmap.getWidth());
                String dimentions = getJsonDimentions(imageBitmap.getWidth(),imageBitmap.getHeight());

                byte[] data = bos.toByteArray();

                postRequest = new HttpPost(HTTP_PREFIX+ ConfigData.getServerHostname(context)+IMAGE_UPLOAD_URL);
                ByteArrayBody bab = new ByteArrayBody(data, SAMPLE_FILE_NAME);
                MultipartEntity reqEntity = new MultipartEntity(
                        HttpMultipartMode.BROWSER_COMPATIBLE);
                reqEntity.addPart("file", bab);
                reqEntity.addPart("filename",new StringBody(SAMPLE_FILE_NAME));
                reqEntity.addPart("caption", new StringBody(caption));
                reqEntity.addPart("mediatype",new StringBody(dimentions));
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
                Log.d(TAG,"Response from server is " + responseData);
                if(responseData.equals(RESPONSE_SUCCESS)) {
                    return UPLOAD_RESULT_SUCCESS;
                } else {
                    return UPLOAD_RESULT_FAILURE;
                }
            } catch (Exception e) {

                Log.e(e.getClass().getName(), e.getMessage());
                if(requestAborted) {
                    Log.d(TAG,"Image Upload Aborted");
                    return UPLOAD_RESULT_ABORTED;
                } else {
                    Log.d(TAG,"Image Upload Failure");
                    return UPLOAD_RESULT_FAILURE;
                }

            }

        }

    }

    private String getJsonDimentions(int width, int height) {
        JSONObject imageDimentions = new JSONObject();
        try {
            imageDimentions.put("height",height);
            imageDimentions.put("width",width);
        } catch (JSONException e) {
            Log.e(TAG,"" + e);

        }
        return imageDimentions.toString();
    }

}
