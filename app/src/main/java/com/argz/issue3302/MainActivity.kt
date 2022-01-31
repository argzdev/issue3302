package com.argz.issue3302

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.google.android.gms.tasks.Tasks.await
import com.google.firebase.Timestamp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.Source
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        getData()
    }

    fun getData(){
        val auth = Firebase.auth
        setContentView(R.layout.activity_main)

        auth.signInAnonymously()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val db = Firebase.firestore
                    val result = db.collection("pocfirestore")
                        .orderBy("createdAt", Query.Direction.DESCENDING)
                        .limit(1)

                    result.addSnapshotListener { documents, e ->
                        if (documents != null) {
                            for (doc in documents) {
                                val textView = findViewById<TextView>(R.id.text);
                                val text = doc.data.get("createdAt")

                                if (text is Timestamp) {
                                    Log.d(TAG, "ID: ${doc.id}")
                                    textView.text = "last message created at ${text.toDate()}"
                                }
                            }
                        }
                    }
                }
            }
    }
}