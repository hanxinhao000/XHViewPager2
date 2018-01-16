package com.xinhao.xhviewpagercool

import android.app.Dialog
import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView

import com.xinhao.xhviewpagercool.XHViewPagerCool

/**
 * XINHAO_HAN  一个临时的Dialog为了让你看看效果啦~~~~
 */
class XHDialog : Dialog {

    var mContext: Context? = null
    /**
     * 一个备用的View
     */
    var mView: View? = null

    var mXhView: XHViewPagerCool? = null

    /**
     * View的高
     */

    var vH: Int = 0

    /**
     * View的宽
     */

    var vW: Int = 0

    constructor(context: Context?) : super(context, R.style.MyDialog) {
        initView(context)
    }

    constructor(context: Context?, themeResId: Int) : super(context, themeResId) {
        initView(context)
    }

    //初始化
    private fun initView(context: Context?) {
        mContext = context
        mView = View.inflate(context, R.layout.dialog_item, null)
        mXhView = mView?.findViewById<View>(R.id.xhView) as XHViewPagerCool?
        setContentView(mView)
        initmXhView()
    }

    //初始化mXhView

    private fun initmXhView() {


        val arr: ArrayList<View> = ArrayList()

        for (i in 0..4) {

            val rl: RelativeLayout.LayoutParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT)
            val mView = View.inflate(context, R.layout.dialog_load_view, null)
            mView.layoutParams = rl
            val text: TextView = mView.findViewById<View>(R.id.text) as TextView

            arr.add(mView)
        }


        mXhView?.addViewViewPager(arr)
        viewMe()
    }

    //测量View
    private fun viewMe() {

        val h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        val w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)

        mView?.measure(w, h)

        vH = mView?.measuredHeight!!
        vW = mView?.measuredWidth!!
    }

    override fun show() {
        super.show()

        /**
         * 控制Dialog大小
         */

        window.setLayout(vW - 500, vH - 1200)


    }

}