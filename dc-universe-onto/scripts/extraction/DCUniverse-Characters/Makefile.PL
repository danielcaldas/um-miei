use 5.006;
use strict;
use warnings;
use ExtUtils::MakeMaker;

WriteMakefile(
    NAME             => 'DCUniverse::Characters',
    AUTHOR           => q{Daniel Caldas <danielcaldas@sapo.pt>},
    VERSION_FROM     => 'lib/DCUniverse/Characters.pm',
    ABSTRACT_FROM    => 'lib/DCUniverse/Characters.pm',
    LICENSE          => 'artistic_2',
    PL_FILES         => {},
    MIN_PERL_VERSION => 5.006,
    CONFIGURE_REQUIRES => {
        'ExtUtils::MakeMaker' => 0,
    },
    BUILD_REQUIRES => {
        'Test::More' => 0,
    },
    PREREQ_PM => {
        "LWP::Simple" => "0",
        "HTML::TreeBuilder" => "0",
        "HTML::TreeBuilder::XPath" => "0",
        "HTML::Element" => "0",
        "String::Util qw(trim)" => "0",
        "JSON" => "0",
    },
    dist  => { COMPRESS => 'gzip -9f', SUFFIX => 'gz', },
    clean => { FILES => 'DCUniverse-Characters-*' },
);
