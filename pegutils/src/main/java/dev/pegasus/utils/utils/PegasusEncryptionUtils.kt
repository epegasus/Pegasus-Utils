package dev.pegasus.utils.utils

import android.os.Environment
import android.util.Log
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import javax.crypto.Cipher
import javax.crypto.CipherInputStream
import javax.crypto.CipherOutputStream
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

/**
 * @Author: SOHAIB AHMED
 * @Date: 01,June,2023.
 * @Accounts
 *      -> https://github.com/epegasus
 *      -> https://www.linkedin.com/in/epegasus
 */

object PegasusEncryptionUtils {

    var appFolder = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), ".doNotDelete")
    private val encryptionFilePath = File(appFolder, ".do_not_delete_security_purposes.txt")

    private const val ALGORITHM = "AES"
    private const val TRANSFORMATION = "AES"

    /**
     * Must be a path of folder, where file should be store
     */

    fun setFolderPath(file: File) {
        appFolder = file
    }

    fun encryptText(data: String, password: String) {
        try {
            encryptionFilePath.parentFile?.mkdirs()
            val outputStream = FileOutputStream(encryptionFilePath)
            val inputStream = ByteArrayInputStream(data.toByteArray())
            encrypt(inputStream, outputStream, password)
            inputStream.close()
            outputStream.close()
        } catch (ex: IOException) {
            Log.e(PegasusHelperUtils.TAG, "saveEncryptedFile: ", ex)
            ex.printStackTrace()
        }
    }

    fun decryptText(password: String): String {
        try {
            encryptionFilePath.parentFile?.mkdirs()
            val inputStream = FileInputStream(encryptionFilePath)
            val outputStream = ByteArrayOutputStream()
            decrypt(inputStream, outputStream, password)
            val decryptedData = outputStream.toString()
            inputStream.close()
            outputStream.close()
            return decryptedData
        } catch (ex: IOException) {
            Log.e(PegasusHelperUtils.TAG, "decryptFile: ", ex)
            ex.printStackTrace()
        }
        return ""
    }

    fun isTextFileExist(): Boolean {
        return try {
            encryptionFilePath.exists()
        } catch (ex: IOException) {
            false
        }
    }

    private fun generateSecretKey(password: String): SecretKey {
        val keyGenerator = KeyGenerator.getInstance(ALGORITHM)
        keyGenerator.init(256)
        return SecretKeySpec(password.toByteArray(), ALGORITHM)
    }

    private fun encrypt(input: InputStream, output: OutputStream, password: String) {
        try {
            val secretKey = generateSecretKey(password)
            val cipher = Cipher.getInstance(TRANSFORMATION)
            cipher.init(Cipher.ENCRYPT_MODE, secretKey)

            val outputStream = CipherOutputStream(output, cipher)
            val buffer = ByteArray(1024)
            var read: Int
            while (input.read(buffer).also { read = it } >= 0) {
                outputStream.write(buffer, 0, read)
            }
            outputStream.flush()
            outputStream.close()
        } catch (ex: Exception) {
            Log.e(PegasusHelperUtils.TAG, "encrypt: ", ex)
            ex.printStackTrace()
        }
    }

    private fun decrypt(input: InputStream, output: OutputStream, password: String) {
        try {
            val secretKey = generateSecretKey(password)
            val cipher = Cipher.getInstance(TRANSFORMATION)
            cipher.init(Cipher.DECRYPT_MODE, secretKey)

            val inputStream = CipherInputStream(input, cipher)
            val buffer = ByteArray(1024)
            var read: Int
            while (inputStream.read(buffer).also { read = it } >= 0) {
                output.write(buffer, 0, read)
            }
            output.flush()
            output.close()
        } catch (ex: Exception) {
            Log.e(PegasusHelperUtils.TAG, "decrypt: ", ex)
            ex.printStackTrace()
        }
    }
}