package com.example.currencyconverter.repository

import androidx.lifecycle.MutableLiveData
import com.example.currencyconverter.model.dataClasses.ExchangeRatesModel
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MyRepositoryTest{

    private  var myRepository: MyRepository = MyRepository()
    var testlist : MutableLiveData<ExchangeRatesModel> = MutableLiveData()

    @Mock
    var myRepo: MyRepository = mock(MyRepository::class.java)


}