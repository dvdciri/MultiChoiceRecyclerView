package com.davidecirillo.multichoicerecyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class MultiChoiceAdapterTest {

    private TestViewHolder mViewHolder;
    private MultiChoiceAdapter<TestViewHolder> mMultiChoiceAdapter;

    @Mock
    MultiChoiceAdapter.SelectionListener mMultiChoiceAdapterListener;

    @Before
    public void setUp() throws Exception {

        mMultiChoiceAdapterListener = mock(MultiChoiceAdapter.SelectionListener.class);

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
        } catch (Exception e) {
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
    public void testAdapterShouldNotCallSingleItemClickListenerIfNotInMultiChoiceModeOrSingleChoiceMode() throws Exception {
        // Given
        mMultiChoiceAdapter.isInMultiChoiceMode = false;
        mMultiChoiceAdapter.isInSingleClickMode = false;

        mMultiChoiceAdapter.onBindViewHolder(mViewHolder, 1);
        mViewHolder.itemView.callOnClick();

        verify(mMultiChoiceAdapterListener, times(0)).onSingleItemClickListener(any(View.class), any(Integer.class));
    }

    @Test
    public void testAdapterShouldCallSingleItemClickListenerIfIsEitherSingleOrMultiChoiceMode() throws Exception {
        // Given
        ArgumentCaptor<View.OnClickListener> clickListenerCapture = ArgumentCaptor.forClass(View.OnClickListener.class);
        mMultiChoiceAdapter.isInMultiChoiceMode = true;
        mMultiChoiceAdapter.isInSingleClickMode = false;

        // When
        mMultiChoiceAdapter.onBindViewHolder(mViewHolder, 1);

        //Then
        verify(mViewHolder.itemView, times(1)).setOnClickListener(clickListenerCapture.capture());

        // When2
        clickListenerCapture.getValue().onClick(mViewHolder.itemView);

        // Then
        verify(mMultiChoiceAdapterListener, times(1)).onSingleItemClickListener(mViewHolder.itemView, mViewHolder.getAdapterPosition());
    }

    @Test
    public void testAdapterShouldCallSingleItemClickListenerIfIsEitherSingleOrMultiChoiceMode2() throws Exception {
        // Given
        ArgumentCaptor<View.OnClickListener> clickListenerCapture = ArgumentCaptor.forClass(View.OnClickListener.class);
        mMultiChoiceAdapter.isInMultiChoiceMode = false;
        mMultiChoiceAdapter.isInSingleClickMode = true;

        // When
        mMultiChoiceAdapter.onBindViewHolder(mViewHolder, 1);

        //Then
        verify(mViewHolder.itemView, times(1)).setOnClickListener(clickListenerCapture.capture());

        // When2
        clickListenerCapture.getValue().onClick(mViewHolder.itemView);

        // Then
        verify(mMultiChoiceAdapterListener, times(1)).onSingleItemClickListener(mViewHolder.itemView, mViewHolder.getAdapterPosition());
    }

    @Test
    public void testDisableItemShouldNotBeSelectedInMultiChoiceMode() throws Exception {
        // Given
        mMultiChoiceAdapter = new TestAdapter() {
            @Override
            protected boolean isSelectableInMultiChoiceMode(int position) {
                return false;
            }

            @Override
            protected View.OnClickListener defaultItemViewClickListener(TestViewHolder holder, int position) {
                return null;
            }
        };
        mMultiChoiceAdapter.setMultiChoiceListener(mMultiChoiceAdapterListener);
        mMultiChoiceAdapter.isInMultiChoiceMode = false;
        mMultiChoiceAdapter.isInSingleClickMode = true;

        // When
        mMultiChoiceAdapter.onBindViewHolder(mViewHolder, 1);

        // Then
        verify(mViewHolder.itemView, times(0)).setOnClickListener(any(View.OnClickListener.class));
    }

    @Test
    public void testDisableItemShouldNotBeSelectedInMultiChoiceModeAndShouldHaveDefaultClickListenerIfNotNull() throws Exception {
        // Given
        ArgumentCaptor<View.OnClickListener> clickListenerCapture = ArgumentCaptor.forClass(View.OnClickListener.class);
        mMultiChoiceAdapter = new TestAdapter() {
            @Override
            protected boolean isSelectableInMultiChoiceMode(int position) {
                return false;
            }
        };
        mMultiChoiceAdapter.setMultiChoiceListener(mMultiChoiceAdapterListener);
        mMultiChoiceAdapter.isInMultiChoiceMode = false;
        mMultiChoiceAdapter.isInSingleClickMode = true;

        // When
        mMultiChoiceAdapter.onBindViewHolder(mViewHolder, 1);

        // Then
        verify(mViewHolder.itemView, times(1)).setOnClickListener(clickListenerCapture.capture());

        assertEquals("The setOnClickListener doesn't have the correct onClickListener object", ((TestAdapter) mMultiChoiceAdapter)
                .getOnClickListener(), clickListenerCapture.getValue());
    }

    @Test
    public void testAdapterIsUsingTheDefaultClickListenerIfNotInMultiAndSingleClickModeAndTheItemIsSelectable() throws Exception {
        // Given
        ArgumentCaptor<View.OnClickListener> clickListenerCapture = ArgumentCaptor.forClass(View.OnClickListener.class);
        mMultiChoiceAdapter.isInMultiChoiceMode = false;
        mMultiChoiceAdapter.isInSingleClickMode = false;

        // When
        mMultiChoiceAdapter.onBindViewHolder(mViewHolder, 1);

        // Then
        verify(mViewHolder.itemView, times(1)).setOnClickListener(clickListenerCapture.capture());

        assertEquals("The setOnClickListener doesn't have the correct onClickListener object", ((TestAdapter) mMultiChoiceAdapter)
                .getOnClickListener(), clickListenerCapture.getValue());
    }

    @Test
    public void testAdapterIsUsingNotTheDefaultClickListenerIfNotInMultiAndSingleClickModeAndTheItemIsSelectableAndTheDefaultClickIsNull() throws
            Exception {
        // Given
        mMultiChoiceAdapter = new TestAdapter() {
            @Override
            protected View.OnClickListener defaultItemViewClickListener(TestViewHolder holder, int position) {
                return null;
            }
        };
        mMultiChoiceAdapter.setMultiChoiceListener(mMultiChoiceAdapterListener);
        mMultiChoiceAdapter.isInMultiChoiceMode = false;
        mMultiChoiceAdapter.isInSingleClickMode = false;

        // When
        mMultiChoiceAdapter.onBindViewHolder(mViewHolder, 1);

        // Then
        verify(mViewHolder.itemView, times(0)).setOnClickListener(any(View.OnClickListener.class));
    }

    private class TestViewHolder extends RecyclerView.ViewHolder {
        private TestViewHolder(View itemView) {
            super(itemView);
        }
    }

    private class TestAdapter extends MultiChoiceAdapter<TestViewHolder> {

        private View.OnClickListener mOnClickListener;

        @Override
        public int getItemCount() {
            return 0;
        }

        @Override
        protected View.OnClickListener defaultItemViewClickListener(TestViewHolder holder, int position) {
            mOnClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            };
            return mOnClickListener;
        }

        public View.OnClickListener getOnClickListener() {
            return mOnClickListener;
        }
    }

}