package com.example.earthsustain.fragment

import android.content.Intent
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
import androidx.recyclerview.widget.RecyclerView
import com.example.earthsustain.R
import com.example.earthsustain.activity.AdminActivity
import com.example.earthsustain.database.User
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.core.Context
import com.squareup.picasso.Picasso


class UserAdapter(private val userList: List<User>) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.textViewName)
        val emailTextView: TextView = itemView.findViewById(R.id.textViewEmail)
        val phoneNumberTextView: TextView = itemView.findViewById(R.id.textViewPhoneNumber)
        val pictureImageView: ImageView = itemView.findViewById(R.id.imageViewPicture)
        val userIdTextView: TextView = itemView.findViewById(R.id.textViewUserId)
        val updateButton: Button = itemView.findViewById<Button>(R.id.updateButton)
        val deleteButton: Button = itemView.findViewById<Button>(R.id.deleteButton)


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

        // Load and display the user's picture using Picasso (replace with your image-loading library)
        Picasso.get()
            .load(user.imageLink)
            .into(holder.pictureImageView)

        holder.updateButton.setOnClickListener {
            val context = holder.itemView.context

            // Create an AlertDialog for updating user information
            val alertDialog = AlertDialog.Builder(context)
            val updateView = LayoutInflater.from(context).inflate(R.layout.fragment_admin_edit_user, null)
            alertDialog.setView(updateView)

            // Find the EditText fields in the updateView layout
            val firstNameEditText: EditText = updateView.findViewById(R.id.firstNameEditText)
            val lastNameEditText: EditText = updateView.findViewById(R.id.lastNameEditText)
            val phoneEditText: EditText = updateView.findViewById(R.id.phoneNumberEditText)
            val pictureImageView: ImageView = updateView.findViewById(R.id.profileImageView)


            // Set initial values from the user object
            firstNameEditText.setText(user.firstName)
            lastNameEditText.setText(user.lastName)
            phoneEditText.setText(user.phoneNumber)
            Picasso.get()
                .load(user.imageLink)
                .into(pictureImageView)

            alertDialog.setPositiveButton("Update") { _, _ ->
                // Get updated values from EditText fields
                val updatedFirstName = firstNameEditText.text.toString().trim()
                val updatedLastName = lastNameEditText.text.toString().trim()
                val updatedPhone = phoneEditText.text.toString().trim()

                // Update user data in Firebase
                updateUser(context, user, updatedFirstName, updatedLastName, updatedPhone)

                // Notify the RecyclerView of data changes
                notifyDataSetChanged()
            }

            alertDialog.setNegativeButton("Cancel", null)
            alertDialog.show()
        }



        if (user.email == "earthsustain2023@gmail.com") {
            holder.deleteButton.visibility = View.GONE
        } else {
            holder.deleteButton.visibility = View.VISIBLE
            holder.deleteButton.setOnClickListener {
                val context = holder.itemView.context
                AlertDialog.Builder(context)
                    .setTitle("Delete User")
                    .setMessage("Are you sure you want to delete this user?")
                    .setPositiveButton("Delete") { _, _ ->
                        // deleteUser(context, user)
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
        updatedPhoneNumber: String
    ) {
        val dbRef = FirebaseDatabase.getInstance().getReference("Users").child(user.userId)

        val updatedUser = User(
            user.email,
            user.password,
            updatedFirstName,
            updatedLastName,
            updatedPhoneNumber,
            user.imageLink,
            user.userId
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

    override fun getItemCount(): Int {
        return userList.size
    }
}