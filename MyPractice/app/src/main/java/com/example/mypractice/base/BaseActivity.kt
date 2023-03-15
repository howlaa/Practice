package com.example.mypractice.base

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<T : ViewBinding> : AppCompatActivity() {
    companion object {
        private const val TAG = "MainActivity"
    }
    private lateinit var _binding: T
    protected val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = getViewBinding()
        setContentView(_binding.root)

        initViews()
        initEvents()

    }

    protected abstract fun getViewBinding() : T

    open fun initViews() {}
    open fun initEvents() {}

    /**
     * Base toast
     */
    fun toast(str : String?) {
        Toast.makeText(this, str, Toast.LENGTH_LONG).show()
    }

}