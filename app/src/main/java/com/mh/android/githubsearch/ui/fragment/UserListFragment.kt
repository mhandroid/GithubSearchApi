package com.mh.android.githubsearch.ui.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mh.android.githubsearch.GithubSearchApplication
import com.mh.android.githubsearch.R
import com.mh.android.githubsearch.adapter.SearchAdapter
import com.mh.android.githubsearch.isVisible
import com.mh.android.githubsearch.model.ApiResult
import com.mh.android.githubsearch.model.User
import com.mh.android.githubsearch.viewmodel.SearchViewModel
import javax.inject.Inject

/**
 * A fragment representing a list of Items.
 */
class UserListFragment(val listType: ListType) : Fragment(), SearchAdapter.ListItemCLickListener {

    private var columnCount = 1

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private var searchViewModel: SearchViewModel? = null
    lateinit var adapter: SearchAdapter
    lateinit var progressBar: ProgressBar
    lateinit var mRecyclerView: RecyclerView

    private var listItemClickListener: ListItemClickListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ListItemClickListener) {
            listItemClickListener = context
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_user_list, container, false)

        // Set the adapter
        initializeView(view)
        return view
    }

    /**
     * Method to initialize view
     */
    private fun initializeView(root: View) {
        progressBar = root.findViewById(R.id.btnProgress)

        mRecyclerView = root.findViewById(R.id.list)
        with(mRecyclerView) {
            layoutManager = when {
                columnCount <= 1 -> LinearLayoutManager(context)
                else -> GridLayoutManager(context, columnCount)
            }
        }

        val linearLayoutManager = LinearLayoutManager(root.context)
        mRecyclerView.layoutManager = linearLayoutManager
        mRecyclerView.setHasFixedSize(true)
        adapter = SearchAdapter(root.context, mutableListOf(), this)
        mRecyclerView.adapter = adapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        configureDagger()
        searchViewModel = viewModelFactory.create(SearchViewModel::class.java)
        when (listType) {
            ListType.FOLLOWING -> {
                showLoader(true)
                loadUserFollowing()
            }
            ListType.SEARCH -> {
                showLoader(false)
            }
            ListType.FOLLOWERS -> {
                showLoader((true))
                loadUserFollowers()
            }
        }
    }

    /**
     * Method to init dagger
     */
    private fun configureDagger() {
        (context?.applicationContext as GithubSearchApplication).getAppComponent().inject(this)
    }

    fun startSearching(string: String) {
        Log.d("MUB", "search string $string")
        searchViewModel?.getListOfUser(string)?.observe(viewLifecycleOwner, { apiResult ->
            showLoader(false)
            when (apiResult) {
                is ApiResult.Success -> {
                    Log.d("MUB", "Success result ${apiResult.data?.size}")
                    apiResult.data?.let {
                        adapter.updateList(it)
                    }
                }
                is ApiResult.Error -> {
                    Toast.makeText(context, apiResult.error, Toast.LENGTH_LONG).show()
                    Log.d("MUB", "Error response error msg:${apiResult.error}")
                }
                else -> Log.d("MUB", "Unknown error")
            }
        })
    }

    private fun loadUserFollowing() {
        Log.d("MUB", "loadUserFollowers")
        arguments?.getString(ARG_USER_LOGIN_NAME)?.let { loginName ->
            searchViewModel?.getFollowingList(loginName)?.observe(viewLifecycleOwner, { apiResult ->
                showLoader(false)
                when (apiResult) {
                    is ApiResult.Success -> {
                        Log.d("MUB", "Success result ${apiResult.data?.size}")
                        apiResult.data?.let {
                            adapter.updateList(it)
                        }
                    }
                    is ApiResult.Error -> {
                        Toast.makeText(context, apiResult.error, Toast.LENGTH_LONG).show()
                        Log.d("MUB", "Error response error msg:${apiResult.error}")
                    }
                    else -> Log.d("MUB", "Unknown error")
                }
            })
        }
    }

    private fun loadUserFollowers() {
        Log.d("MUB", "loadUserFollowers")
        arguments?.getString(ARG_USER_LOGIN_NAME)?.let { loginName ->
            searchViewModel?.getFollowersList(loginName)?.observe(viewLifecycleOwner, { apiResult ->
                showLoader(false)
                when (apiResult) {
                    is ApiResult.Success -> {
                        Log.d("MUB", "Success result ${apiResult.data?.size}")
                        apiResult.data?.let {
                            adapter.updateList(it)
                        }
                    }
                    is ApiResult.Error -> {
                        Log.d("MUB", "Error response error msg:${apiResult.error}")
                        Log.d("MUB", "Error response error msg:${apiResult.error}")
                    }
                    else -> Log.d("MUB", "Unknown error")
                }
            })
        }
    }

    fun showLoader(show: Boolean) {
        progressBar.isVisible = show
        mRecyclerView.isVisible = !show
    }

    companion object {
        const val ARG_COLUMN_COUNT = "column-count"
        const val ARG_USER_LOGIN_NAME = "user-login-name"

        @JvmStatic
        fun newInstance(columnCount: Int, userLoginName: String? = null, listType: ListType) = UserListFragment(listType).apply {
            arguments = Bundle().apply {
                putInt(ARG_COLUMN_COUNT, columnCount)
                putString(ARG_USER_LOGIN_NAME, userLoginName)
            }
        }
    }

    override fun onItemClick(user: User) {
        listItemClickListener?.onListItemClick(user)
    }

    interface ListItemClickListener {
        fun onListItemClick(user: User)
    }

    enum class ListType(val value: Int) {
        SEARCH(1), FOLLOWING(2), FOLLOWERS(3)
    }
}