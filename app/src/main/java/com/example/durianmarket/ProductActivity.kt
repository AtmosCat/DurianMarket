package com.example.durianmarket

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.durianmarket.databinding.ActivityProductBinding
import com.google.android.material.snackbar.Snackbar
import java.text.NumberFormat

class ProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_product)

        // binding 이름 겹쳐도 되는지?
        binding = ActivityProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var productPosition = intent.getStringExtra("position").toString()
        var productPositionIndex = productPosition.toInt()
        var product = ProductManager.products[productPositionIndex]

        var productPriceString = product.price.toString()
        var edittedPrice = ""

        // price에 천 단위 쉼표 넣기
        if (productPriceString.length >= 4) {
            try {
                val productPrice = productPriceString.toLong()
                edittedPrice = NumberFormat.getInstance().format(productPrice).toString() + "원"
            } catch (e: NumberFormatException) {
                // priceString이 Long 타입으로 변환되지 않으면 원래 문자열을 반환
                edittedPrice = productPriceString + "원"
            }
        } else {
            edittedPrice = productPriceString + "원"
        }

        //스코프함수로 간소화
        with(product) {
            binding.ivProductImage.setImageResource(icon)
            binding.tvProductTitle.text = title
            binding.tvProductSeller.text = seller
            binding.tvProductAddress.text = address
            binding.tvProductIntroduction.text = introduction
            binding.tvProductPrice.text = edittedPrice
        }

        val chatButton = binding.btnBack
        chatButton.setOnClickListener {
            finish()
        }

        val emptyFavorite = binding.ivFavorite
        val filledFavorite = binding.ivFavorite2

        if (!ProductManager.products[productPositionIndex].favorite) {
            filledFavorite.visibility = View.GONE
        } else {
            filledFavorite.visibility = View.VISIBLE
        }

        emptyFavorite.setOnClickListener {
            filledFavorite.visibility = View.VISIBLE
            ProductManager.products[productPositionIndex].like += 1
            ProductManager.products[productPositionIndex].favorite = true
            showSnackbar(it, "관심 목록에 추가되었습니다.")
//            val intent1 = Intent(this, MainActivity::class.java)
//            intent1.putExtra("newLike", ProductManager.products[productPositionIndex].like.toString())
//            intent1.putExtra("newFavorite", ProductManager.products[productPositionIndex].favorite)
//            startActivity(intent1)
            // 이렇게 하면 다시 MainActivity가 생김
            // resultCallback 쓰기, finish,
            // ActivityLifeCycleCallback - onResume,
            // main위에 ProductActivity가 올라가는 순간 Main이 onStopped
        }

        // 이미지 리소스만 바꾸는 방식으로 변경해도 됨
        filledFavorite.setOnClickListener {
            filledFavorite.visibility = View.GONE
            ProductManager.products[productPositionIndex].like -= 1
            ProductManager.products[productPositionIndex].favorite = false
            showSnackbar(it, "관심 목록에서 삭제되었습니다.")
        }
    }
}

private fun showSnackbar(view: View, message: String) {
    Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
}