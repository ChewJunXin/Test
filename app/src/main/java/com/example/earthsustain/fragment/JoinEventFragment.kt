package com.example.earthsustain.fragment

import android.content.Intent
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentActivity
import com.example.earthsustain.R
import com.example.earthsustain.activity.EventActivity

class JoinEventFragment : Fragment() {

    private lateinit var visible: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_join_event, container, false)

        // Find the ListView components
        val eventjoinedlist = view.findViewById<ListView>(R.id.eventjoinedlist)
        val eventcancellist = view.findViewById<ListView>(R.id.eventcancellist)

        // Sample data for event list
        val events = mutableListOf("Event 1", "Event 2", "Event 3")
        val joineventAdapter = JoinEventAdapter(requireContext() as FragmentActivity, events)
        eventjoinedlist.adapter = joineventAdapter

        // Sample data for my event list
        val myEvents = mutableListOf("My Event 1", "My Event 2", "My Event 3")
        val cancelEventAdapter = CancelEventAdapter(requireContext() as FragmentActivity, myEvents)
        eventcancellist.adapter = cancelEventAdapter

        val myjoinbtn = view.findViewById<Button>(R.id.myjoinbtn)

        // Handle create button click (implement your edit logic here)
        myjoinbtn.setOnClickListener {
            // Implement create button
            val intent = Intent(requireContext(), EventActivity::class.java)
            intent.putExtra("joinevent", "approve")
            startActivity(intent)
        }

        val noEventsTextView = view.findViewById<TextView>(R.id.noEventsTextView)
        val cancellist = view.findViewById<ListView>(R.id.eventcancellist)

        visible = "Yes"
        if (visible == "Yes") {
            cancellist.visibility = View.VISIBLE
            noEventsTextView.visibility = View.GONE
        }

        return view
    }

    // Custom adapter for the event list with only a delete button
    inner class JoinEventAdapter(context: FragmentActivity, private val events: MutableList<String>) :
        ArrayAdapter<String>(context, R.layout.event_check_layout, events) {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val view = convertView ?: LayoutInflater.from(context)
                .inflate(R.layout.event_check_layout, parent, false)

            val eventTitle = view.findViewById<TextView>(R.id.eventTitle)
            val descriptionTextView = view.findViewById<TextView>(R.id.description)

            val event = events[position]

            eventTitle.text = event

            // Set an OnTouchListener to the descriptionTextView
            descriptionTextView.setOnTouchListener { _, event ->
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
                false
            }

            // Enable scrolling for descriptionTextView
            descriptionTextView.movementMethod = ScrollingMovementMethod()

            return view
        }
        private fun showToast(message: String) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    // Custom adapter for my event list with edit and delete buttons in parallel
    inner class CancelEventAdapter(context: FragmentActivity, private val myEvents: MutableList<String>) :
        ArrayAdapter<String>(context, R.layout.event_check_layout, myEvents) {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val view = convertView ?: LayoutInflater.from(context)
                .inflate(R.layout.event_check_layout, parent, false)

            val eventTitle = view.findViewById<TextView>(R.id.eventTitle)
            val descriptionTextView = view.findViewById<TextView>(R.id.description)

            if (visible == "Yes") {
                val event = myEvents[position]

                eventTitle.text = event

                // Set an OnTouchListener to the eventTitle TextView
                descriptionTextView.setOnTouchListener { _, event ->
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
                    false
                }

                // Enable scrolling for descriptionTextView
                descriptionTextView.movementMethod = ScrollingMovementMethod()
            }
            return view
        }
    }

}