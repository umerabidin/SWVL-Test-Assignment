import android.app.Activity
import android.app.Application
import java.io.InputStream

const val PAGE_GENRE = 0
const val PAGE_CAST = 1
const val PAGE_COUNT = 2

const val MOVIES_JSON = "movies.json"
const val API_KEY = "89769c3879fc569f3c02ac1c3a85c7ec"
const val APP_SECRET = "3d363ee26654cc89"

public fun Application.readHospitalJsonFromAssets(): String? {
    var json: String? = null
    try {
        val inputStream: InputStream = assets.open(MOVIES_JSON)
        json = inputStream.bufferedReader().use { it.readText() }
    } catch (ex: Exception) {
        ex.printStackTrace()
        return null
    }
    return json
}