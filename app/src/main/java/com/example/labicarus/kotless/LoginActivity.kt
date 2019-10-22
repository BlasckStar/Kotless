package com.example.labicarus.kotless

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.widget.Toast
import com.facebook.appevents.AppEventsLogger
import com.facebook.login.LoginResult
import com.google.gson.Gson
import com.google.gson.JsonElement
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.login_activity.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.R.attr.button
import android.support.v4.app.SupportActivity
import android.support.v4.app.SupportActivity.ExtraData
import android.support.v4.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.util.Log
import android.view.View
import com.facebook.*
import com.facebook.login.LoginManager
import org.json.JSONObject
import java.util.*


class LoginActivity: AppCompatActivity() {

    class LoginClass{
        companion object{
            @SuppressLint("StaticFieldLeak")
            var activity: Activity? = null

            @SuppressLint("StaticFieldLeak")
            var loading: Activity? = null
        }
    }

    //region //----- UNIQUE VARIABLES -----\\
    var list: MutableList<Employees>? = mutableListOf()
    //endregion

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)

        FacebookSdk.sdkInitialize(applicationContext)
        AppEventsLogger.activateApp(this)

        val callbackManager = CallbackManager.Factory.create()
        callbackManager
        login_button.setReadPermissions("email")
        login_button.setOnClickListener(View.OnClickListener {
            // Login
            LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email"))
            LoginManager.getInstance().registerCallback(callbackManager,
                object : FacebookCallback<LoginResult> {
                    override fun onSuccess(loginResult: LoginResult) {
                        Toast.makeText(this@LoginActivity, "aisnidsaoino", Toast.LENGTH_SHORT).show()
                    }

                    override fun onCancel() {
                        Log.d("MainActivity", "Facebook onCancel.")
                        Toast.makeText(this@LoginActivity, "isandisaodos", Toast.LENGTH_SHORT).show()
                    }

                    override fun onError(error: FacebookException) {
                        Log.d("MainActivity", "Facebook onError.")

                        Toast.makeText(this@LoginActivity, "osakodsaosaodos", Toast.LENGTH_SHORT).show()
                    }
                })
        })

        //region //----- INIT -----\\
        LoginClass.activity = this@LoginActivity
        //endregion

        //region //----- LISTENERS -----\\
        buttonListeners()
        //endregion

    }

    //region //----- LISTENERS FUNCTION -----\\
    fun buttonListeners(){
        btn_login_back.setOnClickListener {
            finish()
        }

        btn_login_enter.setOnClickListener {
            if (input_login_username.text.toString() != "" && input_login_password.text.toString() != ""){
                val username =  input_login_username.text.toString()
                val password = CryptoClient().encode(input_login_password.text.toString()+username)
                startActivity(Intent(this, SplashActivity::class.java))
                TesteWebClient().getUser(username, password, list,this)
            }else{
                Toast.makeText(this, "Entre com login e senha!", Toast.LENGTH_SHORT).show()
            }
        }
    }
    //endregion

}