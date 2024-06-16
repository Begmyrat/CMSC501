package com.begmyratmammedov.finalmanagement

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import com.begmyratmammedov.finalmanagement.adapter.ItemsListAdapter
import com.begmyratmammedov.finalmanagement.databinding.FragmentItemsBinding
import com.begmyratmammedov.finalmanagement.model.Item
import com.begmyratmammedov.finalmanagement.model.Sale
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.data.Entry


class ItemsFragment : Fragment() {

    private lateinit var binding: FragmentItemsBinding
    private lateinit var itemsAdapter: ItemsListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentItemsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupChart()
        loadChartData()
        itemsAdapter = ItemsListAdapter(requireContext())
        binding.rvTopSellingItems.apply {
            adapter = itemsAdapter
            layoutManager = LinearLayoutManager(context)
        }
        loadSampleData()
    }

    private fun setupChart() {
        val entries = ArrayList<Entry>()
        // Sample data
        entries.add(Entry(1f, 240f)) // 1st of month, 240 sales
        entries.add(Entry(2f, 480f)) // 2nd of month, 480 sales
        entries.add(Entry(3f, 150f)) // etc.

        val dataSet = LineDataSet(entries, "Sales Over Time")
        val lineData = LineData(dataSet)
        binding.chart.data = lineData
        binding.chart.invalidate() // refresh the chart
    }

    fun fetchSalesData(): List<Sale> {
        // This should ideally be done in the background
        return listOf(
            Sale(System.currentTimeMillis() - 86400000 * 3, "1", 20),
            Sale(System.currentTimeMillis() - 86400000 * 2, "2", 15),
            Sale(System.currentTimeMillis() - 86400000, "3", 40)
        )
    }

    private fun loadChartData() {
        val salesData = fetchSalesData()
        val entries = mutableListOf<Entry>()

        salesData.forEach { sale ->
            // Convert timestamp to a readable format or relative days, etc.
            // Here simply using timestamp, you might want to adjust it based on your requirements
            entries.add(Entry(sale.date.toFloat(), sale.quantity.toFloat()))
        }

        // Create a dataset from these entries
        val dataSet = LineDataSet(entries, "Sales Over Time")
        dataSet.color = resources.getColor(R.color.black, null)
        dataSet.valueTextColor = resources.getColor(R.color.gray, null)

        // Apply the dataset to the chart
        val lineData = LineData(dataSet)
        binding.chart.data = lineData
        binding.chart.description.text = "Sales Data" // Customize your chart description

        // Refresh the chart to show updated data
        binding.chart.invalidate()
    }

    private fun loadSampleData() {
        val sampleItems = listOf(
            Item("1", "Apple", imageURL = "https://m.media-amazon.com/images/I/918YNa3bAaL.jpg"),
            Item("2", "Orange", imageURL = "https://m.media-amazon.com/images/I/710A+YmGSqL._AC_UF894,1000_QL80_.jpg"),
            Item("3", "Watermelon", imageURL = "https://media.istockphoto.com/id/1142119394/photo/whole-and-slices-watermelon-fruit-isolated-on-white-background.jpg?s=612x612&w=0&k=20&c=A5XnLyeI_3mwkCNadv-QLU4jzgNux8kUPfIlDvwT0jo=")
        )
        itemsAdapter.differ.submitList(sampleItems)
    }
}