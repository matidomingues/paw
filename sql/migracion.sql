DROP TABLE mentionnotification;
DROP TABLE retwattnotification;
DROP TABLE followingnotification;
DROP TABLE hashtag_twatt;
DROP TABLE hashtag;
DROP TABLE twatt_twattuser;
DROP TABLE twattuser_twatt;
DROP TABLE twattuser_twattuser;
DROP TABLE twatt;
DROP TABLE twattuser;
DROP TABLE url;

CREATE TABLE url
(
  id serial NOT NULL,
  base character varying(255),
  resol character varying(255),
  CONSTRAINT url_pkey PRIMARY KEY (id )
)
WITH (
  OIDS=FALSE
);
ALTER TABLE url
  OWNER TO paw;

CREATE TABLE twattuser
(
  id serial NOT NULL,
  access bigint,
  date timestamp without time zone NOT NULL,
  description character varying(255),
  enabled boolean NOT NULL,
  name character varying(255),
  password character varying(255),
  photo oid,
  privacy boolean NOT NULL,
  secretanswer character varying(255),
  secretquestion character varying(255),
  surname character varying(255),
  username character varying(255) NOT NULL,
  CONSTRAINT twattuser_pkey PRIMARY KEY (id ),
  CONSTRAINT twattuser_username_key UNIQUE (username )
)
WITH (
  OIDS=FALSE
);
ALTER TABLE twattuser
  OWNER TO paw;


CREATE TABLE twattuser_twattuser
(
  followings_id integer NOT NULL,
  followers_id integer NOT NULL,
  CONSTRAINT twattuser_twattuser_pkey PRIMARY KEY (followings_id , followers_id ),
  CONSTRAINT fk432297533f6881c9 FOREIGN KEY (followings_id)
      REFERENCES twattuser (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk432297538aa320d6 FOREIGN KEY (followers_id)
      REFERENCES twattuser (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE twattuser_twattuser
  OWNER TO paw;


CREATE TABLE twatt
(
  id serial NOT NULL,
  deleted boolean NOT NULL,
  message character varying(255) NOT NULL,
  "timestamp" timestamp without time zone NOT NULL,
  creator_id integer,
  original_id integer,
  dtype character varying(31) NOT NULL,
  CONSTRAINT twatt_pkey PRIMARY KEY (id ),
  CONSTRAINT fk4d7491e4c90795f FOREIGN KEY (creator_id)
      REFERENCES twattuser (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk4d7491eb0d5baa4 FOREIGN KEY (original_id)
      REFERENCES twatt (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE twatt
  OWNER TO paw;


CREATE TABLE twattuser_twatt
(
  twattuser_id integer NOT NULL,
  favourites_id integer NOT NULL,
  CONSTRAINT twattuser_twatt_pkey PRIMARY KEY (twattuser_id , favourites_id ),
  CONSTRAINT fkb64e2b684d194ceb FOREIGN KEY (favourites_id)
      REFERENCES twatt (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fkb64e2b687540fa62 FOREIGN KEY (twattuser_id)
      REFERENCES twattuser (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE twattuser_twatt
  OWNER TO paw;

CREATE TABLE hashtag
(
  id serial NOT NULL,
  tagname character varying(255) NOT NULL,
  firsttwatt_id integer,
  CONSTRAINT hashtag_pkey PRIMARY KEY (id ),
  CONSTRAINT fk8ccc53ac8b65ac7 FOREIGN KEY (firsttwatt_id)
      REFERENCES twatt (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT hashtag_tagname_key UNIQUE (tagname )
)
WITH (
  OIDS=FALSE
);
ALTER TABLE hashtag
  OWNER TO paw;

CREATE TABLE hashtag_twatt
(
  hashtags_id integer NOT NULL,
  twatts_id integer NOT NULL,
  CONSTRAINT fk9aa58ecb9a159300 FOREIGN KEY (twatts_id)
      REFERENCES twatt (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk9aa58ecbb2deeb2a FOREIGN KEY (hashtags_id)
      REFERENCES hashtag (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT hashtag_twatt_twatts_id_key UNIQUE (twatts_id )
)
WITH (
  OIDS=FALSE
);
ALTER TABLE hashtag_twatt
  OWNER TO paw;


CREATE TABLE retwattnotification
(
  id serial NOT NULL,
  retwatt_id integer,
  CONSTRAINT retwattnotification_pkey PRIMARY KEY (id ),
  CONSTRAINT fk7b2faf5678afe9b7 FOREIGN KEY (retwatt_id)
      REFERENCES twatt (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE retwattnotification
  OWNER TO paw;

CREATE TABLE mentionnotification
(
  id serial NOT NULL,
  twatt_id integer,
  CONSTRAINT mentionnotification_pkey PRIMARY KEY (id ),
  CONSTRAINT fkbc9f33d59ecd3817 FOREIGN KEY (twatt_id)
      REFERENCES twatt (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE mentionnotification
  OWNER TO paw;

CREATE TABLE followingnotification
(
  id serial NOT NULL,
  follower_id integer,
  CONSTRAINT followingnotification_pkey PRIMARY KEY (id ),
  CONSTRAINT fk7329cdfc59d77c2d FOREIGN KEY (follower_id)
      REFERENCES twattuser (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE followingnotification
  OWNER TO paw;
