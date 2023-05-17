package org.veupathdb.vdi.lib.common.util

import java.io.Closeable

interface CloseableIterator<T>: Iterator<T>, Closeable