package com.example.bmi

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun mainActivityTest() {
        val appCompatEditText = onView(withId(R.id.massTextField))
        appCompatEditText.perform(click())

        val appCompatEditText2 = onView(withId(R.id.massTextField))
        appCompatEditText2.perform(replaceText("70"), closeSoftKeyboard())

        val appCompatEditText3 = onView(withId(R.id.heightTextField))
        appCompatEditText3.perform(replaceText("170"), closeSoftKeyboard())

        val appCompatButton = onView(withId(R.id.countButton))
        appCompatButton.perform(click())

        val textView = onView(withId(R.id.BMIResultNumber))
        textView.check(matches(withText("24.22")))
    }

}
