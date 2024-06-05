package com.example.finalchat

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth


class loginFragment : Fragment() {

    lateinit var edtpasslog: TextInputEditText
    lateinit var edtemaillog: TextInputEditText
    lateinit var btnlog: Button
    lateinit var db: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_login, container, false)

        edtemaillog = view.findViewById(R.id.edtloginemail)
        edtpasslog = view.findViewById(R.id.edtloginpass)
        btnlog = view.findViewById(R.id.btnlogin)

        btnlog.alpha = 1.0f

        btnlog.setOnClickListener {

            btnlog.animate().alpha(0.5f).duration = 1000
            var email = edtemaillog.text.toString().trim()
            var pass = edtpasslog.text.toString().trim()

            if(pass.length < 6){
                Toast.makeText(requireContext(), "password must be atleast six characters", Toast.LENGTH_SHORT).show()
            }else{
                login(email,pass)
            }
        }


        return view
    }

    private fun login(email: String, pass: String) {

        db = FirebaseAuth.getInstance()

        db.signInWithEmailAndPassword(email,pass).addOnCompleteListener(){
            if(it.isSuccessful){
                Toast.makeText(requireContext(), "login successful", Toast.LENGTH_SHORT).show()
                var intent = Intent(requireContext(), peopleActivity::class.java)
                startActivity(intent)
            }else{
                Toast.makeText(requireContext(), "login not successful", Toast.LENGTH_SHORT).show()
            }
        }



    }


}