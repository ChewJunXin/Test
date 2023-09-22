package com.example.earthsustain


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_test_programme)

        // Find the ListView components
        val eventListView = findViewById<ListView>(R.id.eventlist)
        val myEventListView = findViewById<ListView>(R.id.myeventlist)

        // Sample data for event list
        val events = mutableListOf("Event 1", "Event 2", "Event 3")
        val eventAdapter = EventAdapter(this, events)
        eventListView.adapter = eventAdapter

        // Sample data for my event list
        val myEvents = mutableListOf("My Event 1", "My Event 2", "My Event 3")
        val myEventAdapter = MyEventAdapter(this, myEvents)
        myEventListView.adapter = myEventAdapter

        val createButton = findViewById<Button>(R.id.createbtn)

        // Handle create button click (implement your edit logic here)
        createButton.setOnClickListener {
            // Implement your edit logic here
        }

    }

    // Custom adapter for the event list with only a delete button
    inner class EventAdapter(context: AppCompatActivity, private val events: MutableList<String>) :
        ArrayAdapter<String>(context, R.layout.event_item_layout, events) {

        private val joinedEvents: MutableSet<Int> = HashSet()

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val view = convertView ?: LayoutInflater.from(context)
                .inflate(R.layout.event_item_layout, parent, false)

            val eventTitle = view.findViewById<TextView>(R.id.eventTitle)
            val joinButton = view.findViewById<Button>(R.id.joinbtn)
            val descriptionTextView = view.findViewById<TextView>(R.id.description)

            val event = events[position]

            eventTitle.text = event

            if (joinedEvents.contains(position)) {
                joinButton.text = "Joined"
            } else {
                joinButton.text = "Join"
            }

            joinButton.setOnClickListener {
                if (joinedEvents.contains(position)) {
                    // Display a confirmation dialog to cancel join
                    showCancelJoinConfirmationDialog(position)
                } else {
                    joinEvent(position, joinButton)
                }
            }

            // Set an OnTouchListener to the descriptionTextView
            descriptionTextView.setOnTouchListener { v, event ->
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        // Disallow ListView to intercept touch events
                        parent.requestDisallowInterceptTouchEvent(true)
                    }
                    MotionEvent.ACTION_UP -> {
                        // Allow ListView to intercept touch events
                        parent.requestDisallowInterceptTouchEvent(false)
                    }
                }
                // Handle the touch event in the TextView
                v.onTouchEvent(event)
                true
            }

            // Enable scrolling for descriptionTextView
            descriptionTextView.movementMethod = ScrollingMovementMethod()

            return view
        }

        private fun showCancelJoinConfirmationDialog(position: Int) {
            val alertDialogBuilder = AlertDialog.Builder(context)
            alertDialogBuilder.setMessage("Are you sure you want to cancel join the event?")
            alertDialogBuilder.setPositiveButton("Yes") { _, _ ->
                joinedEvents.remove(position)
                notifyDataSetChanged()
                // Show a success Toast message
                showToast("Event cancelled successfully")
            }
            alertDialogBuilder.setNegativeButton("No") { _, _ -> }
            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()
        }

        private fun joinEvent(position: Int, joinButton: Button) {
            // Perform the join operation (implement your logic here)
            // For now, we'll simulate a successful join
            joinedEvents.add(position)
            notifyDataSetChanged()
            // Show a success Toast message
            showToast("Event joined successfully")
        }

        private fun showToast(message: String) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    // Custom adapter for my event list with edit and delete buttons in parallel
    inner class MyEventAdapter(context: AppCompatActivity, private val myEvents: MutableList<String>) :
        ArrayAdapter<String>(context, R.layout.my_event_item_layout, myEvents) {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val view = convertView ?: LayoutInflater.from(context)
                .inflate(R.layout.my_event_item_layout, parent, false)

            val eventTitle = view.findViewById<TextView>(R.id.eventTitle)
            val editButton = view.findViewById<Button>(R.id.editbtn)
            val deleteButton = view.findViewById<Button>(R.id.deletebtn)
            val descriptionTextView = view.findViewById<TextView>(R.id.description)

            val event = myEvents[position]

            eventTitle.text = event

            // Set an OnTouchListener to the eventTitle TextView
            descriptionTextView.setOnTouchListener { v, event ->
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        // Disallow ListView to intercept touch events
                        parent.requestDisallowInterceptTouchEvent(true)
                    }
                    MotionEvent.ACTION_UP -> {
                        // Allow ListView to intercept touch events
                        parent.requestDisallowInterceptTouchEvent(false)
                    }
                }
                // Handle the touch event in the TextView
                v.onTouchEvent(event)
                true
            }

            // Handle edit button click (implement your edit logic here)
            editButton.setOnClickListener {
                // Implement your edit logic here
            }

            // Handle delete button click (implement your delete logic here)
            deleteButton.setOnClickListener {
                showDeleteConfirmationDialog(position)
            }

            // Enable scrolling for descriptionTextView
            descriptionTextView.movementMethod = ScrollingMovementMethod()

            return view
        }

        private fun showDeleteConfirmationDialog(position: Int) {
            val alertDialogBuilder = AlertDialog.Builder(context)
            alertDialogBuilder.setMessage("Are you sure you want to delete this event?")
            alertDialogBuilder.setPositiveButton("Yes") { _, _ ->
                // Remove the item from the list and notify the adapter
                myEvents.removeAt(position)
                notifyDataSetChanged()

                // Show a success Toast message
                Toast.makeText(context, "Event deleted successfully", Toast.LENGTH_SHORT).show()
            }
            alertDialogBuilder.setNegativeButton("No") { _, _ -> }
            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()
        }
    }
}