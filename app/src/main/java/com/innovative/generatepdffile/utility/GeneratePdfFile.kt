package com.innovative.generatepdffile.utility

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.*
import android.graphics.pdf.PdfDocument
import android.icu.text.SimpleDateFormat
import android.os.Environment
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.innovative.generatepdffile.R
import com.innovative.generatepdffile.datamodel.Inspection
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*

class GeneratePdfFile(private val mContext: Context) {

    private var pageHeight = 1120
    private var pageWidth = 792
    private val paint = Paint()
    private var pdfFile: File? = null
    private var verticallySpace = 100f
    private var listVerticallyWidth = 100f

    private fun getPdfPages(itemPerPage: Int, listSize: Int): Int {
        return if (listSize > itemPerPage) {
            if (listSize % itemPerPage > 0) {
                listSize / itemPerPage + 1
            } else {
                listSize / itemPerPage
            }
        } else {
            if (listSize > 0) {
                1
            } else {
                0
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun createReportFile(
        cellHeight: Int,
        inspectionList: ArrayList<Inspection>,
        fileName: String,
        isCoverPage: Boolean
    ) {
        val pdfDocument = PdfDocument()
        val itemPerPage = 1000 / cellHeight
        var isCoverPage = isCoverPage
        var pdfPageSize = getPdfPages(itemPerPage, inspectionList.size)
        if (isCoverPage) {
            pdfPageSize += 1
        }
        Log.d("itemPerPage", "---$itemPerPage")
        Log.d("pdfPageSize", "---$pdfPageSize")
        for (i in 1..pdfPageSize) {
            val myPageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, i).create()
            val myPage = pdfDocument.startPage(myPageInfo)
            val canvas = myPage.canvas
            drawPageNumber(canvas, i, pdfPageSize)
            verticallySpace = 100f
            if (isCoverPage) {
                generateCoverPage(canvas)
                isCoverPage = false
            } else {
                drawTextInCenter(canvas, "Generate PDF")
            }
            drawFooterText(canvas, "@2022 Inspection Audit")
            pdfDocument.finishPage(myPage)
        }
        val formattedDate = SimpleDateFormat("dd-MM-yyyy HH_mm_ss")
        val date = Date()
        val fileNameWithDate = formattedDate.format(date)
        val file = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),
            "$fileName $fileNameWithDate.pdf"
        )
        try {
            pdfDocument.writeTo(FileOutputStream(file))
            pdfFile = file
            val u = FileProvider.getUriForFile(mContext, mContext.applicationInfo.packageName, file)
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = u
            intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
            mContext.startActivity(intent)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        pdfDocument.close()
    }

    private fun generateCoverPage(canvas: Canvas) {
        drawTextInCenter(canvas, "NRK Biz Park",18f)
        verticallySpace += 40
        drawTextInCenter(canvas, "Vijay Nagar")
        verticallySpace += 40
        drawTextInCenter(canvas, "12")
        verticallySpace += 40
        drawTextInCenter(canvas, "06-Jan-2023")
        verticallySpace += 40
        drawTextInCenter(canvas, "Client Name : Sonu")
        verticallySpace += 60
        drawBitMapInCenter(canvas,BitmapFactory.decodeResource(mContext.resources, R.drawable.ic_dummy),100,100)
        verticallySpace += 140
        drawTextInCenter(canvas, "IDA")
        verticallySpace += 60
        drawBitMapInCenter(canvas,BitmapFactory.decodeResource(mContext.resources, R.drawable.sign),100,100)
        verticallySpace += 140
        drawTextInCenter(canvas, "Monu")
        verticallySpace += 80
        drawTextInCenter(canvas, "Total #1 Inspection")
        verticallySpace += 80
        drawCircleWithText(canvas, (canvas.width / 2).toFloat() - 120,verticallySpace,"1",Color.RED,32f)
        drawCircleWithText(canvas, (canvas.width / 2).toFloat() - 40,verticallySpace,"1",Color.BLUE,32f)
        drawCircleWithText(canvas, (canvas.width / 2).toFloat() + 40,verticallySpace,"1",Color.YELLOW,32f)
        drawCircleWithText(canvas, (canvas.width / 2).toFloat() + 120,verticallySpace,"1",Color.GREEN,32f)
    }

    @SuppressLint("SimpleDateFormat")
    fun createPdfFile() {
        val pdfDocument = PdfDocument()
        val myPageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 0).create()
        val myPage = pdfDocument.startPage(myPageInfo)
        val canvas = myPage.canvas
        drawTextInCenter(canvas, "Generate PDF")
        verticallySpace += 60
        drawBitMapInCenter(
            canvas,
            BitmapFactory.decodeResource(mContext.resources, R.drawable.ic_dummy),
            100,
            100
        )
        verticallySpace += 160
        drawCircleWithText(
            canvas,
            (canvas.width / 2).toFloat(),
            verticallySpace,
            "1",
            Color.RED,
            32f
        )
        pdfDocument.finishPage(myPage)
        val formattedDate = SimpleDateFormat("dd-MM-yyyy HH_mm_ss")
        val date = Date()
        val fileNameWithDate = formattedDate.format(date)
        val file = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),
            "ReportFile - $fileNameWithDate.pdf"
        )
        try {
            pdfDocument.writeTo(FileOutputStream(file))
            pdfFile = file
            val u = FileProvider.getUriForFile(mContext, mContext.applicationInfo.packageName, file)
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = u
            intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
            mContext.startActivity(intent)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        pdfDocument.close()
    }

    @SuppressLint("SimpleDateFormat")
    fun createMultiplePagePdfFile() {
        val pdfDocument = PdfDocument()
        for (i in 0..5) {
            val myPageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, i).create()
            val myPage = pdfDocument.startPage(myPageInfo)
            val canvas = myPage.canvas
            drawTextInCenter(canvas, "Generate PDF")
            pdfDocument.finishPage(myPage)
        }
        val formattedDate = SimpleDateFormat("dd-MM-yyyy HH_mm_ss")
        val date = Date()
        val fileNameWithDate = formattedDate.format(date)
        val file = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),
            "ReportFile - $fileNameWithDate.pdf"
        )
        try {
            pdfDocument.writeTo(FileOutputStream(file))
            pdfFile = file
            val u = FileProvider.getUriForFile(mContext, mContext.applicationInfo.packageName, file)
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = u
            intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
            mContext.startActivity(intent)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        pdfDocument.close()
    }

    private fun drawTextInCenter(
        canvas: Canvas,
        text: String,
        textSize: Float = 16f
    ) {
        paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
        paint.textSize = textSize
        paint.color = ContextCompat.getColor(mContext, R.color.black)
        paint.textAlign = Paint.Align.CENTER
        canvas.drawText(text, (canvas.width / 2).toFloat(), verticallySpace, paint)
    }

    private fun drawPageNumber(
        canvas: Canvas,
        pageNumber: Int,
        totalPage: Int
    ) {
        paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
        paint.textSize = 16f
        paint.color = ContextCompat.getColor(mContext, R.color.black)
        paint.textAlign = Paint.Align.CENTER
        canvas.drawText("$pageNumber of $totalPage", 80f, 40f, paint)
    }

    private fun drawFooterText(
        canvas: Canvas,
        text: String
    ) {
        paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
        paint.textSize = 14f
        paint.color = ContextCompat.getColor(mContext, R.color.black)
        paint.textAlign = Paint.Align.CENTER
        canvas.drawText(text, (canvas.width / 2).toFloat(), 1080f, paint)
    }

    private fun drawTextInStart(
        canvas: Canvas,
        startSpace: Float,
        text: String

    ) {
        paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
        paint.textSize = 18f
        paint.color = ContextCompat.getColor(mContext, R.color.black)
        paint.textAlign = Paint.Align.CENTER
        canvas.drawText(text, startSpace, verticallySpace, paint)
    }


    private fun drawBitMapInStart(
        canvas: Canvas,
        startSpace: Float,
        bitmap: Bitmap,
        width: Int,
        height: Int
    ) {
        val scaleBitMap = Bitmap.createScaledBitmap(bitmap, width, height, false)
        canvas.drawBitmap(
            scaleBitMap,
            startSpace,
            verticallySpace,
            paint
        )
    }

    private fun drawBitMapInCenter(canvas: Canvas, bitmap: Bitmap, width: Int, height: Int) {
        val scaleBitMap = Bitmap.createScaledBitmap(bitmap, width, height, false)
        canvas.drawBitmap(
            scaleBitMap,
            (canvas.width / 2 - scaleBitMap!!.width / 2).toFloat(),
            verticallySpace,
            paint
        )
    }

    private fun drawCircleWithText(
        canvas: Canvas,
        xAxis: Float,
        yAxis: Float,
        text: String,
        circleColor: Int,
        radius: Float
    ) {
        val bounds = Rect()
        val circlePaint = Paint()
        circlePaint.color = circleColor
        circlePaint.isAntiAlias = true
        paint.getTextBounds(text, 0, text.length, bounds)
        canvas.drawCircle(
            xAxis,
            yAxis,
            radius,
            circlePaint
        )
        paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        paint.textSize = 24f
        paint.color = ContextCompat.getColor(mContext, R.color.black)
        paint.textAlign = Paint.Align.CENTER
        canvas.drawText(text, xAxis, yAxis + 8, paint)
    }

}