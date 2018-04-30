import java.io.File

val newVersion = "v1.4.1"

File(".")
		.absoluteFile
		.parentFile
		.parentFile
		.parentFile
		.listFiles()
		.orEmpty()
		.asSequence()
		.filterNotNull()
		.map { it.resolve("build.gradle.kts") }
		.filter { it.exists() }
		.map { it to it.readText() }
		.map { (f, s) ->
			//language=RegExp
			f to s.replace(Regex("val\\s+version\\s+=\\s+\"[^\"]+\""), "val version = \"$newVersion\"")
		}
		.forEach { (file, string) -> file.writeText(string) }
