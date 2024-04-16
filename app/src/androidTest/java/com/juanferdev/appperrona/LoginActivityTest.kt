package com.juanferdev.appperrona

import android.Manifest
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.GrantPermissionRule
import com.juanferdev.appperrona.api.ApiResponseStatus
import com.juanferdev.appperrona.auth.AuthRepositoryContract
import com.juanferdev.appperrona.auth.LoginActivity
import com.juanferdev.appperrona.constants.SemanticConstants.SEMANTIC_EMAIL_FIELD
import com.juanferdev.appperrona.constants.SemanticConstants.SEMANTIC_LOGIN_BUTTON
import com.juanferdev.appperrona.constants.SemanticConstants.SEMANTIC_PASSWORD_FIELD
import com.juanferdev.appperrona.di.AuthRepositoryModule
import com.juanferdev.appperrona.models.User
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import javax.inject.Inject
import org.junit.Rule
import org.junit.Test

@UninstallModules(AuthRepositoryModule::class)
@HiltAndroidTest
class LoginActivityTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val runtimePermissionRule: GrantPermissionRule =
        GrantPermissionRule.grant(Manifest.permission.CAMERA)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<LoginActivity>()


    @Test
    fun onUserLoginWhenAllIsGoodThenOpenMainActivity() {
        val context = composeTestRule.activity
        composeTestRule.onNodeWithText(
            text = context.getString(R.string.login)
        ).assertIsDisplayed()

        composeTestRule
            .onNodeWithTag(testTag = SEMANTIC_EMAIL_FIELD)
            .performTextInput("example@email.com")

        composeTestRule
            .onNodeWithTag(testTag = SEMANTIC_PASSWORD_FIELD)
            .performTextInput("12345678")

        composeTestRule.onNodeWithTag(
            testTag = SEMANTIC_LOGIN_BUTTON
        ).performClick()

        Espresso.onView(withId(R.id.take_photo_fab))
            .check(matches(isDisplayed()))


    }


    class FakeAuthRepository @Inject constructor() : AuthRepositoryContract {
        private val user = User(
            id = 121212L,
            email = "fernaanxd17@gmail.com",
            authenticationToken = "kajdlfjadlfj923j239j"
        )

        override suspend fun signUp(
            email: String,
            password: String,
            passwordConfirmation: String
        ): ApiResponseStatus<User> {
            return ApiResponseStatus.Success(user)
        }

        override suspend fun login(email: String, password: String): ApiResponseStatus<User> {
            return ApiResponseStatus.Success(user)
        }

    }

    @Module
    @InstallIn(ViewModelComponent::class)
    fun interface AuthRepositoryModule {
        @Binds
        fun provideAuthRepository(authRepository: FakeAuthRepository): AuthRepositoryContract
    }


}