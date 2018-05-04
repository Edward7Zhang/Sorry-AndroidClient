package com.edwardzhang.sorry.templatelist

import android.arch.lifecycle.Observer
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.edwardzhang.sorry.R
import com.edwardzhang.sorry.entity.GifTemplateItemEntity
import com.edwardzhang.sorry.utils.MATCH
import com.edwardzhang.sorry.utils.VideoCoverLoader
import com.edwardzhang.sorry.utils.WRAP
import com.edwardzhang.sorry.utils.dip2Px

class TemplateListAdapter : RecyclerView.Adapter<TemplateListAdapter.ViewHolder>(),Observer<List<GifTemplateItemEntity>> {
    override fun onChanged(t: List<GifTemplateItemEntity>?) {
        mList = t
    }

    var mList: List<GifTemplateItemEntity>? = null
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    var onItemClickListener: OnItemClickListener? = null

    interface OnItemClickListener{
        fun onItemClick(position: Int, item: GifTemplateItemEntity?)
    }

    operator fun get(index: Int): GifTemplateItemEntity?{
       return mList?.get(index)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_template_list,parent,false)
        return ViewHolder(itemView).apply { itemView.setOnClickListener(this) }
    }

    override fun getItemCount(): Int {
        return mList?.size ?: 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val name = this[position]?.name
        val file = this[position]?.file
        holder.textName.text = name?:"null"
        VideoCoverLoader.instance.load(file,holder.imageCover)
    }


    inner class ViewHolder(itemView: View,
                           val imageCover: ImageView = itemView.findViewById(R.id.imageItemTemplateCover),
                           val textName: TextView = itemView.findViewById(R.id.textItemTemplateName)):
            RecyclerView.ViewHolder(itemView),
            View.OnClickListener {
        init {
            val params = RecyclerView.LayoutParams(MATCH,WRAP)
            params.leftMargin = dip2Px(5)
            params.rightMargin = params.leftMargin
            itemView.layoutParams = params
        }
        override fun onClick(v: View?) {
            onItemClickListener?.onItemClick(adapterPosition,mList?.get(adapterPosition))
        }

    }
}