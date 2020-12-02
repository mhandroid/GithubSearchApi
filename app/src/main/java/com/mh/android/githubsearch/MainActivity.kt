package com.mh.android.githubsearch

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.mh.android.githubsearch.model.User
import com.mh.android.githubsearch.ui.fragment.UserListFragment
import com.mh.android.githubsearch.utils.RxSearchObservable.fromView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity(), UserListFragment.ListItemClickListener {

    private lateinit var eTSearch: EditText

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    lateinit var userListFragment: UserListFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        configureDagger()
        initializeView()
        userListFragment = UserListFragment.newInstance(1,listType = UserListFragment.ListType.SEARCH)
        supportFragmentManager.beginTransaction().add(R.id.container, userListFragment, "userlist").commit()

        compositeDisposable.add(fromView(eTSearch).debounce(500, TimeUnit.MILLISECONDS).filter {
            Log.d("MUB", "user typing.... $it")
            !it.isBlank()
        }.distinctUntilChanged().observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe {
            Log.d("MUB", "start for search $it")
            userListFragment.showLoader(true)
            startSearching(it)
        })
    }

    /**
     * Method to initialize view
     */
    private fun initializeView() {
        setSupportActionBar(findViewById(R.id.toolbar))
        eTSearch = findViewById(R.id.eTSearch)
        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show()
        }
    }

    /**
     * Method to init dagger
     */
    private fun configureDagger() {
        (application as GithubSearchApplication).getAppComponent().inject(this)
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
        super.onDestroy()
    }

    private fun startSearching(string: String) {
        Log.d("MUB", "search string $string")
        userListFragment.startSearching(string)
    }

    override fun onListItemClick(user: User) {
        startActivity(Intent(this, UserProfileActivity::class.java).apply {
            this.putExtra("USER_DATA", user)
        })
    }
}