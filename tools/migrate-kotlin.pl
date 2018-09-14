#!/usr/bin/perl

use strict;
use warnings FATAL => 'all';

use File::Spec;

my $newVersion = '1.2.70';

# my $pwd = '../..';
# opendir(DIR, "$pwd") or die "Cannot open $pwd\n";
# my @dirs = readdir(DIR);
# closedir(DIR);

my @dirs = (
		'covscript-devkt',
		'json-devkt',
		'julia-devkt',
		'la-clojure-devkt',
		'lua-devkt',
		'pilang-devkt',
		'plugin-template',
		'properties-devkt',
		'rust-devkt',
		'solidity-devkt',
		'toml-devkt',
		'yaml-devkt',
);

foreach my $file (@dirs) {
		my $buildFile = "../../$file/build.gradle.kts";
		print $buildFile, "\n";
	  next unless -e $buildFile;
		rename($buildFile, $buildFile.'.bak');
		open(IN, '<'.$buildFile.'.bak') or die $!;
		open(OUT, '>'.$buildFile) or die $!;

		while(<IN>) {
				s/(val\s+kotlinVersion\s+=\s+)"[^\"]+"/$1"$newVersion"/g;
				s/(kotlin\("jvm"\)\s+version\s+)"[^\"]+"/$1"$newVersion"/g;
				# s/java \{/tasks.withType<KotlinCompile> {\n\tkotlinOptions { freeCompilerArgs = listOf("-Xjvm-default=enable") }\n}\n\njava \{/g;
				# s/import/import org.jetbrains.kotlin.gradle.tasks.KotlinCompile\nimport/;
				# s/jcenter\(\)/jcenter()\n\tmaven("https:\/\/dl.bintray.com\/ice1000\/ice1000")/;
				print OUT $_;
		}

		close(IN);
		close(OUT);
}
