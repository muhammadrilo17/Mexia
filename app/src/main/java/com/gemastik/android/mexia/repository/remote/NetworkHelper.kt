package com.gemastik.android.mexia.repository.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONArrayRequestListener
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.gemastik.android.mexia.repository.remote.entity.Api4WordEntity
import com.gemastik.android.mexia.repository.remote.entity.Api5WordEntity
import com.gemastik.android.mexia.repository.remote.entity.ApiUserEntity
import org.json.JSONArray
import org.json.JSONObject

class NetworkHelper {
    private val baseUrl = "https://mexiarestserver.000webhostapp.com/api/"

    fun getUserByEmail(email: String): LiveData<ApiUserEntity> {
        val user = MutableLiveData<ApiUserEntity>()
        val temp = ApiUserEntity()
        AndroidNetworking.get(baseUrl + "MexiaUser")
            .addQueryParameter("email", email)
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject?) {
                    val list = response?.getJSONArray("data")
                    if (list != null) {
                        val getList = list.getJSONObject(0)
                        temp.id = getList.getString("id_user")
                        temp.username = getList.getString("username")
                        temp.email = getList.getString("email")
                        temp.password = getList.getString("password")
                        temp.phoneNumber = getList.getString("phone_number")
                        temp.address = getList.getString("address")
                        temp.timeLearning = getList.getString("waktu_belajar")
                        temp.totalXp = getList.getString("total_xp")
                        temp.pathImage = getList.getString("foto_user")
                        user.postValue(temp)
                    }
                }
                override fun onError(anError: ANError?) {
                    Log.d("EXCEPTION", anError.toString())
                    user.postValue(temp)
                }
            })
        return user
    }

    fun getAll5Word(): LiveData<List<Api5WordEntity>> {
        val items = MutableLiveData<List<Api5WordEntity>>()
        val tempItems = ArrayList<Api5WordEntity>()
        AndroidNetworking.get(baseUrl + "LimaHuruf")
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject?) {
                    val list = response?.getJSONArray("data")
                    if (list != null) {
                        for (i in 0 until list.length()) {
                            val getList = list.getJSONObject(i)
                            val temp = Api5WordEntity()
                            temp.id = getList.getString("id_gambarLimaHuruf")
                            temp.name = getList.getString("name_limaHuruf")
                            temp.image = getList.getString("gambar_limaHuruf")
                            temp.point = getList.getString("poin_limaHuruf")
                            tempItems.add(temp)
                        }
                        items.postValue(tempItems)
                    }
                }

                override fun onError(anError: ANError?) {
                    Log.d("EXCEPTION", anError.toString())
                    items.postValue(tempItems)
                }

            })
        return items
    }

    fun registerUser(user: ApiUserEntity) {
        AndroidNetworking.post(baseUrl+"MexiaUser")
            .addBodyParameter("username", "-")
            .addBodyParameter("email", user.email)
            .addBodyParameter("password", user.password)
            .addBodyParameter("phone_number", "-")
            .addBodyParameter("address", "-")
            .addBodyParameter("waktu_belajar", "0")
            .addBodyParameter("total_xp", "0")
            .addBodyParameter("foto_user", "-")
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject?) {
                    val resp = response?.getString("status")
                    if (resp != null) Log.d("SUCCESS", resp)
                }

                override fun onError(anError: ANError?) {
                    Log.d("FAILED", anError.toString())
                }

            })
    }
    fun getAll4Word(): LiveData<List<Api4WordEntity>> {
        val items = MutableLiveData<List<Api4WordEntity>>()
        val tempItems = ArrayList<Api4WordEntity>()
        AndroidNetworking.get(baseUrl + "EmpatHuruf")
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject?) {
                    val list = response?.getJSONArray("data")
                    if (list != null) {
                        for (i in 0 until list.length()) {
                            val getList = list.getJSONObject(i)
                            val temp = Api4WordEntity()
                            temp.id = getList.getString("id_EmpatHuruf")
                            temp.name = getList.getString("name_EmpatHuruf")
                            temp.image = getList.getString("poin_EmpatHuruf")
                            temp.point = getList.getString("gambar_EmpatHuruf")
                            tempItems.add(temp)
                        }
                        items.postValue(tempItems)
                    }
                }

                override fun onError(anError: ANError?) {
                    Log.d("EXCEPTION", anError.toString())
                    items.postValue(tempItems)
                }

            })
        return items
    }
}