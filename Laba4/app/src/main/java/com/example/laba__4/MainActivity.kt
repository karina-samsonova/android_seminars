package com.example.laba__4

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val accessToken: String = "6225510a6225510a6225510ae96134a890662256225510a0186299972b28378b19a0b60"
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        val editText = findViewById(R.id.editTextId) as EditText

        val search: ImageButton = findViewById(R.id.search)
        search.setOnClickListener{
            lifecycleScope.launch {
                val user_id: String = editText.text.toString()
                val data = httpGet("https://api.vk.com/method/photos.getAlbums?user_id=" + user_id +
                        "&need_covers=1&access_token=" + accessToken + "&v=5.131")
                if (data.substring(2, 7) == "error"){
                    val item = data.substring(data.indexOf(':')+1, data.lastIndexOf('}'))
                    val error: Error = Gson().fromJson(item, Error::class.java)
                    val builder = AlertDialog.Builder(this@MainActivity)
                    builder.setTitle(error.error_code + " error")
                    builder.setMessage(error.error_msg)
                    builder.show()
                }else {
                    val items = data.substring(data.indexOf('['), data.lastIndexOf(']') + 1)
                    val albums: Array<Album> = Gson().fromJson(items, Array<Album>::class.java)
                    for (album in albums) {
                        album.thumb = crop(httpGetImage(album.thumb_src))
                    }

                    val manager =
                        GridLayoutManager(this@MainActivity, 2, GridLayoutManager.VERTICAL, false)
                    recyclerView.layoutManager = manager
                    val adapter = AlbumsRecyclerAdapter(albums)
                    recyclerView.adapter = adapter
                    adapter.setOnItemClickListener(object :
                        AlbumsRecyclerAdapter.onItemClickListener {
                        override fun onItemClick(position: Int) {
                            val id = albums[position].id
                            val owner_id = albums[position].owner_id

                            lifecycleScope.launch {
                                val data = httpGet(
                                    "https://api.vk.com/method/photos.get?owner_id=" + owner_id +
                                            "&album_id=" + id + "&need_covers=1&access_token=" + accessToken + "&v=5.131"
                                )
                                val items =
                                    data.substring(data.indexOf('['), data.lastIndexOf(']') + 1)
                                val photos: Array<Photo> =
                                    Gson().fromJson(items, Array<Photo>::class.java)
                                for (photo in photos) {
                                    photo.image =
                                        crop(httpGetImage(photo.sizes.sortedByDescending { it.height }[0].url))
                                }

                                val manager = GridLayoutManager(
                                    this@MainActivity,
                                    3,
                                    GridLayoutManager.VERTICAL,
                                    false
                                )
                                recyclerView.layoutManager = manager
                                val adapter = PhotosRecyclerAdapter(photos)
                                recyclerView.adapter = adapter
                            }
                        }
                    })
                }
            }
        }
    }

    private suspend fun httpGet(myURL: String?): String {
        val result = withContext(Dispatchers.IO) {
            val url = URL(myURL)
            val httpConnection = url.openConnection() as HttpURLConnection
            httpConnection.doInput = true
            httpConnection.connect()
            val inputStream: InputStream = httpConnection.inputStream
            inputStream.bufferedReader().readText()
        }
        return result
    }

    private suspend fun httpGetImage(myURL: String?): Bitmap {
        val result = withContext(Dispatchers.IO) {
            val url = URL(myURL)
            val httpConnection = url.openConnection() as HttpURLConnection
            httpConnection.doInput = true
            httpConnection.connect()
            val inputStream: InputStream = httpConnection.inputStream
            BitmapFactory.decodeStream(inputStream)
        }
        return result
    }

    fun crop(srcBmp: Bitmap): Bitmap{
        if (srcBmp.getWidth() >= srcBmp.getHeight()){
            return Bitmap.createBitmap(
                srcBmp,
                srcBmp.getWidth()/2 - srcBmp.getHeight()/2,
                0,
                srcBmp.getHeight(),
                srcBmp.getHeight()
            )
        }else{
            return Bitmap.createBitmap(
                srcBmp,
                0,
                srcBmp.getHeight()/2 - srcBmp.getWidth()/2,
                srcBmp.getWidth(),
                srcBmp.getWidth()
            )
        }
    }

}