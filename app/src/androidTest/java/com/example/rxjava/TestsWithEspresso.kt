package com.example.rxjava

import android.view.View
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.rxjava.app.activities.MainActivity
import com.example.rxjava.home.adapters.ItemHomeGenresAdapter
import com.example.rxjava.home.ui.HomeFragment
import org.hamcrest.Matcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TestsWithEspresso {

    @Rule
    @JvmField
    val rule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testEventFragment() {
        val scenario = launchFragmentInContainer<HomeFragment>(
            initialState = Lifecycle.State.INITIALIZED
        )
        scenario.moveToState(Lifecycle.State.RESUMED)
    }

    @Test
    fun testDisplayedPosterImage() {
        onView(isRoot()).perform(waitFor(2000))
        onView(withId(R.id.ivImagePoster)).check(matches(isDisplayed()))
    }

    @Test
    fun testRecyclerView() {
            onView(isRoot()).perform(waitFor(2000))
            onView(withId(R.id.recyclerView)).perform(
                RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                    3
                )
            )
    }

    @Test
    fun testClickOnItemRecyclerView() {
        onView(isRoot()).perform(waitFor(2000))
        onView(withId(R.id.recyclerView)).perform(
            RecyclerViewActions.actionOnItemAtPosition<ItemHomeGenresAdapter.PrevPosterViewHolder>(
                2,
                click()
            )
        )
    }

    private fun waitFor(delay: Long): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> = isRoot()
            override fun getDescription(): String = "wait for $delay milliseconds"
            override fun perform(uiController: UiController, v: View?) {
                uiController.loopMainThreadForAtLeast(delay)
            }
        }
    }
}