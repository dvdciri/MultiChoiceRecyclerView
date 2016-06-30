package com.davidecirillo.multichoicesample;

/*
        ~Copyright(c)2014 Davide Cirillo
        ~
        ~Licensed under the Apache License,Version2.0(the"License");
        ~you may not use this file except in compliance with the License.
        ~You may obtain a copy of the License at
        ~
        ~http://www.apache.org/licenses/LICENSE-2.0
        ~
        ~Unless required by applicable law or agreed to in writing,software
        ~distributed under the License is distributed on an"AS IS"BASIS,
        ~WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,either express or implied.
        ~See the License for the specific language governing permissions and
        ~limitations under the License.
        ~Come on,don't tell me you read that.
        */


import android.graphics.drawable.ColorDrawable;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.davidecirillo.multichoicesample.api.BaseMultiChoiceActivityTest;
import com.davidecirillo.multichoicesample.sampleCustomView.SampleCustomActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.longClick;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.StringContains.containsString;

@RunWith(AndroidJUnit4.class)
public class SampleCustomViewTest extends BaseMultiChoiceActivityTest {

    public SampleCustomActivity mActivity;

    @Rule
    public ActivityTestRule<SampleCustomActivity> mActivityRule = new ActivityTestRule<>(
            SampleCustomActivity.class);

    @Before
    public void setUp() {
        mActivity = mActivityRule.getActivity();
        wakeScreen(mActivity);
    }

    /**
     * #1 testFirstLongClickSelection
     * <p>
     * Long click on the first item in order to make the first selection and check if the item is selected properly
     */
    @Test
    public void testFirstLongClickSelection() {
        onView(withId(R.id.multiChoiceRecyclerView))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, longClick()))
                .check(matches(isSelected()));
    }

    /**
     * #2 testFirstLongClickSelection
     * <p>
     * Long click on the first item in order to make the first selection and then single clicks
     * on other items and check that everything is selected properly
     */
    @Test
    public void testFirstLongClickPlusOtherSingleClick() {
        onView(withId(R.id.multiChoiceRecyclerView))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, longClick()))
                .check(matches(isSelected()));

        onView(withId(R.id.multiChoiceRecyclerView))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()))
                .check(matches(isSelected()));

        onView(withId(R.id.multiChoiceRecyclerView))
                .perform(RecyclerViewActions.actionOnItemAtPosition(2, click()))
                .check(matches(isSelected()));

        onView(withId(R.id.multiChoiceRecyclerView))
                .perform(RecyclerViewActions.actionOnItemAtPosition(3, click()))
                .check(matches(isSelected()));
    }

    /**
     * #3 testItemShouldNotBeSelected
     * <p>
     * Try to perform a single click on one item: the item shouldn't be selected (because we're in the default mode
     * and we need the long click on the first item to activate the multi choice mode)
     */
    @Test
    public void testItemShouldNotBeSelected() {
        onView(withId(R.id.multiChoiceRecyclerView))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()))
                .check(matches(not(isSelected())));
    }

    /**
     * #4 testDeselectItem
     * <p>
     * Long Click to activate the multic choice mode and then deselect the same item with a single click - check
     */
    @Test
    public void testDeselectItem() {
        onView(withId(R.id.multiChoiceRecyclerView))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, longClick()))
                .check(matches(isSelected()));

        onView(withId(R.id.multiChoiceRecyclerView))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()))
                .check(matches(not(isSelected())));
    }

    /**
     * #5 testMultiChoiceToolbar
     * <p>
     * Make sure that the multi choice toolbar is displayed and updated with the correct number of selected items
     */
    @Test
    public void testMultiChoiceToolbar() {
        onView(withId(R.id.multiChoiceRecyclerView))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, longClick()));

        onView(allOf(isAssignableFrom(TextView.class), withParent(isAssignableFrom(Toolbar.class))))
                .check(matches(withText(containsString("1"))));

        onView(withId(R.id.multiChoiceRecyclerView))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

        onView(allOf(isAssignableFrom(TextView.class), withParent(isAssignableFrom(Toolbar.class))))
                .check(matches(withText(containsString("2"))));

        onView(withId(R.id.multiChoiceRecyclerView))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

        onView(withId(R.id.multiChoiceRecyclerView))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(allOf(isAssignableFrom(TextView.class), withParent(isAssignableFrom(Toolbar.class))))
                .check(matches(withText(R.string.app_name)));
    }

    @Override
    protected boolean isSelected(View view) {
        RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R.id.container);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);

        return relativeLayout != null
                && ((ColorDrawable) relativeLayout.getBackground()).getColor() == ContextCompat.getColor(mActivity, R.color.colorBackgroundLight)
                && ((ColorDrawable) imageView.getBackground()).getColor() == ContextCompat.getColor(mActivity, R.color.colorPrimaryDark);
    }
}
