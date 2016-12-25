package com.davidecirillo.multichoicerecyclerview;

import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MultiChoiceToolbarHelperTest {

    private static final String TOOLBAR_ERROR_MESSAGE = "Toolbar not implemented via getSupportActionBar method";

    @Mock private MultiChoiceToolbar mMockMultiChoiceToolbar;
    @Mock private AppCompatActivity mMockAppCompatActivity;
    @Mock private ActionBar mMockActionBar;
    @Mock private Toolbar mMockToolbar;
    @Mock private Window mMockWindow;
    @Mock private MultiChoiceToolbar.Listener mListener;

    private MultiChoiceToolbarHelper mCut;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        when(mMockMultiChoiceToolbar.getAppCompatActivity()).thenReturn(mMockAppCompatActivity);
        when(mMockAppCompatActivity.getSupportActionBar()).thenReturn(mMockActionBar);
        when(mMockMultiChoiceToolbar.getToolbar()).thenReturn(mMockToolbar);
        when(mMockAppCompatActivity.getWindow()).thenReturn(mMockWindow);
        when(mMockMultiChoiceToolbar.getToolbarListener()).thenReturn(mListener);

        mCut = new MultiChoiceToolbarHelper(mMockMultiChoiceToolbar);
    }

    @Test
    public void testGivenAppCompactActivityIsNullWhenCreatingHelperThenException() throws Exception {
        // Given
        when(mMockAppCompatActivity.getSupportActionBar()).thenReturn(null);

        // When
        try {
            new MultiChoiceToolbarHelper(mMockMultiChoiceToolbar);
            fail("Library should throw an exception at thi point");
        } catch (Exception e) {
            // Then
            assertTrue("Exception should be of the type 'IllegalArgumentException'", e instanceof IllegalStateException);
            assertEquals("Message should be the expected one", TOOLBAR_ERROR_MESSAGE, e.getMessage());
        }
    }

    @Test
    public void testGivenZeroItemSelectedWhenUpdateToolbarThenDefaultIconShown() throws Exception {
        // Given
        int itemSelected = 0;
        int icon = 1;
        when(mMockMultiChoiceToolbar.getIcon()).thenReturn(icon);

        // When
        mCut.updateToolbar(itemSelected);

        // Then
        verify(mMockMultiChoiceToolbar, times(1)).getIcon();
        verify(mMockActionBar, times(1)).setHomeAsUpIndicator(icon);
        verify(mMockActionBar, times(1)).setDisplayHomeAsUpEnabled(true);
        verify(mMockToolbar, times(1)).setNavigationOnClickListener(mMockMultiChoiceToolbar.getIconAction());
    }

    @Test
    public void testGivenZeroItemSelectedAndNoIconWhenUpdateToolbarThenNoIconShown() throws Exception {
        // Given icon == 0
        int itemSelected = 0;

        // When
        mCut.updateToolbar(itemSelected);

        // Then
        verify(mMockMultiChoiceToolbar, times(1)).getIcon();
        verify(mMockActionBar, times(1)).setDisplayHomeAsUpEnabled(false);
        verify(mMockActionBar, times(0)).setHomeAsUpIndicator(any(Integer.class));
        verify(mMockToolbar, times(0)).setNavigationOnClickListener(any(View.OnClickListener.class));
    }

    @Test
    public void testGivenZeroItemSelectedWhenUpdateToolbarThenDefaultBackgroundSet() throws Exception {
        // Given
        int itemSelected = 0;

        // When
        mCut.updateToolbar(itemSelected);

        // Then
        verify(mMockActionBar, times(1)).setBackgroundDrawable(any(ColorDrawable.class));
        verify(mMockMultiChoiceToolbar, times(1)).getDefaultPrimaryColor();
    }

    @Test
    public void testGivenOneItemSelectedWhenUpdateToolbarThenXIconSet() throws Exception {
        // Given
        int itemSelected = 1;

        // When
        mCut.updateToolbar(itemSelected);

        // Then
        verify(mMockActionBar, times(1)).setHomeAsUpIndicator(R.drawable.ic_close_white_24dp);
        verify(mMockActionBar, times(1)).setDisplayHomeAsUpEnabled(true);
    }

    @Test
    public void testGivenOneItemSelectedWhenUpdateToolbarThenOnClickListenerSet() throws Exception {
        // Given
        int itemSelected = 1;

        // When
        mCut.updateToolbar(itemSelected);

        // Then
        verify(mMockToolbar, times(1)).setNavigationOnClickListener(any(View.OnClickListener.class));
    }

    @Test
    public void testGivenOneItemSelectedWhenUpdateToolbarAndClickOnIconPerformedThenListenerInterfaceClick() throws Exception {
        // Given
        int itemSelected = 1;

        // When
        mCut.updateToolbar(itemSelected);

        // Then
        verify(mMockToolbar, times(1)).setNavigationOnClickListener(any(View.OnClickListener.class));
    }
}