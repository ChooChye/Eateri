package com.example.eateri

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_register.*

/**
 * A simple [Fragment] subclass.
 */
class LoginFragment : Fragment() {
    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_login, container, false)
        val linkToRegister : TextView = v.findViewById(R.id.textView_goToRegister)
        //Link to Register Screen
        linkToRegister.setOnClickListener {view : View->
            view.findNavController().navigate(R.id.nav_register)
        }

        //Login user
        v.findViewById<Button>(R.id.button_login).setOnClickListener{
            loginUser()
        }
        //v.findViewById<View>(R.id.fragment_login).setOnClickListener { closeKeyboard() }

        return v
    }

    private fun loginUser(){

        val idEmail = editText_username
        val idPass = editText_login_password
        val email = idEmail.text.toString()
        val pass = idPass.text.toString()

        //Checking Fields
        if(TextUtils.isEmpty(email)){
            idEmail.error = "Email is required"
            return
        }
        if(pass < 6.toString()){
            idPass.error = "Password is incorrect"
        }

        //Show progress bar
        val progBar : ProgressBar = progressBar_login
        progBar.visibility = View.VISIBLE

        //Sign in
        mAuth.signInWithEmailAndPassword(email, pass)
            .addOnCompleteListener {
                if (!it.isSuccessful) {
                    Log.d("Login", "Login Failed ${it.exception?.message}")
                    //Toast.makeText(context, "Error #1039: Login Failed ${it.exception?.message} " , Toast.LENGTH_SHORT).show()
                    return@addOnCompleteListener
                }else{

                    val currentUser = mAuth.currentUser
                    if(currentUser != null){
                        val name : String? = currentUser.displayName
                        val email : String? = currentUser.email
                        Log.d("Login", "Login user success")
                        //Navigate to Home
                        val intent = Intent(context, MainActivity::class.java)
                        startActivity(intent)
                    }else{
                        Log.d("Login", "No user found")
                    }
                }
            }
            .addOnFailureListener {
                Toast.makeText(context, "Error #1039: Login Failed", Toast.LENGTH_SHORT).show()
            }
    }

    private fun closeKeyboard(){
        val imm =
            activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view!!.windowToken, 0)
    }


}
