package com.example.lecturadeenergia.Firebase

import com.google.firebase.auth.FirebaseAuth

fun obtenerUid(): String? {
    return FirebaseAuth.getInstance().currentUser?.uid
}