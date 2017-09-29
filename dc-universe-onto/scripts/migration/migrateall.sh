#!/bin/bash

node migrate_characters.js ../tmp-onto.owl ../../sparql/characters.rq characters $1 $2
node migrate.js ../tmp-onto.owl ../../sparql/powers.rq powers $1 $2
node migrate.js ../tmp-onto.owl ../../sparql/connections.rq connections $1 $2
node migrate.js ../tmp-onto.owl ../../sparql/connections_weights.rq weights $1 $2
