#!/usr/bin/perl

use strict;
use warnings FATAL => 'all';

use File::Spec;

my $newVersion = 'v1.5-alpha';

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
				print OUT s/(val\s+version\s+=\s+)"[^\"]+"/$1"$newVersion"/gr;
		}

		close(IN);
		close(OUT);
}
