package desipride.socialshaadi.shadiviews;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import desipride.socialshaadi.R;
import desipride.socialshaadi.desipride.socialshaadi.utils.CursorRecyclerViewAdapter;
import desipride.socialshaadi.shadidata.NewsFeedDataSource;
import desipride.socialshaadi.shadidata.NewsFeedItem;

/**
 * Created by parth.mehta on 10/4/15.
 */
public class NewsFeedFragment extends Fragment implements View.OnClickListener{
    public static final String TAG = NewsFeedFragment.class.getSimpleName();
    public static final int SELECT_FILE = 1;
    public static final int UPLOAD_IMAGE = 2;
    public static final int CONNECTION_ERR = 35;
    public static final int TASK_ABORTED = 36;
    public static final String IMAGE_UPLOAD_SUCCESS = "Picture uploaded";
    public static final String IMAGE_UPLOAD_FAILURE = "Connection Error:Picture could not be uploaded";
    public static final String IMAGE_UPLOAD_CANCELLED = "Picture upload cancelled";

    ImageView uploadPictureButton;
    Button refreshButton;
    NewsFeedAdapter newsFeedAdapter;
    NewsFeedCursorAdapter newsFeedCursorAdapter;
    ProgressBar loadingIcon;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_feed_frament, container, false);
        refreshButton = (Button)view.findViewById(R.id.refresh_button);
        refreshButton.setOnClickListener(this);
        uploadPictureButton = (ImageView)view.findViewById(R.id.upload_image_button);
        uploadPictureButton.setOnClickListener(this);
        loadingIcon = (ProgressBar)view.findViewById(R.id.loading_icon);

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
        return view;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.upload_image_button:
                openGallery(SELECT_FILE);
                break;
            case R.id.refresh_button:
                Log.d(TAG,"Refresh my news Feed Boy!!");
                refreshNewsFeed();
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
        Intent i = new Intent(getActivity(),ImageUploadActivity.class);
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

    private void setLoadingIconVisibility(boolean visibility) {
        if(visibility) {
            loadingIcon.setVisibility(View.VISIBLE);
        } else {
            loadingIcon.setVisibility(View.GONE);
        }
    }

    private void refreshNewsFeed() {
        RefreshNewsFeedAsyncTask task = new RefreshNewsFeedAsyncTask(getActivity());
        Log.d(TAG,"Refreshing news feed");
        task.execute();
    }

    private class RefreshNewsFeedAsyncTask extends AsyncTask<Void,Void,Integer> {
        Gson gson;
        Context context;
        Cursor cursor;
        public RefreshNewsFeedAsyncTask(Context context) {
            gson = new Gson();
            this.context = context;
        }

        private static final String GET_NEWSFEED_URL = "http://10.0.3.2:5000/newsfeed";
        @Override
        protected void onPreExecute() {
            setLoadingIconVisibility(true);
        }

        @Override
        protected Integer doInBackground(Void... params) {
            String responseData = null;
            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpResponse response = httpclient.execute(new HttpGet(GET_NEWSFEED_URL));
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
                e.printStackTrace();
                return null;
            }

            NewsFeedItem items[] = gson.fromJson(responseData, NewsFeedItem[].class);
            for(NewsFeedItem item : items) {
                Log.d(TAG,"Adding Item to database: " + item);
                NewsFeedDataSource.insertNewsFeedItem(item,context);
            }

            cursor = NewsFeedDataSource.queryAllNewsFeedItemsGetCursor(context);


            return null;
        }

        @Override
        protected void onPostExecute(Integer result) {
            newsFeedCursorAdapter.changeCursor(cursor);
            setLoadingIconVisibility(false);
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