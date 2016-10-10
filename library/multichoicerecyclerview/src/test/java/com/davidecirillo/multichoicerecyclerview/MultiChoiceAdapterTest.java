package com.davidecirillo.multichoicerecyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.davidecirillo.multichoicerecyclerview.listeners.MultiChoiceAdapterListener;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.RobolectricGradleTestRunner;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by dci03 on 10/10/2016.
 */
public class MultiChoiceAdapterTest {

    private TestViewHolder mViewHolder;
    private MultiChoiceAdapter<TestViewHolder> mMultiChoiceAdapter;

    @Mock
    MultiChoiceAdapterListener mMultiChoiceAdapterListener;

    @Before
    public void setUp() throws Exception {

        mMultiChoiceAdapterListener = mock(MultiChoiceAdapterListener.class);

        mViewHolder = new TestViewHolder(mock(View.class));

        mMultiChoiceAdapter = new TestAdapter();
        mMultiChoiceAdapter.setMultiChoiceListener(mMultiChoiceAdapterListener);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testAdapterWithNoInterfaceShouldThrowSpecificException() throws Exception {
        try {
            mMultiChoiceAdapter.setMultiChoiceListener(null);
            mMultiChoiceAdapter.onBindViewHolder(mViewHolder, 0);
            fail();
        }catch (Exception e){
            assertEquals("The adapter should throw an exception with a specific message",
                    MultiChoiceAdapter.EXCEPTION_MSG_NO_INTERFACE, e.getMessage());
        }
    }

    @Test
    public void testAdapterShouldCallOnUpdateItemListener() throws Exception {
        mMultiChoiceAdapter.onBindViewHolder(mViewHolder, 1);

        verify(mMultiChoiceAdapterListener, times(1)).onUpdateItemListener(mViewHolder.itemView, mViewHolder.getAdapterPosition());
    }

    @Test
    public void testPerformActionShouldNotCallSetAction() throws Exception {
        TestAdapter testAdapter = mock(TestAdapter.class);
        testAdapter.performActivation(null, true);

        verify(testAdapter, never()).setActive(null, true);
    }

    private class TestViewHolder extends RecyclerView.ViewHolder{
        private TestViewHolder(View itemView) {
            super(itemView);
        }
    }

    private class TestAdapter extends MultiChoiceAdapter<TestViewHolder>{
        @Override
        public int getItemCount() {
            return 0;
        }
    }

}