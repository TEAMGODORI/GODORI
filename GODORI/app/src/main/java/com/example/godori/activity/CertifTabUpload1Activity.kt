package com.example.godori.activity

import android.Manifest.permission.CAMERA
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.*
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.MediaStore.Images
import android.util.Log
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.example.godori.R
import kotlinx.android.synthetic.main.activity_certif_tab_upload1.*
import kotlinx.android.synthetic.main.activity_certif_tab_upload2.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class CertifTabUpload1Activity : AppCompatActivity() {
    @SuppressLint("ClickableViewAccessibility")
    private val TAG: String = "태그명"

    // 경로 변수와 요청변수 생성
    lateinit var mCurrentPhotoPath: String //문자열 형태의 사진 경로값 (초기값을 null로 시작하고 싶을 때 - lateinti var)

    private var imgUri: Uri? = null
    private var photoURI: Uri? = null
    private var albumURI: Uri? = null

    private val FROM_CAMERA = 1
    private val FROM_ALBUM = 2
    private val IMAGE_CROP = 3

    @RequiresApi(Build.VERSION_CODES.P)
    @SuppressLint("SimpleDateFormat", "WrongThread")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_certif_tab_upload1)

        //백버튼 눌렀을 때
        backBtn1.setOnClickListener {
            onBackPressed()
        }

        //다음 화면으로 넘어가기
        next1Btn.setOnClickListener {
            val intent = Intent(this, CertifTabUpload2Activity::class.java)
            startActivity(intent)
        }

        //버튼 누르면 촬영하는 부분을 dispatchTakePictureIntent를 불러오게 수정
        Img_Upload.setOnClickListener { v ->
            when (v.id) {
                R.id.Img_Upload -> {
                    if (checkPersmission()) {
                        makeDialog()
                    } else {
                        requestPermission()
                    }

                }
            }
        }
        //현재 시간 가져오기
        val now: Long = System.currentTimeMillis()

        //Date 형식으로 고치기
        val mDate = Date(now)

        //가져오고 싶은 형태로 가져오기 "2018-07-06 01:42:00"
        val simpleDate = SimpleDateFormat("yyyy/MM/dd\n hh:mm")
        val getTime: String = simpleDate.format(mDate)

        //5개 버튼
        time_Btn1.setText(getTime)
        time_Btn2.setText(getTime)
        time_Btn3.setText(getTime)
        time_Btn4.setText(getTime)
        time_Btn5.setText(getTime)

        time_RBtn1.setOnCheckedChangeListener(listener1)
        time_RBtn2.setOnCheckedChangeListener(listener2)

        //버튼 누르면 글쓰기
        time_Btn1.setOnClickListener { v ->
            var img = createImageFile().absoluteFile//절대경로
//            var Uri = getUriFromPath(imgFile.toString())//uri로 변경
            var bitmap = getBitmapImageFromFilePath(img)//비트맵을 파일(img)에 저장 후, 이미지를 비트맵에 저장하는 함수

            when (v.id) {
                R.id.time_Btn1 -> {
//                    val bitmap = (Img_Upload.drawable as BitmapDrawable).bitmap
                    var newBitmap = bitmap?.let { drawTextToBitmap(bitmap) }//그걸 받아서 비트맵에 글씨 올리는 비트맵

//                    val bm = Images.Media.getBitmap(context.getContentResolver(), Uri)//Uri를 비트맵으로
//                        var newBitmap = bitmap?.let { drawTextToBitmap(it) } //사진을 bm으로 넣기
                    Img_Upload.setImageBitmap(newBitmap)//그 비트맵을 이미지뷰에 설정
                }
            }
        }

    }

    //라디오 버튼 멀티라인
    private var listener1: RadioGroup.OnCheckedChangeListener =
        RadioGroup.OnCheckedChangeListener { group, checkedId ->
            if (checkedId != -1) {
                time_RBtn2.setOnCheckedChangeListener(null)
                time_RBtn2.clearCheck()
                time_RBtn2.setOnCheckedChangeListener(listener2)
            }
        }

    //라디오 버튼 멀티라인
    private val listener2: RadioGroup.OnCheckedChangeListener =
        RadioGroup.OnCheckedChangeListener { group, checkedId ->
            if (checkedId != -1) {
                time_RBtn1.setOnCheckedChangeListener(null)
                time_RBtn1.clearCheck()
                time_RBtn1.setOnCheckedChangeListener(listener1)
            }
        }

    // 카메라 권한 요청
    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this, arrayOf(READ_EXTERNAL_STORAGE, CAMERA),
            FROM_CAMERA
        )
    }

    // 카메라 권한 체크
    private fun checkPersmission(): Boolean {
        return (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) ==
                PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED)
    }

    // 권한 요청
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        Log.d(TAG, "onRequestPermissionsResult")
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "Permission: " + permissions[0] + "was " + grantResults[0])
        }
    }

    //다이얼로그 카메라, 앨범선택
    private fun makeDialog() {
        val alt_bld: android.app.AlertDialog.Builder =
            android.app.AlertDialog.Builder(
                this@CertifTabUpload1Activity,
                R.style.MyAlertDialogStyle
            )
        alt_bld.setTitle("사진 업로드")
            .setPositiveButton("사진촬영",
                DialogInterface.OnClickListener { dialog, id ->
                    Log.v("알림", "다이얼로그 > 사진촬영 선택")
                    // 사진 촬영 클릭
                    takePhoto()
                }).setNeutralButton("앨범선택",
                DialogInterface.OnClickListener { dialogInterface, id ->
                    Log.v("알림", "다이얼로그 > 앨범선택 선택")
                    //앨범에서 선택
                    selectAlbum()
                }).setNegativeButton("취소   ",
                DialogInterface.OnClickListener { dialog, id ->
                    Log.v("알림", "다이얼로그 > 취소 선택")
                    // 취소 클릭. dialog 닫기.
                    dialog.cancel()
                })
        val alert: android.app.AlertDialog? = alt_bld.create()
        if (alert != null) {
            alert.show()
        }
    }

    //사진 찍기 클릭, 카메라 인텐트 실행하는 부분
    @SuppressLint("QueryPermissionsNeeded")
    fun takePhoto() {
        // 촬영 후 이미지 가져옴
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        var photoFile: File? = null
        photoFile = createImageFile()

        val providerURI: Uri = FileProvider.getUriForFile(
            this,
            "com.example.godori.fileprovider", photoFile
        )
        imgUri = providerURI
        intent.putExtra(MediaStore.EXTRA_OUTPUT, providerURI)
        startActivityForResult(intent, FROM_CAMERA)
    }

    // 카메라로 촬영한 이미지를 파일로 저장해준다
    // 사진 촬영 후 썸네일만 띄워주므로 이미지를 파일로 저장해야 함. 촬영한 사진을 이미지 파일로 저장하는 함수 createImageFile
    // 사진을 찍기 전, 사진이 저장되는 임시 파일을 생성
    @Throws(IOException::class)
    fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? =
            getExternalFilesDir(Environment.DIRECTORY_PICTURES) // 이미지가 저장될 폴더 이름

        if (storageDir != null) {
            if (!storageDir.exists()) {
                Log.v("알림", "storageDir 존재 x $storageDir")
                storageDir.mkdirs()
            }
        }
        Log.v("알림", "storageDir 존재함 $storageDir")
        val image = File.createTempFile(
            "JPEG_${timeStamp}_",  /* prefix */
            ".jpg",  /* suffix */
            storageDir /* directory */
        )

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.absolutePath
        return image
    }

//    var jpegCallback: android.hardware.Camera.PictureCallback = object : android.hardware.Camera.PictureCallback() {
//        fun onPictureTaken(data: ByteArray, camera: Camera?) {
//            // 파일 저장
//            val sd = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).absolutePath
//            val path = sd + "/test/" + System.currentTimeMillis().toInt() + ".png"
//            try {
//                //byte에서 bitmap으로 변화하는 과정
//                var saveImg = BitmapFactory.decodeByteArray(data, 0, data.size)
//
//                //이부분은 saveImg 변수에서 일정 부분을 짜르는 부분이다.
//                //우선 저장을 위한 코드이기 때문에 신경쓰지 않아도 된다.
//                val imageCutting = ImageCutting()
//                saveImg = imageCutting.imageCut(saveImg)
//                if (saveImg == null) println("이미지 null")
//                if (takeCnt !== 0) {
//                    //자른 이미지를 합치는 코드인데 신경쓰지 않아도 된다.
//                    finishBitmap = CombineImage(finishBitmap, saveImg, true)
//                } else {
//                    finishBitmap = saveImg
//                }
//                System.out.println("카운트 : $takeCnt")
//
//                // 이 부분이 bitmap을 저장하는 if 문이다.
//                if (takeCnt === 19) {
//                    val file = File(path)
//                    val out: OutputStream
//                    file.createNewFile()
//                    out = FileOutputStream(file)
//                    finishBitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
//                    out.close()
//                    println("성공")
//                }
//                takeCnt += 1
//            } catch (e: java.lang.Exception) {
//                e.printStackTrace()
//            }
//            Toast.makeText(
//                applicationContext,
//                "Picture Saved", Toast.LENGTH_LONG
//            ).show() // 파일 저장되었다고 어플리케이션 상에서 출력
//            refreshCamera()
//        }
//    }


//    @Throws(IOException::class)
//    fun createImageFile(): File {
//        val imgFileName = System.currentTimeMillis().toString() + ".jpg"
//        var imageFile: File? = null
//        val storageDir =
//            File(Environment.getExternalStorageDirectory().toString() + "/Pictures", "ireh")
//        if (!storageDir.exists()) {
//            Log.v("알림", "storageDir 존재 x $storageDir")
//            storageDir.mkdirs()
//        }
//        Log.v("알림", "storageDir 존재함 $storageDir")
//        imageFile = File(storageDir, imgFileName)
//        mCurrentPhotoPath = imageFile.absolutePath
//        return imageFile
//    }

    //앨범 선택 클릭 //앨범 열기
    private fun selectAlbum() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = MediaStore.Images.Media.CONTENT_TYPE
        intent.type = "image/*"
        startActivityForResult(intent, FROM_ALBUM)
    }

    //사진 저장
    private fun galleryAddPic() {
        Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE).also { mediaScanIntent ->
            val f = File(mCurrentPhotoPath)
            mediaScanIntent.data = Uri.fromFile(f)
            sendBroadcast(mediaScanIntent)
            Toast.makeText(this, "사진이 저장되었습니다", Toast.LENGTH_SHORT).show()
        }
    }

//    //이미지 자르기
//    private fun launchImageCrop(uri: Uri?){
//        CropImage.activity(uri).setGuidelines(CropImageView.Guidelines.ON)
//            .setCropShape(CropImageView.CropShape.RECTANGLE)
//            .start(this)
//    }

    // 카메라로 촬영한 영상을 가져오는 부분, 가져온 사진 뿌리기
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != RESULT_OK) {
            return
        }
        when (requestCode) {
            FROM_ALBUM -> {
                //앨범에서 가져오기
                if (data != null) {
                    if (data.data != null) {
                        try {
                            var albumFile: File? = null
                            albumFile = createImageFile() //이미지 파일로 저장
                            photoURI = data.data
                            albumURI = Uri.fromFile(albumFile)
                            Img_Upload.setImageURI(photoURI)
                            //cropImage();
                        } catch (e: Exception) {
                            e.printStackTrace()
                            Log.v("알림", "앨범에서 가져오기 에러")
                        }
                    }
                }
            }
            FROM_CAMERA -> {
                //카메라 촬영
                if (resultCode === RESULT_OK) {
                    val file = File(mCurrentPhotoPath)
                    val bitmap: Bitmap?
                    if (Build.VERSION.SDK_INT >= 29) {
                        val source = ImageDecoder.createSource(
                            contentResolver, Uri.fromFile(file)
                        )
                        try {
                            bitmap = ImageDecoder.decodeBitmap(source)
                            Img_Upload.setImageBitmap(bitmap)
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }
                    } else {
                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(
                                this.contentResolver,
                                Uri.fromFile(file)
                            )
                            if (bitmap != null) {
                                Img_Upload.setImageBitmap(bitmap)
                            }
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }
                    }
                } else {
                    Toast.makeText(
                        this@CertifTabUpload1Activity,
                        "사진찍기를 취소하였습니다.",
                        Toast.LENGTH_SHORT
                    ).show();
                }
            }
        }
    }

//    // 비트맵에 글쓰기
//    private fun getTextSize(text1: String, text2: String) =
//        if (text1.length >= 21 || text2.length >= 20) 90 else 125
//
//    @Throws(java.lang.Exception::class, OutOfMemoryError::class)
//    fun loadBackgroundBitmap(
//        context: Context,
//        imgFilePath: String
//    ): Bitmap? {
//        if (!FileUtil.exists(imgFilePath)) {
//            throw FileNotFoundException("background-image file not found : $imgFilePath")
//        }
//
//
//        // 폰의 화면 사이즈를 구한다.
//        val display = (context
//            .getSystemService(WINDOW_SERVICE) as WindowManager).defaultDisplay
//        val displayWidth = display.width
//        val displayHeight = display.height
//
//
//        // 읽어들일 이미지의 사이즈를 구한다.
//        val options = BitmapFactory.Options()
//        options.inPreferredConfig = Config.RGB_565
//        options.inJustDecodeBounds = true
//        BitmapFactory.decodeFile(imgFilePath, options)
//
//
//        // 화면 사이즈에 가장 근접하는 이미지의 리스케일 사이즈를 구한다.
//        // 리스케일의 사이즈는 짝수로 지정한다. (이미지 손실을 최소화하기 위함.)
//        val widthScale = (options.outWidth / displayWidth).toFloat()
//        val heightScale = (options.outHeight / displayHeight).toFloat()
//        val scale = if (widthScale > heightScale) widthScale else heightScale
//        if (scale >= 8) {
//            options.inSampleSize = 8
//        } else if (scale >= 6) {
//            options.inSampleSize = 6
//        } else if (scale >= 4) {
//            options.inSampleSize = 4
//        } else if (scale >= 2) {
//            options.inSampleSize = 2
//        } else {
//            options.inSampleSize = 1
//        }
//        options.inJustDecodeBounds = false
//        return BitmapFactory.decodeFile(imgFilePath, options)
//    }

    //path를 uri로
    open fun getUriFromPath(filePath: String): Uri? {
        val cursor = contentResolver.query(
            Images.Media.EXTERNAL_CONTENT_URI,
            null, "_data = '$filePath'", null, null
        )
        cursor!!.moveToNext()
        val id = cursor.getInt(cursor.getColumnIndex("_id"))
        return ContentUris.withAppendedId(Images.Media.EXTERNAL_CONTENT_URI, id.toLong())
    }

    /**
     * @return Bitmap from saved image file path
     */
    fun getBitmapImageFromFilePath(file: File): Bitmap? { //이미지 파일 경로를 bitmap으로
        if (file.exists()) {
            val myBitmap = BitmapFactory.decodeFile(file.absolutePath)
            val ei = ExifInterface(file.absolutePath)
            val orientation = ei.getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_UNDEFINED
            )
            val rotatedBitmap: Bitmap?
            rotatedBitmap = when (orientation) {
                ExifInterface.ORIENTATION_ROTATE_90 -> myBitmap
                ExifInterface.ORIENTATION_ROTATE_180 -> myBitmap
                ExifInterface.ORIENTATION_ROTATE_270 -> myBitmap
                ExifInterface.ORIENTATION_NORMAL -> myBitmap
                else -> myBitmap
            }
            return rotatedBitmap
        }
        return null
    }

//    //uri를 비트맵으로 변경
//    fun setImageAndSaveImageReturnPath(v: View, data: Intent): String? {
//        return try {
//            // URI 가져오기
//            val selectedImageUri = data.data
//
//            // 선택한 이미지에서 비트맵 생성
//            val input: InputStream = context.getContentResolver().openInputStream(selectedImageUri)
//            val img = BitmapFactory.decodeStream(input)
//            input.close()
//            val path = getString(R.string.app_name)
//            val fileName = "/" + System.currentTimeMillis() + ".png"
//            val externalPath: String = getExternalPath(path)
//            val address = externalPath + fileName
//
//            //imagePath1 = address;
//            //Toast.makeText(context, "imagePath1", Toast.LENGTH_SHORT).show();
//            var out: BufferedOutputStream? = null
//            val dirFile = File(externalPath)
//            if (!dirFile.isDirectory) {
//                dirFile.mkdirs()
//            }
//            val copyFile = File(address)
//
//            // 주소 기반으로 스트림만들기
//
//            // sendBroadcast를 엘범을 최신화한다.
//            try {
//                copyFile.createNewFile()
//                out = BufferedOutputStream(FileOutputStream(copyFile))
//                img.compress(Bitmap.CompressFormat.PNG, 100, out)
//                getActivity().sendBroadcast(
//                    Intent(
//                        Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
//                        Uri.fromFile(copyFile)
//                    )
//                )
//                Log.d(TAG, "이미지저장됨")
//                //Toast.makeText(getActivity(), captureMessage, Toast.LENGTH_LONG).show();
//                // 저장되었다는 문구 생성
//                out.close()
//                // 이거때문인가
//            } catch (e: java.lang.Exception) {
//                e.printStackTrace()
//                Log.d(TAG, "에러")
//            }


//            // drawable로 변환
//            val drawable: Drawable = BitmapDrawable(img)
//
//            // 이미지 표시
//            v.setBackground(drawable)
//            address
//
//            // 비트맵 따로 경로에 저장하고
//            // 그거 파일 패스 가져와야할듯
//        } catch (e: java.lang.Exception) {
//            e.printStackTrace()
//            "null"
//        }
//    }

    fun drawTextToBitmap(
        bitmap: Bitmap
    ): Bitmap {
        //현재 시간 가져오기
        val now: Long = System.currentTimeMillis()
        //Date 형식으로 고치기
        val mDate = Date(now)
        //가져오고 싶은 형태로 가져오기 "2018-07-06 01:42:00"
        val simpleDate = SimpleDateFormat("yyyy/MM/dd\n hh:mm")
        val getTime: String = simpleDate.format(mDate)

        var bt = bitmap
//        var bitmapConfig = bitmap.config
//        if (bitmapConfig == null) {
//            bitmapConfig = Bitmap.Config.ARGB_8888
//        }
//        bitmap = bitmap.copy(bitmapConfig, true)
        val canvas = Canvas(bt)
        val paint = Paint()
//        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.color = Color.BLACK
//        val relation = Math.sqrt((canvas.width * canvas.height).toDouble()) / 250
        paint.textSize = 15.toFloat()//(12 * relation).toFloat()
//        paint.typeface =
//            Typeface.create(Typeface.createFromAsset(assets, "fonts/VCR.ttf"), Typeface.BOLD)
//        val bounds = Rect()
//        paint.getTextBounds(getTime, 0, getTime.length, bounds)
        val horizontalSpacing = 24
        val verticalSpacing = 36
        val y = bitmap.height - verticalSpacing

        canvas.drawText(getTime, horizontalSpacing.toFloat(), y.toFloat(), paint)
        return bt
    }


//    @SuppressLint("ResourceType")
//    fun onClick(v: View) {
//        // TODO Auto-generated method stub
//        when (v.getId()) {
//            R.id.btn_id_confirm -> {
//                if (time_RBtn1.getCheckedRadioButtonId() > 0) {
//                    val radioButton: View =
//                        time_RBtn1.findViewById(time_RBtn1.checkedRadioButtonId)
//                    val radioId: Int = time_RBtn1.indexOfChild(radioButton)
//                    val btn = time_RBtn1.getChildAt(radioId) as RadioButton
//                } else if (time_RBtn2.getCheckedRadioButtonId() > 0) {
//                    val radioButton: View =
//                        time_RBtn2.findViewById(time_RBtn2.getCheckedRadioButtonId())
//                    val radioId: Int = time_RBtn2.indexOfChild(radioButton)
//                    val btn = time_RBtn2.getChildAt(radioId) as RadioButton
//                }
//            }
//        }
//    }
}

