package com.example.practica1

import android.app.DownloadManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.practica1.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ArticleAdapter
    private val articleList = mutableListOf<Articles>()
    private var country = "us"
    private var category = "general"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.searchNews.setOnQueryTextListener(this)


        initRecycleView()
        searchNew(category)


        binding.argentina.setOnClickListener { country = "ar" ; searchNew(category) }
        binding.greece.setOnClickListener { country = "gr" ; searchNew(category) }
        binding.netherlands.setOnClickListener { country = "nl" ; searchNew(category) }
        binding.southafrica.setOnClickListener {  country = "za" ; searchNew(category)}
        binding.australia.setOnClickListener {  country = "au" ; searchNew(category)}
        binding.hongkong.setOnClickListener {  country = "hk" ; searchNew(category)}
        binding.hongkong.setOnClickListener {  country = "hk" ; searchNew(category)}
        binding.newzealand.setOnClickListener {  country = "hz" ; searchNew(category)}
        binding.southkorea.setOnClickListener {  country = "kr" ; searchNew(category)}
        binding.austria.setOnClickListener {  country = "at" ; searchNew(category)}
        binding.hungary.setOnClickListener {  country = "hu" ; searchNew(category)}
        binding.nigeria.setOnClickListener {  country = "ng" ; searchNew(category)}
        binding.sweden.setOnClickListener {  country = "se" ; searchNew(category)}
        binding.belgium.setOnClickListener {  country = "be" ; searchNew(category)}
        binding.india.setOnClickListener {  country = "in" ; searchNew(category)}
        binding.norway.setOnClickListener {  country = "no" ; searchNew(category)}
        binding.switzerland.setOnClickListener {  country = "ch" ; searchNew(category)}
        binding.brazil.setOnClickListener {  country = "br" ; searchNew(category)}
        binding.indonesia.setOnClickListener {  country = "id" ; searchNew(category)}
        binding.philippines.setOnClickListener {  country = "ph" ; searchNew(category)}
        binding.taiwan.setOnClickListener {  country = "tw" ; searchNew(category)}
        binding.bulgaria.setOnClickListener {  country = "bg" ; searchNew(category)}
        binding.ireland.setOnClickListener {  country = "ie" ; searchNew(category)}
        binding.poland.setOnClickListener {  country = "pl" ; searchNew(category)}
        binding.thailand.setOnClickListener {  country = "th" ; searchNew(category)}
        binding.canada.setOnClickListener {  country = "ca" ; searchNew(category)}
        binding.israel.setOnClickListener {  country = "il" ; searchNew(category)}
        binding.portugal.setOnClickListener {  country = "pt" ; searchNew(category)}
        binding.turkey.setOnClickListener {  country = "tr" ; searchNew(category)}
        binding.china.setOnClickListener {  country = "cn" ; searchNew(category)}
        binding.italy.setOnClickListener {  country = "it" ; searchNew(category)}
        binding.romania.setOnClickListener {  country = "ro" ; searchNew(category)}
        binding.uae.setOnClickListener {  country = "ae" ; searchNew(category)}
        binding.colombia.setOnClickListener {  country = "co" ; searchNew(category)}
        binding.japan.setOnClickListener {  country = "jp" ; searchNew(category)}
        binding.russia.setOnClickListener {  country = "ru" ; searchNew(category)}
        binding.ukraine.setOnClickListener {  country = "ua" ; searchNew(category)}
        binding.cuba.setOnClickListener {  country = "cu" ; searchNew(category)}
        binding.latvia.setOnClickListener {  country = "lv" ; searchNew(category)}
        binding.saudiarabia.setOnClickListener {  country = "sa" ; searchNew(category)}
        binding.unitedkingdom.setOnClickListener {  country = "gb" ; searchNew(category)}
        binding.czechrepublic.setOnClickListener {  country = "cz" ; searchNew(category)}
        binding.lithuania.setOnClickListener {  country = "lt" ; searchNew(category)}
        binding.serbia.setOnClickListener {  country = "rs" ; searchNew(category)}
        binding.unitedstates.setOnClickListener {  country = "us" ; searchNew(category)}
        binding.egypt.setOnClickListener {  country = "eg" ; searchNew(category)}
        binding.malaysia.setOnClickListener {  country = "my" ; searchNew(category)}
        binding.singapore.setOnClickListener {  country = "sg" ; searchNew(category)}
        binding.venezuela.setOnClickListener {  country = "ve" ; searchNew(category)}
        binding.france.setOnClickListener {  country = "fr" ; searchNew(category)}
        binding.mexico.setOnClickListener {  country = "mx" ; searchNew(category)}
        binding.slovakia.setOnClickListener {  country = "sk" ; searchNew(category)}
        binding.germany.setOnClickListener {  country = "de" ; searchNew(category)}
        binding.morocco.setOnClickListener {  country = "ma" ; searchNew(category)}
        binding.slovenia.setOnClickListener {  country = "si" ; searchNew(category)}














    }

    private fun initRecycleView(){
        adapter = ArticleAdapter(articleList)
        binding.rvNews.layoutManager = LinearLayoutManager(this)
        binding.rvNews.adapter = adapter

    }

    private fun searchNew(category: String){

        val api = Retrofit2()

        CoroutineScope(Dispatchers.IO).launch {

            val call = api.getService()?.getNewsByCategory(country,category,"4b94054dbc6b4b3b9e50d8f62cde4f6c")
            val news: NewsResponse? = call?.body()

            runOnUiThread{

                if(call!!.isSuccessful){
                    if(news?.status.equals("ok")){
                        val articles = news?.articles ?: emptyList()
                        articleList.clear()
                        articleList.addAll(articles)
                        adapter.notifyDataSetChanged()
                    }else{
                        showMessage("Error en webservices")
                    }
                }else{
                    showMessage("Error en retrofit")
                }
                hideKeyBoard()

            }

        }
    }

    private fun hideKeyBoard() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.viewRoot.windowToken, 0)
    }


    private fun showMessage(mensaje:String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if(!query.isNullOrEmpty()){
            searchNew(query.toLowerCase())
            category = query.toLowerCase()
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }
}