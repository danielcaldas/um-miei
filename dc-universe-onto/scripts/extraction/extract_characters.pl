#!/usr/bin/env perl
use warnings;
use strict;
use utf8;
use lib 'lib';
use Data::Dumper;
use DCUniverse::Characters;

print "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
print "<dc-characters>\n";
while(<>) {
  my $dcc = DCUniverse::Characters->new($_);
  print $dcc->as_xml();
}
print "</dc-characters>\n";
