package com.example.durianmarket

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.lifecycle.ReportFragment.Companion.reportFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.durianmarket.databinding.ActivityMainBinding
import android.os.Handler
import android.util.Log
import android.widget.TextView
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import com.example.durianmarket.databinding.ItemRecyclerviewBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val adapter by lazy { MyAdapter(ProductManager.products) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        // 데이터 원본 준비
        ProductManager.addProduct("0", R.drawable.sample1, "산 지 한 달 된 선풍기 팝니다", "이사가서 필요가 없어졌어요 급하게 내놓습니다",
            "대현동", 1000, "서울 서대문구 창천동", 13, "25", false)
        ProductManager.addProduct("1", R.drawable.sample2, "김치냉장고", "이사로인해 내놔요",
            "안마담", 20000, "인천 계양구 귤현동", 8, "44", false)
        ProductManager.addProduct("2", R.drawable.sample3, "샤넬 카드지갑", "고퀄지갑이구요\n사용감이 있어서 싸게 내어둡니다",
            "코코유", 10000, "수성구 범어동", 25, "24", false)
        ProductManager.addProduct("3", R.drawable.sample4, "금고", "금고\n떼서 가져가야함\n대우월드마크센텀\n미국이주관계로 싸게 팝니다",
            "Nicole", 10000, "해운대구 우제2동", 4, "11", false)
        ProductManager.addProduct("4", R.drawable.sample5, "갤럭시Z플립3 팝니다", "갤럭시 Z플립3 그린 팝니다\n항시 케이스 씌워서 썻고 필름 한장챙겨드립니다\n화면에 살짝 스크래치난거 말고 크게 이상은없습니다!",
            "절명", 150000, "연제구 연산제8동", 28, "77", false)
        ProductManager.addProduct("5", R.drawable.sample6, "프라다 복조리백", "까임 오염없고 상태 깨끗합니다\n정품여부모름",
            "미니멀하게", 50000, "수원시 영통구 원천동", 83, "14", false)
        ProductManager.addProduct("6", R.drawable.sample7, "울산 동해오션뷰 60평 복층 펜트하우스 1일 숙박권 펜션 힐링 숙소 별장", "울산 동해바다뷰 60평 복층 펜트하우스 1일 숙박권\n\n(에어컨이 없기에 낮은 가격으로 변경했으며 8월 초 가장 더운날 다녀가신 분 경우 시원했다고 잘 지내다 가셨습니다)\n\n1. 인원: 6명 기준입니다. 1인 10,000원 추가요금\n2. 장소: 북구 블루마시티, 32-33층\n3. 취사도구, 침구류, 세면도구, 드라이기 2개, 선풍기 4대 구비\n4. 예약방법: 예약금 50,000원 하시면 저희는 명함을 드리며 입실 오전 잔금 입금하시면 저희는 동.호수를 알려드리며 고객님은 예약자분 신분증 앞면 주민번호 뒷자리 가리시거나 지우시고 문자로 보내주시면 저희는 카드키를 우편함에 놓아 둡니다.\n5. 33층 옥상 야외 테라스 있음, 가스버너 있음\n6. 고기 굽기 가능\n7. 입실 오후 3시, 오전 11시 퇴실, 정리, 정돈 , 밸브 잠금 부탁드립니다.\n8. 층간소음 주의 부탁드립니다.\n9. 방3개, 화장실3개, 비데 3개\n10. 저희 집안이 쓰는 별장입니다.",
            "굿리치", 150000, "남구 옥동", 7, "4", false)
        ProductManager.addProduct("7", R.drawable.sample8, "샤넬 탑핸들 가방", "샤넬 트랜디 CC 탑핸들 스몰 램스킨 블랙 금장 플랩백 !\n + 색상 : 블랙\n + 사이즈 : 25.5cm * 17.5cm * 8cm\n + 구성 : 본품더스트\n + 급하게 돈이 필요해서 팝니다 ㅠ ㅠ",
            "난쉽", 180000, "동래구 온천제2동", 42, "12", false)
        ProductManager.addProduct("8", R.drawable.sample9, "4행정 엔진분무기 판매합니다.", "3년전에 사서 한번 사용하고 그대로 둔 상태입니다. 요즘 사용은 안해봤습니다. 그래서 저렴하게 내 놓습니다. 중고라 반품은 어렵습니다.\n",
            "알뜰한", 30000, "원주시 명륜2동", 4, "1", false)
        ProductManager.addProduct("9", R.drawable.sample10, "셀린느 버킷 가방", "22년 신세계 대전 구매입니당\n + 셀린느 버킷백\n + 구매해서 몇번사용했어요\n + 까짐 스크래치 없습니다.\n + 타지역에서 보내는 거라 택배로 진행합니당!",
            "똑태현", 190000, "중구 동화동", 8, "28", false)

        // 데이터 통신을 위해 만들어놓은 어댑터의 itemClick 변수에 MyAdapter의 Itemclick 타입의 오브젝트를 만들어놓음
        adapter.itemClick = object : MyAdapter.ItemClick {
            // MyAdapter 30번째 줄에서 호출한 itemClick이 여기로 콜백
            override fun onClick(view: View, position: Int) {
                val intent = Intent(this@MainActivity, ProductActivity::class.java)
                intent.putExtra ("position", position.toString())
                startActivity(intent)
            }
        }

        adapter.itemLongClick = object : MyAdapter.ItemLongClick {
            override fun onLongClick(view: View, position: Int) {
                AlertDialog.Builder(this@MainActivity)
                    .setMessage("상품을 삭제하시겠습니까?")
                    .setPositiveButton("확인") { dialog, which ->
                        val index: String = ProductManager.products[position].index
                        ProductManager.deleteProduct(index)
                        adapter.notifyDataSetChanged()
                    }
                    .setNegativeButton("취소") { dialog, which ->
                    }
                    .show()
            }
        }

        binding.btnFinish.setOnClickListener(){
            AlertDialog.Builder(this)
                .setMessage("앱을 종료하시겠습니까?")
                .setPositiveButton("확인") { dialog, which ->
                    System.exit(0)
                }
                .setNegativeButton("취소") { dialog, which ->
                }
                .show()
        }

        val btn_upscrollButton = binding.ivUpscrollButton

        binding.recyclerView.addOnScrollListener (object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy>0) {
                    if (btn_upscrollButton.visibility == View.GONE){
                        btn_upscrollButton.apply{
                            visibility = View.VISIBLE
                            alpha = 0f
                            animate().alpha(1f).setDuration(300).start()
                        }
                    }
                } else {
                    if(btn_upscrollButton.visibility == View.VISIBLE){
                        btn_upscrollButton.animate()
                            .alpha(0f)
                            .setDuration(800)
                            .withEndAction{btn_upscrollButton.visibility = View.GONE}
                            .start()
                    }
                }
            }
        })

        fun notification() {
            val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            val builder : NotificationCompat.Builder

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channelID = "id"
                val channelName = "default channel"
                val channel = NotificationChannel(
                    channelID,
                    channelName,
                    NotificationManager.IMPORTANCE_DEFAULT
                ).apply {
                    description = "알림"
                }
                manager.createNotificationChannel(channel)
                builder = NotificationCompat.Builder(this, channelID)
            } else {
                // 26 버전 이하에서는 channel ID를 사용할 필요가 없음
                builder = NotificationCompat.Builder(this)
            }

            builder.run {
                setSmallIcon(R.drawable.durian)
                setWhen(System.currentTimeMillis())
                setContentTitle("[두리안마켓]")
                setContentText("알림이 생성되었습니다.")
                setStyle(NotificationCompat.BigTextStyle())
            }
            manager.notify(1, builder.build())
        }

        binding.ivNotificationButton.setOnClickListener{
            notification()
        }

        btn_upscrollButton.setOnClickListener{
            binding.recyclerView.smoothScrollToPosition(0)

            binding.ivUpscrollButton2.visibility = ImageView.VISIBLE
            Handler(Looper.getMainLooper()).postDelayed({
                binding.ivUpscrollButton2.visibility = ImageView.GONE
            }, 50) // 100밀리초, 0.1초
        }

    }

    // 1번
    override fun onResume() {
        super.onResume()
        Log.d("MainActivity-lifecycle", "onResume")
        adapter.notifyDataSetChanged() // 전부 다 수정(대규모 데이터 처리 비추)

    }

    // 2번 - ResultCallback
    // 3번 -

    override fun onStart() {
        super.onStart()
        Log.d("MainActivity-lifecycle", "onStart")
    }

    override fun onStop() {
        super.onStop()
        Log.d("MainActivity-lifecycle", "onStop")
    }
}
