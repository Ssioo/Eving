package com.whoissio.eving.adapters

import android.content.Intent
import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.whoissio.eving.R
import com.whoissio.eving.databinding.ItemExerciseRecordBinding
import com.whoissio.eving.models.Exercise
import com.whoissio.eving.utils.Helpers.toPx
import com.whoissio.eving.viewmodels.ExerciseRecordViewModel
import com.whoissio.eving.views.ExerciseRecordActivity
import com.whoissio.eving.views.ExerciseRecordDetailActivity

class ExerciseListAdapter(vm: ExerciseRecordViewModel): BaseRecyclerAdapter<Exercise, ExerciseRecordViewModel, ItemExerciseRecordBinding>(
    viewmodel = vm,
    layoutId = R.layout.item_exercise_record
) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<Exercise, ItemExerciseRecordBinding> = object : BaseViewHolder<Exercise, ItemExerciseRecordBinding>(
        DataBindingUtil.inflate(LayoutInflater.from(parent.context), layoutId, parent, false)
    ) {
        override fun initItem(item: Exercise) {
            super.initItem(item)
            binding.root.setOnClickListener {
                (binding.root.context as ExerciseRecordActivity).let {
                    it.startActivity(Intent(it, ExerciseRecordDetailActivity::class.java).apply {
                        putExtra("id", item.id)
                        putExtra("title", item.title)
                        putExtra("createdAt", item.createdAt)
                    })
                }
            }
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        recyclerView.addItemDecoration(MarginDecorator())
    }

    class MarginDecorator: RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            super.getItemOffsets(outRect, view, parent, state)
            outRect.bottom = 10.toPx(parent.context)
        }
    }
}