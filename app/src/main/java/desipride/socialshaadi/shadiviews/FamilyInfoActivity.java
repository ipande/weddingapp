package desipride.socialshaadi.shadiviews;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import desipride.socialshaadi.R;
import desipride.socialshaadi.shadidata.FamilyMember;
import desipride.socialshaadi.shadidata.FamilyMemberData;

import static desipride.socialshaadi.desipride.socialshaadi.utils.Constants.FAMILY_IDENTIFIER;
import static desipride.socialshaadi.desipride.socialshaadi.utils.Constants.INVALID_FAMILY_CODE;
import static desipride.socialshaadi.desipride.socialshaadi.utils.Constants.PARTH_FAMILY_CODE;
import static desipride.socialshaadi.desipride.socialshaadi.utils.Constants.PRIYA_FAMILY_CODE;

public class FamilyInfoActivity extends AppCompatActivity {
    private static final String TAG = FamilyInfoActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.family_info_activity);

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.family_member_list);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        ArrayList<FamilyMember> familyMembers = null;
        int familyCode = getIntent().getIntExtra(FAMILY_IDENTIFIER, INVALID_FAMILY_CODE);
        switch(familyCode) {
            case PARTH_FAMILY_CODE:
                familyMembers = FamilyMemberData.getParthFamilyMembers();
                getSupportActionBar().setTitle(getString(R.string.parth_family));
                break;
            case PRIYA_FAMILY_CODE:
                familyMembers = FamilyMemberData.getPriyaFamilyMembers();
                getSupportActionBar().setTitle(getString(R.string.priya_family));
                break;
            case INVALID_FAMILY_CODE:
                Log.w(TAG, "Invalid family code in startng activity");
                familyMembers = new ArrayList<FamilyMember>();
                break;
        }

        FamilyListAdapter familyListAdapter = new FamilyListAdapter(familyMembers, this);
        recyclerView.setAdapter(familyListAdapter);
    }

    private static class FamilyMemberViewHolder extends RecyclerView.ViewHolder {
        protected TextView name;
        protected TextView relation;
        protected TextView info;
        protected ImageView familyMemberImage;

        public FamilyMemberViewHolder(View v) {
            super(v);
            name =  (TextView) v.findViewById(R.id.family_member_name);
            relation = (TextView)  v.findViewById(R.id.family_member_relation);
            info = (TextView)  v.findViewById(R.id.family_member_info);
            familyMemberImage = (ImageView) v.findViewById(R.id.family_member_image);
        }
    }

    private static class FamilyListAdapter extends RecyclerView.Adapter<FamilyMemberViewHolder> {

        private ArrayList<FamilyMember> familyMembers;
        private Context context;

        public FamilyListAdapter(ArrayList<FamilyMember> familyMembers, Context context) {
            this.familyMembers = familyMembers;
            this.context = context;
        }

        @Override
        public FamilyMemberViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View itemView = LayoutInflater.
                    from(viewGroup.getContext()).
                    inflate(R.layout.family_member_card, viewGroup, false);

            return new FamilyMemberViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(FamilyMemberViewHolder familyMemberViewHolder, int i) {
            FamilyMember familyMember = familyMembers.get(i);

            familyMemberViewHolder.name.setText(familyMember.getName());
            familyMemberViewHolder.relation.setText(familyMember.getRelation());
            familyMemberViewHolder.info.setText(familyMember.getInformation());

            Picasso.with(context)
                    .load(familyMember.getThumbnail()).into(familyMemberViewHolder.familyMemberImage);
        }

        @Override
        public int getItemCount() {
            return familyMembers.size();
        }
    }


}
