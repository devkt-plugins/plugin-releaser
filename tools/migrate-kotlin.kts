import java.io.File

val newVersion = "1.2.41"

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
			f to s.replace(Regex("val\\s+kotlinVersion\\s+=\\s+\"[^\"]+\""), "val kotlinVersion = \"$newVersion\"")
		}
		.forEach { (file, string) -> file.writeText(string) }
