package com.example.apparduino3.ui.auth

import com.example.apparduino3.data.AuthRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test

class AuthViewModelTest {

    private lateinit var authViewModel: AuthViewModel
    private val authRepository: AuthRepository = mockk(relaxed = true)

    @Before
    fun setUp() {
        authViewModel = AuthViewModel(authRepository)
    }

    @Test
    fun `login success`() {
        // Given
        every { authRepository.login(any(), any(), any()) } answers { 
            val onComplete = arg< (Boolean) -> Unit>(2)
            onComplete(true)
        }

        // When
        var successResult: Boolean? = null
        authViewModel.login("test@test.com", "password") { success ->
            successResult = success
        }

        // Then
        verify { authRepository.login("test@test.com", "password", any()) }
        assert(successResult == true)
    }

    @Test
    fun `login failure`() {
        // Given
        every { authRepository.login(any(), any(), any()) } answers { 
            val onComplete = arg< (Boolean) -> Unit>(2)
            onComplete(false)
        }

        // When
        var successResult: Boolean? = null
        authViewModel.login("test@test.com", "password") { success ->
            successResult = success
        }

        // Then
        verify { authRepository.login("test@test.com", "password", any()) }
        assert(successResult == false)
    }
}
