package com.yzt.common.listener

interface OnRvScrollListener {

    fun onRvScroll(totalDy: Int)

    //通过重载companion object的invoke方法简化接口对象生成
    //OnRvScrollListener表示OnRvScrollListener类的companion object
    //我们重载了invoke方法，也就是说可以把它当做方法一样调用，调用该方法等同于调用其内部的invoke方法了，该invoke方法会生成一个实现OnRvScrollListener接口的对象
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