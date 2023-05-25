package dev.pegasus.utils.fragments

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dev.pegasus.utils.R
import dev.pegasus.utils.extensions.dataTypes.isNull
import dev.pegasus.utils.extensions.ui.showToast
import dev.pegasus.utils.utils.PegasusFileUtils

/**
 * @Author: SOHAIB AHMED
 * @Date: 25,May,2023.
 * @Accounts
 *      -> https://github.com/epegasus
 *      -> https://www.linkedin.com/in/epegasus
 */

open class BasePermissionFragment : Fragment() {

    private var sharedPreferences: SharedPreferences? = null
    private var editor: SharedPreferences.Editor? = null

    private var callback: ((Boolean) -> Unit)? = null
    private var permissionName: String? = null
    private var permissionArray = arrayOf<String>()

    private val permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
        val values: Collection<Boolean> = it.values
        val contains = values.contains(true)
        callback?.invoke(contains)
    }

    private var settingLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { _ ->
        permissionName?.let { permissionLauncher.launch(permissionArray) }
    }

    protected fun checkStoragePermission(): Boolean {
        context?.let {
            val permissionName = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                Manifest.permission.READ_MEDIA_IMAGES
            } else {
                Manifest.permission.READ_EXTERNAL_STORAGE
            }
            return (ContextCompat.checkSelfPermission(it, permissionName) == PackageManager.PERMISSION_GRANTED)
        } ?: return false
    }

    protected fun askStoragePermission(callback: (Boolean) -> Unit) {
        this.callback = callback
        context?.let {
            if (sharedPreferences.isNull || editor.isNull) {
                sharedPreferences = it.getSharedPreferences("permission_preferences", Context.MODE_PRIVATE).also { sp ->
                    editor = sp.edit()
                }
            }

            val permissionName = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                Manifest.permission.READ_MEDIA_IMAGES
            } else {
                Manifest.permission.READ_EXTERNAL_STORAGE
            }
            permissionArray = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                arrayOf(Manifest.permission.READ_MEDIA_IMAGES)
            } else {
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
            this.permissionName = permissionName
            if (ContextCompat.checkSelfPermission(it, permissionName) == PackageManager.PERMISSION_GRANTED) {
                callback.invoke(true)
            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(context as Activity, permissionName))
                    showPermissionDialog()
                else {
                    if (sharedPreferences?.getBoolean(permissionName, true) == true) {
                        editor?.putBoolean(permissionName, false)
                        editor?.apply()
                        permissionLauncher.launch(permissionArray)
                    } else {
                        showSettingDialog()
                    }
                }
            }
        }
    }

    @Synchronized
    private fun showPermissionDialog() {
        val builder = context?.let {
            MaterialAlertDialogBuilder(it)
                .setTitle("Permission")
                .setMessage("Allow this app to access photos media on your device.")
                .setCancelable(false)
                .setPositiveButton("Allow") { dialogInterface, _ ->
                    dialogInterface.dismiss()
                    openSettingPage()
                }
                .setNegativeButton("Deny") { dialogInterface, _ ->
                    dialogInterface.dismiss()
                }
        }

        if (!(context as Activity).isFinishing)
            builder?.show()
    }

    private fun showSettingDialog() {
        val builder = context?.let {
            MaterialAlertDialogBuilder(it)
                .setTitle("Permission required")
                .setMessage("Allow permission from 'Setting' to proceed")
                .setCancelable(false)
                .setPositiveButton("Setting") { dialogInterface, _ ->
                    dialogInterface.dismiss()
                    openSettingPage()
                }
                .setNegativeButton("Cancel") { dialogInterface, _ ->
                    dialogInterface.dismiss()
                }
        }

        if (!(context as Activity).isFinishing)
            builder?.show()
    }

    private fun openSettingPage() {
        context?.let {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            val uri: Uri = Uri.fromParts("package", it.packageName, null)
            intent.data = uri
            settingLauncher.launch(intent)
        }
    }

    /* ------------------------------------------ File Check ------------------------------------------ */

    /**
     * @param filePath: Path to the file that is going to be used
     * @return true if the file exists, false otherwise
     */

    fun checkFile(filePath: String): Boolean {
        return if (checkStoragePermission()) {
            if (!PegasusFileUtils.isFileValid(filePath)) {
                showToast(R.string.file_not_found)
                false
            } else true
        } else false
    }
}