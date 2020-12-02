package com.mh.android.githubsearch.utils

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

/**
 * Created by mubarak.hussain on 30/11/20.
 */
object RxSearchObservable {
    fun fromView(searchView: EditText): Observable<String> {
        val subject = PublishSubject.create<String>()
        searchView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                subject.onNext(s.toString())
            }

        })
        return subject
    }
}