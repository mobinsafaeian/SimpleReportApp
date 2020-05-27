package mobinsafaeian.devipe.entrance.ui.view

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import kotlinx.android.synthetic.main.activity_main.*
import mobinsafaeian.devipe.entrance.R
import mobinsafaeian.devipe.entrance.databinding.ActivityMainBinding
import mobinsafaeian.devipe.entrance.model.connections.responses.RollCall
import mobinsafaeian.devipe.entrance.ui.adapters.RecyclerViewAdapter
import mobinsafaeian.devipe.entrance.ui.viewmodel.ListViewModel
import mobinsafaeian.devipe.entrance.utils.Utils
import mobinsafaeian.devipe.entrance.utils.toast
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Workbook
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception

class MainActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {

    // Definitions
    private val listViewModel: ListViewModel by lazy { ViewModelProviders.of(this@MainActivity).get(ListViewModel::class.java) }
    private lateinit var recyclerViewAdapter: RecyclerViewAdapter
    private var listItems: ArrayList<RollCall>? = null
    private lateinit var workbook: Workbook
    private lateinit var utils: Utils
    private val root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString()

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

        // Field Initializations
        workbook = HSSFWorkbook()
        listItems = ArrayList()
        utils = Utils()

        // SwipeRefreshLayout Initializations
        main_swipe_refresh_layout.setOnRefreshListener(this)

        // RecyclerView Initializations
        initRecyclerView()

        // Start to Observe Response LiveData
        observeLiveData()

        // Handle onClick Events
        onClickHandler()

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
            listItems?.addAll(it)
            listItems?.size.toString() toast this
        })

        listViewModel.message.observe(this@MainActivity , Observer { it.toast(this@MainActivity) })
    }


    /**
     * Handle onClick Events
     */
    private fun onClickHandler() {

        xls_fab.setOnClickListener {
            utils.checkPermission(this@MainActivity) {
                val sheet = workbook.createSheet("EntranceReport")
                for (i in 0 until listItems?.size!!) {
                    val row = sheet.createRow(i)
                    val c0 = row.createCell(0)
                    c0.setCellValue(listItems!![i].username)
                    val c1 = row.createCell(1)
                    c1.setCellValue(listItems!![i].entrance)
                    val c2 = row.createCell(2)
                    c2.setCellValue(if (listItems!![i].exit.isNotEmpty()) listItems!![i].exit else "")
                    sheet.setColumnWidth(0, (15 * 500))
                    sheet.setColumnWidth(1, (15 * 500))
                    sheet.setColumnWidth(2, (15 * 500))
                }

                val myDir = File("$root/coordinator khoshgeli")

                if (!myDir.exists())
                    myDir.mkdir()

                val file = File(myDir, "entrance.xls")
                var os: FileOutputStream? = null

                try {
                    os = FileOutputStream(file)
                    workbook.write(os)
                    os.flush()
                    os.close()
                    "excel file created in your storage" toast this
                }
                catch (e: Exception){
                    Log.e("error" , e.message.toString())
                }finally {
                    os?.let { os.close() }
                }
            }
        }

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
