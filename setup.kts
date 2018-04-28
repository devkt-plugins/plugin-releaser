import java.io.File

fun lang(language: String) = "https://github.com/devkt-plugins/$language-devkt.git"
fun github(org: String, repo: String) = "https://github.com/$org/$repo.git"
val target = File("target").apply { if (!exists()) mkdir() }

listOf(
    lang("julia"),
    lang("json"),
    lang("solidity"),
    lang("emmylua"),
    lang("la-clojure"),
    lang("yaml"),
    lang("toml"),
    github("ice1000", "properties-devkt"), // TODO awaiting GitHub fix bug
    github("covscript", "covscript-devkt")
)
    .asSequence()
    .onEach { repository ->
      ProcessBuilder()
          .command("git", "clone", repository, "--depth=1")
          .directory(null)
          .inheritIO()
          .start()
          .waitFor()
    }
    .map { it.split('/') }
    .mapNotNull { it.lastOrNull() }
    .map { it.removeSuffix(".git") }
    .map { File(it) }
    .onEach { project ->
      ProcessBuilder()
          .command("bash", "gradlew", "jar", "--info", "--warning-mode=all")
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
