package com.davidecirillo.multichoicesample.api;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

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

public abstract class BaseMultiChoiceActivityTest {


    //Add custom implementation for each sample
    protected abstract boolean isSelected(View view);

    /**
     * Check if an item is selected by checking the custom style
     * <p>
     *
     * @return true if the item is selected for SampleToolbarActivity customisation specs
     */
    protected Matcher<View> isSelected() {
        return new TypeSafeMatcher<View>() {

            @Override
            public void describeTo(Description description) {
                description.appendText("Item should be selected with the correct style");
            }

            @Override
            public boolean matchesSafely(View view) {
                return isSelected(view);
            }
        };
    }

    protected Matcher<View> areAllSelected() {
        return new TypeSafeMatcher<View>() {

            @Override
            public void describeTo(Description description) {
                description.appendText("All items should be selected");
            }

            @Override
            public boolean matchesSafely(View view) {

                if (view instanceof RecyclerView) {
                    RecyclerView recyclerView = ((RecyclerView) view);

                    for (int i = 0; i<recyclerView.getChildCount(); i++){
                        View child = recyclerView.getChildAt(i);

                        if(!isSelected(child))
                            return false;
                    }
                    return true;
                }

                return false;
            }
        };
    }
}
