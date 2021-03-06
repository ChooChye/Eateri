package com.example.eateri.ui.myprofile

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.example.eateri.R
import com.example.eateri.ui.datas.UserItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.dialog_layout.*
import kotlinx.android.synthetic.main.fragment_myprofile.*
import timber.log.Timber


class MyProfile : Fragment() {

    private var fAuth               : FirebaseAuth = FirebaseAuth.getInstance()
    private var fStore              : FirebaseFirestore = FirebaseFirestore.getInstance()
    lateinit var users              : UserItem
    lateinit var root               : View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        initData() // Initiaize data
        root = inflater.inflate(R.layout.fragment_myprofile, container, false)
        var fname       = root.findViewById<CardView>(R.id.cardView_fName)
        var lname       = root.findViewById<CardView>(R.id.cardView_lName)
        var email       = root.findViewById<CardView>(R.id.cardView_email)
        var mobile      = root.findViewById<CardView>(R.id.cardView_mobile)
        var btnSave     = root.findViewById<Button>(R.id.btn_profile_save)
        var btnCancel   = root.findViewById<Button>(R.id.btn_profile_cancel)
        var passwordCard= root.findViewById<CardView>(R.id.cardView_password)



        passwordCard.setOnClickListener{ passwordDialog() }
        btnSave.setOnClickListener{ saveEdit() }
        btnCancel.setOnClickListener { cancelEdit() }

        return root
    }
    private fun passwordDialog() {

        // Initialize a new instance of
        val inflater = this.layoutInflater;

        val builder = AlertDialog.Builder(context)

        // Set the alert dialog title
        builder.setTitle("Change Password")

        // Display a message on alert dialog
        val viewDialog = inflater.inflate(R.layout.dialog_layout, null)
        builder.setView(viewDialog)

        // Set a positive button and its click listener on alert dialog
        builder.setPositiveButton("SAVE") { dialog, which ->
            // Do something when user press the positive button
            //Toast.makeText(context, "Ok, we change the app background.", Toast.LENGTH_SHORT).show()

            updatePassword(viewDialog)

        }

        // Display a neutral button on alert dialog
        builder.setNeutralButton("Cancel"){_,_ ->
            //Toast.makeText(context,"You cancelled the dialog.",Toast.LENGTH_SHORT).show()
        }

        // Finally, make the alert dialog using builder
        val dialog: AlertDialog = builder.create()

        // Display the alert dialog on app interface
        dialog.show()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.profile_option_menu, menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_profile_edit -> {
                editBox(profile_fName_data, profile_fname_editText, false)
                editBox(profile_lName_data, profile_lname_editText, false)
                //editBox(profile_email_data, profile_email_editText, false)
                editBox(profile_hp_data, profile_mobile_editText, false)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun editBox(box : TextView, edit : EditText, cancel : Boolean){
        val value = box.text.toString()
        if(!cancel){
            if(box.visibility == View.VISIBLE){
                box.visibility = View.INVISIBLE
                edit.visibility = View.VISIBLE
                edit.setText(value)
                btn_profile_save.visibility = View.VISIBLE
                btn_profile_cancel.visibility = View.VISIBLE
            }
        }else{
            if(edit.visibility == View.VISIBLE){
                edit.visibility = View.INVISIBLE
                box.visibility = View.VISIBLE
                box.setText(value)
                btn_profile_save.visibility = View.GONE
                btn_profile_cancel.visibility = View.GONE
            }
        }
    }

    private fun saveEditBox(box : TextView, edit : EditText, str : String){

        if(edit.visibility == View.VISIBLE){
            edit.visibility = View.INVISIBLE
            box.visibility = View.VISIBLE
            box.setText(str)
            btn_profile_save.visibility = View.GONE
            btn_profile_cancel.visibility = View.GONE
        }
    }

    private fun cancelEdit(){
        editBox(profile_fName_data, profile_fname_editText, true)
        editBox(profile_lName_data, profile_lname_editText, true)
        //editBox(profile_email_data, profile_email_editText, true)
        editBox(profile_hp_data, profile_mobile_editText, true)
        //Toast.makeText(context, "Cancel", Toast.LENGTH_LONG).show()
        closeKeyboard()
    }

    private fun saveEdit(){
        closeKeyboard()
        val user: MutableMap<String, Any> = HashMap()
        user["FirstName"]   = profile_fname_editText.text.toString()
        user["LastName"]    = profile_lname_editText.text.toString()
        user["Email"]   = profile_email_editText.text.toString()
        user["mobile_no"]      = profile_mobile_editText.text.toString()

        val docRef = fStore.collection("users").document(fAuth.uid.toString()).collection("users_info").document("user_list")
        docRef.set(user)
            .addOnSuccessListener {
                Toast.makeText(context, "Successfully Saved", Toast.LENGTH_LONG).show()
            }
            .addOnFailureListener {
                Toast.makeText(context, "Failed to save", Toast.LENGTH_LONG).show()
            }

        saveEditBox(profile_fName_data, profile_fname_editText, user["FirstName"].toString())
        saveEditBox(profile_lName_data, profile_lname_editText, user["LastName"].toString())
        //saveEditBox(profile_email_data, profile_email_editText, user["Email"].toString())
        saveEditBox(profile_hp_data, profile_mobile_editText, user["mobile_no"].toString())


    }

    private fun addValue(box : TextView, str : String){
        box.setText(str)
    }

    private fun initData(){
        val docRef = fStore.collection("users").document(fAuth.uid.toString()).collection("users_info").document("user_list")

        docRef.get()
            .addOnSuccessListener {doc : DocumentSnapshot ->
                if(doc != null){

                    val first_name      = doc.data!!["FirstName"].toString()
                    val last_name       = doc.data!!["LastName"].toString()
                    val email           = doc.data!!["Email"].toString()
                    val hp              = "${doc.data!!["mobile_no"].toString()}"

                    addValue(profile_fName_data, first_name)
                    addValue(profile_lName_data, last_name)
                    addValue(profile_email_data, email)
                    addValue(profile_hp_data, hp)
                    //Toast.makeText(context, "Display DOC ${first_name}", Toast.LENGTH_LONG).show()
                }else{
                    //Toast.makeText(context, "No document", Toast.LENGTH_LONG).show()
                    Timber.d("No document")
                }
            }
            .addOnFailureListener {
                Toast.makeText(context, "Failed", Toast.LENGTH_LONG).show()
            }
        //Toast.makeText(context, currentUser, Toast.LENGTH_LONG).show()
    }

    private fun closeKeyboard(){
        val imm =
            activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view!!.windowToken, 0)
    }

    private fun updatePassword(view : View){
        val newPassword = view.findViewById<EditText>(R.id.profile_dialog_password_editText)
        val reNewPassword = view.findViewById<EditText>(R.id.profile_dialog_repassword_editText)

        if(TextUtils.isEmpty(newPassword.text.toString())){
            newPassword.error = "Please enter your new password"

        }
        if(TextUtils.isEmpty(reNewPassword.text.toString())){
            newPassword.error = "Please re-enter your new password"

        }
        if(newPassword.text.toString() != reNewPassword.text.toString()){
            Toast.makeText(context, "Incorrect Password", Toast.LENGTH_LONG).show()

        }
        Toast.makeText(context, "OK Password : ${newPassword}", Toast.LENGTH_LONG).show()
    }

}
