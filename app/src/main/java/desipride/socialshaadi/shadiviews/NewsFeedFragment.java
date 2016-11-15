package desipride.socialshaadi.shadiviews;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.parceler.Parcels;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import desipride.socialshaadi.BuildConfig;
import desipride.socialshaadi.R;
import desipride.socialshaadi.desipride.socialshaadi.utils.ConfigData;
import desipride.socialshaadi.shadidata.NewsFeedDataSource;
import desipride.socialshaadi.shadidata.NewsFeedItem;

import static desipride.socialshaadi.desipride.socialshaadi.utils.Constants.CONNECTION_ERR;
import static desipride.socialshaadi.desipride.socialshaadi.utils.Constants.CONNECTION_TIMEOUT_MS;
import static desipride.socialshaadi.desipride.socialshaadi.utils.Constants.GET_NEWSFEED_URL;
import static desipride.socialshaadi.desipride.socialshaadi.utils.Constants.HTTP_PREFIX;
import static desipride.socialshaadi.desipride.socialshaadi.utils.Constants.IMAGE_UPLOAD_CANCELLED;
import static desipride.socialshaadi.desipride.socialshaadi.utils.Constants.IMAGE_UPLOAD_FAILURE;
import static desipride.socialshaadi.desipride.socialshaadi.utils.Constants.IMAGE_UPLOAD_SUCCESS;
import static desipride.socialshaadi.desipride.socialshaadi.utils.Constants.NEWSFEED_NOT_REFRESHED;
import static desipride.socialshaadi.desipride.socialshaadi.utils.Constants.SELECT_FILE;
import static desipride.socialshaadi.desipride.socialshaadi.utils.Constants.TASK_ABORTED;
import static desipride.socialshaadi.desipride.socialshaadi.utils.Constants.UPLOAD_IMAGE;

/**
 * Created by parth.mehta on 10/4/15.
 */
public class NewsFeedFragment extends Fragment implements View.OnClickListener, View.OnLongClickListener{
    public static final String TAG = NewsFeedFragment.class.getSimpleName();

    ImageView uploadPictureButton;
    FirebaseImgAdapter newsFeedAdapter;
//    NewsFeedCursorAdapter newsFeedCursorAdapter;
    SwipeRefreshLayout newsFeedRefreshLayout;
    private RecyclerView recyclerNewsFeedView;
    private static final int CARD_MARGIN = 10;

    private DatabaseReference firebaseDB;
    private ChildEventListener mChildEventListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public void onStart() {
        super.onStart();
        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d(TAG, "onChildAdded:" + dataSnapshot.getKey() + "prevChildName: "+s);
                Log.d(TAG,"new image added: "+dataSnapshot.getValue());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Log.d(TAG, "onChildChanged:" + dataSnapshot.getKey() + "prevChildName: "+s);
                Log.d(TAG,"image changed at: "+dataSnapshot.getValue());
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onChildRemoved:" + dataSnapshot.getKey());
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        firebaseDB.addChildEventListener(childEventListener);
        mChildEventListener = childEventListener;
    }

    @Override
    public void onStop() {
        super.onStop();
        firebaseDB.removeEventListener(mChildEventListener);
        // Clean up comments listener
        newsFeedAdapter.cleanupListener();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_feed_frament, container, false);
        uploadPictureButton = (ImageView)view.findViewById(R.id.upload_image_button);
        uploadPictureButton.setOnClickListener(this);
        if(BuildConfig.DEBUG) {
            uploadPictureButton.setLongClickable(true);
            uploadPictureButton.setOnLongClickListener(this);
        }

        firebaseDB = FirebaseDatabase.getInstance().getReference("trial_firebase_objects");

        newsFeedRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.newsfeed_swipe_refresh_layout);

        recyclerNewsFeedView = (RecyclerView) view.findViewById(R.id.newsfeed);
        recyclerNewsFeedView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerNewsFeedView.setLayoutManager(llm);
        newsFeedAdapter = new FirebaseImgAdapter(getActivity(),firebaseDB);
        recyclerNewsFeedView.setAdapter(newsFeedAdapter);

//        NewsFeedDataSource.queryAllNewsFeedItems(getActivity());
//        newsFeedCursorAdapter = new NewsFeedCursorAdapter(getActivity(),
//                NewsFeedDataSource.queryAllNewsFeedItemsGetCursor(getActivity()));
//        recyclerNewsFeedView.setAdapter(newsFeedCursorAdapter);



        //TODO reenable refresh
//        refreshNewsFeed();
        //launchFirebaseUpload();



        newsFeedRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                refreshNewsFeed();
            }
        });
        return view;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.upload_image_button:
                openGallery(SELECT_FILE);
                break;
        }
    }


    public void openGallery(int req_code){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select file to upload "), req_code);
    }


    private void onImageSelected(Uri imageUri) {
        Intent i = new Intent(getActivity(), ImageUploadActivity.class);
        i.setData(imageUri);
        startActivityForResult(i, UPLOAD_IMAGE);
    }

    // So that all clients can refreshNewsfeed
    private void updateFirebaseDB(String imgName, NewsFeedItem newlyAddedItem){
        firebaseDB.push().setValue(newlyAddedItem, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if(databaseError == null){
                    Log.d(TAG,"Written to Firebase DB successfully");
                }
                else
                    Log.e(TAG,"Writing to Firebase DB failed because: "+databaseError.getMessage());
            }
        });
    }


    public void onActivityResult(int requestCode, int resultCode, Intent imageSelectedIntent) {
        Log.d(TAG, "Activity Result obtained ");

        switch(requestCode) {
            case SELECT_FILE:
                if(resultCode == Activity.RESULT_OK) {
                    Log.d(TAG,"Calling On Image Selected on news feed fragment");
                    onImageSelected(imageSelectedIntent.getData());
                } else {
                    Log.d(TAG,"No Image Selected");
                }

                break;
            case UPLOAD_IMAGE:
                if(resultCode == Activity.RESULT_OK) {
                    Log.d(TAG, "Image Uploaded Successfully");
                    Toast.makeText(getActivity(), IMAGE_UPLOAD_SUCCESS, Toast.LENGTH_SHORT).show();
                    String imgadded = imageSelectedIntent.getStringExtra("img_added");
                    Log.d(TAG,"This image was recently added: "+imgadded);
                    NewsFeedItem newlyAddedItem = (NewsFeedItem) Parcels.unwrap(imageSelectedIntent.getParcelableExtra("NewsfeedItem"));
                    Log.d(TAG,"Testing parceler: caption: "+newlyAddedItem.getCaption() + "img dim: " + newlyAddedItem.getDimentions());
                    updateFirebaseDB(imgadded, newlyAddedItem);
//                    refreshNewsFeed();
                } else if(resultCode == Activity.RESULT_CANCELED) {
                    Log.d(TAG, "Result Cancelled, image not uploaded");
                } else if(resultCode == TASK_ABORTED) {
                    Log.d(TAG, "Task Aborted");
                    Toast.makeText(getActivity(), IMAGE_UPLOAD_CANCELLED, Toast.LENGTH_LONG).show();
                } else if(resultCode ==CONNECTION_ERR) {
                    Log.d(TAG, "Connection Error, image could not be uploaded");
                    Toast.makeText(getActivity(), IMAGE_UPLOAD_FAILURE, Toast.LENGTH_LONG).show();
                }
                break;
            default:
                Log.d(TAG,"Unknown request code");
                break;
        }
    }
    private boolean isFragmentActive() {
        return isAdded() && !isDetached() && !isRemoving();
    }

    private void refreshNewsFeed() {
        newsFeedRefreshLayout.setRefreshing(true);
        RefreshNewsFeedAsyncTask task = new RefreshNewsFeedAsyncTask(getActivity());
        Log.d(TAG,"Refreshing news feed");
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    @Override
    public boolean onLongClick(View v) {
        // get prompts.xml view
        LayoutInflater li = LayoutInflater.from(getActivity());
        View promptsView = li.inflate(R.layout.server_address_dialog, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                getActivity());

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final EditText userInput = (EditText) promptsView
                .findViewById(R.id.editTextDialogUserInput);

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // get user input and set it to result
                                // edit text
                                ConfigData.setServerHostname(userInput.getText().toString(), getActivity());
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();

        return true;
    }






    private class RefreshNewsFeedAsyncTask extends AsyncTask<Void,Void,Integer> {
        Gson gson;
        Context context;
        Cursor cursor;
        private final static int SUCCESS = 0;
        private final static int ERROR = -1;
        public RefreshNewsFeedAsyncTask(Context context) {
            gson = new Gson();
            this.context = context;
        }


        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Integer doInBackground(Void... params) {
            String responseData = null;
            try {
                final HttpParams httpParams = new BasicHttpParams();
                HttpConnectionParams.setConnectionTimeout(httpParams, CONNECTION_TIMEOUT_MS);
                HttpClient httpclient = new DefaultHttpClient(httpParams);
                HttpResponse response = httpclient.execute(new HttpGet(HTTP_PREFIX + ConfigData.getServerHostname(context) + GET_NEWSFEED_URL));
                BufferedReader reader = null;


                reader = new BufferedReader(new InputStreamReader(
                        response.getEntity().getContent(), "UTF-8"));

                String sResponse;
                StringBuilder s = new StringBuilder();

                while ((sResponse = reader.readLine()) != null) {
                    s = s.append(sResponse);
                }
                responseData = s.toString();
                Log.v(TAG,responseData);
            } catch (IOException e) {
                Log.e(TAG,"",e);
                return ERROR;
            }

            NewsFeedItem items[];
            try {
                items = gson.fromJson(responseData, NewsFeedItem[].class);
            } catch(com.google.gson.JsonSyntaxException e) {
                Log.e(TAG,"Invalid Response" ,e);
                return ERROR;
            }
            Log.d(TAG,"Received " + items.length + " items in response");

            for(NewsFeedItem item : items) {
                Log.v(TAG,"Adding Item to database: " + item);
                NewsFeedDataSource.insertNewsFeedItem(item,context);
            }

            cursor = NewsFeedDataSource.queryAllNewsFeedItemsGetCursor(context);


            return SUCCESS;
        }

        @Override
        protected void onPostExecute(Integer result) {
            if(result == SUCCESS) {
//                newsFeedCursorAdapter.changeCursor(cursor);
            } else {
                if(isFragmentActive()) {
                    Log.d(TAG,"Could not refresh newsfeed toast");
                    Toast.makeText(getActivity(),NEWSFEED_NOT_REFRESHED,Toast.LENGTH_SHORT).show();
                }

            }
            newsFeedRefreshLayout.setRefreshing(false);
        }

    }
}