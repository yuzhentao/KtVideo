package com.yuzhentao.ktvideo.ui.fragment

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yuzhentao.ktvideo.R

const val SEARCH_TAG = "SearchFragment"

class SearchFragment : DialogFragment() {

    lateinit var rootView: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_search, container, false)
        return rootView
    }

}