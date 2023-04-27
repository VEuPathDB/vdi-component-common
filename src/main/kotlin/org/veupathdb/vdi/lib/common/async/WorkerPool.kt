package org.veupathdb.vdi.lib.common.async

import org.slf4j.LoggerFactory
import kotlin.time.Duration.Companion.milliseconds
import kotlinx.coroutines.CoroutineScope
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

  fun start(ctx: CoroutineScope) {
    log.trace("start(ctx={}}", ctx)

    log.info("starting worker pool {} with queue size {} and worker count {}", name, jobQueueSize, workerCount)

    repeat(workerCount) {
      ctx.launch {
        while (!shutdown.isTriggered()) {
          val job = queue.tryReceive()
            .getOrNull()

          if (job == null) {
            delay(100.milliseconds)
          } else {
            log.debug("received ")
            job()
          }
        }

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

