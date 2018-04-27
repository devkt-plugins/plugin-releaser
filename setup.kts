import java.io.File

fun official(language: String) = "https://github.com/devkt-plugins/$language-devkt.git"
fun github(org: String, repo: String) = "https://github.com/$org/$repo.git"
val target = File("target").apply { if (!exists()) mkdir() }

listOf(
    official("julia"),
    official("json"),
    official("solidity"),
    official("emmylua"),
    official("la-clojure"),
    github("covscript", "covscript-devkt")
)
    .asSequence()
    .onEach { repository ->
      ProcessBuilder()
          .command("git", "clone", repository)
          .directory(null)
          .inheritIO()
          .start()
          .waitFor()
    }
    .map { it.split('/') }
    .mapNotNull { it.lastOrNull() }
    .map { File(it) }
    .onEach { project ->
      ProcessBuilder()
          .command("bash", "gradlew", "jar")
          .directory(project)
          .inheritIO()
          .start()
          .waitFor()
    }
    .map { it.resolve("build") }
    .map { it.resolve("libs") }
    .flatMap {
      it
          .listFiles()
          .orEmpty()
          .filterNotNull()
          .asSequence()
    }
    .filter { it.extension == "jar" }
    .forEach {
      it.copyTo(target.resolve(it.name), overwrite = true)
    }
