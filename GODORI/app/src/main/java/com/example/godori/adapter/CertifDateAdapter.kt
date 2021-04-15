package com.example.godori.adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.example.godori.R
import com.example.godori.data.ResponseCertiTab
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

//        val bitmap = BitmapFactory.decodeFile(certifList!![position].image)
//        if(bitmap == null)
//            holder.certifImg.setImageResource(R.drawable.certif_un)
//        else
//            holder.certifImg.setImageBitmap(bitmap)

        holder.personName.setText(certifList!![position].user_name)
//            holder.userImg.setImageResource(certiList[position].user_img)
    }

    //리스트의 전체 개수
    override fun getItemCount(): Int {
        var size : Int = 0
        if (certifList != null) {
            size = certifList.size
        }
        return size
    }
}