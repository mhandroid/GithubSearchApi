package com.mh.android.githubsearch.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mh.android.githubsearch.R
import com.mh.android.githubsearch.model.User
import com.squareup.picasso.Picasso

/**
 * Created by mubarak.hussain on 30/11/20.
 */
class SearchAdapter(val context: Context, private val userList: MutableList<User>, private val listItemCLickListener: ListItemCLickListener?) : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    class SearchViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val txtTitle: TextView = itemView.findViewById(R.id.txtTitle)
        val imgAlbum: ImageView = itemView.findViewById(R.id.imgAlbum)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.search_item_layout, parent, false)
        return SearchViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val user: User = userList[position]
        holder.txtTitle.text = user.login
        Picasso.with(context).load(user.avatarUrl).placeholder(R.drawable.ic_launcher_background).fit().into(holder.imgAlbum)
        holder.view.setOnClickListener { listItemCLickListener?.onItemClick(user) }
    }

    override fun getItemCount(): Int = userList.count()

    interface ListItemCLickListener {
        fun onItemClick(user: User)
    }

    fun updateList(newList:List<User>){
        userList.clear()
        userList.addAll(newList)
        this.notifyDataSetChanged()
    }
}