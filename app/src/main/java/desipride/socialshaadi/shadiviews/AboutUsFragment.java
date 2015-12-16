package desipride.socialshaadi.shadiviews;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

import desipride.socialshaadi.R;

import static desipride.socialshaadi.desipride.socialshaadi.utils.Constants.FAMILY_IDENTIFIER;
import static desipride.socialshaadi.desipride.socialshaadi.utils.Constants.PARTH_FAMILY_CODE;
import static desipride.socialshaadi.desipride.socialshaadi.utils.Constants.PRIYA_FAMILY_CODE;

/**
 * Created by parth.mehta on 10/4/15.
 */
public class AboutUsFragment extends Fragment implements View.OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.about_us, container, false);
        LinearLayout aboutUsLayout = (LinearLayout)view.findViewById(R.id.our_story);
        aboutUsLayout.setOnClickListener(this);
        LinearLayout parth_family = (LinearLayout)view.findViewById(R.id.parth_family_view);
        parth_family.setOnClickListener(this);
        LinearLayout priya_family = (LinearLayout)view.findViewById(R.id.priya_family_view);
        priya_family.setOnClickListener(this);
        ImageView parthPriyaAboutUs = (ImageView)view.findViewById(R.id.priya_parth_about_us);
        ImageView parthFamily = (ImageView)view.findViewById(R.id.parth_family);
        ImageView priyaFamily = (ImageView)view.findViewById(R.id.priya_family);

        Picasso.with(getActivity())
                .load(R.drawable.parth_priya_circle).into(parthPriyaAboutUs);
        Picasso.with(getActivity())
                .load(R.drawable.parth_family_circle).into(parthFamily);
        Picasso.with(getActivity())
                .load(R.drawable.priya_family_circle).into(priyaFamily);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.our_story:
                Intent ourStoryIntent = new Intent(getActivity(),OurStoryActivity.class);
                startActivity(ourStoryIntent);
                break;
            case R.id.priya_family_view:
                Intent priyaFamilyIntent = new Intent(getActivity(),FamilyInfoActivity.class);
                priyaFamilyIntent.putExtra(FAMILY_IDENTIFIER, PRIYA_FAMILY_CODE);
                startActivity(priyaFamilyIntent);
                break;
            case R.id.parth_family_view:
                Intent parthFamilyIntent = new Intent(getActivity(),FamilyInfoActivity.class);
                parthFamilyIntent.putExtra(FAMILY_IDENTIFIER, PARTH_FAMILY_CODE);
                startActivity(parthFamilyIntent);
                break;
        }
    }
}