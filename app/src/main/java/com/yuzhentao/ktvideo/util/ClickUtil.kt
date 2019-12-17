package com.yuzhentao.ktvideo.util

object ClickUtil {

    private var lastClickTime: Long = 0
    private const val DIFF: Long = 1000
    private var lastButtonId = -1

    /**
     * 判断两次点击的间隔，如果小于1000，则认为是多次无效点击
     */
    val isFastDoubleClick: Boolean
        get() = isFastDoubleClick(-1, DIFF)

    /**
     * 判断两次点击的间隔，如果小于1000，则认为是多次无效点击
     */
    fun isFastDoubleClick(buttonId: Int): Boolean {
        return isFastDoubleClick(buttonId, DIFF)
    }

    /**
     * 判断两次点击的间隔，如果小于diff，则认为是多次无效点击
     */
    fun isFastDoubleClick(buttonId: Int, diff: Long): Boolean {
        val time = System.currentTimeMillis()
        val timeD = time - lastClickTime
        if (lastButtonId == buttonId && lastClickTime > 0 && timeD < diff) {
            return true
        }
        lastClickTime = time
        lastButtonId = buttonId
        return false
    }

}