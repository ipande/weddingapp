/*
 * Copyright (C) 2013 Andreas Stuetz <andreas.stuetz@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package desipride.socialshaadi.shadiviews;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import desipride.socialshaadi.R;
import desipride.socialshaadi.desipride.socialshaadi.utils.RecyclerItemClickListener;
import desipride.socialshaadi.shadidata.Event;
import desipride.socialshaadi.shadidata.EventData;

public class EventsFragment extends Fragment implements ViewTreeObserver.OnGlobalLayoutListener {

    private static final String TAG = EventsFragment.class.getSimpleName();
    private class EventViewHolder extends RecyclerView.ViewHolder {
        protected TextView title;
        protected TextView dateTime;
        protected TextView location;
        protected ImageView eventThumbnail;

        public EventViewHolder(View v) {
            super(v);
            title =  (TextView) v.findViewById(R.id.event_title);
            dateTime = (TextView)  v.findViewById(R.id.event_date_time);
            location = (TextView)  v.findViewById(R.id.event_location);
            eventThumbnail = (ImageView) v.findViewById(R.id.event_thumbnail);

        }
    }

    private class EventAdapter extends RecyclerView.Adapter<EventViewHolder> {

        private ArrayList<Event> events;
        private Context context;
        public EventAdapter(ArrayList<Event> events, Context context) {
            this.context = context;
            this.events = events;
        }

        @Override
        public EventViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View itemView = LayoutInflater.
                    from(viewGroup.getContext()).
                    inflate(R.layout.event_card, viewGroup, false);

            return new EventViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(EventViewHolder eventViewHolder, int i) {
            Event event = events.get(i);
            eventViewHolder.title.setText(event.getTitle());
            String date_time = event.getEventDateString();
            eventViewHolder.dateTime.setText(date_time);
            eventViewHolder.location.setText(event.getAddressTitle());
            Log.d(TAG, "Loading event image in image view");
                Picasso.with(context)
                        .load(event.getThumbnail()).into(eventViewHolder.eventThumbnail);
        }

        @Override
        public int getItemCount() {
            return events.size();
        }
    }
    private int relativeLayoutWidth;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

    private RecyclerView recyclerEventView;
    private RelativeLayout parentRelativeLayout;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.event_list, container, false);

        parentRelativeLayout = (RelativeLayout)view.findViewById(R.id.events_container);
        ViewTreeObserver vto = parentRelativeLayout.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(this);

        recyclerEventView = (RecyclerView) view.findViewById(R.id.eventlist);
        recyclerEventView.setHasFixedSize(true);


        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerEventView.setLayoutManager(llm);

        ArrayList<Event> eventList = EventData.getEvents(getActivity());
        EventAdapter eventAdapter = new EventAdapter(eventList,getActivity());
        recyclerEventView.setAdapter(eventAdapter);
        recyclerEventView.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent i = new Intent(getActivity(), EventActivity.class);
                        i.putExtra(EventActivity.EVENT_INDEX, position);
                        startActivity(i);
                    }
                })
        );


        return view;
	}

    @Override
    public void onGlobalLayout() {
        parentRelativeLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
        relativeLayoutWidth = parentRelativeLayout.getWidth();
        int height = parentRelativeLayout.getHeight();
        Log.d(TAG, "onGlobalLayout W:" + relativeLayoutWidth + ", H:" + height);
    }

}