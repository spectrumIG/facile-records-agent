package it.facile.records.agent

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import org.junit.Rule
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    var activityRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

//    @Test
//    fun verify_button_is_disabled_correctly() {
//        onView(withId(R.id.app_bar_search)).perform(click())
//        onView(withId(R.id.search_button)).check(matches(not(isEnabled())))
//    }
//
//    @Test
//    fun verify_button_is_enabled_after_inserting_one_filter() {
//        onView(withId(R.id.app_bar_search)).perform(click())
//        onView(withId(R.id.brewed_before)).perform(typeText("04-2013"))
//        onView(withId(R.id.search_button)).check(matches((isEnabled())))
//    }

}
