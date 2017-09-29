<?php

// Power
function power($power_id, $power_desc) {
  if(strcmp($power_desc,"")!=0) {
    print "\t<owl:NamedIndividual rdf:about=\"http://www.semanticweb.org/daniel/ontologies/2016/3/dcontology#".$power_id."\">\n";
    print "\t\t<rdf:type rdf:resource=\"http://www.semanticweb.org/daniel/ontologies/2016/3/dcontology#Power\"/>\n";
    print "\t\t<description>".$power_desc."</description>\n";
    print "\t</owl:NamedIndividual>\n";
  }
}

// City
function city($city_id, $city_name) {
  if(strcmp($city_name,"")!=0) {
    print "\t<owl:NamedIndividual rdf:about=\"http://www.semanticweb.org/daniel/ontologies/2016/3/dcontology#".$city_id."\">\n";
    print "\t\t<rdf:type rdf:resource=\"http://www.semanticweb.org/daniel/ontologies/2016/3/dcontology#City\"/>\n";
    if(strcmp($city_id,"amnesty_bay")==0) {
      print "\t\t<hasRealLocation rdf:resource=\"http://www.semanticweb.org/daniel/ontologies/2016/3/dcontology#maine\"/>\n";
    } else if(strcmp($city_id,"apokolips")==0) {
      print "\t\t<hasRealLocation rdf:resource=\"http://www.semanticweb.org/daniel/ontologies/2016/3/dcontology#earth_center\"/>\n";
    } else if(strcmp($city_id,"central_city")==0) {
      print "\t\t<hasRealLocation rdf:resource=\"http://www.semanticweb.org/daniel/ontologies/2016/3/dcontology#missouri\"/>\n";
    } else if(strcmp($city_id,"coast_city")==0) {
      print "\t\t<hasRealLocation rdf:resource=\"http://www.semanticweb.org/daniel/ontologies/2016/3/dcontology#california\"/>\n";
    } else if(strcmp($city_id,"detroit")==0) {
      print "\t\t<hasRealLocation rdf:resource=\"http://www.semanticweb.org/daniel/ontologies/2016/3/dcontology#michigan\"/>\n";
    } else if(strcmp($city_id,"gorilla_city")==0) {
      print "\t\t<hasRealLocation rdf:resource=\"http://www.semanticweb.org/daniel/ontologies/2016/3/dcontology#africa\"/>\n";
    } else if(strcmp($city_id,"gotham")==0) {
      print "\t\t<hasRealLocation rdf:resource=\"http://www.semanticweb.org/daniel/ontologies/2016/3/dcontology#new_jersey\"/>\n";
    } else if(strcmp($city_id,"louisiana")==0) {
      print "\t\t<hasRealLocation rdf:resource=\"http://www.semanticweb.org/daniel/ontologies/2016/3/dcontology#louisiana_us\"/>\n";
    } else if(strcmp($city_id,"new_york_city")==0 || strcmp($city_id,"metropolis")==0) {
      print "\t\t<hasRealLocation rdf:resource=\"http://www.semanticweb.org/daniel/ontologies/2016/3/dcontology#new_york\"/>\n";
    } else if(strcmp($city_id,"seattle")==0) {
      print "\t\t<hasRealLocation rdf:resource=\"http://www.semanticweb.org/daniel/ontologies/2016/3/dcontology#seattle_us\"/>\n";
    } else if(strcmp($city_id,"the_watchtower")==0) {
      print "\t\t<hasRealLocation rdf:resource=\"http://www.semanticweb.org/daniel/ontologies/2016/3/dcontology#kansas\"/>\n";
    }
    print "\t\t<name>".$city_name."</name>\n";
    print "\t</owl:NamedIndividual>\n";
  }
}

// First Appearance
// $firstap_description - BATMAN: VENGEANCE OF BANE #1 (1993), need to extract the year
function first_appearance($firstap_id, $firstap_description) {
  if(strcmp($firstap_description,"")!=0) {
    $first_appearance = (preg_split("/\\) \\(|\\(|\\)/", $firstap_description, -1, PREG_SPLIT_NO_EMPTY));
    // $first_appearance[0] - name $first_appearance[1] - year
    print "\t<owl:NamedIndividual rdf:about=\"http://www.semanticweb.org/daniel/ontologies/2016/3/dcontology#".$firstap_id."\">\n";
    print "\t\t<rdf:type rdf:resource=\"http://www.semanticweb.org/daniel/ontologies/2016/3/dcontology#Comic\"/>\n";
    if(isset($first_appearance[0]) && isset($first_appearance[1])) {
      print "\t\t<description>".$first_appearance[0]."</description>\n";
      print "\t\t<year rdf:datatype=\"http://www.w3.org/2001/XMLSchema#integer\">".$first_appearance[1]."</year>\n";
    } else {
      print "\t\t<description>".$firstap_description."</description>\n";
    }
    print "\t</owl:NamedIndividual>\n";
  }
}

// Alignment
function alignment($align_id, $align_name) {
  if(strcmp($align_name,"")!=0) {
    print "\t<owl:NamedIndividual rdf:about=\"http://www.semanticweb.org/daniel/ontologies/2016/3/dcontology#".$align_id."\">\n";
    print "\t\t<rdf:type rdf:resource=\"http://www.semanticweb.org/daniel/ontologies/2016/3/dcontology#Alignment\"/>\n";
    print "\t\t<name>".$align_name."</name>\n";
    print "\t</owl:NamedIndividual>\n";
  }
}

// Character
function character($char_id, $char_alignment, $char_city, $char_thumbnail, $char_firstap, $char_powers, $char_related, $char_alterego, $char_facebook, $char_history, $char_name, $char_occupation, $char_real_name, $char_short_description) {
  print "\t<owl:NamedIndividual rdf:about=\"http://www.semanticweb.org/daniel/ontologies/2016/3/dcontology#".$char_id."\">\n";
  print "\t\t<rdf:type rdf:resource=\"http://www.semanticweb.org/daniel/ontologies/2016/3/dcontology#Character\"/>\n";
  if(strcmp($char_name,"")!=0) {
    print "\t\t<name>".$char_name."</name>\n";
  }
  if(strcmp($char_real_name,"")!=0) {
    print "\t\t<realName>".$char_real_name."</realName>\n";
  }
  if(strcmp($char_thumbnail,"")!=0) {
    print "\t\t<thumbnail>".$char_thumbnail."</thumbnail>\n";
  }
  if(strcmp($char_alterego,"")!=0) {
    print "\t\t<alterEgo>".$char_alterego."</alterEgo>\n";
  }
  if(strcmp($char_occupation,"")!=0) {
    print "\t\t<occupation>".$char_occupation."</occupation>\n";
  }
  if(strcmp($char_short_description,"")!=0) {
    print "\t\t<shortDescription>".$char_short_description."</shortDescription>\n";
  }
  if(strcmp($char_history,"")!=0) {
    print "\t\t<history>".$char_history."</history>\n";
  }
  if(strcmp($char_alignment,"")!=0) {
    print "\t\t<hasAlignment rdf:resource=\"http://www.semanticweb.org/daniel/ontologies/2016/3/dcontology#".$char_alignment."\"/>\n";
  }
  if(strcmp($char_city,"")!=0) {
    print "\t\t<hasBaseOfOperations rdf:resource=\"http://www.semanticweb.org/daniel/ontologies/2016/3/dcontology#".$char_city."\"/>\n";
  }
  if(strcmp($char_firstap,"")!=0) {
    print "\t\t<hasFirstAppearance rdf:resource=\"http://www.semanticweb.org/daniel/ontologies/2016/3/dcontology#".$char_firstap."\"/>\n";
  }
  foreach ($char_powers as $power) {
    if(strcmp($power,"")!=0) {
      print "\t\t<hasPower rdf:resource=\"http://www.semanticweb.org/daniel/ontologies/2016/3/dcontology#".$power."\"/>\n";
    }
  }
  foreach ($char_related as $related) {
    if(strcmp($related,"")!=0) {
      print "\t\t<isRelatedWith rdf:resource=\"http://www.semanticweb.org/daniel/ontologies/2016/3/dcontology#".$related."\"/>\n";
    }
  }
  if(strcmp($char_facebook,"")!=0) {
    print "\t\t<facebook>".$char_facebook."</facebook>\n";
  }
  print "\t</owl:NamedIndividual>\n";
}
?>
