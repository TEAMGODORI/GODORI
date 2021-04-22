package com.example.godori.adapter

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.godori.R
import com.example.godori.data.ResponseCertiTab
import java.net.URL
import java.util.*


class CertifDateAdapter(
    private val certifList: List<ResponseCertiTab.Data>?,
    val context: Context?
) :
    RecyclerView.Adapter<CertifDateAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) { //
        var personName: TextView = itemView.findViewById(R.id.personName)
        var certifImg: ImageView = itemView.findViewById(R.id.certifImg)
        var userImg: ImageView = itemView.findViewById(R.id.my_iv_profile)
    }

    // 아이템 하나가 들어갈 뷰를 만들고 뷰 홀더에 넣어줌
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CertifDateAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_certif_tab, parent, false)
        return ViewHolder(view)
    }

    //뷰를 그리는 부분
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

//        val bitmap = BitmapFactory.decodeStream(URL(certifList?.get(position)?.image).getContent() as InputStream?)
//        if(bitmap == null)
//            holder.certifImg.setImageResource(R.drawable.certif_un)
//        else
//            holder.certifImg.setImageBitmap(bitmap)
//        if (context != null) {
//            Glide.with(context).load(certifList!![position].image).into(certifImg)
//        }

        val item: ResponseCertiTab.Data = certifList!!.get(position)
        val url: String = certifList.get(position).image

        if (url.length > 0) {
            Glide.with(holder.certifImg.context)
                .load(url)
                .placeholder(android.R.drawable.progress_indeterminate_horizontal)
                .error(android.R.drawable.stat_notify_error)
                .into(holder.certifImg)

        } else {
            Glide.with(holder.certifImg.context)
                .load(R.drawable.certif_un)
                .placeholder(android.R.drawable.progress_indeterminate_horizontal)
                .error(android.R.drawable.stat_notify_error)
                .into(holder.certifImg)
        }

        holder.personName.setText(certifList?.get(position)?.user_name)
        holder.userImg.setImageResource(R.drawable.gr_img_profile_basic)
    }

    //리스트의 전체 개수
    override fun getItemCount(): Int {
        var size: Int = 0
        if (certifList != null) {
            size = certifList.size
        }
        return size
    }
}