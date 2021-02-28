package com.alayon.mydrawingapp

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.get
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_brush_size.*

class MainActivity : AppCompatActivity() {

    companion object{
        private const val STORAGE_PERMISSION_CODE = 1
        private const val GALLERY = 2
    }

    private var imgButtonCurrentPaint:ImageButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawingView.setSizeForBrush(5.toFloat())

        //black color is in 2nd position
        imgButtonCurrentPaint = this.layoutPaintColors[1] as ImageButton
        imgButtonCurrentPaint!!.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.pallet_pressed))

        imgBrush.setOnClickListener{
            showBrushSizeChooserDialog()
        }

        imgGallery.setOnClickListener {
            if (isReadStorageAllowed()){
                val pickPhotoIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(pickPhotoIntent, GALLERY)

            }else{
                requestStoragePermission()
            }
        }

        imgUndo.setOnClickListener {
            drawingView.undo()
        }

        imgRedo.setOnClickListener {
            drawingView.redo()
        }

        imgClear.setOnClickListener {
            drawingView.clearAll()
        }

    }

    private fun showBrushSizeChooserDialog(){
        val brushDialog = Dialog(this)
        brushDialog.setContentView(R.layout.dialog_brush_size)
        brushDialog.setTitle("Brush size: ")

        //small button event
        brushDialog.btnSmallBrush.setOnClickListener{
            drawingView.setSizeForBrush(5.toFloat())
            brushDialog.dismiss()
        }

        //medium button event
        brushDialog.btnMediumBrush.setOnClickListener {
            drawingView.setSizeForBrush(10.toFloat())
            brushDialog.dismiss()
        }

        //large button event
        brushDialog.btnLargeBrush.setOnClickListener {
            drawingView.setSizeForBrush(15.toFloat())
            brushDialog.dismiss()
        }
        brushDialog.show()
    }

    fun paintClicked(view:View){
        if (view !== imgButtonCurrentPaint){
            val imgButton = view as ImageButton
            drawingView.setColor(imgButton.tag.toString())
            imgButton.setImageDrawable(
                ContextCompat.getDrawable(this, R.drawable.pallet_pressed)
            )
            imgButtonCurrentPaint!!.setImageDrawable(
                ContextCompat.getDrawable(this, R.drawable.pallet_normal)
            )
            imgButtonCurrentPaint = imgButton
        }
    }

    private fun requestStoragePermission(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(this,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE).toString())){
            Toast.makeText(this, "Need permission to add a Background", Toast.LENGTH_SHORT).show()
        }
        ActivityCompat.requestPermissions(this,  arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE),STORAGE_PERMISSION_CODE )
    }

    private fun isReadStorageAllowed():Boolean{
        val result = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
        return result == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            STORAGE_PERMISSION_CODE->{
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "Permission granted, now you can read the storage file",
                                            Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this, "You have just denied the permission, you are not able to read from storage file",
                        Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK){
            if (requestCode == GALLERY){
                try {
                    if (data!!.data != null){
                        imageViewBackground.visibility = View.VISIBLE
                        imageViewBackground.setImageURI(data!!.data)
                    }else{
                        Toast.makeText(this, "Error in parsing the image or its corrupted!", Toast.LENGTH_SHORT)
                            .show()
                    }
                }catch (e: Exception){
                    e.printStackTrace()
                }
            }
        }
    }

}