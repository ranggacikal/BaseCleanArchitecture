package com.ranggacikal.basecleanarchitecture.presentation.data_user.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.ranggacikal.basecleanarchitecture.R
import com.ranggacikal.basecleanarchitecture.domain.model.UserModel
import com.ranggacikal.basecleanarchitecture.presentation.data_user.HomeFragmentDirections

class UserAdapter(private val listUser: List<UserModel>, private val context: Context) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtNama: TextView = itemView.findViewById(R.id.tv_item_nama_user)
        val txtEmail: TextView = itemView.findViewById(R.id.tv_item_email_user)
        val txtNoHandphone: TextView = itemView.findViewById(R.id.tv_item_no_handphone_user)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val itemsViewModel = listUser[position]
        holder.txtNama.text = itemsViewModel.nama_lengkap
        holder.txtEmail.text = itemsViewModel.email
        holder.txtNoHandphone.text = itemsViewModel.no_handphone

        holder.itemView.setOnClickListener {
            val id_user = itemsViewModel.id_user
            val action = HomeFragmentDirections.actionHomeToDetail(id_user)
            Navigation.findNavController(holder.itemView).navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return listUser.size
    }
}