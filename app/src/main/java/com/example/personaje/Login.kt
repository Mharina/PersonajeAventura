package com.example.personaje

import com.google.firebase.auth.auth
import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.personaje.databinding.ActivityLoginBinding
import com.example.personaje.databinding.ActivityMainBinding
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth



class Login : AppCompatActivity() {

    private lateinit var binding : ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        acceder()

    }

    private fun acceder(){

        val email : TextInputLayout = binding.correo
        val pass : TextInputLayout = binding.pass

        binding.login.setOnClickListener{
            if (email.editText?.text?.isNotEmpty() == true && pass.editText?.text?.isNotEmpty() == true){

                auth.signInWithEmailAndPassword(
                    email.editText?.text.toString(),
                    pass.editText?.text.toString()).addOnCompleteListener {
                    if (it.isSuccessful){
                        Log.d(ContentValues.TAG, "Login de usuario")
                        // Comprobar primero si ha jugado alguna vez mirando si tiene algun pj creado(uid)
                        // Personaje creado Partida si no a MainActivity
                        val logueado = Intent (this, Partida::class.java)
                        logueado.putExtra("email",email.editText?.text?.toString())
                        startActivity(logueado)
                    } else {
                        showAlert()
                    }
                }
            }
        }
        binding.reg.setOnClickListener {
            Toast.makeText(this, "Jugador registrado con éxito", Toast.LENGTH_LONG).show()
        }
    }

    private fun showAlert(){
        Log.d(ContentValues.TAG, "Error creando nuevo usuario")
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error en el login de usuario")
        builder.setPositiveButton("Aceptar",null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

}