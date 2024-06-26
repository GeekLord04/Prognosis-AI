package com.example.prognosisai.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prognosisai.Repository.AuthRepositoryImpl
import com.example.prognosisai.data.Hospital
import com.example.prognosisai.data.Patient
import com.example.prognosisai.utils.NetworkResource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val repository: AuthRepositoryImpl, private val firebaseAuth: FirebaseAuth) : ViewModel() {
    val userSignupResponseLiveData: LiveData<NetworkResource<String>>
        get() = repository.userSignupResponseLiveData

    val userSignInResponseLiveData: LiveData<NetworkResource<String>>
        get() = repository.userSignInResponseLiveData

    val forgetpassResponseLiveData: LiveData<NetworkResource<String>>
        get() = repository.forgetpassResponseLiveData

    val emailVerfResponseLiveData: LiveData<NetworkResource<String>>
        get() = repository.emailVerfResponseLiveData


    val storingHospitalsDetails: LiveData<NetworkResource<String>>
        get() = repository.storingHospitalsDetails

    val storingPatientDetails: LiveData<NetworkResource<String>>
        get() = repository.storingPatientDetails

    suspend fun signupUsingEmailAndPassword(hospital: Hospital) {
        viewModelScope.launch {
            repository.signUpWithEmail(hospital)
        }
    }
    suspend fun signInusingEmailAndPassword(hospital: Hospital){
        viewModelScope.launch {
            repository.signInWithEmail(hospital)
        }
    }

    suspend fun forgetPasswordUsingEmail(hospital: Hospital){
        viewModelScope.launch {
            repository.forgotPassword(hospital)
        }
    }

    suspend fun emailVerificationUsingEmail(){
        viewModelScope.launch {
            repository.emailVerification()
        }
    }

    suspend fun checkMailVerificationUsingEmail(): Boolean {
        //firebaseAuth.currentUser!!.reload()
        delay(300)
        val str = viewModelScope.async {
            repository.checkEmailVerification()
        }
        return str.await()
    }
    suspend fun alreadyLogedInUsingEmail(): Boolean{
        val str = viewModelScope.async {
            repository.alReadyLogedIn()
        }
        return str.await()
    }

    suspend fun storeHospitalData(hospital: Hospital) {
        viewModelScope.launch {
            repository.storingHospitalDetailsRDB(hospital)
        }
    }

    suspend fun storePatientData(patient: Patient){
        viewModelScope.launch {
            repository.storingPatientDetailsRDB(patient)
        }
    }
}