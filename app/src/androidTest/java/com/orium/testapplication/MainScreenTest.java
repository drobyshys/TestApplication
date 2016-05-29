package com.orium.testapplication;

import android.support.annotation.NonNull;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.ViewAssertion;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.orium.testapplication.rules.MockWebServerRule;
import com.orium.testapplication.rules.NeedsMockWebServer;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.runner.RunWith;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItem;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
public class MainScreenTest {

    @Rule
    public RuleChain rules = RuleChain.emptyRuleChain()
            .around(new MockWebServerRule(this))
            .around(new ActivityTestRule<>(MainActivity.class));

    @Test // For real, it's really easy to break Toolbar, so why not test it?
    public void shouldDisplayTitle() {
        onView(allOf(withText( R.string.app_name), withParent(withId(R.id.toolbar)))).check(matches(isDisplayed()));
    }

    @Test
    @NeedsMockWebServer(setupMethod = "setupMockWebServer")
    public void shouldDisplayAllItemsFromSuccessfulResponse() {
        final int itemsCount = 8;
        onView(withId(android.R.id.list)).check(new ViewAssertion() {
            @Override public void check(final View view, final NoMatchingViewException noViewFoundException) {
                final RecyclerView recyclerView = (RecyclerView) view;
                final int actualCount = recyclerView.getAdapter().getItemCount();
                if (actualCount != itemsCount) {
                    throw new AssertionError("RecyclerView has " + actualCount + " while expected " + itemsCount);
                }
            }
        });

        for (int i = 1; i < itemsCount+1; i++) {
            onView(withId(android.R.id.list))
                    .perform(actionOnItem(hasDescendant(
                            allOf(
                                    withId(R.id.tv_name),
                                    withText("Test title " + i))),
                            noOp()));
        }
    }

    public void setupMockWebServer(@NonNull MockWebServer mockWebServer) {
        mockWebServer.enqueue(new MockResponse().setBody("["
                + "{ \"id\": \"test_id_1\", \"image_preview_url\": \"https://url1\", \"title\": \"Test title 1\", \"short_description\": \"Short desc 1\"},"
                + "{ \"id\": \"test_id_2\", \"image_preview_url\": \"https://url2\", \"title\": \"Test title 2\", \"short_description\": \"Short desc 2\"},"
                + "{ \"id\": \"test_id_3\", \"image_preview_url\": \"https://url3\", \"title\": \"Test title 3\", \"short_description\": \"Short desc 3\"},"
                + "{ \"id\": \"test_id_4\", \"image_preview_url\": \"https://url4\", \"title\": \"Test title 4\", \"short_description\": \"Short desc 4\"},"
                + "{ \"id\": \"test_id_5\", \"image_preview_url\": \"https://url5\", \"title\": \"Test title 5\", \"short_description\": \"Short desc 5\"},"
                + "{ \"id\": \"test_id_6\", \"image_preview_url\": \"https://url6\", \"title\": \"Test title 6\", \"short_description\": \"Short desc 6\"},"
                + "{ \"id\": \"test_id_7\", \"image_preview_url\": \"https://url7\", \"title\": \"Test title 7\", \"short_description\": \"Short desc 7\"},"
                + "{ \"id\": \"test_id_8\", \"image_preview_url\": \"https://url8\", \"title\": \"Test title 8\", \"short_description\": \"Short desc 8\"}"
                + "]"));
    }

    @NonNull
    public static ViewAction noOp() {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return null;
            }

            @Override
            public String getDescription() {
                return "no-op";
            }

            @Override
            public void perform(UiController uiController, View view) {
                // no-op
            }
        };
    }
}
