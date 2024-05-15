package com.devrex.sharedtest

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.Rule
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.MockitoAnnotations

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnit4::class)
open class UseCaseTestWatcher : TestWatcher() {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    val testDispatcher = StandardTestDispatcher()
    val testCoroutineScope = TestScope(testDispatcher)

    override fun starting(description: Description) {
        super.starting(description)
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description) {
        super.finished(description)
        Dispatchers.resetMain()
    }

}