package com.gauravsaluja.redmart.fragments;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;

import com.gauravsaluja.redmart.R;
import com.gauravsaluja.redmart.activities.ListingActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by Gaurav Saluja on 21-Apr-18.
 *
 * UI test for ListingFragment
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ListingFragmentTest {

    @Rule
    public ActivityTestRule<ListingActivity> activityRule = new ActivityTestRule<>(ListingActivity.class);

    @Before
    public void setUp() {

    }

    // load listing page --> click on 1st item --> go back --> perform next page load -->
    // scroll to bottom of next page --> perform click on 54th item --> go back
    @Test
    public void clickProductItem_loadDetailPage() throws InterruptedException {

        Thread.sleep(2000);

        onView(withId(R.id.product_listing)).perform(actionOnItemAtPosition(0, click()));

        Thread.sleep(2000);

        pressBack();

        RecyclerView recyclerView = activityRule.getActivity().findViewById(R.id.product_listing);
        onView(withId(R.id.product_listing)).perform(RecyclerViewActions.scrollToPosition(recyclerView.getAdapter().getItemCount() - 1));

        Thread.sleep(2000);

        onView(withId(R.id.product_listing)).perform(RecyclerViewActions.scrollToPosition(recyclerView.getAdapter().getItemCount() - 5));

        Thread.sleep(2000);

        onView(withId(R.id.product_listing)).perform(actionOnItemAtPosition(54, click()));

        Thread.sleep(2000);

        pressBack();

        Thread.sleep(2000);
    }
}