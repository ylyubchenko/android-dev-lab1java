package ua.nure.liubchenko.lab1

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.work.testing.TestListenableWorkerBuilder
import androidx.work.ListenableWorker.Result
import androidx.work.WorkManager
import org.hamcrest.CoreMatchers.`is`
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import ua.nure.liubchenko.lab1.workers.SeedDatabaseWorker

@RunWith(AndroidJUnit4::class)
class SeedDatabaseWorkerTest {

    private lateinit var context: Context
    private lateinit var workManager: WorkManager

    @Before fun setup() {
        context = ApplicationProvider.getApplicationContext()
        workManager = WorkManager.getInstance(context)
    }

    @Test fun workerSucceeds() {
        // Get the ListenableWorker
        val worker = TestListenableWorkerBuilder<SeedDatabaseWorker>(context).build()

        // Start the work synchronously
        val result = worker.startWork().get()

        assertThat(result, `is`(Result.success()))
    }
}
