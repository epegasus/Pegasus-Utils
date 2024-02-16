package dev.pegasus.utils.utils

import android.app.SearchManager
import android.content.ActivityNotFoundException
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.annotation.StringRes
import dev.pegasus.utils.extensions.tools.printToErrorLog


/**
 * @Author: SOHAIB AHMED
 * @Date: 07,April,2023.
 * @Accounts
 *      -> https://github.com/epegasus
 *      -> https://www.linkedin.com/in/epegasus
 */

object PegasusSettingUtils {

    fun Context?.openPlayStoreApp() {
        this?.let {
            try {
                it.startActivity(
                    Intent(
                        Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=${it.packageName}")
                    )
                )
            } catch (ex: Exception) {
                ex.printToErrorLog("openPlayStoreApp")
            }
        }
    }

    fun Context?.rateUs() {
        this?.let {
            try {
                it.startActivity(
                    Intent(
                        Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=${it.packageName}")
                    )
                )
            } catch (ex: Exception) {
                ex.printToErrorLog("rateUs")
            }
        }
    }

    fun Context?.aboutUs(@StringRes linkStringId: Int) {
        this?.let {
            try {
                it.startActivity(
                    Intent(
                        Intent.ACTION_VIEW, Uri.parse(it.getString(linkStringId))
                    )
                )
            } catch (ex: Exception) {
                ex.printToErrorLog("aboutUs")
            }
        }
    }

    fun Context?.privacyPolicy(@StringRes policyLinkStringId: Int) {
        this?.let {
            try {
                it.startActivity(
                    Intent(
                        Intent.ACTION_VIEW, Uri.parse(it.getString(policyLinkStringId))
                    )
                )
            } catch (ex: Exception) {
                ex.printToErrorLog("privacyPolicy")
            }
        }
    }

    fun Context?.feedback(@StringRes emailStringId: Int, appNameStringId: Int) {
        this?.let {
            val emailIntent = Intent(Intent.ACTION_SEND)
            emailIntent.type = "message/rfc822"
            emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(it.getString(emailStringId)))
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, it.getString(appNameStringId))
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Feedback...")
            try {
                it.startActivity(Intent.createChooser(emailIntent, "Send mail..."))
            } catch (ex: ActivityNotFoundException) {
                ex.printToErrorLog("feedback")
            }
        }
    }

    fun Context?.shareApp(appNameStringId: Int) {
        this?.let {
            try {
                val sendIntent = Intent()
                sendIntent.action = Intent.ACTION_SEND
                sendIntent.putExtra(Intent.EXTRA_SUBJECT, it.getString(appNameStringId))
                sendIntent.putExtra(
                    Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=${it.packageName}"
                )
                sendIntent.type = "text/plain"
                it.startActivity(sendIntent)
            } catch (ex: Exception) {
                ex.printToErrorLog("shareApp")
            }
        }
    }

    fun Context?.copyClipboardData(text: String) {
        this?.let {
            try {
                val clipboard = it.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clip: ClipData = ClipData.newPlainText("simple text", text)
                clipboard.setPrimaryClip(clip)
            } catch (ex: Exception) {
                ex.printToErrorLog("copyClipboardData")
            }
        }
    }

    fun Context?.shareTextData(data: String) {
        this?.let {
            try {
                val intentTextData = Intent(Intent.ACTION_SEND)
                intentTextData.type = "text/plain"
                intentTextData.putExtra(Intent.EXTRA_SUBJECT, "Data")
                intentTextData.putExtra(Intent.EXTRA_TEXT, data)
                it.startActivity(Intent.createChooser(intentTextData, "Choose to share"))
            } catch (ex: Exception) {
                ex.printToErrorLog("shareTextData")
            }
        }
    }

    fun Context?.searchData(text: String) {
        this?.let {
            try {
                val intentSearch = Intent(Intent.ACTION_WEB_SEARCH)
                intentSearch.putExtra(SearchManager.QUERY, text)
                it.startActivity(intentSearch)
            } catch (ex: Exception) {
                ex.printToErrorLog("searchData")
            }
        }
    }

    fun Context?.translateDate(mData: String) {
        this?.let {
            try {
                val url = "https://translate.google.com/#view=home&op=translate&sl=auto&tl=en&text=$mData"
                val intentTranslate = Intent(Intent.ACTION_VIEW)
                intentTranslate.data = Uri.parse(url)
                it.startActivity(intentTranslate)
            } catch (ex: Exception) {
                ex.printToErrorLog("translateDate")
            }
        }
    }

    fun Context?.shareText(title: String, text: String) {
        this?.let {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT, text)
            it.startActivity(Intent.createChooser(intent, title))
        }
    }

    fun Context?.openSubscriptions() {
        this?.let {
            try {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/account/subscriptions?package=${it.packageName}")));
            } catch (ex: ActivityNotFoundException) {
                ex.printToErrorLog("openSubscriptions")
            }
        }
    }
}