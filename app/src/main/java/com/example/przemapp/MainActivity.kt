package com.example.przemapp


import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import kotlin.math.abs
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity(), GestureDetector.OnGestureListener{

    var x1 = 0.0f
    var x2 = 0.0f
    var y1 = 0.0f
    var y2 = 0.0f
    lateinit var gestureDetector: GestureDetector
    companion object {
        const val MIN_DISTANCE = 150
    }

    var counter = 0

    fun mill(view: TextView){
        val wiatrak = listOf<String>("---", """\""", "|", "/")
        Thread.sleep(500)
        GlobalScope.launch {
            wiatrak.forEach(){
                view.text = it
                delay(100)
            }
        }
//        view.text = ""
    }

//    @DelicateCoroutinesApi
//    private fun callWiatrak() = runBlocking{
//        val job = GlobalScope.launch {
//        mill(textView)
//        }
//        job.join()
//    }

    private fun loadFragment(fragment: Fragment){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainerView, fragment)
            .commitNow()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gestureDetector = GestureDetector(this, this)

        loadFragment(ScrollingFragment())

        button.setOnClickListener {
            mill(textView)
        }

        button2.setOnClickListener {
            button2.text = supportFragmentManager.findFragmentById(R.id.fragmentContainerView).toString().substringBefore("{")
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        gestureDetector.onTouchEvent(event)
        when(event?.action){
            0->{
                x1 = event.x
                y1 = event.y
            }
            1->{
                x2 = event.x
                y2 = event.y

                val valueX = x2 - x1
                val valueY = y2 - y1

                val frag : String = supportFragmentManager.findFragmentById(R.id.fragmentContainerView).toString().substringBefore("{")

                if(abs(valueX) > MIN_DISTANCE)
                {
                    if(x2 > x1){
                        Toast.makeText(this, "Right swipe", Toast.LENGTH_SHORT).show()
                        when(frag){
                            "ScrollingFragment" -> { loadFragment(ScrollingFragment2()) }
                            "ScrollingFragment2" -> { loadFragment(ScrollingFragment3()) }
                            "ScrollingFragment3" -> { loadFragment(ScrollingFragment()) }
                        }
                    }  else{
                        Toast.makeText(this, "Left swipe", Toast.LENGTH_SHORT).show()
                        when(frag){
                            "ScrollingFragment" -> { loadFragment(ScrollingFragment3()) }
                            "ScrollingFragment2" -> {  loadFragment(ScrollingFragment()) }
                            "ScrollingFragment3" -> {  loadFragment(ScrollingFragment2()) }
                        }
                    }
                } else if(abs(valueY) > MIN_DISTANCE){
                    if(y2 > y1){
                        Toast.makeText(this, "Top swipe", Toast.LENGTH_SHORT).show()
                    }  else{
                        Toast.makeText(this, "Bottom swipe", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        return super.onTouchEvent(event)
    }

    override fun onDown(p0: MotionEvent?): Boolean {
        //TODO("Not yet implemented")
        return false
    }

    override fun onShowPress(p0: MotionEvent?) {
        //TODO("Not yet implemented")
    }

    override fun onSingleTapUp(p0: MotionEvent?): Boolean {
        //TODO("Not yet implemented")
        return false
    }

    override fun onScroll(p0: MotionEvent?, p1: MotionEvent?, p2: Float, p3: Float): Boolean {
        //TODO("Not yet implemented")
        return false
    }

    override fun onLongPress(p0: MotionEvent?) {
        //TODO("Not yet implemented")
    }

    override fun onFling(p0: MotionEvent?, p1: MotionEvent?, p2: Float, p3: Float): Boolean {
        //TODO("Not yet implemented")
        return false
    }
}