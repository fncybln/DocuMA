package com.theatretools.documa.resultContracts

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import com.theatretools.documa.dataobjects.PresetItemPackage

class AddPreset : ActivityResultContract<Void?, PresetItemPackage?>() {
    override fun createIntent(context: Context, input: Void?): Intent {
        TODO("Not yet implemented")
    }

    override fun parseResult(resultCode: Int, result: Intent?): PresetItemPackage? {
        if (resultCode != Activity.RESULT_OK){
            return null
        }
        return null
    }

}