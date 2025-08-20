// AppDatabase.kt
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.danew_app.data.converter.StringListConverter
import com.example.danew_app.data.entity.NewsEntity

@Database(entities = [NewsEntity::class], version = 1, exportSchema = false)
@TypeConverters(StringListConverter::class)
abstract class AppDatabase : RoomDatabase() {
}