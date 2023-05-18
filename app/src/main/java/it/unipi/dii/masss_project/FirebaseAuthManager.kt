package it.unipi.dii.masss_project

import android.content.Context
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import it.unipi.dii.masss_project.databinding.ActivityMainBinding

class FirebaseAuthManager(context: Context, binding: ActivityMainBinding,
                          private val firestoreManager: FirestoreManager
) {
    // initialize application context
    private val appContext = context

    // initialize activity main binding
    private val appMainBinding = binding

    // initialize utility class
    private val util = Util(appContext, appMainBinding)

    // initialize firebase authentication
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun checkIfUserExists(email: String, callback: (Boolean) -> Unit){
        auth.fetchSignInMethodsForEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val result = task.result?.signInMethods
                    if (result?.contains(EmailAuthProvider.EMAIL_PASSWORD_SIGN_IN_METHOD) == true) {
                        callback(true)
                    } else {
                        callback(false)
                    }
                }else {
                    // handle error
                    util.showErrorToast("Error in checking user existence")
                    task.exception?.message?.let { util.showErrorTextView(it) }
                }
            }
    }

    fun loginUser(email: String, password: String, callback: (Boolean) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callback(true)
                } else {
                    util.showErrorToast("Authentication failed")
                    task.exception?.message?.let { util.showErrorTextView(it) }
                    callback(false)
                }
            }
    }

    fun registerUser(email: String, password: String, callback: (Boolean) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user: FirebaseUser? = auth.currentUser
                    val userId: String? = user?.uid
                    if (userId != null) {
                        firestoreManager.createUserDocument(userId, email, password) { success ->
                            callback(success)
                        }
                    } else {
                        callback(false)
                    }
                } else {
                    util.showErrorToast("Registration failed")
                    task.exception?.message?.let { util.showErrorTextView(it) }
                    callback(false)
                }
            }
    }

    fun logoutUser(){
        auth.signOut()
    }
}