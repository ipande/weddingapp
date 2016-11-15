package desipride.socialshaadi.shadiviews;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import desipride.socialshaadi.R;
import desipride.socialshaadi.desipride.socialshaadi.utils.DeviceDimensionsHelper;
import desipride.socialshaadi.shadidata.NewsFeedItem;

import static desipride.socialshaadi.R.id.imageView;

/**
 * Created by ishanpande on 11/14/16.
 */

public class FirebaseImgAdapter extends RecyclerView.Adapter<FirebaseImgAdapter.NewsFeedViewHolder> {
    public static final String TAG = FirebaseImgAdapter.class.getSimpleName();

    private Context mContext;
    private DatabaseReference mDatabaseReference;
    private ChildEventListener mChildEventListener;
    private List<NewsFeedItem> mNewsfeedItems = new ArrayList<>();
    int targetImageWidth;
    FirebaseStorage storage;
    StorageReference storageRef;
    private static final int CARD_MARGIN = 10;

    public static class NewsFeedViewHolder extends RecyclerView.ViewHolder{
        protected TextView caption;
        protected ImageView image;
        protected View view;

        public String getImageURI() {
            return imageURI;
        }

        public void setImageURI(String imageURI) {
            this.imageURI = imageURI;
        }

        protected String imageURI;

        public NewsFeedViewHolder(View v) {
            super(v);
            caption = (TextView)v.findViewById(R.id.caption);
            image = (ImageView)v.findViewById(R.id.newsfeed_image);
            view = v;
        }
    }



    public FirebaseImgAdapter(final Context context, DatabaseReference ref){
        mContext = context;
        mDatabaseReference = ref;


        storage = FirebaseStorage.getInstance();

        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d(TAG, "onChildAdded:" + dataSnapshot.getKey() + "prevChildName: "+s);
                Log.d(TAG,"new image added: "+dataSnapshot.getValue());
                NewsFeedItem item = dataSnapshot.getValue(NewsFeedItem.class);
                mNewsfeedItems.add(item);
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

        ref.addChildEventListener(childEventListener);
        // [END child_event_listener_recycler]

        // Store reference to listener so it can be removed on app stop
        mChildEventListener = childEventListener;
    } // end constructor

    // Easy access to the context object in the recyclerview
    private Context getContext() {
        return mContext;
    }

    @Override
    public FirebaseImgAdapter.NewsFeedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.news_feed_card, parent, false);

        return new FirebaseImgAdapter.NewsFeedViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FirebaseImgAdapter.NewsFeedViewHolder newsFeedViewHolder, int position) {
        NewsFeedItem currItem = mNewsfeedItems.get(position);
        newsFeedViewHolder.caption.setText(currItem.getCaption());
        // get the width of imageview.
        int targetImageWidth = getTargetImageWidth(getContext());
        int targetImageHeight = currItem.height*targetImageWidth/currItem.width;
        Log.d(TAG,"Setting image view to height " + targetImageHeight);
        newsFeedViewHolder.image.getLayoutParams().height = targetImageHeight;
//        Picasso.with(mContext)
//                .load(currItem.getUrl()).resize(targetImageWidth,targetImageHeight).placeholder(R.drawable.placeholder)
//                .into(newsFeedViewHolder.image);
        Log.d(TAG,"ImageURL: gs://imugweddingapp.appspot.com/"+currItem.getUrl());
        storageRef = storage.getReferenceFromUrl("gs://imugweddingapp.appspot.com/"+currItem.getUrl());
        Glide.with(mContext)
                .using(new FirebaseImageLoader())
                .load(storageRef)
                .into(newsFeedViewHolder.image);
    }

    private int getTargetImageWidth(Context context) {
        if(targetImageWidth == 0) {
            int width = (int)(DeviceDimensionsHelper.getDisplayWidth(context) - 2*DeviceDimensionsHelper.convertDpToPixel(CARD_MARGIN,context));
            targetImageWidth = width;
        }
        return  targetImageWidth;

    }
    public void cleanupListener() {
        if (mChildEventListener != null) {
            mDatabaseReference.removeEventListener(mChildEventListener);
        }
    }

    @Override
    public int getItemCount() {
        return mNewsfeedItems.size();
    }

}
