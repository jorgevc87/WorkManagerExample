package com.example.draw.workmanagerexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.work.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Creating workManager Constraints
        val networkConstraints =
            Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()

        //Creating workManager id
        val catAgentId = "CatAgent1"

        //Creating workManager Resquests
        val catStretchingRequest = OneTimeWorkRequest.Builder(CatStretchingWorker::class.java)
            .setConstraints(networkConstraints)
            .setInputData(
                getCatAgentIdInputData(
                    CatStretchingWorker.INPUT_DATA_CAT_AGENT_ID,
                    catAgentId
                )
            ).build()

        val catFurGroomingRequest = OneTimeWorkRequest.Builder(CatFurGroomingWorker::class.java)
            .setConstraints(networkConstraints)
            .setInputData(
                getCatAgentIdInputData(
                    CatFurGroomingWorker.INPUT_DATA_CAT_AGENT_ID,
                    catAgentId
                )
            ).build()

        val catLitterBoxSittingRequest =
            OneTimeWorkRequest.Builder(CatLitterBoxSittingWorker::class.java)
                .setConstraints(networkConstraints)
                .setInputData(
                    getCatAgentIdInputData(
                        CatLitterBoxSittingWorker.INPUT_DATA_CAT_AGENT_ID,
                        catAgentId
                    )
                ).build()

        val catSuitUpRequest =
            OneTimeWorkRequest.Builder(CatSuitUpWorker::class.java)
                .setConstraints(networkConstraints)
                .setInputData(
                    getCatAgentIdInputData(
                        CatSuitUpWorker.INPUT_DATA_CAT_AGENT_ID,
                        catAgentId
                    )
                ).build()


        //Lauching the WorkManager and enqueing WorkRequests
        WorkManager.getInstance(this).beginWith(catStretchingRequest)
            .then(catFurGroomingRequest)
            .then(catLitterBoxSittingRequest)
            .then(catSuitUpRequest)
            .enqueue()

        //Getting responses from workers
        WorkManager.getInstance(this).getWorkInfoByIdLiveData(catStretchingRequest.id)
            .observe(this, Observer { info ->
                if (info.state.isFinished) {
                    showResult("catStretchingRequest DONE!!")
                }
            })

        WorkManager.getInstance(this).getWorkInfoByIdLiveData(catFurGroomingRequest.id)
            .observe(this, Observer { info ->
                if (info.state.isFinished) {
                    showResult("catFurGroomingRequest DONE!!")
                }
            })

        WorkManager.getInstance(this).getWorkInfoByIdLiveData(catLitterBoxSittingRequest.id)
            .observe(this, Observer { info ->
                if (info.state.isFinished) {
                    showResult("catLitterBoxSittingRequest DONE!!")
                }
            })

        WorkManager.getInstance(this).getWorkInfoByIdLiveData(catSuitUpRequest.id)
            .observe(this, Observer { info ->
                if (info.state.isFinished) {
                    showResult("catSuitUpRequest DONE!!")
                }
            })
    }

    private fun getCatAgentIdInputData(catAgentIdKey: String, catAgentIdValue: String) =
        Data.Builder().putString(catAgentIdKey, catAgentIdValue).build()

    private fun showResult(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}