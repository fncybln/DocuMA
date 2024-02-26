package com.theatretools.documa

import android.app.Application
import android.content.SharedPreferences
import com.theatretools.documa.data.AppDatabase
import com.theatretools.documa.data.DataRepository
import com.theatretools.documa.telnet.TelnetClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob


class MainApplication: Application() {
    val applicationScope = CoroutineScope(SupervisorJob())
    val database by lazy { AppDatabase.getInstance(this) }
    val repository by lazy { DataRepository(database.appDao()) }


}