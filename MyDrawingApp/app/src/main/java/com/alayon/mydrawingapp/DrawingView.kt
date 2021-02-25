package com.alayon.mydrawingapp

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View

class DrawingView(context:Context, attrs:AttributeSet) : View(context, attrs) {

    private val paths = ArrayList<CustomPath>()
    private var drawingPath: CustomPath? = null
    private var canvasBitmap: Bitmap? = null
    private var drawPaint: Paint? = null
    private var canvasPaint: Paint? = null
    private var brushSize: Float = 0.toFloat()
    private var color = Color.BLACK
    private var canvas: Canvas? = null


    init {
        setupDrawing()
    }

    private fun setupDrawing() {
        drawPaint = Paint()
        drawingPath = CustomPath(color, brushSize)
        drawPaint!!.color = color
        drawPaint!!.style = Paint.Style.STROKE
        drawPaint!!.strokeJoin = Paint.Join.ROUND
        drawPaint!!.strokeCap = Paint.Cap.ROUND
        canvasPaint = Paint(Paint.DITHER_FLAG)
        brushSize = 20.toFloat()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(canvasBitmap!!, 0f, 0f, canvasPaint)

        for (path in paths){
            drawPaint!!.strokeWidth = path.brushThickness
            drawPaint!!.color = path!!.color
            canvas.drawPath(path, drawPaint!!)
        }
        if (!drawingPath!!.isEmpty) {
            drawPaint!!.strokeWidth = drawingPath!!.brushThickness
            drawPaint!!.color = drawingPath!!.color
            canvas.drawPath(drawingPath!!, drawPaint!!)
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val touchX = event?.x
        val touchY = event?.y

        when(event?.action){
            MotionEvent.ACTION_DOWN ->{
                drawingPath!!.color = color
                drawingPath!!.brushThickness = brushSize

                drawingPath!!.reset()
                drawingPath!!.moveTo(touchX!!,touchY!!)
            }
            MotionEvent.ACTION_MOVE ->{
                drawingPath!!.lineTo(touchX!!, touchY!!)
            }
            MotionEvent.ACTION_UP ->{
                paths.add(drawingPath!!)
                drawingPath = CustomPath(color, brushSize)
            }
            else -> return false
        }
        invalidate()
        return true
    }

    override fun onSizeChanged(width: Int, height: Int, oldWidth: Int, oldHeight: Int) {
        super.onSizeChanged(width, height, oldWidth, oldHeight)
        canvasBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        canvas = Canvas(canvasBitmap!!)
    }

    fun setSizeForBrush(newSize:Float){
        brushSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, newSize, resources.displayMetrics)
        drawPaint!!.strokeWidth = brushSize
    }
    internal inner class CustomPath(var color:Int, var brushThickness:Float): Path(){

    }
}