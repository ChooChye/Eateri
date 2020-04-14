package com.example.eateri.ui.register

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.eateri.MainActivity
import com.example.eateri.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_register.*
import timber.log.Timber
import java.util.*


/**
 * A simple [Fragment] subclass.
 */
class RegisterFragment : Fragment(){

    private var fStore         : FirebaseFirestore = FirebaseFirestore.getInstance()
    private var fAuth          : FirebaseAuth = FirebaseAuth.getInstance()
    private var fData          : FirebaseDatabase = FirebaseDatabase.getInstance()

    private var userArrInfo : UserInfo = UserInfo()
    private lateinit var userArrayList : UserItem
    lateinit var test : EditText

    lateinit var firstName: EditText
    lateinit var lastName: EditText
    lateinit var email: EditText
    lateinit var mobileNo: EditText
    lateinit var password: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_register, container, false)
        val links: TextView = v.findViewById(R.id.textView_goToLogin)

        //Toast.makeText(context, firstName.toString(), Toast.LENGTH_SHORT).show()

        //Go to Login
        links.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.loginFragment)
        }
        //Register User
        v.findViewById<Button>(R.id.button_register).setOnClickListener {
            registerUser()
        }
        firstName = v.findViewById(R.id.editText_fName)
        lastName = v.findViewById(R.id.editText_lName)
        email = v.findViewById(R.id.editText_email)
        mobileNo = v.findViewById(R.id.editText_mobile)
        password = v.findViewById(R.id.editText_reg_password)
        return v
    }

    //Registers the user
    private fun registerUser() {
        closeKeyboard()
        val rfirstName: String = firstName.text.toString()
        val rlastName: String = lastName.text.toString()
        val remail: String = email.text.toString()
        val rmobileNo: String = mobileNo.text.toString()
        val rpassword: String = password.text.toString()
        //Check Fields
        if (TextUtils.isEmpty(rfirstName)) {
            firstName.error = "First Name is required"
            return
        }
        if (TextUtils.isEmpty(rlastName)) {
            editText_lName.error = "Last Name is required"
            return
        }
        if (TextUtils.isEmpty(remail)) {
            email.error = "Email is required"
            //Toast.makeText(context, "Please fill up the empty fields", Toast.LENGTH_SHORT).show()
            return
        }
        if (password.length() < 6) {
            password.error = "Password must be greater than 5 characters"
            return
        }
        if (TextUtils.isEmpty(rpassword) || rpassword == "") {
            editText_reg_password.error = "Password is required"
            return
        }
        if(TextUtils.isEmpty(rmobileNo)){
            mobileNo.error = "Mobile Number is required"
            return
        }
        //Debugging
        Timber.d("Email: $email")
        Timber.d("Password: $password")
        Timber.d("Mobile: $mobileNo")
        userArrayList = UserItem(
            uid     = fAuth.uid.toString() ,
            fName   = rfirstName,
            lName   = rlastName,
            email   = remail,
            num     = rmobileNo.toInt(),
            pass    = rpassword
        )
        //Start progress
        val progress: ProgressBar = progressBar_reg
        progress.visibility = View.VISIBLE



        //Firebase
        fAuth.createUserWithEmailAndPassword(userArrayList.email, userArrayList.pass)
            .addOnCompleteListener {
                if (!it.isSuccessful) {
                    return@addOnCompleteListener
                } else {
                    Timber.d("UID: ${it.result!!.user!!.uid}")
                    saveUserToFirebase(userArrayList)
                    Toast.makeText(context, "Successfully Registered", Toast.LENGTH_SHORT).show()
//                    this.findNavController().navigate(R.id.nav_home)
                    val intent : Intent = Intent(context, MainActivity::class.java)
                    startActivity(intent)
                }
            }
            .addOnFailureListener {
                Timber.d("Error: ${it.message}")
            }
    }

    private fun saveUserToFirebase(userItem: UserItem) {
        //Get instance
        val uid = fAuth.uid ?: ""
        val ref = fData.getReference("/users/$uid")

        //Debugging Purposes
        Timber.d("UID: ${userItem.uid}")
        Timber.d("First Name: ${userItem.fName}")
        Timber.d("Last Name: ${userItem.lName}")
        Timber.d("Email: ${userItem.email}")
        Timber.d("Mobile No: ${userItem.num}")
        Timber.d("Password: ${userItem.pass}")
        //Success -> Save
        ref.setValue(userItem)
            .addOnSuccessListener {
                Timber.d("User Saved")
                val userId = fAuth.currentUser!!.uid
                //Save to FireStore
                val docRef: DocumentReference = fStore.collection("users").document(userId)
                val users = HashMap<String, Any>()
                users["FirstName"] = userItem.fName
                users["LastName"] = userItem.lName
                users["email"] = userItem.email
                users["mobile_no"] = userItem.num
                docRef.collection("users_info").document("user_list")
                    .set(users)
                    .addOnSuccessListener { Timber.d("DocumentSnapshot successfully written!") }
                    .addOnFailureListener { e -> Timber.w("Error writing document $e") }
            }
    }

    private fun closeKeyboard(){
        val imm =
            activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view!!.windowToken, 0)
    }

}
