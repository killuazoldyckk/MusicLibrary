import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.spotify.database.FavouriteSong
import com.example.spotify.repository.FavouriteSongRepository

class LibraryViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: FavouriteSongRepository = FavouriteSongRepository(application)
    val allFavouriteSongs: LiveData<List<FavouriteSong>> = repository.getAllFavouriteSongs()

    // You can add more methods here to interact with the repository as needed

}
