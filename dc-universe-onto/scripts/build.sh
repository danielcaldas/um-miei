#!/bin/bash

# 1. Extracting characters from DC web site
printf "Extracting information from www.dccomics.com...  "
perl extraction/extract_characters.pl ../res/character_ids.txt > tmp-db.xml
printf "[OK]\n"

# 2. Using generated previous XML to create Ontology
printf "Creating OWL Ontology...  "
php conversion/create_ontology.php tmp-db.xml > tmp-onto.owl
printf "[OK]\n"

# 3. Migrate characters in Ontology to MongoDB database (local or remote depending on passed parameters)
printf "Extracting knowledge from ontology and migrating to dcdb MongoDB database...\n"
node migration/migrate.js tmp-onto.owl ../sparql/characters.rq characters $1 $2
node migration/migrate.js tmp-onto.owl ../sparql/powers.rq powers $1 $2
node migration/migrate.js tmp-onto.owl ../sparql/connections_weights.rq weights $1 $2
node migration/migrate.js tmp-onto.owl ../sparql/connections.rq connections $1 $2

printf "[OK]\n"

mv tmp-onto.owl new-ontology.owl
mv new-ontology.owl ../
