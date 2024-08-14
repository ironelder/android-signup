package nextstep.signup.study

import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Rule
import org.junit.Test

class RecompositionTest {
    @get:Rule
    val composeTestRule = createComposeRule()
    private var userName by mutableStateOf("")
    private var other by mutableStateOf("")
    private var count = 0

    @Composable
    fun UserNameTextField(
        userName: String,
        other: String
    ) {
        val usernameLengthError = (userName.length !in 2..5)
            .also { count++ }
        TextField(
            value = userName,
            onValueChange = {},
            isError = usernameLengthError
        )
    }

    @Test
    fun 리컴포지션될때_매번_유효성_검사() {
        composeTestRule.setContent {
            UserNameTextField(userName, other)
        }
        count = 0

        userName = "김컴포즈"
        composeTestRule.waitForIdle()
        assert(count == 1)

        other = "김컴포즈2"
        composeTestRule.waitForIdle()
        assert(count == 2)
    }


    @Composable
    fun UserNameTextFieldWithRemember(
        userName: String,
        other: String
    ) {
        val usernameLengthError = remember(key1 = userName) {
            (userName.length !in 2..5)
                .also { count++ }
        }
        TextField(
            value = userName,
            onValueChange = {},
            isError = usernameLengthError
        )
    }

    @Test
    fun 리컴포지션될때_매번_유효성_검사2() {
        composeTestRule.setContent {
            UserNameTextFieldWithRemember(userName, other)
        }
        count = 0

        userName = "김컴포즈"
        composeTestRule.waitForIdle()
        assert(count == 1)

        other = "김컴포즈2"
        composeTestRule.waitForIdle()
        assert(count == 1)
    }
}
