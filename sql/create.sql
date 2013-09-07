DROP TABLE IF EXISTS hashtag_tweet;
DROP TABLE IF EXISTS hashtag;
DROP TABLE IF EXISTS tweet;
DROP TABLE IF EXISTS twat_user;

CREATE TABLE twat_user (
	id SERIAL PRIMARY KEY NOT NULL,
	name VARCHAR(50) NOT NULL,
	username VARCHAR(50) NOT NULL UNIQUE,
	passphrase VARCHAR(50) NOT NULL,
	surname VARCHAR(50) NOT NULL,
	description VARCHAR(256),
	enabled BOOLEAN NOT NULL,
	create_time timestamp DEFAULT current_timestamp
);

CREATE TABLE tweet (
	id SERIAL PRIMARY KEY NOT NULL,
	user_id INTEGER NOT NULL,
	message VARCHAR(141) NOT NULL,
	time_stamp TIMESTAMP NOT NULL current_timestamp,
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
	CONSTRAINT PK_HT_T PRIMARY KEY (hashtag_id, tweet_id)
);
