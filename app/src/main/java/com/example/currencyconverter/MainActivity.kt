package com.example.currencyconverter

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyconverter.adapter.ConvertedCurrencyListAdapter
import com.example.currencyconverter.model.dataClasses.ExchangeRatesModel
import com.example.currencyconverter.model.dataClasses.Rates
import com.example.currencyconverter.viewModel.CurrencyListViewModel
import org.koin.android.ext.android.inject
import java.lang.Double.*
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var tvConvertedAmount : TextView
    lateinit var btnConvert: Button
    lateinit var amountToConvert : EditText
    lateinit var recyclerView:RecyclerView


    private var toCurrencySelectedRate : Double = 1.00
    private var fromAmountEntered: Double = 1.00
    private var convertedAmountToDisplay: Double = 0.00
    private var amountConvertedList = ArrayList<Any>()

    private val currencyListViewModel : CurrencyListViewModel by inject()
    val context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        amountToConvert = findViewById(R.id.etFrom)

        tvConvertedAmount = findViewById(R.id.tvConvertedCurrencyAmount)
        btnConvert = findViewById(R.id.btnConvert)
        recyclerView = findViewById(R.id.recyclerView)

        var spinner: Spinner = findViewById(R.id.spToCurrencyAmount)
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View?, position: Int, id: Long) {
                val item = adapterView.getItemAtPosition(position)
                if (item != null) {
                    toCurrencySelected = item.toString()
                }
            }
            override fun onNothingSelected(adapterView: AdapterView<*>?) {
                // TODO Auto-generated method stub
            }
        }
        setupAdapter()
        setupViewModel()
        setupObserver()
        onTextChanged()
        onConvertButtonClick()
    }

    private fun setupAdapter() {
        var layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(applicationContext)
        recyclerView.layoutManager = layoutManager
        val adapter = ConvertedCurrencyListAdapter(this, amountConvertedList)
        recyclerView.adapter = adapter

    }

    private fun onConvertButtonClick() {
        amountToConvert.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //TODO("Not yet implemented")
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (amountToConvert.text.toString().length == 0 ) {
                    btnConvert.setEnabled(false)
                } else {
                    btnConvert.setEnabled(true);
                }
            }

            override fun afterTextChanged(p0: Editable?) {
                //TODO("Not yet implemented")
            }
        })
        btnConvert.setOnClickListener {
            setupObserver()
            tvConvertedAmount.text = convertedAmountToDisplay.toString() //convertedCurrency
            amountConvertedList.add("$toCurrencySelected : $convertedAmountToDisplay")
            setupAdapter()
        } 
    }

    private fun onTextChanged() {
        var spinner: Spinner = findViewById(R.id.spToCurrencyAmount)
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>, view: View?, position: Int, id: Long) {
                val item = adapterView.getItemAtPosition(position)
                if (item != null && amountToConvert.text != null) {
                    btnConvert.visibility = View.VISIBLE
                    toCurrencySelected = item.toString()
                    onConvertButtonClick()
                }
            }
            override fun onNothingSelected(adapterView: AdapterView<*>?) {
                // TODO Auto-generated method stub
            }
        }
    }

    private fun setupObserver() {
        currencyListViewModel.exchangeRateLiveData.observe(this, object : Observer<ExchangeRatesModel?> {
                override fun onChanged(curencyModels: ExchangeRatesModel?) {
                    if (curencyModels != null) {

                        rates = curencyModels.rates!!
                        toCurrencySelectedRate = parseDouble(getRateForCurrency(toCurrencySelected, rates).toString())

                        fromAmountEntered = parseDouble(amountToConvert.text.toString())

                        if (toCurrencySelectedRate == 0.00) {
                            Toast.makeText(applicationContext, "No exchange rate available at the moment", Toast.LENGTH_LONG).show()
                        } else {
                            convertedAmountToDisplay = toCurrencySelectedRate * fromAmountEntered
                        }
                    } else {
                        Toast.makeText(applicationContext,"No internet..Please Turn on data..Then Try Again Restarting The App!!",Toast.LENGTH_LONG).show()
                    }
                }
            })


    }
    private fun getRateForCurrency(currency: String, rates: Rates) = when (currency) {
        "CAD" -> rates.CAD
        "HKD" -> rates.HKD
        "ISK" -> rates.ISK
        "EUR" -> rates.EUR
        "PHP" -> rates.PHP
        "DKK" -> rates.DKK
        "HUF" -> rates.HUF
        "CZK" -> rates.CZK
        "AUD" -> rates.AUD
        "RON" -> rates.RON
        "SEK" -> rates.SEK
        "IDR" -> rates.IDR
        "INR" -> rates.INR
        "BRL" -> rates.BRL
        "RUB" -> rates.RUB
        "HRK" -> rates.HRK
        "JPY" -> rates.JPY
        "THB" -> rates.THB
        "CHF" -> rates.CHF
        "SGD" -> rates.SGD
        "PLN" -> rates.PLN
        "BGN" -> rates.BGN
        "CNY" -> rates.CNY
        "NOK" -> rates.NOK
        "NZD" -> rates.NZD
        "ZAR" -> rates.ZAR
        "USD" -> rates.USD
        "MXN" -> rates.MXN
        "ILS" -> rates.ILS
        "GBP" -> rates.GBP
        "KRW" -> rates.KRW
        "MYR" -> rates.MYR
        else -> null
    }

    private fun setupViewModel() {
        currencyListViewModel.apiCall()
    }

    companion object {
        private lateinit var toCurrencySelected: String
        private lateinit var rates: Rates
    }
}