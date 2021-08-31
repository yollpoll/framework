package com.yollpoll.myframework.ui.moshi

import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.squareup.moshi.JsonClass
import com.yollpoll.fast.FastActivity
import com.yollpoll.fast.FastApplication
import com.yollpoll.fast.FastViewModel
import com.yollpoll.framework.annotation.ContentView
import com.yollpoll.framework.annotation.ViewModel
import com.yollpoll.framework.extend.*
import com.yollpoll.myframework.R
import com.yollpoll.myframework.databinding.ActivityTestMoshiBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by spq on 2021/8/31
 */
private const val TAG = "spq2"

@ContentView(R.layout.activity_test_moshi)
@ViewModel(TestMoshiViewModel::class)
class TestMoshiActivity : FastActivity<ActivityTestMoshiBinding, TestMoshiViewModel>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}

class TestMoshiViewModel(application: Application) : FastViewModel(application) {

    init {
        viewModelScope.launch(Dispatchers.IO) {
            try {
//                saveBean("test",TestBean("测试", 1))
//                val json=TestBean("测试", 1).toJson()
//                val bean = getBean("test", TestBean::class.java)
//                Log.d(TAG, "json: $json")
//                Log.d(TAG, "testBean: ${bean?.name}")

                val list = arrayListOf(
                    TestBean("测试1", 1),
                    TestBean("测试2", 2),
                    TestBean("测试3", 3)
                )

                val map = HashMap<String, TestBean>()
                map["test"] = TestBean("测试1", 1)

                val mapJson = map.toMapJson()
                Log.d(TAG, "mapJson>: ${mapJson}")

                val mapBean = mapJson.toMapBean<String, TestBean>()!!
                Log.d(TAG, "mapBean>>>: ${mapBean["test"]?.name}")

                val json = list.toListJson()
                Log.d(TAG, "listJson>>>${json}")

                val list2 = json.toListBean<TestBean>()
                Log.d(TAG, "listBean size>>>>: ${list2?.size}")


                //view model test
                saveList("list", list)
                val listBean = getList<TestBean>("list")
                Log.d(TAG, "save list >>: ${listBean?.size}")

                saveMap("map", map)
                Log.d(TAG, "save map>>>: ${map["test"]?.name}")

                saveBean("bean", TestBean("save", 1))
                Log.d(TAG, "save bean>>>>: ${getBean<TestBean>("bean")?.name}")

            } catch (e: Exception) {
                Log.d(TAG, "${e.message} ")
            }

        }
    }

}

@JsonClass(generateAdapter = true)
public data class TestBean(val name: String, val id: Int)