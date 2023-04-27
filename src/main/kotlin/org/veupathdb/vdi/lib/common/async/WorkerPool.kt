package org.veupathdb.vdi.lib.common.async

import org.slf4j.LoggerFactory
import kotlin.time.Duration.Companion.milliseconds
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
  fun start(ctx: CoroutineScope) {
    log.trace("start(ctx={}}", ctx)

    log.info("starting worker pool {} with queue size {} and worker count {}", name, jobQueueSize, workerCount)

    var i = 0
    repeat(workerCount) {
      val j = ++i
      ctx.launch {
        log.debug("worker pool {} starting worker #{}", name, j)

        while (!shutdown.isTriggered()) {
          if (!queue.isEmpty) {
            log.debug("worker pool {} executing job {}", name, ++jobs)
            queue.receive()()
          } else {
            log.trace("worker pool {} is sleeping for 100ms", name)
            delay(100.milliseconds)
          }
        }

        log.debug("worker pool {} shutting down worker #{}", name, j)
        count.decrement()
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

