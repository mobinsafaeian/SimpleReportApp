package mobinsafaeian.devipe.entrance.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.android.synthetic.main.activity_main.*
import mobinsafaeian.devipe.entrance.R
import mobinsafaeian.devipe.entrance.databinding.ActivityMainBinding
import mobinsafaeian.devipe.entrance.ui.adapters.RecyclerViewAdapter
import mobinsafaeian.devipe.entrance.ui.viewmodel.ListViewModel
import mobinsafaeian.devipe.entrance.utils.toast

class MainActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {

    // Definitions
    private val listViewModel: ListViewModel by lazy { ViewModelProviders.of(this@MainActivity).get(ListViewModel::class.java) }
    private lateinit var recyclerViewAdapter: RecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initializations
        init()

    }


    /**
     * Initializations
     */
    private fun init() {
        // DataBinding Initializations for @this Context
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this , R.layout.activity_main)
        with(binding) {
            lifecycleOwner = this@MainActivity
            viewModel = listViewModel
        }

        // SwipeRefreshLayout Initializations
        main_swipe_refresh_layout.setOnRefreshListener(this)

        // RecyclerView Initializations
        initRecyclerView()

        // Start to Observe Response LiveData
        observeLiveData()

        // Fetching List
        listViewModel.fetchList()
    }


    /**
     * RecyclerView Initializations
     */
    private fun initRecyclerView() {
        recyclerViewAdapter = RecyclerViewAdapter()
        with(main_recycler_view) {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = recyclerViewAdapter
        }
    }


    /**
     * Start to Observe Server Response LiveData and Inject them to View
     */
    private fun observeLiveData() {

        listViewModel.data.observe(this@MainActivity , Observer {
            main_swipe_refresh_layout.isRefreshing = false
            recyclerViewAdapter.reset()
            recyclerViewAdapter.setData(it)
        })

        listViewModel.message.observe(this@MainActivity , Observer { it.toast(this@MainActivity) })
    }


    /**
     * SwipeRefreshLayout Listener
     */
    override fun onRefresh() {
        listViewModel.fetchList()
    }


    /**
     * Whenever this Activity is Destroyed, The RxJava Network Observers must be disposed.
     * the unbind function do the job for us.
     */
    override fun onDestroy() {
        listViewModel.unbind()
        super.onDestroy()
    }
}
