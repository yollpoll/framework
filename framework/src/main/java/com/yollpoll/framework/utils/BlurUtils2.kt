package com.yollpoll.framework.utils

import android.annotation.TargetApi
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import android.view.PixelCopy
import android.view.View
import android.view.Window

/**
 * Created by spq on 2021/1/14
 */
const val RADIUS = 10f//模糊半径
const val SCALE_FACTOR = 8;

/**
 * 设置高斯模糊四步
 * 1获取屏幕背景图片对应view位置的截图
 * 2压缩图片质量
 * 3获取模糊截图
 * 4设置背景
 *
 * @param fromView View 背景图来源
 * @param toView View 需要设置高斯模糊的view
 * @param radius Float 模糊半径
 * @param scaleFactor Float 压缩比例
 */
fun setBlur(fromView: View, toView: View, radius: Float, scaleFactor: Int) {
    //开启缓存
    fromView.isDrawingCacheEnabled = true
    //获取屏幕截图
    var bitmap = getSnapshot(fromView, toView)
    if (null == bitmap) {
        fromView.isDrawingCacheEnabled = false
        return
    }
    //压缩图片质量
    bitmap = scaleBitmap(bitmap, scaleFactor)
    if (null == bitmap) {
        fromView.isDrawingCacheEnabled = false
        return
    }
    //模糊处理
    bitmap = blurBitmap(fromView.context, bitmap, radius)
    toView.background = BitmapDrawable(bitmap)
//    bitmap.recycle()
    fromView.isDrawingCacheEnabled = false
}

/**
 * 使用piexlCopy复制，在高版本使用
 * @param window Window
 * @param toView View
 */
@TargetApi(Build.VERSION_CODES.O)
fun setBlur(window: Window, toView: View) {
    val bitmap = Bitmap.createBitmap(toView.width, toView.height, Bitmap.Config.ARGB_8888)
    val rect = Rect()
    val viewLocationArray = IntArray(2)
    toView.getLocationOnScreen(viewLocationArray)
    rect.left = viewLocationArray[0]
    rect.top = viewLocationArray[1]
    rect.right = viewLocationArray[0] + toView.width
    rect.bottom = viewLocationArray[1] + toView.height
    PixelCopy.request(window, rect, bitmap, object : PixelCopy.OnPixelCopyFinishedListener {
        override fun onPixelCopyFinished(copyResult: Int) {
            TODO("Not yet implemented")
            println("finished")
            bitmap = scaleBitmap(bitmap, SCALE_FACTOR)
            bitmap = blurBitmap(toView.context, bitmap, RADIUS)
            toView.background = BitmapDrawable(bitmap)
        }
    }, Handler(Looper.getMainLooper()))
}

/**
 * 获取fromview中toview对应位置的截图
 * @param fromView View screen背景截图
 * @param toView View 需要设置blur的view
 * @return Bitmap
 */
fun getSnapshot(fromView: View, toView: View): Bitmap {
    var bitmap = fromView.drawingCache
    if (null != bitmap) {
        val outWidth = toView.width
        val outHeight = toView.height

        //获取需要截图部分的在屏幕上的坐标(view的左上角坐标）
        val viewLocationArray = IntArray(2)
        toView.getLocationOnScreen(viewLocationArray)
        //背景view的坐标
        val fromViewLoationArray = IntArray(2)
        fromView.getLocationOnScreen(fromViewLoationArray)
        //x,y传相对坐标
        bitmap = Bitmap.createBitmap(
                bitmap, viewLocationArray[0] - fromViewLoationArray[0],
                viewLocationArray[1] - fromViewLoationArray[1], outWidth, outHeight
        )
    }

    return bitmap;
}

/**
 * 压缩bitmap
 * @param bitmap Bitmap 原始数据
 * @param scaleFactor Float 压缩比
 */
fun scaleBitmap(bitmap: Bitmap, scaleFactor: Int) =
        //fase 选择质量更差但是效率更高
        Bitmap.createScaledBitmap(
                bitmap,
                bitmap.width / scaleFactor,
                bitmap.height / scaleFactor,
                false
        )


/**
 * 对bitmap做模糊处理
 * @param context Context
 * @param bitmap Bitmap 需要渲染的图片
 * @param radius Float 模糊半径
 */
fun blurBitmap(context: Context, bitmap: Bitmap, radius: Float): Bitmap {
    //创建输出的bitmap
    val outputBitmap = Bitmap.createBitmap(bitmap)

    //创建renderScript内核对象
    val renderScript = RenderScript.create(context)
    //创建一个模糊的renderScript工具对象
    val blurScript = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript))

    // 由于RenderScript并没有使用VM来分配内存,所以需要使用Allocation类来创建和分配内存空间。
    // 创建Allocation对象的时候其实内存是空的,需要使用copyTo()将数据填充进去。
    val tmpIn = Allocation.createFromBitmap(renderScript, bitmap)
    val tmpOut = Allocation.createFromBitmap(renderScript, outputBitmap)

    // 设置渲染的模糊程度, 25f是最大模糊度
    blurScript.setRadius(radius)
    // 设置blurScript对象的输入内存
    blurScript.setInput(tmpIn)
    // 将输出数据保存到输出内存中
    blurScript.forEach(tmpOut)

    // 将数据填充到Allocation中
    tmpOut.copyTo(outputBitmap)
    return outputBitmap;
}


/**
 * 直接设置已经模糊的bitmap，
 * 会偏移view
 * @param view View
 * @param bitmap Bitmap
 */
fun setBlurBitmapDirectly(view: View, scaleFactor: Float, bitmap: Bitmap) {
    view.isDrawingCacheEnabled = true;
    val bp: Bitmap = view.drawingCache

    val canvas = Canvas(bp)
    canvas.translate((-view.left / scaleFactor).toFloat(), (-view.top / scaleFactor).toFloat())
    canvas.scale(1 / scaleFactor, 1 / scaleFactor)
    val paint = Paint()
    paint.flags = Paint.FILTER_BITMAP_FLAG
    canvas.drawBitmap(bitmap, 0f, 0f, paint)
    view.background = BitmapDrawable(bitmap)
    view.isDrawingCacheEnabled = false
}

