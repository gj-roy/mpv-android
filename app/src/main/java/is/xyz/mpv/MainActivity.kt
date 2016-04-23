package `is`.xyz.mpv

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.text.Html
import android.view.View

import com.nononsenseapps.filepicker.AbstractFilePickerFragment

import java.io.File

class MainActivity : AppCompatActivity(), AbstractFilePickerFragment.OnFilePickedListener {

    private var fragment: MPVFilePickerFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById(R.id.nnf_button_container)!!.visibility = View.GONE
        fragment = supportFragmentManager.findFragmentById(R.id.file_picker_fragment) as MPVFilePickerFragment

        // The correct way is to modify styles.xml.
        // It doesn't work and I'm too tired to figure out why so let's have this ugly hack instead.
        val ab = supportActionBar
        if (ab != null)
            ab.title = Html.fromHtml("<font color=\"#ffffff\">" + getString(R.string.mpv_activity) + "</font>")
    }

    private fun playFile(filepath: String) {
        val i = Intent(this, MPVActivity::class.java)
        i.putExtra("filepath", filepath)
        startActivity(i)
    }

    override fun onFilePicked(file: Uri) {
        val f = File(file.path)
        playFile(f.absolutePath)
    }

    override fun onFilesPicked(files: List<Uri>) {
    }

    override fun onCancelled() {
    }

    override fun onBackPressed() {
        if (fragment!!.isBackTop) {
            super.onBackPressed()
        } else {
            fragment!!.goUp()
        }
    }

    companion object {
        private val TAG = "mpv"
    }
}