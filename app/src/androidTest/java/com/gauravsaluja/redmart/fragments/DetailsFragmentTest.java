package com.gauravsaluja.redmart.fragments;

import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;

import com.gauravsaluja.redmart.R;
import com.gauravsaluja.redmart.activities.DetailsActivity;
import com.gauravsaluja.redmart.activities.ListingActivity;
import com.gauravsaluja.redmart.viewholder.ListingItemViewHolder;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.action.ViewActions.swipeRight;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static org.hamcrest.core.AllOf.allOf;

/**
 * Created by Gaurav Saluja on 21-Apr-18.
 *
 * UI test for DetailsFragment
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class DetailsFragmentTest {

    @Rule
    public ActivityTestRule<ListingActivity> activityRule = new ActivityTestRule<>(ListingActivity.class);

    @Before
    public void setUp() {

    }

    // load listing page --> click on 1st item --> swipe left on viewpager --> swipe right on viewpager -->
    // swipe up to go to bottom of detail page --> swipe down to go to top of detail page --> go back -->
    // perform next page load --> scroll to bottom of next page --> perform click on 54th item -->
    // swipe left on viewpager --> swipe left on viewpager --> swipe right on viewpager -->
    // swipe right on viewpager --> swipe up to go to bottom of detail page -->
    // swipe down to go to top of detail page --> go back
    @Test
    public void openDetailPage_SwipeLeftRight_Scroll() throws InterruptedException {

        Thread.sleep(2000);

        onView(withId(R.id.product_listing)).perform(actionOnItemAtPosition(0, click()));

        Thread.sleep(2000);

        onView(withId(R.id.images_pager)).perform(swipeLeft());

        Thread.sleep(2000);

        onView(withId(R.id.images_pager)).perform(swipeRight());

        Thread.sleep(2000);

        onView(withId(R.id.product_detail_scroll_view)).perform(ViewActions.swipeUp());

        Thread.sleep(2000);

        onView(withId(R.id.product_detail_scroll_view)).perform(ViewActions.swipeDown());

        Thread.sleep(2000);

        pressBack();

        RecyclerView recyclerView = activityRule.getActivity().findViewById(R.id.product_listing);
        onView(withId(R.id.product_listing)).perform(RecyclerViewActions.scrollToPosition(recyclerView.getAdapter().getItemCount() - 1));

        Thread.sleep(2000);

        onView(withId(R.id.product_listing)).perform(RecyclerViewActions.scrollToPosition(recyclerView.getAdapter().getItemCount() - 5));

        Thread.sleep(2000);

        onView(withId(R.id.product_listing)).perform(actionOnItemAtPosition(54, click()));

        Thread.sleep(2000);

        onView(withId(R.id.images_pager)).perform(swipeLeft());

        Thread.sleep(2000);

        onView(withId(R.id.images_pager)).perform(swipeLeft());

        Thread.sleep(2000);

        onView(withId(R.id.images_pager)).perform(swipeRight());

        Thread.sleep(2000);

        onView(withId(R.id.images_pager)).perform(swipeRight());

        Thread.sleep(2000);

        onView(withId(R.id.product_detail_scroll_view)).perform(ViewActions.swipeUp());

        Thread.sleep(2000);

        onView(withId(R.id.product_detail_scroll_view)).perform(ViewActions.swipeDown());

        Thread.sleep(2000);

        pressBack();

        Thread.sleep(2000);
    }
}