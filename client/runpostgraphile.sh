#!/usr/bin/env sh

yarn postgraphile --append-plugins @graphile-contrib/pg-simplify-inflector -c postgres:///react \
  --watch --dynamic-json --allow-explain --enhance-graphiql
