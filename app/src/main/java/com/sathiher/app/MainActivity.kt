package com.sathiher.app

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()

        setContent {
            MaterialTheme {
                PhoneLoginScreen(auth = auth, activity = this)
            }
        }
    }
}

@Composable
fun PhoneLoginScreen(
    auth: FirebaseAuth,
    activity: ComponentActivity
) {
    var phoneNumber by remember { mutableStateOf("") }
    var otp by remember { mutableStateOf("") }
    var verificationId by remember { mutableStateOf<String?>(null) }
    var isOtpVerified by remember { mutableStateOf(false) }

    // ✅ AFTER OTP VERIFIED → HOME SCREEN
    if (isOtpVerified) {
        HomeScreen()
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text("SathiHer", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(24.dp))

        // 📱 PHONE NUMBER SCREEN
        if (verificationId == null) {

            OutlinedTextField(
                value = phoneNumber,
                onValueChange = { phoneNumber = it },
                label = { Text("Phone Number") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    val phone = "+1$phoneNumber" // change country code if needed

                    val options = PhoneAuthOptions.newBuilder(auth)
                        .setPhoneNumber(phone)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(activity)
                        .setCallbacks(object :
                            PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                            override fun onVerificationCompleted(
                                credential: PhoneAuthCredential
                            ) {
                                auth.signInWithCredential(credential)
                                    .addOnSuccessListener {
                                        Log.d("OTP", "Auto verified")
                                        isOtpVerified = true
                                    }
                            }

                            override fun onVerificationFailed(e: FirebaseException) {
                                Log.e("OTP", "Verification failed", e)
                            }

                            override fun onCodeSent(
                                id: String,
                                token: PhoneAuthProvider.ForceResendingToken
                            ) {
                                verificationId = id
                                Log.d("OTP", "OTP sent")
                            }
                        })
                        .build()

                    PhoneAuthProvider.verifyPhoneNumber(options)
                }
            ) {
                Text("Send OTP")
            }

        } else {
            // 🔐 OTP SCREEN
            OutlinedTextField(
                value = otp,
                onValueChange = { otp = it },
                label = { Text("Enter OTP") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    val credential = PhoneAuthProvider.getCredential(
                        verificationId!!,
                        otp
                    )

                    auth.signInWithCredential(credential)
                        .addOnSuccessListener {
                            Log.d("OTP", "OTP verified successfully")
                            isOtpVerified = true
                        }
                        .addOnFailureListener {
                            Log.e("OTP", "Invalid OTP", it)
                        }
                }
            ) {
                Text("Verify OTP")
            }
        }
    }
}

@Composable
fun HomeScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Welcome 🎉", style = MaterialTheme.typography.headlineMedium)
    }
}




