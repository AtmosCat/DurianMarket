package com.example.durianmarket

data class MyItem(val index: String, val icon: Int, val title: String, val introduction: String,
    val seller: String, val price: Long, val address: String, var like: Int, val chat: String, var favorite: Boolean) {
}

object ProductManager {
    val products = mutableListOf<MyItem>()

    fun addProduct(index: String, icon: Int, title: String, introduction: String, seller: String, price: Long,
                   address: String, like: Int, chat: String, favorite: Boolean){
        products.add(MyItem(index, icon, title, introduction, seller, price, address, like, chat, favorite))
    }

    fun deleteProduct(index_: String){
        products.remove(products.find { it.index == index_ })
    }
}