package com.innovative.generatepdffile

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.innovative.generatepdffile.databinding.ActivityMainBinding
import com.innovative.generatepdffile.datamodel.Inspection
import com.innovative.generatepdffile.utility.AppProgressDialog
import com.innovative.generatepdffile.utility.GeneratePdfFile
import com.innovative.generatepdffile.utility.executeAsyncTask
import com.innovative.generatepdffile.utility.onClick
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private var dialog: Dialog? = null
    private var inspectionList: ArrayList<Inspection> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        dialog = Dialog(this)
        initializeSetup()
    }

    private fun initializeSetup() {
        for (i in 1..20){
            inspectionList.add(Inspection())
        }
        binding.btnGeneratePdfFile onClick {
            lifecycleScope.executeAsyncTask(
                onPreExecute = {
                    AppProgressDialog.show(dialog!!)
                },
                doInBackground = {
                    GeneratePdfFile(this@MainActivity).createReportFile(200,inspectionList,"NRK Biz Park",true)
                },
                onPostExecute = {
                    AppProgressDialog.hide(dialog!!)
                    resetDialog()
                }
            )
        }
    }

    private fun resetDialog() {
        dialog = null
        dialog = Dialog(this@MainActivity)
    }
}