package com.edwardzhang.sorry.templatelist

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.edwardzhang.sorry.entity.GifTemplateItemEntity
import com.edwardzhang.sorry.utils.application
import com.edwardzhang.sorry.utils.callLiveData
import org.json.JSONObject
import java.io.InputStreamReader

class TemplateListViewModel : ViewModel() {
    val liveTemplateList = MutableLiveData<List<GifTemplateItemEntity>>()

    fun parseList() {
        with(application.assets) {
            list("templates")
                    .filter { it.endsWith(".json") }
                    .map { it.substring(0, it.length - 5) }
                    .toList()
                    .map { string ->
                        InputStreamReader(open("templates/$string.json"))
                                .use {
                                    val json = it.readText()
                                    val name = (org.json.JSONTokener(json).nextValue() as JSONObject).getString("name")
                                    val file = "templates/$string.mp4"
                                    com.edwardzhang.sorry.entity.GifTemplateItemEntity(name, file,json)
                                }
                    }
                    .callLiveData(liveTemplateList)
        }
    }
}