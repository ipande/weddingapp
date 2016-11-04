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
import static desipride.socialshaadi.desipride.socialshaadi.utils.Constants.ISHAN_FAMILY_CODE;
import static desipride.socialshaadi.desipride.socialshaadi.utils.Constants.MUGDHA_FAMILY_CODE;

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
        LinearLayout parth_family = (LinearLayout)view.findViewById(R.id.ishan_family_view);
        parth_family.setOnClickListener(this);
        LinearLayout priya_family = (LinearLayout)view.findViewById(R.id.mugdha_family_view);
        priya_family.setOnClickListener(this);
        ImageView iMugAboutUs = (ImageView)view.findViewById(R.id.imug_about_us);
        ImageView ishanFamily = (ImageView)view.findViewById(R.id.parth_family);
        ImageView mugdhaFamily = (ImageView)view.findViewById(R.id.priya_family);

        Picasso.with(getActivity())
                .load(R.drawable.imugcirclesmall).into(iMugAboutUs);
        Picasso.with(getActivity())
                .load(R.drawable.ishansfamily).into(ishanFamily);
        Picasso.with(getActivity())
                .load(R.drawable.mugdhasfamily).into(mugdhaFamily);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.our_story:
                Intent ourStoryIntent = new Intent(getActivity(),OurStoryActivity.class);
                startActivity(ourStoryIntent);
                break;
            case R.id.mugdha_family_view:
                Intent priyaFamilyIntent = new Intent(getActivity(),FamilyInfoActivity.class);
                priyaFamilyIntent.putExtra(FAMILY_IDENTIFIER, MUGDHA_FAMILY_CODE);
                startActivity(priyaFamilyIntent);
                break;
            case R.id.ishan_family_view:
                Intent parthFamilyIntent = new Intent(getActivity(),FamilyInfoActivity.class);
                parthFamilyIntent.putExtra(FAMILY_IDENTIFIER, ISHAN_FAMILY_CODE);
                startActivity(parthFamilyIntent);
                break;
        }
    }
}