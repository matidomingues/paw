DROP TABLE IF EXISTS hashtag_tweet;
DROP TABLE IF EXISTS hashtag;
DROP TABLE IF EXISTS tweet;
DROP TABLE IF EXISTS twat_user;
DROP TABLE IF EXISTS short_url;

CREATE TABLE twat_user (
	id SERIAL PRIMARY KEY NOT NULL,
	name VARCHAR(50) NOT NULL,
	username VARCHAR(50) NOT NULL UNIQUE,
	password VARCHAR(50) NOT NULL,
	surname VARCHAR(50) NOT NULL,
	description VARCHAR(256),
	enabled BOOLEAN NOT NULL,
	created_time timestamp DEFAULT current_timestamp,
	secret_question VARCHAR(100) NOT NULL,
	secret_answer VARCHAR(100) NOT NULL
);

CREATE TABLE tweet (
	id SERIAL PRIMARY KEY NOT NULL,
	user_id INTEGER NOT NULL,
	message VARCHAR(141) NOT NULL,
	created_time TIMESTAMP DEFAULT current_timestamp,
	deleted BOOLEAN NOT NULL,
	CONSTRAINT FK_USR FOREIGN KEY(user_id) REFERENCES twat_user(id)
);

CREATE TABLE hashtag (
	id SERIAL PRIMARY KEY NOT NULL,
	first_tweet INTEGER NOT NULL,
	tag_name VARCHAR(140) NOT NULL,
	description VARCHAR(256) NOT NULL,
	CONSTRAINT FK_TWEET FOREIGN KEY(first_tweet) REFERENCES tweet(id)
);

CREATE TABLE hashtag_tweet (
	hashtag_id INTEGER NOT NULL,
	tweet_id INTEGER NOT NULL,
	CONSTRAINT PK_HT_T PRIMARY KEY (hashtag_id, tweet_id),
	CONSTRAINT FK_HT_T FOREIGN KEY(hashtag_id) REFERENCES hashtag(id),
	CONSTRAINT FK_HT_T2 FOREIGN KEY(tweet_id) REFERENCES tweet(id)
);

CREATE TABLE short_url (
	base VARCHAR(10) PRIMARY KEY NOT NULL,
	resol VARCHAR(100) NOT NULL
);
