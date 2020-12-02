package com.mh.android.githubsearch

import android.view.View

/**
 * Created by mubarak.hussain on 01/12/20.
 */

/**
 * If it's true then view has visibility status VISIBLE else GONE
 */
var View.isVisible: Boolean
    get() {
        return this.visibility == View.VISIBLE
    }
    set(value) {
        if (value) {
            this.visibility = View.VISIBLE
        } else {
            this.visibility = View.GONE
        }
    }