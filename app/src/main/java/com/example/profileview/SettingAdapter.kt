package com.example.profileview

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.profileview.component.TextCell

class SettingAdapter(var context: Context) : RecyclerView.Adapter<SettingAdapter.ViewHolder>() {

    private var firstName = 0
    private var lastName = 0
    private var bio = 0
    private var rowCount = 0

    fun updateRows() {
        rowCount = 0
        firstName = rowCount++
        lastName = rowCount++
        bio = rowCount++
        bio = rowCount++
        bio = rowCount++
        bio = rowCount++
        bio = rowCount++
        bio = rowCount++
        bio = rowCount++
        bio = rowCount++
        bio = rowCount++
        bio = rowCount++
        bio = rowCount++
        rowCount = rowCount++
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(TextCell(context))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val textCell = holder.itemView as TextCell
        if (position == firstName) {
            textCell.setText(
                "First Name",
                true
            )
        } else if (position == lastName) {
            textCell.setText(
                "Last Name",
                true
            )
        } else {
            textCell.setText(
                "Bio$position", true
            )
        }
    }

    override fun getItemCount(): Int {
        return rowCount
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
