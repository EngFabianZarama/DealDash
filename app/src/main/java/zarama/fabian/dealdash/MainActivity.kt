package zarama.fabian.dealdash

import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import org.json.JSONArray
import org.json.JSONObject
import java.net.URL
import java.util.regex.Pattern

class MainActivity : AppCompatActivity() {

    var arrayName = arrayListOf<String>()
    var arrayURLS = arrayListOf<String>()
    var arrayPosition : Int = 0

    // Pass in a string, pass out String
    open class DownloadTask() : AsyncTask<String, Void, String>(){
        protected override fun doInBackground(vararg urls: String?): String { // is like an array
            var result: String = ""

            try {

                result = URL(urls.get(0)).readText()

            }catch (e: Exception){
                Log.i("error DownloadTask()", e.localizedMessage)
            }
            return result
        }


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var task = DownloadTask()
        var result : String = ""

        try {
            //result = task.execute("http://www.posh24.se/kandisar").get()
            result = task.execute("https://www.dealdash.com/#do=cat&q=7&page=0").get()
            var splitResult  = result.split("class=\"time\"")

            var p = Pattern.compile("img src=\"(.*?)\"")
            var m = p.matcher(splitResult.get(1))



            while (m.find()){

                arrayURLS.add(0,m.group(1))
            }

            arrayURLS.reverse()

            p = Pattern.compile("<h3>(.*?)</h3?")
            m = p.matcher(splitResult.get(1))


            while (m.find()){
                arrayName.add(m.group(1))

            }




        }catch (e: Exception){
            Log.i("error onCreate", e.printStackTrace().toString())
        }

    }
}
