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

// 어댑터는 원본 데이터를 파라미터로 받아 RecyclerView에 배치하는 역할 수행
// 따라서 그 기능을 하는 RecyclerView.Adapter를 상속


class MyAdapter(val mItems: MutableList<MyItem>) : RecyclerView.Adapter<MyAdapter.Holder>() {
    // 인터페이스는 특정 기능을 클래스에 강제하는 계약을 정의함 - 구현부가 없는 설계도
    // 실제 구현을 할 때 :object로 만드는 게 일종의 정해진 틀임
    // 인터페이스를 구현하는 클래스는 인터페이스에 정의된 메서드와 프로퍼티를 구체적으로 구현해야 함
    // MainActivity, Adapter 사이에 통신할 수 있는 인터페이스
    // 어댑터에서 구체적인 메서드까지 구현하면 안되므로(단일책임원칙 / 메서드 구현하고 어떤 동작하는지는 Activity의 영역)
    // interface 선언만 하고, 구현이 아직 안된 함수 fun onCLick()만 선언해놓음
    interface ItemClick {
        fun onClick(view: View, position: Int)
    }

    interface ItemLongClick {
        fun onLongClick(view: View, position: Int)
    }

    // 통신을 위한 인터페이스 타입의 변수 선언
    // onBindViewHolder 안에서 itemView 클릭 시 onClick() 메서드가 실행된다는 사실을 정의해놓기 위해
    // 가상의 인터페이스 변수 선언
    var itemClick : ItemClick? = null
    var itemLongClick: ItemLongClick? = null

    // RecyclerView가 새로운 ViewHolder를 필요로 할 때 호출되어 새로운 항목 뷰를 생성하고 초기화
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        // 뷰 바인딩을 통해 parent(RecyclerView의 부모 뷰 그룹)의 context(현재)의 바인딩 객체를 가져옴
        val binding = ItemRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        // 가져온 binding을 Holder()에 넣어서 ViewHolder를 생성(재활용되는 여러 뷰들 중에 골라서)
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
    // Holder는 화면에 표시할 아이템이나 데이터들을 임시저장하는 역할을 하는 RecyclerView.ViewHolder를 상속
    // -> binding을 파라미터로 받아서 binding이 저장하고 있는 레이아웃의 컴포넌트들을 홀더 안에 val로 저장
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



