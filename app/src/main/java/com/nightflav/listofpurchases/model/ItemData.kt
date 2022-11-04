package com.nightflav.listofpurchases.model

typealias itemListener = (items: List<Item>) -> Unit

class ItemData {
    private var itemList = mutableListOf<Item>(Item(
        "Food",
        "Carrot",
        2,
        "carrotID"
    ))

    private val itemListeners = mutableSetOf<itemListener>()

    fun deleteItem(id: String) {
        for(item in itemList) {
            if(id == item.id) {
                itemList.remove(item)
            }
        }
        notifyItems()
    }

    fun addItem(item: Item) {
        itemList.add(item)
        notifyItems()
    }

    fun getItemList() : MutableList<Item> {
        return itemList
    }

    fun increaseAmountOfUniqueItem(id : String) {
        for(item in itemList) {
            if(id == item.id) {
                item.amount++
            }
        }
        notifyItems()
    }

    fun decreaseAmountOfUniqueItem(id : String) {
        for(item in itemList) {
            if (id == item.id) {
                item.amount--
            }
            if (item.amount < 0)
                deleteItem(item.id)
        }
        notifyItems()
    }

    fun addListener(listener: itemListener) {
        itemListeners.add(listener)
        listener.invoke(itemList)
    }

    fun deleteListener(listener: itemListener) {
        itemListeners.remove(listener)
    }

    private fun notifyItems() {
        itemListeners.forEach {
            it.invoke(itemList)
        }
    }
}