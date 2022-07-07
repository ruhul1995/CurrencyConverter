package com.example.currencyconverter.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.currencyconverter.model.dataClasses.ExchangeRatesModel
import com.example.currencyconverter.repository.MyRepository
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.currencyconverter.MainActivity
import com.example.currencyconverter.MainViewModelFactory
import io.mockk.MockKAnnotations
import kotlinx.coroutines.test.TestCoroutineDispatcher
import okhttp3.internal.immutableListOf
import org.koin.android.ext.android.inject
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.java.KoinJavaComponent.inject

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class CurrencyListViewModelTest {


    @MockK
    private lateinit var repository: MyRepository
    var mainViewModelFactory = MainViewModelFactory(repository)

    @MockK
    private lateinit var mainActivity: MainActivity

    private val viewModel : CurrencyListViewModel by inject()
    private lateinit var mainCoroutineRule: MainCoroutineRule

    @Before
    fun setup()
    {
        mainCoroutineRule = MainCoroutineRule(testDispatcher)
        MockKAnnotations.init(this, true)


        startKoin {
            modules(
                immutableListOf(module {
                    single { MyRepository() }
                    single { CurrencyListViewModel(repository) }
                })
            )
        }
    }

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()



    @MockK
    var observer = ExchangeRatesModel()
    //var observer: Observer<ExchangeRatesModel>? = null

    private val testDispatcher = TestCoroutineDispatcher()

    //@SpyK
    private  var exchangeRatesModel :ExchangeRatesModel = ExchangeRatesModel(null, null, null, null, null)


    @Test
    fun `testing livedata object is success`()  {

        viewModel.apiCall()

        //mocking the result
        coEvery {
            repository.getCurrency()
        } returns exchangeRatesModel
        // observing the livedata data
        viewModel.exchangeRateLiveData.observeForever {
            observer = exchangeRatesModel
        }
        //verifying the observer the result
        //assertTrue(observer == exchangeRatesModel)

    }

}