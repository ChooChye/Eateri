package com.example.eateri.vendor

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import com.example.eateri.ui.datas.VendorItem
import com.example.eateri.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import android.widget.*
import com.example.eateri.MainActivity
import com.google.firebase.firestore.DocumentReference
import kotlinx.android.synthetic.main.fragment_register.*
import timber.log.Timber
import java.util.HashMap

class VendorRegFragment : Fragment() {

    private var fStore         : FirebaseFirestore = FirebaseFirestore.getInstance()
    private var fAuth          : FirebaseAuth = FirebaseAuth.getInstance()
    private var fData          : FirebaseDatabase = FirebaseDatabase.getInstance()

    private var vendorArrInfo : VendorInfo = VendorInfo()
    private lateinit var vendorArrList : VendorItem
    lateinit var test : EditText

    lateinit var vdName: EditText
    lateinit var vdEmail: EditText
    lateinit var vdHP: EditText
    lateinit var vdPass: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val vd=  inflater.inflate(R.layout.fragment_vendorreg, container, false)
        val links: TextView = vd.findViewById(R.id.textView_goToLogin)

        vd.findViewById<Button>(R.id.btnVdReg).setOnClickListener {
            registerUser()
        }
        vdName = vd.findViewById(R.id.editText_vd_name)
        vdEmail = vd.findViewById(R.id.editText_vd_email)
        vdHP = vd.findViewById(R.id.editText_vd_phone)
        vdPass = vd.findViewById(R.id.editText_vd_password)
        return vd
    }

    private fun registerUser() {
        closeKeyboard()
        val rvName: String = vdName.text.toString()
        val rvEmail: String = vdEmail.text.toString()
        val rvHP: String = vdHP.text.toString()
        val rvPass: String = vdPass.text.toString()

        if (TextUtils.isEmpty(rvName)) {
            vdName.error = "First Name is required"
            return
        }
        if (TextUtils.isEmpty(rvEmail)) {
            vdEmail.error = "Email is required"
            //Toast.makeText(context, "Please fill up the empty fields", Toast.LENGTH_SHORT).show()
            return
        }
        if (rvPass.length < 8) {
            vdPass.error = "Password must be greater than 8 characters"
            return
        }
        if (TextUtils.isEmpty(rvPass) || rvPass == "") {
            vdPass.error = "Please enter a password"
            return
        }
        if(TextUtils.isEmpty(rvHP)){
            vdHP.error = "Mobile Number is required"
            return
        }
        //Debugging
        Timber.d("Email: $vdEmail")
        Timber.d("Password: $vdPass")
        Timber.d("Mobile: $vdHP")
        vendorArrList = VendorItem(
            vdName = rvName,
            vdEmail = rvEmail,
            vdHP = rvHP.toInt(),
            vdPass = rvPass
        )
        //Start progress
        val progress: ProgressBar = progressBar_reg
        progress.visibility = View.VISIBLE

        //Firebase
        fAuth.createUserWithEmailAndPassword(vendorArrList.vdEmail, vendorArrList.vdPass)
            .addOnCompleteListener {
                if (!it.isSuccessful) {
                    return@addOnCompleteListener
                } else {
                    Timber.d("vdid: ${it.result!!.user!!.uid}")
                    saveUserToFirebase(vendorArrList)
                    Toast.makeText(context, "Successfully Registered", Toast.LENGTH_SHORT).show()
//                    this.findNavController().navigate(R.id.nav_home)
                    val intent = Intent(context, MainActivity::class.java)
                    startActivity(intent)
                }
            }
            .addOnFailureListener {
                Timber.d("Error: ${it.message}")
            }
    }

    private fun saveUserToFirebase(vendorItem: VendorItem) {
        //Get instance
        val vdID = fAuth.uid ?: ""
        val ref = fData.getReference("/vendors/$vdID")

        //Debugging Purposes
        Timber.d("Vendor Name: ${vendorItem.vdName}")
        Timber.d("Vendor Email: ${vendorItem.vdEmail}")
        Timber.d("Vendor HP: ${vendorItem.vdHP}")
        Timber.d("Vendor Password: ${vendorItem.vdPass}")
        //Success -> Save
        ref.setValue(vendorItem)
            .addOnSuccessListener {
                Timber.d("Vendor Saved")
                val vendorId = fAuth.currentUser!!.uid
                //Save to FireStore
                val docRef: DocumentReference = fStore.collection("vendors").document(vendorId)
                val vendors = HashMap<String, Any>()
                vendors["VendorName"] = vendorItem.vdName
                vendors["VendorEmail"] = vendorItem.vdEmail
                vendors["VendorHP"] = vendorItem.vdHP
                vendors["VendorPass"] = vendorItem.vdPass
                docRef.collection("vendors_info").document("vendors_list")
                    .set(vendors)
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
