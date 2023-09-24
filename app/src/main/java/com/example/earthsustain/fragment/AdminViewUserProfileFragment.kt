package com.example.earthsustain.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.earthsustain.R
import com.example.earthsustain.activity.EventActivity
import com.example.earthsustain.activity.LoginActivity

class AdminViewUserProfileFragment : Fragment() {

    data class UserProfile(val name: String, val email: String, val phoneNumber: String)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_admin_view_user_profile, container, false)
        val userRecyclerView = rootView.findViewById<RecyclerView>(R.id.userRecyclerView)
        val adapter = UserAdapter(getSampleUserProfiles())
        userRecyclerView.adapter = adapter
        return rootView
    }

    // Replace this with actual user data retrieval from your database
    private fun getSampleUserProfiles(): List<UserProfile> {
        val userList = ArrayList<UserProfile>()
        userList.add(UserProfile("User 1", "user1@example.com", "123-456-7890"))
        userList.add(UserProfile("User 2", "user2@example.com", "987-654-3210"))
        // Add more user profiles as needed
        return userList
    }

    class UserAdapter(private val userList: List<UserProfile>) :
        RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.user_item, parent, false)
            return UserViewHolder(view)
        }

        override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
            val user = userList[position]
            holder.bind(user)
        }

        override fun getItemCount(): Int {
            return userList.size
        }

        inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            fun bind(user: UserProfile) {
                itemView.findViewById<TextView>(R.id.userNameTextView).text = user.name
                itemView.findViewById<TextView>(R.id.userEmailTextView).text = user.email
                itemView.findViewById<TextView>(R.id.userPhoneTextView).text = user.phoneNumber
            }
        }
    }
}