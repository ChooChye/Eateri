package com.example.eateri

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.android.gms.common.util.NumberUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_register.*
import kotlin.toString as toString1


/**
 * A simple [Fragment] subclass.
 */
class RegisterFragment : Fragment() {

    private var firstName: String = ""
    private var lastName: String = ""
    private var email: String = ""
    private var mobileNo: Int? = 0
    private var password: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_register, container, false)
        val links: TextView = v.findViewById(R.id.textView_goToLogin)

        //Redirect if user session exists
        /*if(FirebaseAuth.getInstance().currentUser == null){
            this.findNavController().navigate(R.id.nav_home)
        }*/

        //Go to Login
        links.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.loginFragment)
        }
        //Register User
        v.findViewById<Button>(R.id.button_register).setOnClickListener {
            registerUser()
        }
        return v
    }

    //Registers the user
    private fun registerUser() {
        firstName = editText_fName.text.toString()
        lastName = editText_lName.text.toString()
        email = editText_email.text.toString()
        mobileNo = editText_mobile.text.toString().toIntOrNull()
        password = editText_reg_password.text.toString()
        //Check Fields
        if(TextUtils.isEmpty(firstName)){
            editText_fName.error = "First Name is required"
            return
        }
        if(TextUtils.isEmpty(lastName)){
            editText_lName.error = "Last Name is required"
            return
        }
        if (TextUtils.isEmpty(email)) {
            editText_email.error = "Email is required"
            //Toast.makeText(context, "Please fill up the empty fields", Toast.LENGTH_SHORT).show()
            return
        }
        if(editText_reg_password.length() > 6){
            editText_reg_password.error = "Password must be greater than 6 characters"
            return
        }
        if(TextUtils.isEmpty(password)){
            editText_reg_password.error = "Password is required"
            return
        }

        //Start progress
        val progress : ProgressBar = progressBar_reg
        progress.visibility = View.VISIBLE

        //Debugging
        Log.d("RegisterScr", "Email: $email")
        Log.d("RegisterScr", "Password: $password")
        Log.d("RegisterScr", "Mobile: $mobileNo")

        //Firebase
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (!it.isSuccessful) {
                    return@addOnCompleteListener
                } else {
                    Log.d("RegisterScr", "UID: ${it.result!!.user!!.uid}")
                    saveUserToFirebase()
                    Toast.makeText(context, "Successfully Registered", Toast.LENGTH_SHORT).show()
                    this.findNavController().navigate(R.id.nav_home)

                }
            }
            .addOnFailureListener {
                Log.d("RegisterScr", "Error: ${it.message}")
            }
    }

    private fun saveUserToFirebase() {

        //Get instance
        val uid = FirebaseAuth.getInstance().uid ?: ""
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")

        //Make object
        val user = User(uid, firstName, lastName, email, mobileNo!! , password)
        Log.d("RegisterScr", "First Name: $uid")
        Log.d("RegisterScr", "First Name: $firstName")
        Log.d("RegisterScr", "First Name: $lastName")
        Log.d("RegisterScr", "First Name: $email")
        Log.d("RegisterScr", "First Name: $mobileNo")
        Log.d("RegisterScr", "First Name: $password")


        ref.setValue(user)
            .addOnSuccessListener {
                Log.d("RegisterScr", "User Saved")
            }
    }

    class User(
        val uid: String,
        val fName: String,
        val lName: String,
        val email: String,
        val num: Int,
        val pass: String
    )
}
