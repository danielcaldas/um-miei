<?php

function ontology_header() {
  print "<?xml version='1.0'?>\n";
  print "<rdf:RDF xmlns='http://www.semanticweb.org/daniel/ontologies/2016/3/dcontology#'
  xml:base='http://www.semanticweb.org/daniel/ontologies/2016/3/dcontology'
  xmlns:rdf='http://www.w3.org/1999/02/22-rdf-syntax-ns#'
  xmlns:owl='http://www.w3.org/2002/07/owl#'
  xmlns:xml='http://www.w3.org/XML/1998/namespace'
  xmlns:dcontology='http://www.semanticweb.org/daniel/ontologies/2016/3/dcontology#'
  xmlns:xsd='http://www.w3.org/2001/XMLSchema#'
  xmlns:rdfs='http://www.w3.org/2000/01/rdf-schema#'>\n";
  print "<owl:Ontology rdf:about='http://www.semanticweb.org/daniel/ontologies/2016/3/dcontology'/>\n";

  print "<!--\n";
  print "///////////////////////////////////////////////////////////////////////////////////////\n";
  print "//\n";
  print "// Object Properties\n";
  print "//\n";
  print "///////////////////////////////////////////////////////////////////////////////////////\n";
  print "-->\n";

  print "<!-- http://www.semanticweb.org/daniel/ontologies/2016/3/dcontology#hasAlignment -->\n";

  print "<owl:ObjectProperty rdf:about='http://www.semanticweb.org/daniel/ontologies/2016/3/dcontology#hasAlignment'>\n";
  print "<owl:inverseOf rdf:resource='http://www.semanticweb.org/daniel/ontologies/2016/3/dcontology#isAlignmentOf'/>\n";
  print "</owl:ObjectProperty>\n";


  print "<!-- http://www.semanticweb.org/daniel/ontologies/2016/3/dcontology#hasBaseOfOperations -->\n";

  print "<owl:ObjectProperty rdf:about='http://www.semanticweb.org/daniel/ontologies/2016/3/dcontology#hasBaseOfOperations'>\n";
  print "<owl:inverseOf rdf:resource='http://www.semanticweb.org/daniel/ontologies/2016/3/dcontology#isBaseOfOperations'/>\n";
  print "</owl:ObjectProperty>\n";



  print "<!-- http://www.semanticweb.org/daniel/ontologies/2016/3/dcontology#hasFirstAppearance -->\n";

  print "<owl:ObjectProperty rdf:about='http://www.semanticweb.org/daniel/ontologies/2016/3/dcontology#hasFirstAppearance'>\n";
  print "<owl:inverseOf rdf:resource='http://www.semanticweb.org/daniel/ontologies/2016/3/dcontology#isFirstAppearanceOfChar'/>\n";
  print "</owl:ObjectProperty>\n";



  print "<!-- http://www.semanticweb.org/daniel/ontologies/2016/3/dcontology#hasPower -->\n";

  print "<owl:ObjectProperty rdf:about='http://www.semanticweb.org/daniel/ontologies/2016/3/dcontology#hasPower'>\n";
  print "<owl:inverseOf rdf:resource='http://www.semanticweb.org/daniel/ontologies/2016/3/dcontology#isPowerOf'/>\n";
  print "</owl:ObjectProperty>\n";



  print "<!-- http://www.semanticweb.org/daniel/ontologies/2016/3/dcontology#hasRealLocation -->\n";

  print "<owl:ObjectProperty rdf:about='http://www.semanticweb.org/daniel/ontologies/2016/3/dcontology#hasRealLocation'>\n";
  print "<owl:inverseOf rdf:resource='http://www.semanticweb.org/daniel/ontologies/2016/3/dcontology#hasDcCity'/>\n";
  print "</owl:ObjectProperty>\n";



  print "<!-- http://www.semanticweb.org/daniel/ontologies/2016/3/dcontology#isAlignmentOf -->\n";

  print "<owl:ObjectProperty rdf:about='http://www.semanticweb.org/daniel/ontologies/2016/3/dcontology#isAlignmentOf'/>\n";



  print "<!-- http://www.semanticweb.org/daniel/ontologies/2016/3/dcontology#isBaseOfOperations -->\n";

  print "<owl:ObjectProperty rdf:about='http://www.semanticweb.org/daniel/ontologies/2016/3/dcontology#isBaseOfOperations'/>\n";



  print "<!-- http://www.semanticweb.org/daniel/ontologies/2016/3/dcontology#isFirstAppearanceOfChar -->\n";

  print "<owl:ObjectProperty rdf:about='http://www.semanticweb.org/daniel/ontologies/2016/3/dcontology#isFirstAppearanceOfChar'/>\n";



  print "<!-- http://www.semanticweb.org/daniel/ontologies/2016/3/dcontology#isPowerOf -->\n";

  print "<owl:ObjectProperty rdf:about='http://www.semanticweb.org/daniel/ontologies/2016/3/dcontology#isPowerOf'/>\n";



  print "<!-- http://www.semanticweb.org/daniel/ontologies/2016/3/dcontology#isRelatedWith -->\n";

  print "<owl:ObjectProperty rdf:about='http://www.semanticweb.org/daniel/ontologies/2016/3/dcontology#isRelatedWith'>\n";
  print "<owl:inverseOf rdf:resource='http://www.semanticweb.org/daniel/ontologies/2016/3/dcontology#isRelatedWith'/>\n";
  print "</owl:ObjectProperty>\n";

  print "<!--///////////////////////////////////////////////////////////////////////////////////////\n";
  print "//\n";
  print "// Data properties\n";
  print "//\n";
  print "///////////////////////////////////////////////////////////////////////////////////////\n";
  print "-->\n";
  print "<!-- http://www.semanticweb.org/daniel/ontologies/2016/3/dcontology#alterEgo -->\n";
  print "<owl:DatatypeProperty rdf:about='http://www.semanticweb.org/daniel/ontologies/2016/3/dcontology#alterEgo'/>\n";


  print "<!-- http://www.semanticweb.org/daniel/ontologies/2016/3/dcontology#description -->\n";
  print "<owl:DatatypeProperty rdf:about='http://www.semanticweb.org/daniel/ontologies/2016/3/dcontology#description'/>\n";

  print "<!-- http://www.semanticweb.org/daniel/ontologies/2016/3/dcontology#facebook -->\n";
  print "<owl:DatatypeProperty rdf:about='http://www.semanticweb.org/daniel/ontologies/2016/3/dcontology#facebook'/>\n";

  print "<!-- http://www.semanticweb.org/daniel/ontologies/2016/3/dcontology#thumbnail -->\n";
  print "<owl:DatatypeProperty rdf:about='http://www.semanticweb.org/daniel/ontologies/2016/3/dcontology#thumbnail'/>\n";

  print "<!-- http://www.semanticweb.org/daniel/ontologies/2016/3/dcontology#history -->\n";
  print "<owl:DatatypeProperty rdf:about='http://www.semanticweb.org/daniel/ontologies/2016/3/dcontology#history'/>\n";

  print "<!-- http://www.semanticweb.org/daniel/ontologies/2016/3/dcontology#latitude -->\n";
  print "<owl:DatatypeProperty rdf:about='http://www.semanticweb.org/daniel/ontologies/2016/3/dcontology#latitude'/>\n";

  print "<!-- http://www.semanticweb.org/daniel/ontologies/2016/3/dcontology#longitude -->\n";
  print "<owl:DatatypeProperty rdf:about='http://www.semanticweb.org/daniel/ontologies/2016/3/dcontology#longitude'/>\n";

  print "<!-- http://www.semanticweb.org/daniel/ontologies/2016/3/dcontology#name -->\n";
  print "<owl:DatatypeProperty rdf:about='http://www.semanticweb.org/daniel/ontologies/2016/3/dcontology#name'/>\n";

  print "<!-- http://www.semanticweb.org/daniel/ontologies/2016/3/dcontology#occupation -->\n";
  print "<owl:DatatypeProperty rdf:about='http://www.semanticweb.org/daniel/ontologies/2016/3/dcontology#occupation'/>\n";

  print "<!-- http://www.semanticweb.org/daniel/ontologies/2016/3/dcontology#realName -->\n";
  print "<owl:DatatypeProperty rdf:about='http://www.semanticweb.org/daniel/ontologies/2016/3/dcontology#realName'/>\n";

  print "<!-- http://www.semanticweb.org/daniel/ontologies/2016/3/dcontology#shortDescription -->\n";
  print "<owl:DatatypeProperty rdf:about='http://www.semanticweb.org/daniel/ontologies/2016/3/dcontology#shortDescription'/>\n";

  print "<!-- http://www.semanticweb.org/daniel/ontologies/2016/3/dcontology#title -->\n";
  print "<owl:DatatypeProperty rdf:about='http://www.semanticweb.org/daniel/ontologies/2016/3/dcontology#title'/>\n";

  print "<!-- http://www.semanticweb.org/daniel/ontologies/2016/3/dcontology#year -->\n";
  print "<owl:DatatypeProperty rdf:about='http://www.semanticweb.org/daniel/ontologies/2016/3/dcontology#year'/>\n";


  print "<!--\n";
  print "///////////////////////////////////////////////////////////////////////////////////////\n";
  print "//\n";
  print "// Classes\n";
  print "//\n";
  print "///////////////////////////////////////////////////////////////////////////////////////\n";
  print "-->\n";

  print "<!-- http://www.semanticweb.org/daniel/ontologies/2016/3/dcontology#Alignment -->\n";

  print "<owl:Class rdf:about='http://www.semanticweb.org/daniel/ontologies/2016/3/dcontology#Alignment'/>\n";

  print "<!-- http://www.semanticweb.org/daniel/ontologies/2016/3/dcontology#Character -->\n";

  print "<owl:Class rdf:about='http://www.semanticweb.org/daniel/ontologies/2016/3/dcontology#Character'/>\n";

  print "<!-- http://www.semanticweb.org/daniel/ontologies/2016/3/dcontology#City -->\n";

  print "<owl:Class rdf:about='http://www.semanticweb.org/daniel/ontologies/2016/3/dcontology#City'/>\n";


  print "<!-- http://www.semanticweb.org/daniel/ontologies/2016/3/dcontology#Comic -->\n";

  print "<owl:Class rdf:about='http://www.semanticweb.org/daniel/ontologies/2016/3/dcontology#Comic'/>\n";


  print "<!-- http://www.semanticweb.org/daniel/ontologies/2016/3/dcontology#Power -->\n";

  print "<owl:Class rdf:about='http://www.semanticweb.org/daniel/ontologies/2016/3/dcontology#Power'/>\n";


  print "<!-- http://www.semanticweb.org/daniel/ontologies/2016/3/dcontology#GeoLocation -->\n";

  print "<owl:Class rdf:about='http://www.semanticweb.org/daniel/ontologies/2016/3/dcontology#GeoLocation'/>\n";
}

function ontology_close() {
  print "<!--\n";
  print "///////////////////////////////////////////////////////////////////////////////////////\n";
  print "//\n";
  print "// GeoLocations\n";
  print "//\n";
  print "///////////////////////////////////////////////////////////////////////////////////////\n";
  print "-->\n";

  print "<owl:NamedIndividual rdf:about=\"http://www.semanticweb.org/daniel/ontologies/2016/3/dcontology#africa\">\n";
  print "<rdf:type rdf:resource=\"http://www.semanticweb.org/daniel/ontologies/2016/3/dcontology#GeoLocation\"/>\n";
  print "<latitude rdf:datatype=\"http://www.w3.org/2001/XMLSchema#decimal\">-4.0383</latitude>\n";
  print "<longitude rdf:datatype=\"http://www.w3.org/2001/XMLSchema#decimal\">21.7587</longitude>\n";
  print "<name>Africa</name>\n";
  print "</owl:NamedIndividual>\n\n";


  print "<owl:NamedIndividual rdf:about=\"http://www.semanticweb.org/daniel/ontologies/2016/3/dcontology#california\">\n";
  print "<rdf:type rdf:resource=\"http://www.semanticweb.org/daniel/ontologies/2016/3/dcontology#GeoLocation\"/>\n";
  print "<latitude rdf:datatype=\"http://www.w3.org/2001/XMLSchema#decimal\">36.7783</latitude>\n";
  print "<longitude rdf:datatype=\"http://www.w3.org/2001/XMLSchema#decimal\">-119.4179</longitude>\n";
  print "<name>California, US State</name>\n";
  print "</owl:NamedIndividual>\n\n";


  print "<owl:NamedIndividual rdf:about=\"http://www.semanticweb.org/daniel/ontologies/2016/3/dcontology#kansas\">\n";
  print "<rdf:type rdf:resource=\"http://www.semanticweb.org/daniel/ontologies/2016/3/dcontology#GeoLocation\"/>\n";
  print "<latitude rdf:datatype=\"http://www.w3.org/2001/XMLSchema#decimal\">39.0119</latitude>\n";
  print "<longitude rdf:datatype=\"http://www.w3.org/2001/XMLSchema#decimal\">-98.4842</longitude>\n";
  print "<name>Kansas, US State</name>\n";
  print "</owl:NamedIndividual>\n\n";


  print "<owl:NamedIndividual rdf:about=\"http://www.semanticweb.org/daniel/ontologies/2016/3/dcontology#louisiana_us\">\n";
  print "<rdf:type rdf:resource=\"http://www.semanticweb.org/daniel/ontologies/2016/3/dcontology#GeoLocation\"/>\n";
  print "<latitude rdf:datatype=\"http://www.w3.org/2001/XMLSchema#decimal\">30.9843</latitude>\n";
  print "<longitude rdf:datatype=\"http://www.w3.org/2001/XMLSchema#decimal\">-91.9623</longitude>\n";
  print "<name>Louisiana, US State</name>\n";
  print "</owl:NamedIndividual>\n\n";


  print "<owl:NamedIndividual rdf:about=\"http://www.semanticweb.org/daniel/ontologies/2016/3/dcontology#maine\">\n";
  print "<rdf:type rdf:resource=\"http://www.semanticweb.org/daniel/ontologies/2016/3/dcontology#GeoLocation\"/>\n";
  print "<latitude rdf:datatype=\"http://www.w3.org/2001/XMLSchema#decimal\">45.2538</latitude>\n";
  print "<longitude rdf:datatype=\"http://www.w3.org/2001/XMLSchema#decimal\">-69.4455</longitude>\n";
  print "<name>Maine, US State</name>\n";
  print "</owl:NamedIndividual>\n\n";


  print "<owl:NamedIndividual rdf:about=\"http://www.semanticweb.org/daniel/ontologies/2016/3/dcontology#michigan\">\n";
  print "<rdf:type rdf:resource=\"http://www.semanticweb.org/daniel/ontologies/2016/3/dcontology#GeoLocation\"/>\n";
  print "<latitude rdf:datatype=\"http://www.w3.org/2001/XMLSchema#decimal\">44.3148</latitude>\n";
  print "<longitude rdf:datatype=\"http://www.w3.org/2001/XMLSchema#decimal\">-85.6024</longitude>\n";
  print "<name>Detroit, City in Michigan</name>\n";
  print "</owl:NamedIndividual>\n\n";


  print "<owl:NamedIndividual rdf:about=\"http://www.semanticweb.org/daniel/ontologies/2016/3/dcontology#missouri\">\n";
  print "<rdf:type rdf:resource=\"http://www.semanticweb.org/daniel/ontologies/2016/3/dcontology#GeoLocation\"/>\n";
  print "<latitude rdf:datatype=\"http://www.w3.org/2001/XMLSchema#decimal\">37.9643</latitude>\n";
  print "<longitude rdf:datatype=\"http://www.w3.org/2001/XMLSchema#decimal\">-91.8318</longitude>\n";
  print "<name>Missouri, US State</name>\n";
  print "</owl:NamedIndividual>\n\n";


  print "<owl:NamedIndividual rdf:about=\"http://www.semanticweb.org/daniel/ontologies/2016/3/dcontology#new_jersey\">\n";
  print "<rdf:type rdf:resource=\"http://www.semanticweb.org/daniel/ontologies/2016/3/dcontology#GeoLocation\"/>\n";
  print "<latitude rdf:datatype=\"http://www.w3.org/2001/XMLSchema#decimal\">40.0583</latitude>\n";
  print "<longitude rdf:datatype=\"http://www.w3.org/2001/XMLSchema#decimal\">-74.4057</longitude>\n";
  print "<name>New Jersey</name>\n";
  print "</owl:NamedIndividual>\n\n";


  print "<owl:NamedIndividual rdf:about=\"http://www.semanticweb.org/daniel/ontologies/2016/3/dcontology#new_york\">\n";
  print "<rdf:type rdf:resource=\"http://www.semanticweb.org/daniel/ontologies/2016/3/dcontology#GeoLocation\"/>\n";
  print "<latitude rdf:datatype=\"http://www.w3.org/2001/XMLSchema#decimal\">40.7128</latitude>\n";
  print "<longitude rdf:datatype=\"http://www.w3.org/2001/XMLSchema#decimal\">-74.0059</longitude>\n";
  print "<name>New York, US State</name>\n";
  print "</owl:NamedIndividual>\n\n";


  print "<owl:NamedIndividual rdf:about=\"http://www.semanticweb.org/daniel/ontologies/2016/3/dcontology#seattle_us\">\n";
  print "<rdf:type rdf:resource=\"http://www.semanticweb.org/daniel/ontologies/2016/3/dcontology#GeoLocation\"/>\n";
  print "<latitude rdf:datatype=\"http://www.w3.org/2001/XMLSchema#decimal\">47.6062</latitude>\n";
  print "<longitude rdf:datatype=\"http://www.w3.org/2001/XMLSchema#decimal\">-122.3321</longitude>\n";
  print "<name>Seattle, City in Washington</name>\n";
  print "</owl:NamedIndividual>\n\n";

  // Close mark
  print "</rdf:RDF>\n";
}

?>
