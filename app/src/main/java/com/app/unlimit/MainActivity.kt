package com.app.unlimit

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.unlimit.databinding.ActivityMainBinding
import com.app.unlimit.util.ConnectionManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: JokeViewModel by inject()
    private val connectionManager: ConnectionManager by inject()
    private lateinit var jokesAdapter: JokesListAdapter
    private val TIMER = 5000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        getLocalJokes()

        initRecyclerView()
        observeViewModel()
    }

    private fun getLocalJokes() {
        viewModel.getAllLocalJokes()
    }

    private fun getJokesFromApi() {
        if (connectionManager.isOnline()) {
            viewModel.callJokesApi()
        } else {
            showToast(getString(R.string.error_no_internet))
            retryJokesApi()
        }
    }

    private fun retryJokesApi() {
        CoroutineScope(Dispatchers.Main).launch {
            delay(TIMER)
            getJokesFromApi()
        }
    }

    private fun initRecyclerView() {
        binding.rvJokes.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)

            adapter = JokesListAdapter(ArrayList())
            jokesAdapter = adapter as JokesListAdapter
        }
    }

    private fun observeViewModel() {

        viewModel.observeError.observe(this) {
            if (it is Int)
                showToast(getString(it))
            else
                showToast(it.toString())
        }

        viewModel.observeJokeResponse.observe(this) {
            retryJokesApi()
            if (this::jokesAdapter.isInitialized) {
                jokesAdapter.notifySingleItem(it)

                binding.rvJokes.scrollToPosition(jokesAdapter.itemCount - 1)
            }
        }

        viewModel.observeLocalJokes.observe(this) {
            getJokesFromApi()

            if (this::jokesAdapter.isInitialized) {
                jokesAdapter.notifyLocalList(it)
            }
        }
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }
}