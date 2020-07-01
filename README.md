## Example of using React + PostGraphile + AgGrid

* Create a user `react`

```
createuser --interactive react
```

* Create database `react` with owner user `react`

```
createdb -O react react
```

* Login to database and create table `jsFrameworks`

```
CREATE TABLE public."jsFrameworks"
(
    name text
)
WITH (
    OIDS = TRUE
);

ALTER TABLE public."jsFrameworks"
    OWNER to react;
```

* Fill table `jsFrameworks` with some data

```
INSERT INTO public."jsFrameworks" (
name) VALUES (
'react'::text)
 returning oid;
INSERT INTO public."jsFrameworks" (
name) VALUES (
'angular'::text)
 returning oid;
INSERT INTO public."jsFrameworks" (
name) VALUES (
'vue'::text)
 returning oid;
```

* Check you have data

```
react=# select * from "jsFrameworks";
  name   
---------
 react
 angular
 vue
(3 rows)
```

* Run script `runpostgrphile.sh` which starts watching PostgreSQL

```
./runpostgrphile.sh
```

* Check browser on page `http://localhost:5000/graphiql`

* Run this React application

```
# yarn start
```
