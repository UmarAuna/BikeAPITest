package com.example.bikeapitest.viewmodel

import android.content.Context
import androidx.lifecycle.*
import com.example.bikeapitest.model.Feature
import com.example.bikeapitest.repository.local.BikesApiDao
import com.example.bikeapitest.repository.repo.BikeApiRepository
import com.example.bikeapitest.extensions.NetworkManager
import com.example.bikeapitest.extensions.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BikeApiViewModel(
    private val repository: BikeApiRepository,
    private val dao: BikesApiDao,
    private val networkManager: NetworkManager,
    private val context: Context
) : ViewModel() {

    private val _bikeApi = MutableLiveData<Resource<List<Feature>>>()
    val observeBikeApi: LiveData<Resource<List<Feature>>>
        get() = _bikeApi

    val getCachedApi
        get() = Transformations.map(dao.fetchLocalDataBike()) {
            it
        }

    fun init() {
        getBikeAPI()
    }

    private fun getBikeAPI() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _bikeApi.postValue(Resource.loading(null))
                if (networkManager.isConnected(context)) {
                    repository.getBikeApi().let {
                        if (it.isSuccessful) {
                            _bikeApi.postValue(Resource.success(it.body()?.features))
                        } else {
                            when (it.code()) {
                                404 -> _bikeApi.postValue(Resource.error("not found", null))
                                500 -> _bikeApi.postValue(Resource.error("server broken", null))

                                else -> _bikeApi.postValue(Resource.error(it.errorBody().toString(), null))
                            }
                        }
                    }
                } else {
                    _bikeApi.postValue(Resource.error("No Internet connection", null))
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
