package com.example.przemapp


import android.animation.ValueAnimator
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*
import kotlin.math.abs
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope


class MainActivity : AppCompatActivity(), GestureDetector.OnGestureListener{

    var x1 = 0.0f
    var x2 = 0.0f
    var y1 = 0.0f
    var y2 = 0.0f

    private lateinit var gestureDetector: GestureDetector

    companion object {
        const val MIN_DISTANCE = 150
    }

    fun rotateFragment(){
        Thread.sleep(500L)
        lifecycleScope.launch {
            val listaFrag = listOf<Fragment>(ScrollingFragment(), ScrollingFragment2(), ScrollingFragment3())
            val button2 = findViewById<Button>(R.id.button2)
            for (i in 1..25){
                listaFrag.forEach(){
                    button2.text = supportFragmentManager
                        .findFragmentById(R.id.fragmentContainerView)
                        .toString()
                        .substringBefore("{")
                    delay(800)
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainerView, it)
                    .commit()
                    delay(800)
                }
            }
        }
    }

    fun startAnimation(textView: TextView) {
        val animator = ValueAnimator.ofInt(0, 600)
        animator.duration = 50000 // 50 seconds
        animator.addUpdateListener { animation ->
            textView.text = animation.animatedValue.toString()
        }
        animator.start()
    }

    fun mill(textView: TextView){
        Thread.sleep(500L)
        lifecycleScope.launch {
            val lista = listOf<String>("              ---", """               \""", "               |", "               /")
            for (i in 1..100){
                lista.forEach(){
                    textView.text = it
                    delay(40)
                }
                textView.text = ""
            }
            textView.text = "Marysiowi też się spodoba;)............."
        }
    }

    fun windMill(textView: TextView){
        val wiatrak = listOf<String>("---", """\""", "|", "/", "---", """\""", "|", "/", "---", """\""", "|", "/", "---", """\""", "|", "/")
        for (k in 1..100){
            for (i in 0 until wiatrak.size) {
                Handler(Looper.myLooper()!!).postDelayed({
                    textView.text = "             ${wiatrak[i]}"
                    textView.append("  k = $k")
                }, 5000 + i.toLong() * 2000)
            }

        }
    }

    private fun loadFragment(fragment: Fragment){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainerView, fragment)
            .commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button: Button = findViewById<Button>(R.id.button)
        val button2: Button = findViewById<Button>(R.id.button2)
        val textView: TextView = findViewById<TextView>(R.id.textView)
        textView.isSelected = true

        loadFragment(ScrollingFragment())

        gestureDetector = GestureDetector(this, this)

        button.setOnClickListener {
            try{
                rotateFragment()

//                startAnimation(textView)
            } catch (e:Exception){
                textView.text = e.toString()
            }
        }

        button2.setOnClickListener {
            try{
                button2.text = supportFragmentManager
                    .findFragmentById(R.id.fragmentContainerView)
                    .toString()
                    .substringBefore("{")
            } catch (e: Exception){
                button2.text = e.toString()
            }
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
        val textView = findViewById<TextView>(R.id.textView)
        startAnimation(textView)
        return false
    }

    override fun onScroll(p0: MotionEvent?, p1: MotionEvent?, p2: Float, p3: Float): Boolean {
        //TODO("Not yet implemented")
        return false
    }

    override fun onLongPress(p0: MotionEvent?) {
        val textView = findViewById<TextView>(R.id.textView)
        mill(textView)

    }

    override fun onFling(p0: MotionEvent?, p1: MotionEvent?, p2: Float, p3: Float): Boolean {
        //TODO("Not yet implemented")
        return false
    }
}