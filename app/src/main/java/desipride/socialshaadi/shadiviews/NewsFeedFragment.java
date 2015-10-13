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
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import desipride.socialshaadi.R;
import desipride.socialshaadi.desipride.socialshaadi.utils.ConfigData;
import desipride.socialshaadi.desipride.socialshaadi.utils.CursorRecyclerViewAdapter;
import desipride.socialshaadi.shadidata.NewsFeedDataSource;
import desipride.socialshaadi.shadidata.NewsFeedItem;

/**
 * Created by parth.mehta on 10/4/15.
 */
public class NewsFeedFragment extends Fragment implements View.OnClickListener, View.OnLongClickListener{
    public static final String TAG = NewsFeedFragment.class.getSimpleName();
    public static final int SELECT_FILE = 1;
    public static final int UPLOAD_IMAGE = 2;
    public static final int CONNECTION_ERR = 35;
    public static final int TASK_ABORTED = 36;
    public static final String IMAGE_UPLOAD_SUCCESS = "Picture uploaded";
    public static final String IMAGE_UPLOAD_FAILURE = "Connection Error:Picture could not be uploaded";
    public static final String IMAGE_UPLOAD_CANCELLED = "Picture upload cancelled";
    public static final String NEWSFEED_NOT_REFRESHED = "Newsfeed could not be refreshed";

    ImageView uploadPictureButton;
    NewsFeedCursorAdapter newsFeedCursorAdapter;
    SwipeRefreshLayout newsFeedRefreshLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_feed_frament, container, false);
        uploadPictureButton = (ImageView)view.findViewById(R.id.upload_image_button);
        uploadPictureButton.setOnClickListener(this);
        uploadPictureButton.setLongClickable(true);
        uploadPictureButton.setOnLongClickListener(this);
        newsFeedRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.newsfeed_swipe_refresh_layout);

        RecyclerView recyclerNewsFeedView = (RecyclerView) view.findViewById(R.id.newsfeed);
        recyclerNewsFeedView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerNewsFeedView.setLayoutManager(llm);

        NewsFeedDataSource.queryAllNewsFeedItems(getActivity());
        newsFeedCursorAdapter = new NewsFeedCursorAdapter(getActivity(),
                NewsFeedDataSource.queryAllNewsFeedItemsGetCursor(getActivity()));
        recyclerNewsFeedView.setAdapter(newsFeedCursorAdapter);
        refreshNewsFeed();

        newsFeedRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshNewsFeed();
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
                    refreshNewsFeed();
                } else if(resultCode == Activity.RESULT_CANCELED) {
                    Log.d(TAG, "Result Cancelled, image not uploaded");
                } else if(resultCode == NewsFeedFragment.TASK_ABORTED) {
                    Log.d(TAG, "Task Aborted");
                    Toast.makeText(getActivity(), IMAGE_UPLOAD_CANCELLED, Toast.LENGTH_LONG).show();
                } else if(resultCode == NewsFeedFragment.CONNECTION_ERR) {
                    Log.d(TAG, "Connection Error, image could not be uploaded");
                    Toast.makeText(getActivity(), IMAGE_UPLOAD_FAILURE, Toast.LENGTH_LONG).show();
                }
                break;
            default:
                Log.d(TAG,"Unknown request code");
                break;
        }
    }


    private void refreshNewsFeed() {
        newsFeedRefreshLayout.setRefreshing(true);
        RefreshNewsFeedAsyncTask task = new RefreshNewsFeedAsyncTask(getActivity());
        Log.d(TAG,"Refreshing news feed");
        task.execute();
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
        private static final int CONNECTION_TIMEOUT_MS = 5000;
        Gson gson;
        Context context;
        Cursor cursor;
        private final static int SUCCESS = 0;
        private final static int ERROR = -1;
        public RefreshNewsFeedAsyncTask(Context context) {
            gson = new Gson();
            this.context = context;
        }

        private static final String HTTP_PREFIX = "http://";
        private static final String GET_NEWSFEED_URL = "/newsfeed";
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
                Log.d(TAG,responseData);
            } catch (IOException e) {
                Log.e(TAG,"",e);
                return ERROR;
            }

            NewsFeedItem items[] = gson.fromJson(responseData, NewsFeedItem[].class);

            for(NewsFeedItem item : items) {
                Log.d(TAG,"Adding Item to database: " + item);
                NewsFeedDataSource.insertNewsFeedItem(item,context);
            }

            cursor = NewsFeedDataSource.queryAllNewsFeedItemsGetCursor(context);


            return SUCCESS;
        }

        @Override
        protected void onPostExecute(Integer result) {
            if(result == SUCCESS) {
                newsFeedCursorAdapter.changeCursor(cursor);
            } else {
                Toast.makeText(getActivity(),NEWSFEED_NOT_REFRESHED,Toast.LENGTH_SHORT).show();
            }
            newsFeedRefreshLayout.setRefreshing(false);
        }

    }

    private static class NewsFeedViewHolder extends RecyclerView.ViewHolder {
        protected TextView caption;
        protected ImageView image;

        public NewsFeedViewHolder(View v) {
            super(v);
            caption = (TextView)v.findViewById(R.id.caption);
            image = (ImageView)v.findViewById(R.id.newsfeed_image);
        }
    }

    private static class NewsFeedCursorAdapter extends CursorRecyclerViewAdapter<NewsFeedViewHolder> {

        Context context;

        public NewsFeedCursorAdapter(Context context, Cursor cursor) {
            super(context, cursor);
            this.context = context;
        }

        @Override
        public void onBindViewHolder(NewsFeedViewHolder newsFeedViewHolder, Cursor cursor) {
            NewsFeedItem newsFeedItem = NewsFeedDataSource.cursorToNewsFeedItem(cursor);
            newsFeedViewHolder.caption.setText(newsFeedItem.getCaption());
            Picasso.with(context)
                    .load(newsFeedItem.getUrl())
                    .into(newsFeedViewHolder.image);

        }

        @Override
        public NewsFeedViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View itemView = LayoutInflater.
                    from(viewGroup.getContext()).
                    inflate(R.layout.news_feed_card, viewGroup, false);

            return new NewsFeedViewHolder(itemView);
        }
    }

    private static class NewsFeedAdapter extends RecyclerView.Adapter<NewsFeedViewHolder> {

        private List<NewsFeedItem> newsFeedItems;
        private Context context;

        public NewsFeedAdapter(List<NewsFeedItem> newsFeedItems, Context context) {
            this.newsFeedItems = newsFeedItems;
            this.context = context;
        }

        public void addNewsFeedItem(NewsFeedItem newsFeedItem) {
            newsFeedItems.add(newsFeedItem);
            notifyItemInserted(newsFeedItems.size()-1);
        }

        @Override
        public NewsFeedViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View itemView = LayoutInflater.
                    from(viewGroup.getContext()).
                    inflate(R.layout.news_feed_card, viewGroup, false);

            return new NewsFeedViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(NewsFeedViewHolder newsFeedViewHolder, int i) {
            NewsFeedItem newsFeedItem = newsFeedItems.get(i);
            newsFeedViewHolder.caption.setText(newsFeedItem.getCaption());
            Picasso.with(context)
                    .load(newsFeedItem.getUrl())
                    .into(newsFeedViewHolder.image);

        }

        @Override
        public int getItemCount() {
            return newsFeedItems.size();
        }
    }

}