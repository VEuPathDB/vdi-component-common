package org.veupathdb.vdi.lib.common.async

import org.slf4j.LoggerFactory
import kotlin.time.Duration.Companion.milliseconds
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel

class WorkerPool(
  private val name: String,
  private val jobQueueSize: Int,
  private val workerCount: Int = 5,
) {
  private val log = LoggerFactory.getLogger(javaClass)

  private val shutdown = ShutdownSignal()
  private val queue    = Channel<Job>(jobQueueSize)
  private val count    = CountdownLatch(workerCount)
  private var jobs     = 0uL

  @OptIn(ExperimentalCoroutinesApi::class)
  fun start() {
    log.info("starting worker pool {} with queue size {} and worker count {}", name, jobQueueSize, workerCount)

    var i = 0
    runBlocking {
      repeat(workerCount) {
        val j = ++i
        launch {
          log.debug("worker pool {} starting worker #{}", name, j)

          while (!shutdown.isTriggered()) {
            if (!queue.isEmpty) {
              log.debug("worker pool {} executing job {}", name, ++jobs)
              val job = queue.receive()

              try {
                job()
              } catch (e: Throwable) {
                log.error("job $jobs failed with exception:", e)
              }

            } else {
              delay(100.milliseconds)
            }
          }

          log.debug("worker pool {} shutting down worker #{}", name, j)
          count.decrement()
        }
      }
    }
  }

  suspend fun submit(job: Job) {
    queue.send(job)
  }

  suspend fun stop() {
    shutdown.trigger()
    count.await()
  }
}

