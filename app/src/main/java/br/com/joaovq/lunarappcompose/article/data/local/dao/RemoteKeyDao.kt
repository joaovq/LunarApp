package br.com.joaovq.lunarappcompose.article.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.joaovq.lunarappcompose.article.data.local.model.QUERY_COLUMN_NAME
import br.com.joaovq.lunarappcompose.article.data.local.model.REMOTE_KEYS_TABLE_NAME
import br.com.joaovq.lunarappcompose.article.data.local.model.RemoteKeys

@Dao
interface RemoteKeyDao {
    @Query("SELECT * FROM $REMOTE_KEYS_TABLE_NAME WHERE $QUERY_COLUMN_NAME LIKE '%' || :query || '%'")
    suspend fun remoteKeysByQuery(query: String): RemoteKeys?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKeys: List<RemoteKeys>)

    @Query("DELETE FROM $REMOTE_KEYS_TABLE_NAME")
    suspend fun clearRemoteKeys()

}