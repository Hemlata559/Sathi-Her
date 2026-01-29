package com.sathiher.app.ui.profile


import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

fun saveProfileToFirestore(
    name: String,
    age: String,
    city: String,
    onSuccess: () -> Unit
) {
    val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return

    val profileData = hashMapOf(
        "name" to name,
        "age" to age,
        "city" to city,
        "profileCompleted" to true
    )

    FirebaseFirestore.getInstance()
        .collection("users")
        .document(uid)
        .set(profileData)
        .addOnSuccessListener {
            onSuccess()
        }
}

