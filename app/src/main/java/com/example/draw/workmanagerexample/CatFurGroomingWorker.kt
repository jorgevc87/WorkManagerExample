package com.example.draw.workmanagerexample

import android.content.Context
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters

class CatFurGroomingWorker(context: Context, workerParameters: WorkerParameters) : Worker(context, workerParameters) {

    override fun doWork(): Result {
        val catAgentId = inputData.getString(INPUT_DATA_CAT_AGENT_ID)

        Thread.sleep(6000L)

        val outputData = Data.Builder().putString(OUTPUT_DATA_CAT_AGENT_ID, catAgentId).build()

        return Result.success(outputData)
    }

    companion object {
        const val INPUT_DATA_CAT_AGENT_ID = "CATFURGROOMING_INPUT_DATA_CAT_AGENT_ID"
        const val OUTPUT_DATA_CAT_AGENT_ID = "CATFURGROOMING_OUTPUT_DATA_CAT_AGENT_ID"
    }
}