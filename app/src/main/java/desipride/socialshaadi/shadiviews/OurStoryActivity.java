package desipride.socialshaadi.shadiviews;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import desipride.socialshaadi.R;

public class OurStoryActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.our_story);

        ImageView toddlerImageView = (ImageView)findViewById(R.id.love_story_toddlers);
        ImageView oldLoveStoryImageView = (ImageView)findViewById(R.id.love_story_old);
        ImageView loveStoryImageView = (ImageView)findViewById(R.id.love_story_new);

        Picasso.with(this).load(R.drawable.parth_priya_lovestory_toddler).into(toddlerImageView);
        Picasso.with(this).load(R.drawable.parth_priya_lovestory_old).into(oldLoveStoryImageView);
        Picasso.with(this).load(R.drawable.parth_priya_lovestory).into(loveStoryImageView);
    }

}