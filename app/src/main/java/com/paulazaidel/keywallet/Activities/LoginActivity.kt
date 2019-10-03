package com.paulazaidel.keywallet.Activities

import android.app.KeyguardManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.os.Build
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import java.io.IOException
import android.R
import android.app.Activity
import android.provider.Settings
import java.lang.Exception
import java.security.SecureRandom
import java.util.*
import javax.crypto.spec.IvParameterSpec

class LoginActivity : AppCompatActivity() {

    private val KEY_NAME = "key_wallet_app"
    private val REQUEST_CODE_CONFIRM_DEVICE_CREDENTIALS = 1
    private val AUTHENTICATION_DURATION_SECONDS = 30
    private var keyguardManager: KeyguardManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        keyguardManager = getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager

        generateKey()

        if (keyguardManager!!.isKeyguardSecure) {
            if (logged())
                showAccountsActivity()
        } else {
            showAccountsActivity()
        }
    }

    private fun generateKey() {
        try {
            var keyGenerator: KeyGenerator

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val keyStore = KeyStore.getInstance("AndroidKeyStore")
                keyStore.load(null)

                keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore")

                keyGenerator.init(
                    KeyGenParameterSpec.Builder(
                        KEY_NAME,
                        KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
                    )
                        .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                        .setUserAuthenticationRequired(true)
                        .setUserAuthenticationValidityDurationSeconds(
                            AUTHENTICATION_DURATION_SECONDS
                        )
                        .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
                        .build()
                )
            } else {
                keyGenerator = KeyGenerator.getInstance("AndroidKeyStore")
                keyGenerator.init(2048, SecureRandom())
            }

            keyGenerator.generateKey()
        } catch (e: IOException) {

        }
    }

    private fun logged(): Boolean {
        try {
            val keyStore = KeyStore.getInstance("AndroidKeyStore")
            keyStore.load(null)

            val secretKey = keyStore.getKey(KEY_NAME, null)

            val transformation : String = KeyProperties.KEY_ALGORITHM_AES + "/" +
                    KeyProperties.BLOCK_MODE_CBC + "/" + KeyProperties.ENCRYPTION_PADDING_PKCS7

            val cipher = Cipher.getInstance(transformation)

            cipher.init(Cipher.DECRYPT_MODE, secretKey, IvParameterSpec(ByteArray(16)))
            //cipher.doFinal(SecureRandom.getSeed(16))

            //User has recently authenticated, you will reach here.
            return true
        } catch (e : Exception) {

        }

        showAuthenticationScreen()
        return false
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            if (logged ()) {
                showAccountsActivity()
            }
        } else {
            showAuthenticationScreen()
        }
    }

    private fun showAuthenticationScreen() {
        val intent = keyguardManager?.createConfirmDeviceCredentialIntent(null, null)
        if (intent != null)
            startActivityForResult(intent, REQUEST_CODE_CONFIRM_DEVICE_CREDENTIALS)
    }

    private fun showAccountsActivity() {
        val intent = Intent(this, ListActivity::class.java)
        startActivity(intent)
    }
}