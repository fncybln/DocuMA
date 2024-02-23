package com.theatretools.documa

import android.app.Application
import com.theatretools.documa.data.AppDatabase
import com.theatretools.documa.data.DataRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.coroutineScope

class MainApplication: Application() {
    val applicationScope = CoroutineScope(SupervisorJob())
    val database by lazy { AppDatabase.getInstance(this) }
    val repository by lazy { DataRepository(database.appDao()) }

}