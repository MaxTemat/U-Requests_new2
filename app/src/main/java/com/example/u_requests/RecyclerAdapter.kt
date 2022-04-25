package com.example.u_requests

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import java.time.LocalDateTime

class RecyclerAdapter: RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate((R.layout.item_recycler_view), parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder, position: Int) {
        holder.itemObject.text = "Pas de note en java"
        holder.itemNote.text = "Note final: 00/20"
        holder.itemStatus.text = "En attente de validation"
        holder.itemStep.text = "00/06 etapes"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            holder.itemDate.text = "created at ${LocalDateTime.now()}"
    }

    override fun getItemCount(): Int {
        return 10
    }
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var itemObject: TextView = itemView.findViewById(R.id.object_Tv_recycler)
        var itemNote: TextView = itemView.findViewById(R.id.note_request_recycler)
        var itemStatus: TextView = itemView.findViewById(R.id.status_request_recycler)
        var itemStep: TextView = itemView.findViewById(R.id.step_request_recycler)
        var itemDate: TextView = itemView.findViewById(R.id.createdat_request_recycler)
        var itemDetails: ImageView = itemView.findViewById(R.id.seeDetails_request_recycler_Iv)
        var layoutCly: ConstraintLayout = itemView.findViewById(R.id.layout_Cly)
    }
}