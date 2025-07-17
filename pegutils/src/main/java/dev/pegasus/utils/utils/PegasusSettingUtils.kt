package dev.pegasus.utils.utils

import android.app.SearchManager
import android.content.ActivityNotFoundException
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.annotation.StringRes
import androidx.core.net.toUri
import dev.pegasus.utils.extensions.tools.printToErrorLog


/**
 * @Author: SOHAIB AHMED
 * @Date: 07,April,2023.
 * @Accounts
 *      -> https://github.com/epegasus
 *      -> https://www.linkedin.com/in/epegasus
 */


object PegasusSettingUtils {

    private fun Context?.safeContextOrReturn(): Context {
        this ?: throw IllegalStateException("Context is null")
        return this
    }

    /**
     * Helper function to open a URL with error handling.
     */
    fun Context?.openUrl(url: String, tag: String) {
        this ?: return
        try {
            val intent = Intent(Intent.ACTION_VIEW, url.toUri())
            startActivity(intent)
        } catch (ex: Exception) {
            ex.printToErrorLog(tag)
        }
    }

    fun Context?.openUrl(@StringRes urlResId: Int, tag: String) {
        this ?: return
        openUrl(getString(urlResId), tag)
    }

    /**
     * Opens the Play Store app page for this application.
     */
    fun Context?.openPlayStore() {
        this ?: return
        val url = "https://play.google.com/store/apps/details?id=${this.packageName}"
        openUrl(url, "openPlayStore")
    }

    /**
     * Opens the Play Store page to rate this app.
     */
    fun Context?.rateApp() = openPlayStore()

    /**
     * Opens a web link (e.g., About Us page) from a string resource.
     */
    fun Context?.openLink(@StringRes linkResId: Int) {
        this ?: return
        openUrl(linkResId, "openLink")
    }

    fun Context?.openLink(link: String) {
        this ?: return
        openUrl(link, "openLink")
    }

    /**
     * Opens the privacy policy link from a string resource.
     */
    fun Context?.openPrivacyPolicy(@StringRes linkResId: Int) = openLink(linkResId)

    /**
     * Sends feedback via email using provided string resource IDs.
     */
    fun Context?.sendFeedback(@StringRes emailResId: Int, @StringRes appNameResId: Int) {
        this ?: return
        try {
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "message/rfc822"
                putExtra(Intent.EXTRA_EMAIL, arrayOf(getString(emailResId)))
                putExtra(Intent.EXTRA_SUBJECT, getString(appNameResId))
                putExtra(Intent.EXTRA_TEXT, "Feedback...")
            }
            startActivity(Intent.createChooser(intent, "Send mail..."))
        } catch (ex: ActivityNotFoundException) {
            ex.printToErrorLog("sendFeedback")
        }
    }

    /**
     * Shares the app link using app name as subject.
     */
    fun Context?.shareApp(@StringRes titleResId: Int? = null, @StringRes messageResId: Int? = null) {
        this ?: return
        try {
            val title = titleResId?.let { getString(it) } ?: "Sharing app..."
            val body = messageResId?.let { getString(it) } ?: "Download this amazing app using this link: 'https://play.google.com/store/apps/details?id=$packageName'"
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_SUBJECT, title)
                putExtra(Intent.EXTRA_TEXT, body)
            }
            startActivity(Intent.createChooser(intent, "Share app via"))
        } catch (ex: Exception) {
            ex.printToErrorLog("shareApp")
        }
    }

    /**
     * Copies text to clipboard.
     */
    fun Context?.copyToClipboard(label: String = "Copied Text", text: String) {
        this ?: return
        try {
            val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as? ClipboardManager
            clipboard?.setPrimaryClip(ClipData.newPlainText(label, text))
        } catch (ex: Exception) {
            ex.printToErrorLog("copyToClipboard")
        }
    }

    /**
     * Shares a plain text message.
     */
    fun Context?.shareText(title: String, text: String) {
        this ?: return
        try {
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, text)
            }
            startActivity(Intent.createChooser(intent, title))
        } catch (ex: Exception) {
            ex.printToErrorLog("shareText")
        }
    }

    /**
     * Opens browser to Google Translate with the given query.
     */
    fun Context?.translateText(text: String) {
        this ?: return
        val encodedText = Uri.encode(text)
        val url = "https://translate.google.com/?sl=auto&tl=en&text=$encodedText&op=translate"
        openUrl(url, "translateText")
    }

    /**
     * Performs a web search with the given text.
     */
    fun Context?.searchWeb(text: String) {
        this ?: return
        try {
            val intent = Intent(Intent.ACTION_WEB_SEARCH).apply {
                putExtra(SearchManager.QUERY, text)
            }
            startActivity(intent)
        } catch (ex: Exception) {
            ex.printToErrorLog("searchWeb")
        }
    }

    /**
     * Opens Play Store subscription management screen.
     */
    fun Context?.openSubscriptions() {
        this ?: return
        try {
            val url = "https://play.google.com/store/account/subscriptions?package=$packageName"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        } catch (ex: ActivityNotFoundException) {
            ex.printToErrorLog("openSubscriptions")
        }
    }

}