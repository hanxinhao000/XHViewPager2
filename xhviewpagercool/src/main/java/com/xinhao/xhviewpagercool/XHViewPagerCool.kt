package com.xinhao.xhviewpagercool

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.RelativeLayout
import android.widget.Scroller

/**
 * XINHAO_HAN 一个超级酷的ViewPager
 *
 *
 *
 * 吐槽一下,不得不说每次写Kotlin 编译器就卡
 *
 * 而且这个语言现在阶段还是有点不稳定,希望谷歌大大早点吧Android Studio 和Kotlin 之间的兼容性做的更好一点 ~~~
 *
 *
 *
 */
class XHViewPagerCool : RelativeLayout {

    private var mContext: Context? = null
    //有多少个View
    private var viewCount: Int = 0

    //滚动器
    private var scoll: Scroller? = null
    //View中间
    private var minX: Int = 0
    //View右边
    private var viewR: Int = 0
    //减还是加
    private var isJJ: Boolean = true
    //零时变量
    private var temp: Int = 0

    // private var viewArray: ArrayList<View>? = null

    constructor(context: Context?) : super(context) {
        initView(context)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        initView(context)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView(context)
    }

    /**
     * 初始化信息
     */
    private fun initView(context: Context?) {
        mContext = context
        //  viewArray = ArrayList()
        /**
         * 先模拟添加4个ImageView
         */
        scoll = Scroller(context)
    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        minX = w / 2
        viewR = w
    }

    //加入View
    fun addViewViewPager(viewArr: ArrayList<View>) {


        /***
         * 加入 视图
         */
        for (i in 0..viewArr.size - 1) {

            val arrView = viewArr.get(i)

            var llP: RelativeLayout.LayoutParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT)
            llP.rightMargin = 0
            arrView.layoutParams = llP


            /**
             * 加透明
             */
            if (i != viewArr.size - 1) {

                arrView.alpha = 0f

            }
            addView(arrView)
        }


        viewCount = childCount - 1
    }

    var startX: Int = 0

    override fun onTouchEvent(event: MotionEvent?): Boolean {


        when (event?.action) {
        //移动
            MotionEvent.ACTION_MOVE -> {

                //-----------------------------------操作正常滑动 ↓
                var endX: Int = event.x.toInt()

                val mid = endX - startX


                var imageX = getChildAt(viewCount).x

                // Log.e("抬起", "" + getChildAt(viewCount).x)
                try {
                    if (getChildAt(viewCount).x > 0) {
                        /**
                         * 如果小于大于0就代表往右滑了,调出隐藏在左边的View
                         */

                        getChildAt(viewCount + 1).x = getChildAt(viewCount + 1).x + mid

                        getChildAt(viewCount).alpha = -(getChildAt(viewCount + 1).x) / viewR



                        isJJ = true
                    } else {

                        /**
                         * 否则就是往左滑,隐藏当前View到右边
                         */
                        if (viewCount == childCount - 1) {
                            getChildAt(viewCount).x = imageX + mid
                        } else {

                            if (getChildAt(viewCount).x + mid > 0) {
                                /**
                                 * View坐标的调度
                                 *  写1是为了让上边的大于0产生执行,不然上边就JJ了
                                 */
                                getChildAt(viewCount).x = 1f
                            } else {
                                getChildAt(viewCount).x = imageX + mid
                            }
                        }



                        getChildAt(viewCount - 1).alpha = -(getChildAt(viewCount).x) / viewR
                        //       Log.e("透明度", "" + (-(getChildAt(viewCount).x) / viewR))
                        isJJ = false
                    }
                } catch (e: Exception) {

                }


                startX = endX
                //-----------------------------------操作正常滑动 ↑


            }
        //抬起
            MotionEvent.ACTION_UP -> {

                try {
                    if (isJJ) {

                        /**
                         * 这块用了一个ISJJ  这个变量代表着View是决定隐藏(往左滑)还是显示(往右滑)
                         *
                         *
                         * 这个区域是为了显示隐藏在右边View 简称:显示View
                         */
                        //判断是否大于左边还是小于左边
                        if (getChildAt(viewCount + 1).x > -(viewR + 20)) {
                            //大于中间滚动到第下一个


                            moveView(getChildAt(viewCount + 1).x.toInt(), 0)
                            temp = viewCount + 1
                            viewCount++

                        }

                        if (getChildAt(viewCount + 1).x < -(viewR + 20)) {
                            //小于中间滚动到上一个
                            moveView(getChildAt(viewCount + 1).x.toInt(), -viewR)

                            temp = viewCount + 1
                            if (viewCount == childCount - 1) {
                                moveView(getChildAt(viewCount).x.toInt(), 0)
                            } else {
                                viewCount++
                            }


                        }
                    } else {
                        /**
                         * 然而这个区域是为了隐藏View
                         */
                        //判断是否大于左边还是小于左边
                        if (getChildAt(viewCount).x > 0) {
                            //大于中间滚动到第下一个


                            moveView(getChildAt(viewCount).x.toInt(), 0)
                            temp = viewCount
                            viewCount++

                        }

                        if (getChildAt(viewCount).x < 0) {
                            //小于中间滚动到上一个
                            moveView(getChildAt(viewCount).x.toInt(), -viewR)


                            temp = viewCount
                            if (viewCount == 0) {
                                moveView(getChildAt(viewCount).x.toInt(), 0)
                            } else {
                                viewCount--
                            }


                        }
                    }
                } catch (e: Exception) {

                    /**
                     * 方便与程序不报错误 起见最好tryCatch一下
                     *
                     * 如果超出View有效值的范围,就初始取哪个View
                     */
                    if (viewCount >= childCount) {
                        moveView(getChildAt(childCount - 1).x.toInt(), 0)
                        viewCount = childCount - 1
                    }

                    if (viewCount <= 0) {
                        viewCount = 0
                        moveView(getChildAt(0).x.toInt(), 0)

                    }

                }


                /**
                 * 调度区域,不然每个View滑时间长了都往右走了,左边会留下一点空白,还会越留越大
                 */
                getChildAt(viewCount).x = 0f


            }
        //按下
            MotionEvent.ACTION_DOWN -> {

                /**
                 * 这个玩意是记录坐标点的
                 */
                startX = event.x.toInt()
                // Log.e("坐标记录", "startX:" + startX)

            }


        }


        return true
    }

    /**
     * 开始滑动到距离
     */
    private fun moveView(startInt: Int, endInt: Int) {


        /**
         * 滑动器
         */
        scoll?.startScroll(startInt, 0, endInt - startInt, 0)
    }

    /**
     * \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
     *
     *                                       如果你要写回调就在onTouchEvent
     *                                       和computeScroll里边实现
     *                                       最好先用log打
     *
     * \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
     */

    override fun computeScroll() {
        super.computeScroll()
        if (scoll?.computeScrollOffset()!!) {

            getChildAt(temp).x = scoll?.currX!!.toFloat()
            /**
             * 这块区域是为了更改视图(View)的透明度
             */
            try {
                if (isJJ) {
                    /**
                     * 其实没有必要,要这个isJJ变量做判断的,所以我留个坑 #滑稽  不影响正常使用
                     */
                    var fl = (getChildAt(temp).x) / viewR
                    if (fl < 0) {
                        fl = fl * -1
                    }
                    getChildAt(temp - 1).alpha = fl

                } else {
                    var fl = (getChildAt(temp).x) / viewR
                    if (fl < 0) {
                        fl = fl * -1
                    }
                    getChildAt(temp - 1).alpha = fl

                }

            } catch (e: Exception) {
            }
        }

        /**
         * 老铁记得刷新视图 ~~~~ 不然你就是 Double 666
         *
         */

        invalidate()
    }


    private var l: XHViewPagerListener? = null
    fun setXHViewPagerListener(l: XHViewPagerListener?) {
        this.l = l
    }

    /**
     * 接口老铁
     */

    public interface XHViewPagerListener {
        fun viewIndex(index: Int)
    }
}