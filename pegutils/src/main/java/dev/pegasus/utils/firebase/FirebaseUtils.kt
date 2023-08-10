package dev.pegasus.utils.firebase

import android.os.Bundle
import android.util.Log
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.installations.FirebaseInstallations
import com.google.firebase.ktx.Firebase

/**
 * @Author: SOHAIB AHMED
 * @Date: 10,August,2023.
 * @Accounts
 *      -> https://github.com/epegasus
 *      -> https://www.linkedin.com/in/epegasus
 */

object FirebaseUtils {

    private const val TAG_FIREBASE = "MyTag"

    /**
     *  Syntax:
     *      try {}
     *      catch(ex: Exception){
     *          ex.recordException("MainActivity > OnCreate > getList")
     *      }
     */

    fun Throwable.recordException(log: String) {
        try {
            FirebaseCrashlytics.getInstance().log(log)
            FirebaseCrashlytics.getInstance().recordException(this)
            Log.e(TAG_FIREBASE, "recordException: ${this.message.toString()}")
        } catch (e: Exception) {
            Log.e(TAG_FIREBASE, "recordException: ${this.message.toString()}")
        }
    }

    /**
     *  Syntax:
     *      EventsProvider.HOME_SCREEN.postFirebaseEvent()
     *      EventsProvider.START_BUTTON.postFirebaseEvent()
     */

    fun String.postFirebaseEvent() {
        try {
            val firebaseAnalytics: FirebaseAnalytics = Firebase.analytics
            val bundle = Bundle().also {
                it.putString(this, this)
            }
            firebaseAnalytics.logEvent(this, bundle)
            Log.d(TAG_FIREBASE, "postFirebaseEvent:$this successfully sent")
        } catch (ex: Exception) {
            ex.recordException("post_event_crash > $this")
        }
    }

    fun getDeviceToken() {
        // Add this 'id' in firebase AB testing console as a testing device
        FirebaseInstallations.getInstance().getToken(false)
            .addOnCompleteListener { task ->
                if (task.isSuccessful && task.result != null) {
                    Log.d(TAG_FIREBASE, "Installation auth token: " + task.result.token)
                } else {
                    Log.e(TAG_FIREBASE, "Unable to get Installation auth token")
                }
            }
    }
}