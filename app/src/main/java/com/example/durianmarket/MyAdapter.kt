package com.example.durianmarket

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.durianmarket.databinding.ItemRecyclerviewBinding
import java.text.NumberFormat

class MyAdapter(val mItems: MutableList<MyItem>) : RecyclerView.Adapter<MyAdapter.Holder>() {

    // MainActivity, Adapter 사이에 통신할 수 있는 인터페이스
    interface ItemClick {
        fun onClick(view: View, position: Int)
    }

    interface ItemLongClick {
        fun onLongClick(view: View, position: Int)
    }

    // 통신을 위한 인터페이스 타입의 변수 선언
    var itemClick : ItemClick? = null
    var itemLongClick: ItemLongClick? = null
//    private var itemClickListener: OnItemClickListener? = null

    // ViewHolder를 생성?
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    // Holder가 임시로 갖고 있던 레이아웃들에 실제 원본 데이터의 값을 할당
    override fun onBindViewHolder(holder: Holder, position: Int){
        // itemView(RecyclerView 전체)에 onClickListener를 설정
        // itemView 전체에 클릭이 발생하면, it(View)와 position값을 onClick에 넣어서 itemClick을 호출
        holder.itemView.setOnClickListener{
            itemClick?.onClick(it, position)
        }
        // 롱클릭 발생 시 수행하는 동작
        holder.itemView.setOnLongClickListener{
            itemLongClick?.onLongClick(it, position)
            true // 내가 롱클릭 썼어~(터치 이벤트를 소모했으니 뒤에는 쓰지 마라~)
        }

        holder.icon.setImageResource(mItems[position].icon)
        holder.title.text = mItems[position].title
        holder.price.text = mItems[position].price.toString()
        val priceString = mItems[position].price.toString()
        // price에 천 단위 쉼표 넣기
        if (holder.price.text.length >= 4) {
            try{
                val priceNumber = priceString.toLong()
                holder.price.text = NumberFormat.getInstance().format(priceNumber).toString()+"원"
            } catch (e: NumberFormatException) {
                // priceString이 Long 타입으로 변환되지 않으면 원래 문자열을 반환
                holder.price.text = priceString
            }
        } else {
            holder.price.text = holder.price.text.toString()+"원"
        }

        if (mItems[position].favorite){
            holder.likeIcon.setImageResource(R.drawable.heart_filled)
        } else {
            holder.likeIcon.setImageResource(R.drawable.heart_empty)
        }

        holder.address.text = mItems[position].address
        holder.like.text = mItems[position].like.toString()
        holder.chat.text = mItems[position].chat
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemCount(): Int {
      return mItems.size
    }

    // 원본 데이터가 들어갈 레이아웃의 View들을 임시로 저장
    // Binder의 root에서 유래한(?) RecyclerView 타입의 binding 값을 파라미터로 받아 Holder(임시보관소)를 생성
    inner class Holder(val binding: ItemRecyclerviewBinding): RecyclerView.ViewHolder(binding.root) {
        val icon = binding.ivIcon
        val title = binding.tvTitle
//        val introduction = binding.
//        val seller = binding.
        val price = binding.tvPrice
        val address = binding.tvAddress
        val like = binding.tvLike
        val likeIcon = binding.ivLike
        val chat = binding.tvChat
    }
}



