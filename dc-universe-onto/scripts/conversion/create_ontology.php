<?php
require 'aux_static_ontology_content.php';
require 'aux_create_ontology_individuals.php';
require 'aux_character.php';
error_reporting(E_ERROR | E_PARSE); # Please Mark me at the end. Thanks.

$hash_powers = $hash_cities = $hash_firstaps = $hash_alignments = $hash_characters = array();
$characters = array();

// $dcchars = simplexml_load_file("../../data/dc_characters_db.xml");
// $argv[1] - name of XML source to convert
$dcchars = simplexml_load_file($argv[1]);
$dccharacters = $dcchars->xpath("//character");

// Parse XML database
foreach ($dccharacters as $char) {
  $id = (string)$char[@id];
  $name = (string)$char->name;
  $thumbnail = (string)$char->thumbnail[@src];
  $real_name = (string)$char->{'real-name'};
  $short_description = (string)$char->{'short-description'};
  $history = (string)$char->{'history'};

  // City
  $city_id = (string)$char->{'base-of-operations'}[@id];
  // Gotham and Gotham City Particular case
  $res = preg_match("/gotham/i", $city_id, $match);
  if($res==1){ $city_id = "gotham"; }
  if(isset($city_id)) {
    $city = (string)$char->{'base-of-operations'};
    if(!array_key_exists($city_id, $hash_cities)) {
      $hash_cities[$city_id] = $city;
    }
  }

  // First Appearance
  $firstap_id = (string)$char->{'first-appearance'}[@id];
  // Get rid of "(" and ")"
  $firstap_id = str_replace("(","",$firstap_id);
  $firstap_id = str_replace(")","",$firstap_id);
  $firstap_id = str_replace("#","",$firstap_id);

  $firstap = (string)$char->{'first-appearance'};
  if(!array_key_exists($firstap_id, $hash_firstaps)) {
    $hash_firstaps[$firstap_id] = $firstap;
  }

  // Powers
  $powers = array();
  $char_powers = $dcchars->xpath("//character[@id = '".$id."']//power");
  foreach ($char_powers as $p) {
    $pow_id = (string)$p[@id];
    $pow_desc = (string)$p;
    $powers[] = $pow_id;

    if(!array_key_exists($pow_id, $hash_powers)) {
      $hash_powers[$pow_id] = $pow_desc;
    }
  }

  // Related Characters
  $related_ids = array();
  $char_related = $dcchars->xpath("//character[@id = '".$id."']//related");
  foreach ($char_related as $rel) {
    $rid = (string)$rel[@id];
    $related_ids[] = $rid;
  }

  // Alignment
  $alignment_id = (string)$char->alignment[@id];
  if(isset($alignment_id)) {
    $alignment = (string)$char->alignment;
    if(!array_key_exists($alignment_id, $hash_alignments)) {
      $hash_alignments[$alignment_id] = $alignment;
    }
  }

  $occupation = (string)$char->occupation;
  $alterego = (string)$char->{'alter-ego'};
  $facebook = (string)$char->facebook;

  $c = new Character($id, $alignment_id, $city_id, $thumbnail, $firstap_id, $powers, $related_ids, $alterego, $facebook, $history, $name, $occupation, $real_name, $short_description);

  $characters[$id] = $c;
}

// Now run through all captured content and create ontology
ontology_header();

// $hash_powers = $hash_cities = $hash_firstaps = $hash_alignments = $hash_characters
foreach ($hash_powers as $key => $value) {
  power($key,$value);
}
foreach ($hash_cities as $key => $value) {
  city($key,$value);
}
foreach ($hash_firstaps as $key => $value) {
  first_appearance($key,$value);
}
foreach ($hash_alignments as $key => $value) {
  alignment($key,$value);
}

// Relationship degree algorithm
foreach ($characters as $c) {
  // Calculating relationship degrees for all characters related
  // with $c
  for($i=0; $i < count($c->related_ids); $i++) {
    $ckey = $c->related_ids[$i];
    foreach($characters[$ckey]->relations_class as $ckey_related => $degree) {
      if(array_key_exists($ckey_related,$c->relations_class)) {
        $c->relations_class[$ckey]++;
      }
    }
  }
}

for($n_tries=0; $n_tries<20; $n_tries++) {
  foreach ($characters as $c) {
    if(isset($c) && $c->id!="") {
      if(strcmp($c->city_id,"")==0) {
        // Oh no D: No City! Try to give it the city of the character with higher relationship degree
        arsort($c->relations_class);
        foreach($c->relations_class as $ckey_related => $degree) {
          if(strcmp($characters[$ckey_related]->city_id,"")!=0) {
            $c->city_id = $characters[$ckey_related]->city_id;
            break;
          }
        }
      }
    }
  }
}

// Characters
foreach ($characters as $c) {
  if(isset($c) && $c->id!="") {
    // print "[$c->name], [$c->city_id]\n";
    character($c->id, $c->alignment_id, $c->city_id, $c->thumbnail, $c->first_ap_id, $c->powers_ids, $c->related_ids, $c->alterego, $c->facebook, $c->history, $c->name, $c->occupation, $c->real_name, $c->short_description);
  }
}

// The end
ontology_close();

?>
