package com.edwardzhang.sorry.giftemplate

class GifProgress(var progress: Int, var total: Int){
    val isFinsished: Boolean
        get() = progress == total
}