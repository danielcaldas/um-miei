package DCUniverse::Characters;

use 5.006;
use strict;
use warnings;
use utf8::all;
use LWP::Simple;
use HTML::TreeBuilder;
use HTML::TreeBuilder::XPath;
use HTML::Element;
use String::Util qw(trim);
use Data::Dumper;
use JSON;

=head1 NAME

DCUniverse::Characters - Simple extractor of information
from DC Comics characters at http://www.dccomics.com/

=head1 VERSION

Version 0.01

=cut

our $VERSION = '0.01';


=head1 SYNOPSIS

This module provides a way of accessing DC characters information, as its exposed
in the official http://www.dccomics.com/ web site. This is not an API, this module provides
the information by parsing the dccomics web pages, and organizing it so it can be used by other
programs and applications.

Code snippet.

use DCUniverse::Characters;

my $foo = DCUniverse::Characters->new();
...

=head1 EXPORT

A list of functions that can be exported.  You can delete this section
if you don't export anything, such as for a purely object-oriented module.

=head1 SUBROUTINES/METHODS

=head2 new
Getting a dc character by passing its id. The id
its the final string of the URL of the web page of the character.
(e.g http://www.dccomics.com/characters/the-flash the id is the-flash)
=cut

sub new {
  my ($self, $char_id) = @_;
  my $class = shift;

  my ($site_content, $url, $i, $tree);
  # A hash to return as response with the character data
  my %dc_char;

  $tree = HTML::TreeBuilder->new();
  $url = "http://www.dccomics.com/characters/".$char_id;

  $site_content = get($url);
  $tree->parse($site_content);

  $site_content = get($url);
  $tree = HTML::TreeBuilder::XPath->new;
  $tree->parse($site_content);

  # id
  if($url =~ /(\w(-)?)+$/) {
    my $id = $&;
    $dc_char{id} = trim($id);
  }

  # name
  my $name = $tree->findvalue('/html/body//div[@class="views-field views-field-title"][1]');
  if(defined $name) {
    $dc_char{name} = trim($name);
  }

  # thumbnail
  my $thumbnail = $tree->findvalue('/html/body//div[@class="thumb-trigger"]/img/@src');
  if(defined $thumbnail) {
    $dc_char{thumbnail}{src} = trim($thumbnail);
    $dc_char{thumbnail}{width} = "160";
    $dc_char{thumbnail}{height} = "160";
  }

  # short description
  my $short_desc = $tree->findvalue('//meta[@property="og:description"]/@content');
  if(defined $short_desc) {
    $short_desc =~ s/&/and/g;
    $dc_char{short_description} = trim($short_desc);
  }

  # history
  my $history = $tree->findvalue('/html/body//div[@class="field field-name-body field-type-text-with-summary field-label-hidden"]/div[@class="field-items"]/div[@class="field-item even"]/p');
  $history =~ s/\Q$short_desc//;
  if(defined $history) {
    $history =~ s/&/and/g;
    $dc_char{history} = trim($history);
  }

  # character facts
  # alterEgo (may be alias/aka instead)
  my $alter_ego = $tree->findvalue('/html/body//div[@class="char-facts"]//div[@class="views-field views-field-field-character-alias-alter-ego"]');
  my @altego_parts = split /:/, $alter_ego;
  if(defined $altego_parts[1]) {
    $dc_char{alter_ego} = trim($altego_parts[1]);
  }

  # base of operations, occupation, alignment, real name and first appearance
  my %some_facts = ("Base of Operations" => "", "Real Name" => "", "First Appearance" => "",
  "Alignment" => "", "Occupation" => "", "Alias/AKA" => "", "Alias/Alter Ego" => "");

  my %some_caps_facts = ("BASE_OF_OPERATIONS" => "", "REAL_NAME" => "", "FIRST_APPEARANCE" => "",
  "ALIGNMENT" => "", "OCCUPATION" => "", "ALIAS/AKA" => "", "ALIAS/ALTER_EGO" => "");

  my %some_facts_xml = ("BASE_OF_OPERATIONS" => "base-of-operations", "REAL_NAME" => "real-name", "FIRST_APPEARANCE" => "first-appearance", "ALIGNMENT" => "alignment", "OCCUPATION" => "occupation", "ALIAS/AKA" => "alter-ego", "ALIAS/ALTER_EGO" => "alter-ego");

  my $info = $tree->findvalue('/html/body//div[@class="char-facts"]//div[@class="field-item even"]');

  for my $fact (keys %some_facts) {
    if($info =~ $fact) {
      my $caps_fact = "|".uc($fact).";";
      $caps_fact =~ s/ /_/g;
      $info =~ s/$fact/$caps_fact/g;
    }
  }

  my @real_facts = split /\|/, $info;

  for my $real_fact (@real_facts) {
    my @pair = split /;/, $real_fact;
    if(defined $pair[0] && exists $some_caps_facts{$pair[0]}) {
      my $trimmed_string = trim($pair[1]);
      if(defined $trimmed_string && $pair[0] eq "FIRST_APPEARANCE") {
        if($trimmed_string eq $pair[1]) {
          $some_caps_facts{$pair[0]} = trim($pair[1]);
          # Key conversion
          my $first_ap = lc($pair[1]);
          $first_ap =~ s/ /_/g;
          my $tmp_hash_key = $some_facts_xml{$pair[0]};
          $tmp_hash_key =~ s/-/_/g;
          $tmp_hash_key =~ s/'//g;
          if(!exists $dc_char{$tmp_hash_key}) {
            $dc_char{$tmp_hash_key} = $pair[1];
          }
        }
      } else {
        $some_caps_facts{$pair[0]} = trim($pair[1]);
        my $tmp_hash_key = $some_facts_xml{$pair[0]};
        $tmp_hash_key =~ s/-/_/g;
        $tmp_hash_key =~ s/'//g;
        if(!exists $dc_char{$tmp_hash_key}) {
          $dc_char{$tmp_hash_key} = $pair[1];
        }
      }
    }
  }

  # facebook
  my $ch_facebook = $tree->findvalue('/html/body//div[@class="views-field views-field-field-facebook"]/div[@class="field-content"]');
  if(defined $ch_facebook && $ch_facebook =~ "http") {
    $dc_char{facebook} = $ch_facebook;
  }

  # powers
  my $ch_powers = $tree->findvalue('/html/body//div[@class="views-field views-field-field-character-powers"]/div[@class="field-content"]');
  if(defined $ch_powers) {
    my @powers = split /,/, $ch_powers;
    if(@powers) {
      for my $p_id (@powers) {
        $p_id = trim($p_id);
        my $p_desc = $p_id;
        $p_id =~ s/ /_/g;
        $dc_char{powers}{$p_id} = $p_desc;
      }
    }
    else {
      # the powers may appear inside anchors (e.g. black-canary page)
      $ch_powers = $tree->findvalue('//div[@class="views-field views-field-field-char-powers"]');
      if(defined $ch_powers) {
        my @powers_text = split /:/, $ch_powers;
        if(defined $powers_text[1]) {
          @powers_text = split /,/, $powers_text[1];

          for my $p_id (@powers_text) {
            $p_id = trim($p_id);
            my $p_desc = $p_id;
            $p_id =~ s/ /_/g;
            $dc_char{powers}{$p_id} = $p_desc;
          }
        }
      }
    }
  }

  # related characters
  my $ch_related = $tree->findvalue('//div[@class="field field-name-field-thumbnail field-type-image field-label-hidden"]//div[@class="field-item even"]//a/@href');

  my @ch_related_thumbnails = $tree->findnodes('//div[@class="field field-name-field-thumbnail field-type-image field-label-hidden"]//div[@class="field-item even"]//a/img/@src');

  if(defined $ch_related) {
    my %related_info;
    my @related = split /\/characters\//, $ch_related;
    $i=1;
    for my $src (@ch_related_thumbnails) {
      if(! ($related[$i] =~ "earth") )  {
        $related_info{$related[$i]} = $src->getValue;
      }
      $i++;
    }

    for my $k (keys %related_info) {

      if(defined $related_info{$k}) {
        $dc_char{related_characters}{$k}{thumbnail}{src} = $related_info{$k};
        $dc_char{related_characters}{$k}{thumbnail}{width} = "36";
        $dc_char{related_characters}{$k}{thumbnail}{height} = "36";
      }
    }
  }
  # Store the character
  my $character = { dc_char => \%dc_char };

  return bless $character, $class;
}

=head2 as_xml
Return an instantiated dc character in XML format.
=cut

sub as_xml {
  my ($self) = @_;

  my $new_character = "";

  $new_character .= "<character id=\"".$self->{dc_char}{id}."\">\n";
  if(defined $self->{dc_char}{name}) {
    $new_character .= "\t<name>".$self->{dc_char}{name}."</name>\n";
  }
  if(defined $self->{dc_char}{real_name}) {
    $new_character .= "\t<real-name>".$self->{dc_char}{real_name}."</real-name>\n";
  }
  if(defined $self->{dc_char}{thumbnail}) {
    $new_character .= "\t<thumbnail src=\"".$self->{dc_char}{thumbnail}{src}."\" width='160' height='160'/>\n";
  }
  if(defined $self->{dc_char}{short_description}) {
    $new_character .= "\t<short-description>".$self->{dc_char}{short_description}."</short-description>\n";
  }
  if(defined $self->{dc_char}{history}) {
    $new_character .= "\t<history>".$self->{dc_char}{history}."</history>\n";
  }
  if(defined $self->{dc_char}{alter_ego}) {
    $new_character .= "\t<alter-ego>".$self->{dc_char}{alter_ego}."</alter-ego>\n";
  }
  if(defined $self->{dc_char}{base_of_operations}) {
    my $tmp_city = lc $self->{dc_char}{base_of_operations};
    $tmp_city =~ s/ /_/g;
    $new_character .= "\t<base-of-operations id=\"$tmp_city\">".$self->{dc_char}{base_of_operations}."</base-of-operations>\n";
  }
  if(defined $self->{dc_char}{first_appearance}) {
    my $tmp_firstap = lc $self->{dc_char}{first_appearance};
    $tmp_firstap =~ s/ /_/g;
    $new_character .= "\t<first-appearance id=\"$tmp_firstap\">".$self->{dc_char}{first_appearance}."</first-appearance>\n";
  }
  if(defined $self->{dc_char}{alignment} && !($self->{dc_char}{alignment} eq "")) {
    my $tmp_alignm = lc $self->{dc_char}{alignment};
    $tmp_alignm =~ s/ /_/g;
    $new_character .= "\t<alignment id=\"$tmp_alignm\">".$self->{dc_char}{alignment}."</alignment>\n";
  }
  if(defined $self->{dc_char}{occupation}) {
    $new_character .= "\t<occupation>".$self->{dc_char}{occupation}."</occupation>\n";
  }
  if(defined $self->{dc_char}{facebook}) {
    $new_character .= "\t<facebook>".$self->{dc_char}{facebook}."</facebook>\n";
  }
  if(defined $self->{dc_char}{powers}) {
    $new_character .= "\t<powers>\n";
    for my $k (keys $self->{dc_char}{powers}) {
      my $k_lc = lc $k;
      $new_character .= "\t\t<power id=\"$k_lc\">".$self->{dc_char}{powers}{$k}."</power>\n";
    }
    $new_character .= "\t</powers>\n";
  }
  if(defined $self->{dc_char}{related_characters}) {
    $new_character .= "\t<related-characters>\n";
    for my $k (keys $self->{dc_char}{related_characters}) {
      $new_character .= "\t\t<related id=\"$k\">\n";
      if(defined $self->{dc_char}{related_characters}{$k}{thumbnail}) {
        $new_character .= "\t\t\t<thumbnail src=\"".$self->{dc_char}{related_characters}{$k}{thumbnail}{src}."\" width='36' height='36'/>\n";
      }
      $new_character .= "\t\t</related>\n";
    }
    $new_character .= "\t</related-characters>\n";
  }
  # Close the character
  $new_character .= "</character>\n";

  return $new_character;
}

=head2 as_json
Return an instantiated dc character in JSON format.
=cut

sub as_json {
  my($self) = @_;
  my $dc_char_json = encode_json $self->{dc_char};
  return $dc_char_json;
}

=head1 AUTHOR

Daniel Caldas, C<< <danielcaldas at sapo.pt> >>

=head1 BUGS

Please report any bugs or feature requests to C<bug-dcuniverse-characters at rt.cpan.org>, or through
the web interface at L<http://rt.cpan.org/NoAuth/ReportBug.html?Queue=DCUniverse-Characters>.  I will be notified, and then you'll
automatically be notified of progress on your bug as I make changes.




=head1 SUPPORT

You can find documentation for this module with the perldoc command.

perldoc DCUniverse::Characters


You can also look for information at:

=over 4

=item * RT: CPAN's request tracker (report bugs here)

L<http://rt.cpan.org/NoAuth/Bugs.html?Dist=DCUniverse-Characters>

=item * AnnoCPAN: Annotated CPAN documentation

L<http://annocpan.org/dist/DCUniverse-Characters>

=item * CPAN Ratings

L<http://cpanratings.perl.org/d/DCUniverse-Characters>

=item * Search CPAN

L<http://search.cpan.org/dist/DCUniverse-Characters/>

=back


=head1 ACKNOWLEDGEMENTS


=head1 LICENSE AND COPYRIGHT

Copyright 2016 Daniel Caldas.

This program is free software; you can redistribute it and/or modify it
under the terms of the the Artistic License (2.0). You may obtain a
copy of the full license at:

L<http://www.perlfoundation.org/artistic_license_2_0>

Any use, modification, and distribution of the Standard or Modified
Versions is governed by this Artistic License. By using, modifying or
distributing the Package, you accept this license. Do not use, modify,
or distribute the Package, if you do not accept this license.

If your Modified Version has been derived from a Modified Version made
by someone other than you, you are nevertheless required to ensure that
your Modified Version complies with the requirements of this license.

This license does not grant you the right to use any trademark, service
mark, tradename, or logo of the Copyright Holder.

This license includes the non-exclusive, worldwide, free-of-charge
patent license to make, have made, use, offer to sell, sell, import and
otherwise transfer the Package with respect to any patent claims
licensable by the Copyright Holder that are necessarily infringed by the
Package. If you institute patent litigation (including a cross-claim or
counterclaim) against any party alleging that the Package constitutes
direct or contributory patent infringement, then this Artistic License
to you shall terminate on the date that such litigation is filed.

Disclaimer of Warranty: THE PACKAGE IS PROVIDED BY THE COPYRIGHT HOLDER
AND CONTRIBUTORS "AS IS' AND WITHOUT ANY EXPRESS OR IMPLIED WARRANTIES.
THE IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
PURPOSE, OR NON-INFRINGEMENT ARE DISCLAIMED TO THE EXTENT PERMITTED BY
YOUR LOCAL LAW. UNLESS REQUIRED BY LAW, NO COPYRIGHT HOLDER OR
CONTRIBUTOR WILL BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, OR
CONSEQUENTIAL DAMAGES ARISING IN ANY WAY OUT OF THE USE OF THE PACKAGE,
EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.


=cut

1; # End of DCUniverse::Characters
