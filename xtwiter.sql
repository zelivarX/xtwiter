CREATE TABLE `users` (
	`id` INT PRIMARY KEY AUTO_INCREMENT,
	`first_name` VARCHAR(20) NOT NULL,
	`last_name` VARCHAR(20) NOT NULL,
	`acc` VARCHAR(20) NOT NULL UNIQUE,
	`pass` VARCHAR(20) NOT NULL
	
);

CREATE TABLE `tweets` (
	`id` INT PRIMARY KEY AUTO_INCREMENT,
	`poster_id` INT NOT NULL,
	`retweets` int NOT NULL DEFAULT 0,
	`content` TEXT NOT NULL,
	`send_on` DATETIME NOT NULL,
	FOREIGN KEY(`poster_id`) REFERENCES `users`(`id`)
		on delete cascade on update cascade
);

CREATE TABLE `retweets` (
  	`user_who_retweeted` INT NOT NULL,
  	`tweet_id` INT NOT NULL,
	`retweeted_on` DATETIME NOT NULL,
	UNIQUE(`user_who_retweeted`, `tweet_id`),
  	FOREIGN KEY (`user_who_retweeted`) REFERENCES `users`(`id`)
		on delete cascade on update cascade,
  	FOREIGN KEY (`tweet_id`) REFERENCES `tweets`(`id`)
		on delete cascade on update cascade
);

CREATE TABLE `followers` (
	`follower_id` INT NOT NULL,
	`followed_id` INT NOT NULL,
	UNIQUE(`follower_id`, `followed_id`),
	FOREIGN KEY(`follower_id`) REFERENCES `users`(`id`)
		on delete cascade on update cascade,
	FOREIGN KEY(`followed_id`) REFERENCES `users`(`id`)
		on delete cascade on update cascade
);

CREATE TABLE `notifications` (
	`to_id` INT NOT NULL,
	`from_id` INT NOT NULL,
	`content` TEXT NOT NULL,
	`send_on` DATETIME NOT NULL,
	`SEEN` BIT NOT NULL DEFAULT 0,
	FOREIGN KEY(`to_id`) REFERENCES `users`(`id`)
		on delete cascade on update cascade,
	FOREIGN KEY(`from_id`) REFERENCES `users`(`id`)
		on delete cascade on update cascade
);
