#!/usr/bin/env perl
use warnings;
use strict;
use utf8;
use lib 'lib';
use Data::Dumper;
use DCUniverse::Characters;

my $dcc = DCUniverse::Characters->new('batman');

my $flash = $dcc->getCharacter('batman');

print Dumper($dcc->{dc_char}); # OK

print $dcc->as_xml(); # OK

print $dcc->as_json(); # OK
