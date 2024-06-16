package com.begmyratmammedov.finalmanagement

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.begmyratmammedov.finalmanagement.adapter.ItemsListAdapter
import com.begmyratmammedov.finalmanagement.databinding.FragmentMainBinding
import com.begmyratmammedov.finalmanagement.model.Item
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class MainFragment : Fragment() {

    private lateinit var itemsAdapter: ItemsListAdapter
    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        itemsAdapter = ItemsListAdapter(requireContext())
        binding.recList.apply {
            adapter = itemsAdapter
            layoutManager = LinearLayoutManager(context)
        }
        loadSampleData()

        itemsAdapter.onItemClick = {
            Toast.makeText(requireContext(), it.name, Toast.LENGTH_SHORT).show()
        }

        binding.fabAddItem.setOnClickListener {
            openAddItemDialog()
        }

        setupCategorySpinner()
    }

    private fun setupCategorySpinner() {
        val categories = listOf("Fruits", "Electronics", "Apparel", "Home & Garden", "Toys", "Books")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, categories)
        binding.spinnerCategories.adapter = adapter
    }

    private fun openAddItemDialog() {
        val layout = LayoutInflater.from(context).inflate(R.layout.dialog_add_item, null)
        val textInputLayoutName = layout.findViewById<TextInputLayout>(R.id.textInputLayoutName)
        val textInputLayoutQuantity = layout.findViewById<TextInputLayout>(R.id.textInputLayoutQuantity)
        val editTextName = textInputLayoutName.editText as TextInputEditText
        val editTextQuantity = textInputLayoutQuantity.editText as TextInputEditText

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Add Item")
            .setView(layout)
            .setNegativeButton("Cancel", null)
            .setPositiveButton("Add") { dialog, which ->
                val name = editTextName.text.toString()
                val quantity = editTextQuantity.text.toString()
                addItemToList(name, quantity)
            }
            .show()
    }

    private fun addItemToList(name: String, quantity: String) {
        val newItem = Item(System.currentTimeMillis().toString(), name, quantity)
        val currentList = itemsAdapter.differ.currentList.toMutableList()
        currentList.add(newItem)
        itemsAdapter.differ.submitList(currentList)
        Toast.makeText(context, "Item added: $name, Quantity: $quantity", Toast.LENGTH_LONG).show()
    }

    private fun loadSampleData() {
        val sampleItems = listOf(
            Item("1", "Apple", imageURL = "https://m.media-amazon.com/images/I/918YNa3bAaL.jpg"),

        )
        itemsAdapter.differ.submitList(sampleItems)
    }

}