package nextstep.signup.study

import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class InputValidationTest {

    @get:Rule
    val composeTestRule = createComposeRule()
    private var userName by mutableStateOf("")

    @Before
    fun setUp() {
        composeTestRule.setContent {
            val usernameEmpty = userName.isEmpty()
            val usernameLengthError = userName.length !in 2..5
            val isError = !usernameEmpty && usernameLengthError

            TextField(
                value = userName,
                onValueChange = { userName = it },
                isError = isError,
                supportingText = {
                    if (usernameEmpty) return@TextField
                    if (usernameLengthError) Text(text = "이름은 2~5자여야 합니다.")
                },
            )
        }
    }

    //    Given 이름 초깃값은 빈 문자열이다.
//    When 사용자가 "김컴포즈"라는 문자열을 입력한다.
//    Then 에러 메세지가 보여져서는 안된다.
    @Test
    fun 사용자_이름은_2에서_5자여야_한다() {
        // when
        userName = "김컴포즈"

        // then
        composeTestRule
            .onNodeWithText("이름은 2~5자여야 합니다.")
            .assertDoesNotExist()
    }

    //    Given 이름 초깃값은 빈 문자열이다.
//    When 사용자가 "김컴포즈입니다"라는 문자열을 입력한다.
//    Then "이름은 2~5자여야 합니다"라는 에러 메세지가 노출된다.
    @Test
    fun 사용자_이름이_2에서_5자가_아니면_에러메시지가_노출된다() {
        // when
        userName = "김컴포즈입니다"

        // then
        composeTestRule
            .onNodeWithText("이름은 2~5자여야 합니다.")
            .assertExists()
    }
}
