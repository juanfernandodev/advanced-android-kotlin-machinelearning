package com.juanferdev.appperrona

import android.Manifest
import androidx.camera.core.ImageProxy
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.GrantPermissionRule
import com.juanferdev.appperrona.api.ApiResponseStatus
import com.juanferdev.appperrona.constants.SemanticConstants.SEMANTIC_DETAIL_DOG_BUTTON
import com.juanferdev.appperrona.di.ClassifierRepositoryContractModule
import com.juanferdev.appperrona.di.DogRepositoryModule
import com.juanferdev.appperrona.doglist.DogRepositoryContract
import com.juanferdev.appperrona.machinelearning.ClassifierRepositoryContract
import com.juanferdev.appperrona.machinelearning.DogRecognition
import com.juanferdev.appperrona.main.MainActivity
import com.juanferdev.appperrona.models.Dog
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@UninstallModules(DogRepositoryModule::class, ClassifierRepositoryContractModule::class)
@HiltAndroidTest
class MainActivityTest {

    //Always the first one
    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val runtimePermissionRule: GrantPermissionRule =
        GrantPermissionRule.grant(Manifest.permission.CAMERA)

    @get:Rule(order = 2)
    val composeTestRule = createComposeRule()

    //Always the last one
    @get:Rule(order = 3)
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun registerIdlingResource() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.idlingResource)
    }

    @After
    fun unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.idlingResource)
    }

    @Test
    fun showAllFab() {
        onView(withId(R.id.take_photo_fab))
            .check(matches(isDisplayed()))
        onView(withId(R.id.dog_list_fab))
            .check(matches(isDisplayed()))
        onView(withId(R.id.settings_fab))
            .check(matches(isDisplayed()))
    }

    @Test
    fun dogListOpensWhenClickingButton() {
        onView(withId(R.id.dog_list_fab)).perform(click())
        val context =
            InstrumentationRegistry.getInstrumentation().targetContext // <-- Getting the context

        val string = context.getString(R.string.my_dog_collection)

        composeTestRule.onNodeWithText(
            text = string
        ).assertIsDisplayed()
    }

    @Test
    fun whenRecognizingDogDetailsScreenOpens() {
        onView(withId(R.id.take_photo_fab)).perform(click())
        composeTestRule.onNodeWithTag(
            testTag = SEMANTIC_DETAIL_DOG_BUTTON
        ).assertIsDisplayed()
    }




    class FakeSuccessDogRepository @Inject constructor() : DogRepositoryContract {
        override suspend fun getDogCollection(): ApiResponseStatus<List<Dog>> {
            return ApiResponseStatus.Success(
                listOf(
                    Dog(id = 1),
                    Dog(id = 2),
                    Dog(id = 3)
                )
            )
        }

        override suspend fun addDogToUser(dogId: Long): ApiResponseStatus<Any> {
            return ApiResponseStatus.Success(Unit)
        }

        override suspend fun getRecognizedDog(capturedDogId: String): ApiResponseStatus<Dog> {
            return ApiResponseStatus.Success(Dog(id = capturedDogId.toLong()))
        }

        override fun getProbableDogs(probableDogsIds: List<String>): Flow<ApiResponseStatus<Dog>> {
            TODO("Not yet implemented")
        }
    }

    class FakeSuccessClassifierRepository @Inject constructor() : ClassifierRepositoryContract {
        override suspend fun recognizedImage(imageProxy: ImageProxy): List<DogRecognition> =
            listOf(DogRecognition(id = "1", confidence = 70.0F))
    }

    @Module
    @InstallIn(ViewModelComponent::class)
    fun interface DogRepositoryModule {

        @Binds
        fun bindDogRepository(
            dogRepository: FakeSuccessDogRepository
        ): DogRepositoryContract
    }

    @Module
    @InstallIn(ViewModelComponent::class)
    fun interface ClassifierRepositoryContractModule {

        @Binds
        fun provideClassifierRepositoryContract(classifierRepository: FakeSuccessClassifierRepository): ClassifierRepositoryContract
    }
}