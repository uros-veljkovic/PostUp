package com.urkeev14.myapplication.feature.posts

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class PostsRefresherWorker(
    context: Context,
    params: WorkerParameters
) : Worker(context, params) {
    override fun doWork(): Result {
        TODO("Not yet implemented")
    }
}
