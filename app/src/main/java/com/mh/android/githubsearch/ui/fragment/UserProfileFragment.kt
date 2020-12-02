package com.mh.android.githubsearch.ui.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.mh.android.githubsearch.GithubSearchApplication
import com.mh.android.githubsearch.R
import com.mh.android.githubsearch.model.ApiResult
import com.mh.android.githubsearch.model.User
import com.squareup.picasso.Picasso
import javax.inject.Inject

class UserProfileFragment : Fragment() {

    companion object {
        private const val ARG_USER_LOGIN_NAME = "user_login_name"
        fun newInstance(userLoginName: String) = UserProfileFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_USER_LOGIN_NAME, userLoginName)
            }
        }
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var viewModel: UserProfileViewModel

    lateinit var tvUserLogin: TextView
    lateinit var tvUserBio: TextView
    lateinit var tvUserFullName: TextView
    lateinit var ivAvatar: ImageView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.user_profile_fragment, container, false)
        tvUserLogin = root.findViewById(R.id.tVuserName)
        tvUserBio = root.findViewById(R.id.tvBio)
        tvUserFullName = root.findViewById(R.id.tvFullName)
        ivAvatar = root.findViewById(R.id.imageView)
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        configureDagger()
        viewModel = viewModelFactory.create(UserProfileViewModel::class.java)
        loadData()
    }

    private fun loadData() {
        arguments?.getString(ARG_USER_LOGIN_NAME)?.let { userLoginName ->
            viewModel.getUserProfile(userLoginName).observe(viewLifecycleOwner, { apiResult ->
                when (apiResult) {
                    is ApiResult.Success -> {
                        Log.d("MUB", "Success result ${apiResult.data}")
                        apiResult.data?.let {
                            updateUi(it)
                        }
                    }
                    is ApiResult.Error -> {
                        Toast.makeText(context,apiResult.error,Toast.LENGTH_LONG).show()
                        Log.d("MUB", "Error response error msg:${apiResult.error}")}
                    else -> Log.d("MUB", "Unknown error")
                }
            })
        }
    }

    private fun updateUi(user: User) {
        tvUserLogin.text = "Login: ${user.login}"
        tvUserBio.text = user.bio?.let { "Bio: $it" } ?: "Bio: No data available"
        tvUserFullName.text = "Full Name: ${user.name}"
        user.avatarUrl?.let {
            Picasso.with(ivAvatar.context).load(it).placeholder(R.drawable.ic_launcher_background).fit().into(ivAvatar)
        }
    }

    /**
     * Method to init dagger
     */
    private fun configureDagger() {
        (activity?.applicationContext as GithubSearchApplication).getAppComponent().inject(this)
    }
}