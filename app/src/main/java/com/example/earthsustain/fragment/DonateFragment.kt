package com.example.earthsustain.fragment

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.earthsustain.R

class DonateFragment : Fragment() {

    private val enteredDigits = mutableListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_donate, container, false)

        val donationAmountEditText = view.findViewById<EditText>(R.id.donationAmountEditText)
        val donateButton = view.findViewById<Button>(R.id.donateButton)

        donationAmountEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                // Handle the Enter key press here
                // You can trigger your "Donate" button click action here
                donateButton.performClick()
                return@setOnEditorActionListener true
            }
            false
        }

        donateButton.setOnClickListener {
            val amountString = donationAmountEditText.text.toString()

            if (amountString.isEmpty()) {
                showToast("Please enter some amount")
                return@setOnClickListener
            }

            // Remove leading zeros and unnecessary decimal point
            val amountFormatted = amountString.trimStart('0').removePrefix(".")
            val amount = amountFormatted.toDoubleOrNull() ?: 0.00

            if (amount < 10 || amount > 99999.99) {
                showToast("Amount must be between 10 and 99999.99")
                return@setOnClickListener
            }

            // Clear the focus from donationAmountEditText to hide the keyboard
            donationAmountEditText.clearFocus()

            // Hide the keyboard explicitly
            val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(donationAmountEditText.windowToken, 0)

            // Clear the entered digits and reset the text of all EditTexts
            enteredDigits.clear()

            val builder = AlertDialog.Builder(requireContext())
            val customLayout = layoutInflater.inflate(R.layout.custom_pin_dialog, null)
            builder.setView(customLayout)

            val digit1 = customLayout.findViewById<EditText>(R.id.digit1)
            val digit2 = customLayout.findViewById<EditText>(R.id.digit2)
            val digit3 = customLayout.findViewById<EditText>(R.id.digit3)
            val digit4 = customLayout.findViewById<EditText>(R.id.digit4)

            val digitEditTexts = arrayOf(digit1, digit2, digit3, digit4)

            // Set all digit EditTexts to not clickable and not focusable
            digitEditTexts.forEach { editText ->
                editText.isClickable = false
                editText.isFocusable = false
                editText.isFocusableInTouchMode = false
                editText.setOnTouchListener { _, _ -> true } // Disable touch events
            }

            // Add text change listeners to automatically move focus
            setPinDigitEditTextListeners(digitEditTexts)

            // Set the first digit field to have focus initially
            digit1.isFocusableInTouchMode = true
            digit1.requestFocus()


            builder.setPositiveButton("OK") { _, _ ->
                val enteredPin = enteredDigits.joinToString("")
                val correctPin = "1234" // Replace with your actual PIN code

                if (enteredPin == correctPin) {
                    // Correct PIN entered, process the donation
                    val formattedAmount = String.format("%.2f", amount)
                    val successMessage = "RM $formattedAmount Donate Successful!"
                    showToast(successMessage)
                } else {
                    // Incorrect PIN entered, display an error message using Toast
                    val errorMessage = "Incorrect PIN Code"
                    showToast(errorMessage)
                    // Clear the entered PIN and remove masks
                    enteredDigits.clear()
                    digitEditTexts.forEach { it.text.clear() }
                    // Set focus back to the first digit field
                    digit1.isFocusableInTouchMode = true
                    digit1.requestFocus()
                }
            }

            builder.setNegativeButton("Cancel") { dialog, _ ->
                dialog.cancel()
            }

            val dialog = builder.create()


            // Handle the Enter key press on digit4
            digit4.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    // Handle the Enter key press here
                    // You can trigger your "OK" button click action here
                    val positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                    positiveButton.performClick()
                    return@setOnEditorActionListener true
                }
                false
            }

            // Show the keyboard when the dialog is displayed
            dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)

            dialog.show()

            // Use ViewTreeObserver to set focus after the dialog is displayed
            dialog.window?.decorView?.viewTreeObserver?.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    dialog.window?.decorView?.viewTreeObserver?.removeOnGlobalLayoutListener(
                        this
                    )
                    // Set focus to the first digit field (digit1) after a short delay
                    digit1.post {
                        digit1.isFocusableInTouchMode = true
                        digit1.requestFocus()
                    }
                }
            })
        }

        return view
    }

    private fun setPinDigitEditTextListeners(digitEditTexts: Array<EditText>) {
        for (i in 0 until digitEditTexts.size) {
            val currentDigitEditText = digitEditTexts[i]

            currentDigitEditText.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    // Handle masking and store entered digits
                    s?.let {
                        if (s.isNotEmpty()) {
                            val digit = s.toString()
                            enteredDigits.add(digit)
                            currentDigitEditText.removeTextChangedListener(this)
                            currentDigitEditText.setText("•")
                            currentDigitEditText.setSelection(1)
                            currentDigitEditText.addTextChangedListener(this)
                        } else {
                            // If the user deletes a digit, clear it from the stored digits
                            enteredDigits.takeIf { it.isNotEmpty() }?.removeAt(enteredDigits.size - 1)
                        }
                    }
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    // Do nothing
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    // Move focus to the previous digit EditText on backspace press
                    if (s?.isEmpty() == true && count == 0 && start > 0 && i > 0) {
                        digitEditTexts[i - 1].isFocusableInTouchMode = true
                        digitEditTexts[i - 1].requestFocus()
                    } else if (s?.length == 1) {
                        // Move focus to the next digit EditText
                        if (i < digitEditTexts.size - 1) {
                            digitEditTexts[i + 1].isFocusableInTouchMode = true
                            digitEditTexts[i + 1].requestFocus()
                        }
                    }
                }
            })

            // Handle backspace key press for clearing the current and previous EditTexts
            currentDigitEditText.setOnKeyListener(View.OnKeyListener { _, keyCode, event ->
                if (keyCode == KeyEvent.KEYCODE_DEL && event.action == KeyEvent.ACTION_DOWN) {
                    if (i > 0) {
                        // Clear the current and previous EditTexts
                        for (j in i downTo i - 1) {
                            digitEditTexts[j].text.clear()
                            // Clear digits from the enteredDigits list
                            if (enteredDigits.isNotEmpty()) {
                                enteredDigits.removeAt(enteredDigits.size - 1)
                            }
                        }
                        // Move focus to the first cleared EditText
                        digitEditTexts[i - 1].isFocusableInTouchMode = true
                        digitEditTexts[i - 1].requestFocus()
                    }
                    return@OnKeyListener true
                }
                false
            })


        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}
