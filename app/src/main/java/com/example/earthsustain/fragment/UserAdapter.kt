package com.example.earthsustain.fragment

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.content.Context as AndroidContext
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.earthsustain.R
import com.example.earthsustain.activity.AdminActivity
import com.example.earthsustain.database.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.core.Context
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.squareup.picasso.Picasso



class UserAdapter(private val userList: List<User>) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {
    companion object {
        private const val PICK_IMAGE_REQUEST_CODE = 1
    }

    private lateinit var auth: FirebaseAuth


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.textViewName)
        val emailTextView: TextView = itemView.findViewById(R.id.textViewEmail)
        val phoneNumberTextView: TextView = itemView.findViewById(R.id.textViewPhoneNumber)
        val pictureImageView: ImageView = itemView.findViewById(R.id.imageViewPicture)
        val userIdTextView: TextView = itemView.findViewById(R.id.textViewUserId)
        val updateButton: Button = itemView.findViewById(R.id.updateButton)
        val deleteButton: Button = itemView.findViewById(R.id.deleteButton)

        val uploadImageButton: Button = itemView.findViewById(R.id.browseImageButton)
        private lateinit var uri: Uri
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = userList[position]
        holder.nameTextView.text = "${user.firstName} ${user.lastName}"
        holder.emailTextView.text = user.email
        holder.phoneNumberTextView.text = user.phoneNumber
        holder.userIdTextView.text = "User ID: ${user.userId}"


        Picasso.get()
            .load(user.imageLink)
            .into(holder.pictureImageView)

//        holder.uploadImageButton.setOnClickListener { view ->
//            val user = userList[position]
//
//            // Ensure that you have access to the Activity context
//            val activityContext = view.context as Activity
//
//            // Create an intent to pick an image from the device's gallery
//            val intent = Intent(Intent.ACTION_PICK)
//            intent.type = "image/*"
//
//            // Start the image picker intent with startActivityForResult
//            activityContext.startActivityForResult(intent, PICK_IMAGE_REQUEST_CODE)
//
//        }

        holder.updateButton.setOnClickListener {
            val context = holder.itemView.context

            // Create an AlertDialog for updating user information
            val alertDialog = AlertDialog.Builder(context)
            val updateView =
                LayoutInflater.from(context).inflate(R.layout.fragment_admin_edit_user, null)
            alertDialog.setView(updateView)

            // Find the EditText fields in the updateView layout
            val firstNameEditText: EditText = updateView.findViewById(R.id.firstNameEditText)
            val lastNameEditText: EditText = updateView.findViewById(R.id.lastNameEditText)
            val phoneEditText: EditText = updateView.findViewById(R.id.phoneNumberEditText)
            val emailEditText: EditText = updateView.findViewById(R.id.emailEditText)
            val pictureImageView: ImageView = updateView.findViewById(R.id.profileImageView)
            val browseButton: Button = updateView.findViewById(R.id.browseButton)
            val updateButton: Button = updateView.findViewById(R.id.updateButton)


            // Set initial values from the user object
            firstNameEditText.setText(user.firstName)
            lastNameEditText.setText(user.lastName)
            phoneEditText.setText(user.phoneNumber)
            emailEditText.setText(user.email)
            Picasso.get()
                .load(user.imageLink)
                .into(pictureImageView)
            browseButton.visibility = View.INVISIBLE
            updateButton.visibility = View.INVISIBLE
            if (user.userId == "A001") {
                // For user "A001," allow editing of email
                emailEditText.setText(user.email)
            } else {
                // For other users, disable email editing
                emailEditText.setText(user.email)
                emailEditText.isEnabled = false
            }

            alertDialog.setPositiveButton("Update") { _, _ ->
                // Get updated values from EditText fields
                val updatedFirstName = firstNameEditText.text.toString().trim()
                val updatedLastName = lastNameEditText.text.toString().trim()
                val updatedPhone = phoneEditText.text.toString().trim()
                val updatedEmail = emailEditText.text.toString().trim()


                // Update user data in Firebase Realtime Database
                updateUser(
                    context,
                    user,
                    updatedFirstName,
                    updatedLastName,
                    updatedPhone,
                    updatedEmail,

                )

                auth = FirebaseAuth.getInstance()
                // Check if the user is already logged in
                val currentUser = auth.currentUser
                if (currentUser != null) {
                    auth.createUserWithEmailAndPassword(updatedEmail, user.password)
                        .addOnCompleteListener { createUserTask ->
                            if (createUserTask.isSuccessful) {


                            } else {

                            }
                        }
                } else {

                }

                // Notify the RecyclerView of data changes
                notifyDataSetChanged()
            }

            alertDialog.setNegativeButton("Cancel", null)
            alertDialog.show()
        }

        holder.uploadImageButton.setOnClickListener {

            val context = holder.itemView.context
            if (context is FragmentActivity) {
                val fragmentManager = context.supportFragmentManager
                val fragmentTransaction = fragmentManager.beginTransaction()

                // Create an instance of AdminEditUserFragment with the user object
                val editUserFragment = AdminEditUserFragment(user)

                // Replace the current fragment with AdminEditUserFragment
                fragmentTransaction.replace(R.id.fragment_container, editUserFragment)
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
            }

        }


        if (user.userId == "A001") {
            holder.deleteButton.visibility = View.GONE
        } else {
            holder.deleteButton.visibility = View.VISIBLE
            holder.deleteButton.setOnClickListener {
                val context = holder.itemView.context
                AlertDialog.Builder(context)
                    .setTitle("Delete User")
                    .setMessage("Are you sure you want to delete this user?")
                    .setPositiveButton("Delete") { _, _ ->
                        deleteUser(context, user)
                    }
                    .setNegativeButton("Cancel", null)
                    .show()
            }
        }
    }

    // Function to update user data
    private fun updateUser(
        context: android.content.Context,
        user: User,
        updatedFirstName: String,
        updatedLastName: String,
        updatedPhoneNumber: String,
        updatedEmail: String,

    ) {
        val dbRef = FirebaseDatabase.getInstance().getReference("Users").child(user.userId)

        val updatedUser = User(
            user.userId,
            updatedEmail,
            user.password,
            updatedFirstName,
            updatedLastName,
            updatedPhoneNumber,
            user.imageLink
        )

        dbRef.setValue(updatedUser).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Data updated successfully
                Toast.makeText(context, "User data updated successfully", Toast.LENGTH_SHORT).show()
            } else {
                // Handle the case where the update fails
                Toast.makeText(context, "Failed to update user data", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun deleteUser(context: android.content.Context, user: User) {
        // Get a reference to the Firebase database
        val databaseReference = FirebaseDatabase.getInstance().getReference("Users")

        // Create a reference to the user's data using their user ID
        val userReference = databaseReference.child(user.userId)

        // Remove the user's data from the database
        userReference.removeValue()
            .addOnSuccessListener {
                // User data has been successfully deleted
                //Toast.makeText(context, "User deleted successfully", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                // An error occurred while deleting the user data
                //Toast.makeText(context, "Error deleting user: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    override fun getItemCount(): Int {
        return userList.size
    }

}