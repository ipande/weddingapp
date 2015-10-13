package desipride.socialshaadi.shadiviews;

import android.app.ActionBar;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.astuetz.PagerSlidingTabStrip;

import java.lang.ref.WeakReference;

import desipride.socialshaadi.R;

public class ShaadiActivity extends FragmentActivity {
    private static final String TAG = ShaadiActivity.class.getSimpleName();
	private final Handler handler = new Handler();

	private PagerSlidingTabStrip tabs;
	private ViewPager pager;
	private MyPagerAdapter adapter;
	private WeakReference<AboutUsFragment> aboutUsFragmentWeakReference;
    private WeakReference<EventsFragment> eventsFragmentWeakReference;
	private WeakReference<NewsFeedFragment> newsFeedFragmentWeakReference;


	private Drawable oldBackground = null;
	private int currentColor = R.color.orange;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shaadi_activity);
        aboutUsFragmentWeakReference = new WeakReference<AboutUsFragment>(null);
        eventsFragmentWeakReference = new WeakReference<EventsFragment>(null);
        newsFeedFragmentWeakReference = new WeakReference<NewsFeedFragment>(null);
		tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
		pager = (ViewPager) findViewById(R.id.pager);
		adapter = new MyPagerAdapter(getSupportFragmentManager());

		pager.setAdapter(adapter);

		final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
				.getDisplayMetrics());
		pager.setPageMargin(pageMargin);
		tabs.setShouldExpand(true);
		tabs.setViewPager(pager);

		tabs.setIndicatorColor(Color.WHITE);
		tabs.setUnderlineColor(Color.WHITE);
		tabs.setTextColor(Color.WHITE);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {

//		case R.id.action_contact:
//			QuickContactFragment dialog = new QuickContactFragment();
//			dialog.show(getSupportFragmentManager(), "QuickContactFragment");
//			return true;

		}

		return super.onOptionsItemSelected(item);
	}

	private void changeColor(int newColor) {

		tabs.setIndicatorColor(newColor);

		// change ActionBar color just if an ActionBar is available
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {

			Drawable colorDrawable = new ColorDrawable(newColor);
			Drawable bottomDrawable = getResources().getDrawable(R.drawable.actionbar_bottom);
			LayerDrawable ld = new LayerDrawable(new Drawable[] { colorDrawable, bottomDrawable });
			ActionBar actionBar = getActionBar();
			if (oldBackground == null) {

				if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
					ld.setCallback(drawableCallback);
				} else {
					if(actionBar != null) {
						actionBar.setBackgroundDrawable(ld);
					}
				}

			} else {

				TransitionDrawable td = new TransitionDrawable(new Drawable[] { oldBackground, ld });

				// workaround for broken ActionBarContainer drawable handling on
				// pre-API 17 builds
				// https://github.com/android/platform_frameworks_base/commit/a7cc06d82e45918c37429a59b14545c6a57db4e4
				if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
					td.setCallback(drawableCallback);
				} else {
					if(actionBar!= null) {
						actionBar.setBackgroundDrawable(td);
					}

				}

				td.startTransition(200);

			}

			oldBackground = ld;

			// http://stackoverflow.com/questions/11002691/actionbar-setbackgrounddrawable-nulling-background-from-thread-handler
			if(actionBar != null) {
				actionBar.setDisplayShowTitleEnabled(false);
				actionBar.setDisplayShowTitleEnabled(true);
			}

		}

		currentColor = newColor;

	}

	public void onColorClicked(View v) {

		int color = Color.parseColor(v.getTag().toString());
		changeColor(color);

	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("currentColor", currentColor);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		currentColor = savedInstanceState.getInt("currentColor");
		changeColor(currentColor);
	}

	private Drawable.Callback drawableCallback = new Drawable.Callback() {
		@Override
		public void invalidateDrawable(Drawable who) {
			getActionBar().setBackgroundDrawable(who);
		}

		@Override
		public void scheduleDrawable(Drawable who, Runnable what, long when) {
			handler.postAtTime(what, when);
		}

		@Override
		public void unscheduleDrawable(Drawable who, Runnable what) {
			handler.removeCallbacks(what);
		}
	};

	public class MyPagerAdapter extends FragmentPagerAdapter {

		private final String[] TITLES = { "Events", "About Us", "News Feed" };

		public MyPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return TITLES[position];
		}

		@Override
		public int getCount() {
			return TITLES.length;
		}

		@Override
		public Fragment getItem(int position) {
			Fragment fragment = null;
			switch(position) {
				case 0:
					fragment = getEventsFragment();
					break;
				case 1:
                    fragment = getAboutUsFragment();
					break;
				case 2:
                    fragment = getNewsFeedFragment();
					break;
			}
			return fragment;
		}

	}

	private AboutUsFragment getAboutUsFragment() {
		AboutUsFragment aboutUsFragment;
		aboutUsFragment = aboutUsFragmentWeakReference.get();
		if(aboutUsFragment == null) {
			aboutUsFragment = new AboutUsFragment();
            aboutUsFragmentWeakReference = new WeakReference<AboutUsFragment>(aboutUsFragment);
		}
		return aboutUsFragment;
	}

    private EventsFragment getEventsFragment() {
        EventsFragment fragment;
        fragment = eventsFragmentWeakReference.get();
        if(fragment == null) {
            Log.d(TAG, "EventsFragment was null");
            fragment = new EventsFragment();
            eventsFragmentWeakReference = new WeakReference<EventsFragment>(fragment);
        } else {
            Log.d(TAG, "EventsFragment was not null");
        }
        return fragment;
    }

    private NewsFeedFragment getNewsFeedFragment() {
        NewsFeedFragment fragment;
        fragment = newsFeedFragmentWeakReference.get();
        if(fragment == null) {
            fragment = new NewsFeedFragment();
            newsFeedFragmentWeakReference = new WeakReference<NewsFeedFragment>(fragment);
        }
        return fragment;
    }
}