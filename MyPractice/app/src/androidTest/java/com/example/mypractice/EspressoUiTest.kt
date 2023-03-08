package com.example.mypractice

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.Rule
import org.junit.runner.RunWith
import androidx.test.ext.junit.rules.activityScenarioRule
import org.junit.Before
import org.junit.Test

@RunWith(AndroidJUnit4::class)
@LargeTest
class EspressoUiTest {
    //创建并启动测试中的Activity，并在每次测试后关闭
    @get:Rule var activityScenarioRule = activityScenarioRule<MainActivity>()

    @Before fun init() {

    }

    @Test fun test1() {
        onView(withId(R.id.btn_test1)).perform(click())
        onView(withId(R.id.tv_test1)).check(matches(withText("espresso")))
    }
}