package com.elyeproj.animatedecorator

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.ViewTreeObserver
import kotlinx.android.synthetic.main.activity_main.recycler_view

class MainActivity : AppCompatActivity(), ViewTreeObserver.OnGlobalLayoutListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.adapter = MyViewAdapter(this)
        recycler_view.viewTreeObserver.addOnGlobalLayoutListener(this)
    }

    override fun onGlobalLayout() {
        recycler_view.addItemDecoration(
                AnimatedDecorator(getDrawable(R.drawable.rod_shadow), ShoeLaceAnimatedDecoratorDrawable(
                        resources.getDimension(R.dimen.custom_animated_height).toInt(), recycler_view.measuredWidth),
                        AnimatedDecorator.Side.BOTTOM,
                        { it < recycler_view.layoutManager.itemCount - 1 }))
        recycler_view.viewTreeObserver.removeOnGlobalLayoutListener(this)
    }

}
