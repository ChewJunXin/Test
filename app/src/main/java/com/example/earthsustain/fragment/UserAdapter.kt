package com.example.earthsustain.fragment

import android.app.Activity
import android.content.Intent
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
import androidx.recyclerview.widget.RecyclerView
import com.example.earthsustain.R
import com.example.earthsustain.activity.AdminActivity
import com.example.earthsustain.database.User
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.core.Context
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso



class UserAdapter(private val userList: List<User>) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {
    companion object {
        private const val PICK_IMAGE_REQUEST_CODE = 1
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.textViewName)
        val emailTextView: TextView = itemView.findViewById(R.id.textViewEmail)
        val phoneNumberTextView: TextView = itemView.findViewById(R.id.textViewPhoneNumber)
        val pictureImageView: ImageView = itemView.findViewById(R.id.imageViewPicture)
        val userIdTextView: TextView = itemView.findViewById(R.id.textViewUserId)
        val updateButton: Button = itemView.findViewById(R.id.updateButton)
        val deleteButton: Button = itemView.findViewById(R.id.deleteButton)
        val uploadImageButton: Button = itemView.findViewById(R.id.uploadImageButton)
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

        holder.uploadImageButton.setOnClickListener { view ->
            val user = userList[position]

            // Ensure that you have access to the Activity context
            val activityContext = view.context as Activity

            // Create an intent to pick an image from the device's gallery
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"

            // Start the image picker intent with startActivityForResult
            activityContext.startActivityForResult(intent, PICK_IMAGE_REQUEST_CODE)
        }

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
                        deleteUser(context, user)
                    }
                    .setNegativeButton("Cancel", null)
                    .show()
            }
        }
    }



    fun onImageSelected(position: Int, data: Intent?) {
        val selectedImageUri = data?.data
        if (selectedImageUri != null) {
            val user = userList[position]
            val dbRef = FirebaseDatabase.getInstance().getReference("Users").child(user.userId)
            val storageRef = FirebaseStorage.getInstance().reference
            val imageRef = storageRef.child("images/${System.currentTimeMillis()}")

            val uploadTask = imageRef.putFile(selectedImageUri)

            uploadTask.addOnSuccessListener {
                imageRef.downloadUrl.addOnSuccessListener { downloadUri ->
                    val newImageUrl = downloadUri.toString()
                    dbRef.child("imageLink").setValue(newImageUrl).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            //Toast.makeText(context, "Image updated successfully", Toast.LENGTH_SHORT).show()
                        } else {
                           // Toast.makeText(context, "Failed to update image URL", Toast.LENGTH_SHORT).show()
                        }
                    }
                }.addOnFailureListener {
                   // Toast.makeText(context, "Failed to get image download URL", Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener {
               // Toast.makeText(context, "Failed to upload image", Toast.LENGTH_SHORT).show()
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