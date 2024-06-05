package com.example.finalchat

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


class signupFragment : Fragment() {

    lateinit var edtpasssig:TextInputEditText
    lateinit var edtemailsig:TextInputEditText
    lateinit var btnsig:Button
    lateinit var db:FirebaseAuth
    lateinit var rldb:FirebaseDatabase
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view =  inflater.inflate(R.layout.fragment_signup, container, false)

        edtemailsig = view.findViewById(R.id.edtsignupemail)
        edtpasssig = view.findViewById(R.id.edtsignuppass)
        btnsig = view.findViewById(R.id.btnsignup)

        btnsig.alpha = 1.0f



        btnsig.setOnClickListener {
            btnsig.animate().alpha(0.5f).duration = 1000
            var email = edtemailsig.text.toString().trim()
            var pass = edtpasssig.text.toString().trim()

            if(pass.length < 6){
                Toast.makeText(requireContext(), "password must be atleast six characters", Toast.LENGTH_SHORT).show()
            }else{
                signup(email,pass)
            }


        }

        return view


    }
    fun signup(email:String, password:String){
        db = FirebaseAuth.getInstance()

        db.createUserWithEmailAndPassword(email,password).addOnCompleteListener() { task ->
            if (task.isSuccessful) {
                Toast.makeText(requireContext(), "successfuly signed in", Toast.LENGTH_SHORT).show()

                var prev = prevuser(email,"${FirebaseAuth.getInstance().currentUser?.uid}")
                rldb = FirebaseDatabase.getInstance()
                rldb.getReference().child("people").push().setValue(prev).addOnSuccessListener {

                }
                    .addOnFailureListener{

                    }
            } else {
                Toast.makeText(requireContext(), "not succesfull", Toast.LENGTH_SHORT).show()

            }
        }
    }


}

data class prevuser(var email: String, var userid:String)