package com.sub_zet.medal;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.sub_zet.medal.activities.LoginActivity;
import com.sub_zet.medal.activities.MenuActivity;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.regex.Matcher;

import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */


@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

//    @Rule
//    public ActivityTestRule<LoginActivity> intentsTestRule = new ActivityTestRule<>(LoginActivity.class);
    @Rule
    public ActivityTestRule<MenuActivity> intentsTestRule = new ActivityTestRule<>(MenuActivity.class);

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        assertEquals("com.sub_zet.medal", appContext.getPackageName());

//        Espresso.onView(ViewMatchers.withText("Login with Google")).perform(ViewActions.click());
        try {
            // MenuActivity
            Thread.sleep(2000);
            Espresso.onView(ViewMatchers.withId(R.id.platform_recycler)).perform(
                    RecyclerViewActions.actionOnItemAtPosition(1, ViewActions.click()));
            Thread.sleep(500);
            Espresso.onView(ViewMatchers.withId(R.id.games_recycler)).perform(
                    RecyclerViewActions.actionOnItemAtPosition(1, ViewActions.click()));

            Espresso.onView(ViewMatchers.withText("Choose price")).check(matches(ViewMatchers.isDisplayed()));


            // SelectedGameActivity
            Espresso.onView(ViewMatchers.withHint("Enter your game nickname"))
                    .perform(ViewActions.typeText("aa"));
            Espresso.closeSoftKeyboard();
            Espresso.onView(ViewMatchers.withText("SEARCH")).perform(ViewActions.click());

            //SearchOpponentActivity
            Thread.sleep(5000);
            Espresso.onView(ViewMatchers.withText("Ready")).perform(ViewActions.click());

            Thread.sleep(2000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
