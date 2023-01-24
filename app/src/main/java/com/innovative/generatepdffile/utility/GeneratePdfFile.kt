package com.innovative.generatepdffile.utility

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.icu.text.SimpleDateFormat
import android.os.Environment
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.innovative.generatepdffile.R
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*

class GeneratePdfFile(private val mContext: Context) {

    private var pageHeight = 1120
    private var pageWidth = 792
    private val paint = Paint()
    private var pdfFile : File? = null
    private var verticallyWidth = 100f
    private var listVerticallyWidth = 100f

    @SuppressLint("SimpleDateFormat")
    fun createPdfFile() {
        val pdfDocument = PdfDocument()
        val myPageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 0).create()
        val myPage = pdfDocument.startPage(myPageInfo)
        val canvas = myPage.canvas
        drawTextInCenter(canvas, "Generate PDF")
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
        for (i in 0..5){
            val myPageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 0).create()
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
        text: String
    ) {
        paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
        paint.textSize = 18f
        paint.color = ContextCompat.getColor(mContext, R.color.black)
        paint.textAlign = Paint.Align.CENTER
        canvas.drawText(text, (canvas.width / 2).toFloat(), verticallyWidth, paint)
    }

}