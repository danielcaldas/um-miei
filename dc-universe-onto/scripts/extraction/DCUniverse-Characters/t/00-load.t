#!perl -T
use 5.006;
use strict;
use warnings;
use Test::More;

plan tests => 1;

BEGIN {
    use_ok( 'DCUniverse::Characters' ) || print "Bail out!\n";
}

diag( "Testing DCUniverse::Characters $DCUniverse::Characters::VERSION, Perl $], $^X" );
