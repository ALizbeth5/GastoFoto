package com.example.gastofoto

import android.app.Application
import com.example.gastofoto.data.AppDatabase
import com.example.gastofoto.data.GastoRepository
import com.example.gastofoto.data.remote.RetrofitInstance

class GastoFotoApplication : Application() {
    val database by lazy { AppDatabase.getDatabase(this) }
    val repository by lazy { GastoRepository(database.gastoDao(), RetrofitInstance.api) }
}
