package com.example.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.FoodDonation
import com.example.data.AppRepository
import com.example.data.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class Screen {
    Splash, Login, Signup, Home
}

class MainViewModel(private val repository: AppRepository) : ViewModel() {

    private val _currentScreen = MutableStateFlow(Screen.Splash)
    val currentScreen: StateFlow<Screen> = _currentScreen

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser

    private val _authError = MutableStateFlow<String?>(null)
    val authError: StateFlow<String?> = _authError

    private val _selectedTown = MutableStateFlow("All Locations")
    val selectedTown: StateFlow<String> = _selectedTown

    private val _selectedDietary = MutableStateFlow("Any Dietary")
    val selectedDietary: StateFlow<String> = _selectedDietary

    val uiState: StateFlow<List<FoodDonation>> = combine(
        repository.allDonations,
        _selectedTown,
        _selectedDietary
    ) { donations, town, dietary ->
        donations.filter {
            (town == "All Locations" || it.town == town) &&
            (dietary == "Any Dietary" || dietary == "Any" || it.dietaryInfo == dietary)
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    init {
        viewModelScope.launch {
            // Seed default user
            if (repository.getUserByEmail("ashleymutangiri@gmail.com") == null) {
                repository.insertUser(
                    User(
                        name = "Ashley Mutangiri",
                        email = "ashleymutangiri@gmail.com",
                        passwordHash = "12345"
                    )
                )
            }

            if (repository.getCount() == 0) {
                // Seed initial data
                repository.insert(
                    FoodDonation(
                        title = "50 Loaves of Bread",
                        businessName = "Sunrise Bakery",
                        businessType = "Bakery",
                        volume = "50 items",
                        town = "Lusaka",
                        dietaryInfo = "Vegetarian"
                    )
                )
                repository.insert(
                    FoodDonation(
                        title = "Surplus Vegetables",
                        businessName = "Green Farms",
                        businessType = "Farm",
                        volume = "20kg",
                        town = "Ndola",
                        dietaryInfo = "Vegan"
                    )
                )
                repository.insert(
                    FoodDonation(
                        title = "Prepared Rice & Beans",
                        businessName = "Central Market Kitchen",
                        businessType = "Restaurant",
                        volume = "10 meals",
                        town = "Kitwe",
                        dietaryInfo = "Halal"
                    )
                )
            }
        }
    }

    fun navigateTo(screen: Screen) {
        _authError.value = null
        _currentScreen.value = screen
    }

    fun login(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            _authError.value = "Email and password cannot be empty."
            return
        }
        viewModelScope.launch {
            val user = repository.getUserByEmail(email)
            if (user != null && user.passwordHash == password) {
                _currentUser.value = user
                navigateTo(Screen.Home)
            } else {
                _authError.value = "Invalid email or password."
            }
        }
    }

    fun signup(name: String, email: String, password: String) {
        if (name.isBlank() || email.isBlank() || password.isBlank()) {
            _authError.value = "All fields are required."
            return
        }
        viewModelScope.launch {
            val existing = repository.getUserByEmail(email)
            if (existing != null) {
                _authError.value = "Account with this email already exists."
                return@launch
            }
            
            val newUser = User(name = name, email = email, passwordHash = password)
            repository.insertUser(newUser)
            
            // Automatically log in
            val createdUser = repository.getUserByEmail(email)
            _currentUser.value = createdUser
            navigateTo(Screen.Home)
        }
    }

    fun logout() {
        _currentUser.value = null
        navigateTo(Screen.Login)
    }

    fun updateTownFilter(town: String) {
        _selectedTown.value = town
    }

    fun updateDietaryFilter(dietary: String) {
        _selectedDietary.value = dietary
    }

    fun addDonation(donation: FoodDonation) {
        viewModelScope.launch {
            repository.insert(donation)
        }
    }

    fun claimDonation(id: Int, organizationName: String) {
        val userName = _currentUser.value?.name ?: organizationName
        viewModelScope.launch {
            repository.claim(id, "Claimed by $userName")
        }
    }
}
