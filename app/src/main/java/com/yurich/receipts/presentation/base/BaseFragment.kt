package com.yurich.receipts.presentation.base

import android.os.Bundle
import android.os.Parcelable
import android.view.View
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.navigation.fragment.findNavController
import com.airbnb.epoxy.EpoxyRecyclerView
import com.airbnb.mvrx.BaseMvRxFragment
import com.airbnb.mvrx.MvRx
import com.yurich.receipts.R

abstract class BaseFragment : BaseMvRxFragment(R.layout.fragment_base_mvrx) {

    private lateinit var recyclerView: EpoxyRecyclerView
    private lateinit var title: AppCompatTextView
    private lateinit var additionalButton: AppCompatImageView
    private val epoxyController by lazy { epoxyController() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        epoxyController.onRestoreInstanceState(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = view.findViewById(R.id.recycler_view)

        title = view.findViewById<AppCompatTextView>(R.id.title).apply {
            setText(titleResId())
        }

        additionalButton = view.findViewById<AppCompatImageView>(R.id.additional_button).apply {
            setImageResource(additionalButtonImageRes())
            setOnClickListener { _ -> onAdditionalButtonClicked() }
        }

        recyclerView.setController(epoxyController)
    }

    override fun invalidate() {
        recyclerView.requestModelBuild()
    }

    @StringRes
    abstract fun titleResId(): Int

    @DrawableRes
    abstract fun additionalButtonImageRes(): Int

    abstract fun onAdditionalButtonClicked()

    abstract fun epoxyController(): MvRxEpoxyController

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        epoxyController.onSaveInstanceState(outState)
    }

    override fun onDestroyView() {
        epoxyController.cancelPendingModelBuild()
        super.onDestroyView()
    }

    protected fun navigateTo(@IdRes actionId: Int, arg: Parcelable? = null) {
        val bundle = arg?.let { Bundle().apply { putParcelable(MvRx.KEY_ARG, it) } }
        findNavController().navigate(actionId, bundle)
    }
}