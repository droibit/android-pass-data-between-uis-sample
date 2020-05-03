package com.github.droibit.sample.pass_data

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_detail.*

class DetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        backButton.setOnClickListener {
            val result = DetailResult(message = "Pass data from Detail")
            setFragmentResult(REQUEST_KEY_DETAIL, result.toBundle())
            findNavController().popBackStack()
        }
    }

    companion object {

        const val REQUEST_KEY_DETAIL = "DetailFragment"
    }
}

data class DetailResult(val message: String) {
    constructor(data: Bundle) : this(data.getString(KEY, ""))

    fun toBundle(): Bundle = bundleOf(KEY to message)

    companion object {
        const val KEY = "message"
    }
}