package dev.pegasus.utils.base.fragments

import android.Manifest
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
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dev.pegasus.utils.extensions.dataTypes.isNull

/**
 * @Author: SOHAIB AHMED
 * @Date: 25,May,2023.
 * @Accounts
 *      -> https://github.com/epegasus
 *      -> https://www.linkedin.com/in/epegasus
 */

open class PermissionFragment : Fragment() {

    private var sharedPreferences: SharedPreferences? = null
    private var callback: ((Boolean) -> Unit)? = null

    private lateinit var permissionName: String
    private var permissionArray = arrayOf<String>()

    private val permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
        val granted = permissions.values.any { it }
        callback?.invoke(granted)
    }

    private var settingLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { _ ->
        permissionLauncher.launch(permissionArray)
    }

    protected fun checkStoragePermission(): Boolean {
        val context = context ?: return false
        val permission = getStoragePermissionName()
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
    }

    protected fun askStoragePermission(callback: (Boolean) -> Unit) {
        val context = context ?: return
        initPreferences(context)

        this.callback = callback
        permissionName = getStoragePermissionName()
        permissionArray = getStoragePermissionArray()

        if (ContextCompat.checkSelfPermission(context, permissionName) == PackageManager.PERMISSION_GRANTED) {
            callback(true)
        } else {
            val activity = requireActivity()
            when {
                ActivityCompat.shouldShowRequestPermissionRationale(activity, permissionName) -> {
                    showPermissionDialog()
                }

                sharedPreferences?.getBoolean(permissionName, true) == true -> {
                    sharedPreferences?.edit { putBoolean(permissionName, false) }
                    permissionLauncher.launch(permissionArray)
                }

                else -> {
                    showSettingDialog()
                }
            }
        }
    }

    private fun getStoragePermissionName(): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Manifest.permission.READ_MEDIA_IMAGES
        } else {
            Manifest.permission.READ_EXTERNAL_STORAGE
        }
    }

    private fun getStoragePermissionArray(): Array<String> {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arrayOf(Manifest.permission.READ_MEDIA_IMAGES)
        } else {
            arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        }
    }

    private fun initPreferences(context: Context) {
        if (sharedPreferences.isNull) {
            sharedPreferences = context.getSharedPreferences("permission_preferences", Context.MODE_PRIVATE)
        }
    }

    @Synchronized
    private fun showPermissionDialog() {
        val act = activity ?: return
        if (act.isFinishing) return

        MaterialAlertDialogBuilder(act)
            .setTitle("Permission")
            .setMessage("Allow this app to access photos and media on your device.")
            .setCancelable(false)
            .setPositiveButton("Allow") { dialog, _ ->
                dialog.dismiss()
                openSettingPage()
            }
            .setNegativeButton("Deny") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun showSettingDialog() {
        val act = activity ?: return
        if (act.isFinishing) return

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Permission required")
            .setMessage("Allow permission from 'Settings' to proceed.")
            .setCancelable(false)
            .setPositiveButton("Settings") { dialog, _ ->
                dialog.dismiss()
                openSettingPage()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun openSettingPage() {
        val ctx = context ?: return
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = Uri.fromParts("package", ctx.packageName, null)
        }
        settingLauncher.launch(intent)
    }
}