package desipride.socialshaadi.shadiviews;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import desipride.socialshaadi.R;

/**
 * Created by parth.mehta on 10/4/15.
 */
public class AboutUsFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.about_us, container, false);
        LinearLayout aboutUsLayout = (LinearLayout)view.findViewById(R.id.our_story);
        aboutUsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),OurStoryActivity.class);
                startActivity(i);
            }
        });
        return view;
    }
}