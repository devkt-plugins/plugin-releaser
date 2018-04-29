import java.io.File

File(".")
    .listFiles()
    .orEmpty()
    .filterNotNull()
    .filter { it.isDirectory }
    .filter { it.name.endsWith("-devkt") }
    .forEach { it.deleteRecursively() }
