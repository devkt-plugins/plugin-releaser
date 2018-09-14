import java.io.File

val newVersion = "1.2.70"

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
					.replace(Regex("kotlin\\(\"jvm\"\\) version \"[^\"]+\""), "kotlin(\"jvm\") version \"$newVersion\"")
		}
		.forEach { (file, string) -> file.writeText(string) }
