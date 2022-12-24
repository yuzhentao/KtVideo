package com.yzt.common.listener

interface OnRvScrollListener {

    fun onRvScroll(totalDy: Int)

    //通过重载companion object的invoke方法简化接口对象生成
    companion object {

        operator fun invoke(callback: (totalDy: Int) -> Unit): OnRvScrollListener {
            return object : OnRvScrollListener {
                override fun onRvScroll(totalDy: Int) {
                    callback(totalDy)
                }
            }
        }

    }

}