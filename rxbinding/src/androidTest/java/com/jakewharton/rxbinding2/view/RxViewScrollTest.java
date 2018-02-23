package com.jakewharton.rxbinding2.view;

import android.support.test.annotation.UiThreadTest;
import android.support.test.filters.SdkSuppress;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import com.jakewharton.rxbinding2.RecordingObserver;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

/**
 * Created by jaeyoungpark on 20/02/2018.
 */

@RunWith(AndroidJUnit4.class)
public final class RxViewScrollTest {
  @Rule public final ActivityTestRule<RxViewScrollTestActivity> activityRule =
          new ActivityTestRule<>(RxViewScrollTestActivity.class);

  View view;

  @Before
  public void setUp() {
    RxViewScrollTestActivity activity = activityRule.getActivity();
    view = activity.view;
  }

  @SdkSuppress(minSdkVersion = 19)
  @Test @UiThreadTest public void scrollChangeEvents() {
    RecordingObserver<ViewScrollChangeEvent> o = new RecordingObserver<>();
    RxView.scrollChangeEvents(view).subscribe(o);
    o.assertNoMoreEvents();

    view.scrollTo(1, 1);
    ViewScrollChangeEvent event0 = o.takeNext();
    assertSame(view, event0.view());
    assertEquals(1, event0.scrollX());
    assertEquals(1, event0.scrollY());
    assertEquals(0, event0.oldScrollX());
    assertEquals(0, event0.oldScrollY());

    view.scrollTo(2, 2);
    ViewScrollChangeEvent event1 = o.takeNext();
    assertSame(view, event1.view());
    assertEquals(2, event1.scrollX());
    assertEquals(2, event1.scrollY());
    assertEquals(1, event1.oldScrollX());
    assertEquals(1, event1.oldScrollY());

    o.dispose();
    view.scrollTo(3, 3);
    o.assertNoMoreEvents();
  }
}
