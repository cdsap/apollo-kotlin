package benchmark

import com.apollographql.apollo3.ast.parseAsGQLDocument
import okio.buffer
import okio.source
import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.Fork
import org.openjdk.jmh.annotations.Measurement
import org.openjdk.jmh.annotations.Scope
import org.openjdk.jmh.annotations.Setup
import org.openjdk.jmh.annotations.State
import org.openjdk.jmh.annotations.Warmup
import java.io.File
import java.util.concurrent.TimeUnit

@State(Scope.Benchmark)
@Fork(1)
@Warmup(iterations = 1)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
open class Benchmark {
  private var testFiles: List<File> = emptyList()

  @Setup
  fun setUp() {
    testFiles = File(".").resolve("../apollo-compiler/src/test/graphql")
        .walk()
        .filter { it.extension in setOf("graphql", "graphqls") }
        .toList()
  }

  @Benchmark
  fun antlrTest(): Double {
    return testFiles.sumOf {
      it.source().buffer().parseAsGQLDocument(useAntlr = true).getOrThrow().definitions.size.toDouble()
    }
  }

  @Benchmark
  fun parserTest(): Double {
    return testFiles.sumOf {
      it.source().buffer().parseAsGQLDocument(useAntlr = false).getOrThrow().definitions.size.toDouble()
    }
  }
}