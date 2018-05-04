package com.edwardzhang.sorry.templatelist

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.WindowManager
import com.edwardzhang.sorry.base.BaseActivity
import com.edwardzhang.sorry.entity.GifTemplateItemEntity
import com.edwardzhang.sorry.giftemplate.TemplateActivity
import com.edwardzhang.sorry.utils.MATCH
import com.edwardzhang.sorry.utils.getViewModel
import com.edwardzhang.sorry.utils.setActionBarTitle
import com.edwardzhang.sorry.utils.startActivity

class TemplateListActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //修改标题
        setActionBarTitle("选择模板")
        val adapter = TemplateListAdapter()
        adapter.onItemClickListener = object: TemplateListAdapter.OnItemClickListener{
            override fun onItemClick(position: Int, item: GifTemplateItemEntity?) {
                item?.apply {
                    startActivity(TemplateActivity::class){
                        putExtra("json",(this@apply).json)
                        putExtra("file",(this@apply).file)
                    }
                }
            }

        }
        val recyclerView = RecyclerView(this)
        recyclerView.layoutManager = GridLayoutManager(this, 2) as RecyclerView.LayoutManager?

        recyclerView.adapter = adapter
        setContentView(recyclerView, WindowManager.LayoutParams(MATCH, MATCH))

        val model: TemplateListViewModel = getViewModel()
        model.liveTemplateList.observe(this,adapter)
        model.parseList()
    }
}