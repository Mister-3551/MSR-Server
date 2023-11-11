-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Gostitelj: localhost
-- Čas nastanka: 11. nov 2023 ob 16.11
-- Različica strežnika: 10.4.28-MariaDB
-- Različica PHP: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Zbirka podatkov: `msr`
--

-- --------------------------------------------------------

--
-- Struktura tabele `authorities`
--

CREATE TABLE `authorities` (
  `id` int(11) NOT NULL,
  `authority` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Odloži podatke za tabelo `authorities`
--

INSERT INTO `authorities` (`id`, `authority`) VALUES
(1, 'ROLE_USER'),
(2, 'ROLE_ADMIN');

-- --------------------------------------------------------

--
-- Struktura tabele `contact`
--

CREATE TABLE `contact` (
  `id` int(11) NOT NULL,
  `full_name` varchar(255) NOT NULL,
  `email_address` varchar(255) NOT NULL,
  `subject` text NOT NULL,
  `message` text NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Odloži podatke za tabelo `contact`
--

INSERT INTO `contact` (`id`, `full_name`, `email_address`, `subject`, `message`, `created_at`) VALUES
(1, 'fullName', 'pintargasper1@gmail.com', 'subject', '0', '2023-09-27 13:20:55'),
(2, 'Gašper Pintar', 'pintargasper1@gmail.com', 'Test', '0', '2023-09-27 13:22:49'),
(3, 'Gašper Pintar', 'pintargasper1@gmail.com', 'Test2', '0', '2023-09-27 13:23:59');

-- --------------------------------------------------------

--
-- Struktura tabele `followers`
--

CREATE TABLE `followers` (
  `id` int(11) NOT NULL,
  `follower_id_user` int(11) NOT NULL,
  `following_id_user` int(11) NOT NULL,
  `created_at` datetime NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Odloži podatke za tabelo `followers`
--

INSERT INTO `followers` (`id`, `follower_id_user`, `following_id_user`, `created_at`) VALUES
(154, 35, 34, '2023-10-11 22:04:05'),
(244, 2, 34, '2023-10-12 07:53:05'),
(268, 34, 36, '2023-10-12 08:39:54'),
(277, 34, 2, '2023-10-22 12:48:24');

-- --------------------------------------------------------

--
-- Struktura tabele `missions`
--

CREATE TABLE `missions` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `image` text NOT NULL,
  `map` varchar(255) NOT NULL,
  `description` text DEFAULT NULL,
  `price` float NOT NULL,
  `best_time` time NOT NULL,
  `deadline` time NOT NULL,
  `created_at` datetime NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Odloži podatke za tabelo `missions`
--

INSERT INTO `missions` (`id`, `name`, `image`, `map`, `description`, `price`, `best_time`, `deadline`, `created_at`) VALUES
(1, 'Training', 'mission1.jpg', 'mission1.tmx', 'Save the hostages and take out the baddies. Hostages must be saved to complete the mission', 1000, '00:00:12', '00:00:14', '2023-10-03 10:29:34'),
(2, 'On Your Own', 'mission2.jpg', 'mission2.tmx', 'Complete a series of challenging tasks and obstacles without any external assistance or support', 2000, '00:00:45', '00:00:50', '2023-10-03 10:31:28'),
(3, 'Exploration Expedition', 'mission3.jpg', 'mission3.tmx', 'Travel to unknown territories, discover ancient paths and hidden adventures.', 5000, '00:01:10', '00:01:20', '2023-10-03 10:32:25'),
(4, 'Ultimate Challenge Quest', 'mission4.jpg', 'mission4.tmx', 'Test your adventure skills through a series of difficult challenges.', 7000, '00:01:15', '00:01:22', '2023-10-03 10:33:16'),
(10, 'Mastermind\'s Puzzle Palace', 'mission5.jpg', 'mission5.tmx', 'Infiltrate a high-security fortress designed by a brilliant criminal mastermind', 10000, '00:01:10', '00:01:25', '2023-10-20 14:13:05');

-- --------------------------------------------------------

--
-- Struktura tabele `missions_completed`
--

CREATE TABLE `missions_completed` (
  `id` int(11) NOT NULL,
  `id_user` int(11) NOT NULL,
  `id_mission` int(11) NOT NULL,
  `completed` enum('0','1','2') NOT NULL,
  `created_at` datetime NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Odloži podatke za tabelo `missions_completed`
--

INSERT INTO `missions_completed` (`id`, `id_user`, `id_mission`, `completed`, `created_at`) VALUES
(1, 34, 1, '1', '2023-10-20 14:09:57'),
(2, 2, 1, '2', '2023-10-20 14:10:12'),
(3, 34, 2, '2', '2023-10-20 14:10:39'),
(4, 35, 1, '2', '2023-10-20 14:10:39'),
(5, 36, 1, '2', '2023-10-20 14:10:55');

-- --------------------------------------------------------

--
-- Struktura tabele `missions_statistics`
--

CREATE TABLE `missions_statistics` (
  `id` int(11) NOT NULL,
  `id_user` int(11) NOT NULL,
  `id_mission` int(11) NOT NULL,
  `status` enum('0','1') NOT NULL,
  `lost_lives` int(11) NOT NULL,
  `eliminated_enemies` int(11) NOT NULL,
  `used_time` time NOT NULL,
  `score` int(11) NOT NULL,
  `created_at` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Odloži podatke za tabelo `missions_statistics`
--

INSERT INTO `missions_statistics` (`id`, `id_user`, `id_mission`, `status`, `lost_lives`, `eliminated_enemies`, `used_time`, `score`, `created_at`) VALUES
(1, 34, 1, '1', 2, 13, '00:00:13', 345, '2023-10-05 09:34:25');

-- --------------------------------------------------------

--
-- Struktura tabele `news`
--

CREATE TABLE `news` (
  `id` int(11) NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `text` text DEFAULT NULL,
  `image` varchar(255) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Odloži podatke za tabelo `news`
--

INSERT INTO `news` (`id`, `title`, `text`, `image`, `created_at`) VALUES
(1, 'Mission: Training', 'The first of the missions, has arrived, where hostages and VIPs need your assistance in being freed from the clutches of a notorious group of criminals. This high-risk task must be undertaken with unwavering determination, as the successful liberation of innocent lives is expected despite facing gunfights and intense combat that will ensue. \r\n {{image-0}} \r\nThe notes indicate that the location is unknown to us, under the control of criminals responsible for a series of horrifying crimes in the area. This mission requires a thoughtful approach, as we aim to catch the criminals off guard. With the help of refined skills and tactical knowledge, you will need to navigate through complex obstacles, evade enemy fire, and effectively neutralize any form of opposition. \r\n {{image-1}}', 'news1.jpg', '2023-07-26 12:26:41'),
(2, 'Mission: On Your Own', 'The continuation of the mission reveals another operation, which presents an approach that tests the skills and adaptability of the players in a new scenario. Unlike the previous risky tactics of direct combat, it emphasizes sophistication and precision. The environment in the mission hints at part of the unexplained establishment. The players will face a crowd of enemies who have taken hostages. The mission tests their ability to stay stealthy and use creative problem solving.\r\n\r\nIn this operation, players will start in a partial fort and have to fight their way past the enemy units defending it. The mission also emphasizes the importance of flexibility and reflection. Will the players once again be able to come to the fore and prove their skills in this risky game? Only time will tell the outcome of this mission.', 'news2.jpg', '2023-07-27 21:37:54'),
(3, 'Mission: Exploration Expedition', 'The mission shows the extreme flexibility of the player who stepped into enemy territory to rescue the hostages and restore security. This mission is a turning point, as the action is transferred from the surface to the underground.\r\n\r\nIt takes place in the middle of the day, which requires extreme attention to detail and surroundings to avoid slipping into the abyss. At the same time, the enemies use advanced instruments to detect the player from a distance, so the player must skillfully evade these devices.\r\n\r\nThe mission is also not too challenging as the enemies are not prepared for any visits in the dangerous terrain where the player has to constantly monitor the surroundings and enemies and evade them.', 'news3.jpg', '2023-07-28 19:42:51'),
(4, 'Mission: Ultimate Challenge Quest', 'An extraordinary mission is underway, in which the exceptional adaptability of the player is demonstrated. The player steps into enemy territory to rescue several hostages and establish long-awaited security. In this breakthrough mission, the action involves a complex sequence of challenges, but in the wrong tactics, the player becomes surrounded by enemies from all directions.\r\n\r\nBut that\'s not the only problem he faces. Enemies have organized themselves in even greater numbers this time, which requires an even higher level of skill, as the player must deftly avoid devices that could threaten his life.\r\n\r\nIn addition, more lives are at stake as more hostages have become, which makes the task even more challenging as the player must ensure the safety of the hostages. In the midst of all this, enemies press steadily from all sides, making the task a real test of skill and courage.\r\n\r\nThe mission is a truly unique opportunity for the player to demonstrate their ultimate adaptability, skills and tenacity in one of the most challenging missions ever.', 'news4.jpg', '2023-07-29 14:30:00'),
(5, 'Mission: Mastermind\'s Puzzle Palace', 'An exciting adventure unfolds in the latest mission, where the player finds himself in a bunker designed by an elusive master. The task requires not only extreme prudence, but also ingenuity, as the player enters a mysterious complex.\r\n\r\nIn this task, the player finds himself in a constant challenge of ingenuity, as he has to make his way through complex sections. This requires the player to think carefully and solve, because the master has established a system of defense, so the approach to solving is crucial.\r\n\r\nHowever, the actor is not alone. The player\'s every move requires precision and speed as the mission tests the player\'s reflexes. Nor is the actor alone in this endeavor. The presence of hostages trapped inside the bunker affects the player\'s determination and ingenuity. The fate of the hostages depends on the player\'s ability to rescue them and overcome every obstacle that appears in his path.\r\n\r\nThe mission challenges the player\'s skill and perseverance. It\'s a chance to show off in the best light and reach the ultimate level of action in one of the most challenging missions to date.', 'news5.jpg', '2023-07-30 16:39:04');

-- --------------------------------------------------------

--
-- Struktura tabele `news_images`
--

CREATE TABLE `news_images` (
  `id` int(11) NOT NULL,
  `id_news` int(11) NOT NULL,
  `image` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Odloži podatke za tabelo `news_images`
--

INSERT INTO `news_images` (`id`, `id_news`, `image`) VALUES
(1, 1, 'news3.jpg'),
(2, 1, 'news2.jpg');

-- --------------------------------------------------------

--
-- Struktura tabele `statistics`
--

CREATE TABLE `statistics` (
  `id` int(11) NOT NULL,
  `id_user` int(11) NOT NULL,
  `rank` int(11) NOT NULL DEFAULT 1,
  `money` float NOT NULL DEFAULT 0,
  `current_xp` int(11) NOT NULL DEFAULT 0,
  `next_xp` int(11) NOT NULL DEFAULT 100,
  `play_time` int(11) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Odloži podatke za tabelo `statistics`
--

INSERT INTO `statistics` (`id`, `id_user`, `rank`, `money`, `current_xp`, `next_xp`, `play_time`) VALUES
(1, 2, 1, 0, 0, 100, 3601),
(3, 34, 1, 0, 0, 100, 120000),
(7, 35, 1, 0, 0, 100, 0),
(8, 36, 1, 0, 0, 100, 0);

-- --------------------------------------------------------

--
-- Struktura tabele `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `full_name` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  `email_address` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `image` text NOT NULL DEFAULT 'basic-image.jpg',
  `birth_date` date DEFAULT NULL,
  `country` varchar(255) DEFAULT NULL,
  `account_confirmed` text DEFAULT NULL,
  `account_locked` enum('0','1') NOT NULL DEFAULT '0',
  `unlock_date` datetime DEFAULT NULL,
  `password_reset_timer` datetime DEFAULT NULL,
  `password_change_timer` datetime DEFAULT NULL,
  `reports_number` int(11) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Odloži podatke za tabelo `users`
--

INSERT INTO `users` (`id`, `full_name`, `username`, `email_address`, `password`, `image`, `birth_date`, `country`, `account_confirmed`, `account_locked`, `unlock_date`, `password_reset_timer`, `password_change_timer`, `reports_number`) VALUES
(1, 'ADMIN', 'admin', 'admin@example.com', '$2a$12$i/ZFWhcwriGJqY.Vu.u/FefSugvW4GBvgTjTSfMd0W0n0u/V3clKu', 'basic-image.png', '2000-04-09', 'Slovenia', '1', '0', NULL, NULL, NULL, 0),
(2, 'USER', 'user', 'user@example.com', '$2a$12$NA7l.m9MF9rzYa9FTAMSluG6XeId9CqzZUETmwoG9W6.9CX/41hqG', 'basic-image.png', '1999-09-18', 'Slovenia', '1', '0', NULL, NULL, NULL, 0),
(34, 'Gašper', 'gasperpintar', 'gasper@example.com', '$2a$12$BFRS1Qos/8IThsEJxjFrVO7P2P67tYSujv87AoyaSvl7MAeJ5n09a', 'gasperpintar-image.png', '2002-07-05', 'Slovenia', '1', '0', NULL, '2023-09-28 22:06:50', '2023-09-29 21:37:48', 0),
(35, 'user', 'user2', 'user2@example.com', '$2a$12$9FqnLRY2OWUdRquAzhjfkuhkLuIzxFE6bcGbu17M4XqynOEkdnqnW', 'user2-image.png', '2000-02-03', 'Slovenia', '1', '0', NULL, NULL, NULL, 0),
(36, 'user', 'user3', 'user3@example.com', '$2a$12$BFRS1Qos/8IThsEJxjFrVO7P2P67tYSujv87AoyaSvl7MAeJ5n09a', 'basic-image.png', '2011-01-05', 'Slovenia', '1', '0', NULL, NULL, NULL, 0);

-- --------------------------------------------------------

--
-- Struktura tabele `users_authorities`
--

CREATE TABLE `users_authorities` (
  `id` int(11) NOT NULL,
  `id_user` int(11) NOT NULL,
  `id_authority` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Odloži podatke za tabelo `users_authorities`
--

INSERT INTO `users_authorities` (`id`, `id_user`, `id_authority`) VALUES
(1, 1, 2),
(3, 2, 1),
(32, 34, 2),
(33, 34, 1),
(34, 35, 1),
(35, 36, 1);

-- --------------------------------------------------------

--
-- Struktura tabele `weapons`
--

CREATE TABLE `weapons` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `image` text NOT NULL,
  `price` float NOT NULL,
  `shot_power` float NOT NULL,
  `shots_per_second` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Odloži podatke za tabelo `weapons`
--

INSERT INTO `weapons` (`id`, `name`, `image`, `price`, `shot_power`, `shots_per_second`) VALUES
(1, 'Handgun', 'handgun.png', 0, 0.5, 0.33),
(2, 'Assault Rifle', 'assault_rifle.png', 20000, 0.6, 0.52),
(3, 'Ak-47', 'ak-47.png', 50000, 0.8, 0.62),
(6, 'Uzi', 'uzi.png', 55000, 0.8, 0.66);

-- --------------------------------------------------------

--
-- Struktura tabele `weapons_statistics`
--

CREATE TABLE `weapons_statistics` (
  `id` int(11) NOT NULL,
  `id_user` int(11) NOT NULL,
  `id_weapon` int(11) NOT NULL,
  `shot_bullets` int(11) NOT NULL DEFAULT 0,
  `total_kills` int(11) NOT NULL DEFAULT 0,
  `enemy_kills` int(11) NOT NULL DEFAULT 0,
  `hostage_kills` int(11) NOT NULL DEFAULT 0,
  `vip_kills` int(11) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Odloži podatke za tabelo `weapons_statistics`
--

INSERT INTO `weapons_statistics` (`id`, `id_user`, `id_weapon`, `shot_bullets`, `total_kills`, `enemy_kills`, `hostage_kills`, `vip_kills`) VALUES
(1, 34, 1, 203, 50, 30, 19, 1),
(2, 2, 1, 10, 10, 5, 3, 2),
(3, 34, 2, 1234, 450, 387, 40, 23),
(5, 35, 1, 13, 6, 5, 1, 0),
(7, 36, 1, 9, 7, 7, 0, 0);

--
-- Indeksi zavrženih tabel
--

--
-- Indeksi tabele `authorities`
--
ALTER TABLE `authorities`
  ADD PRIMARY KEY (`id`);

--
-- Indeksi tabele `contact`
--
ALTER TABLE `contact`
  ADD PRIMARY KEY (`id`);

--
-- Indeksi tabele `followers`
--
ALTER TABLE `followers`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK_FU_follower_id_user` (`follower_id_user`),
  ADD KEY `FK_FU_following_id_user` (`following_id_user`);

--
-- Indeksi tabele `missions`
--
ALTER TABLE `missions`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `name` (`name`),
  ADD UNIQUE KEY `map` (`map`);

--
-- Indeksi tabele `missions_completed`
--
ALTER TABLE `missions_completed`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK_MC_M` (`id_mission`),
  ADD KEY `FK_MC_U` (`id_user`);

--
-- Indeksi tabele `missions_statistics`
--
ALTER TABLE `missions_statistics`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK_MS_U_id` (`id_user`),
  ADD KEY `FK_MS_M_id` (`id_mission`);

--
-- Indeksi tabele `news`
--
ALTER TABLE `news`
  ADD PRIMARY KEY (`id`);

--
-- Indeksi tabele `news_images`
--
ALTER TABLE `news_images`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK_N_id` (`id_news`);

--
-- Indeksi tabele `statistics`
--
ALTER TABLE `statistics`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `id_user` (`id_user`);

--
-- Indeksi tabele `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `users_username` (`username`),
  ADD UNIQUE KEY `users_email_address` (`email_address`);

--
-- Indeksi tabele `users_authorities`
--
ALTER TABLE `users_authorities`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK_UA_id` (`id_user`),
  ADD KEY `FK_AU_id` (`id_authority`);

--
-- Indeksi tabele `weapons`
--
ALTER TABLE `weapons`
  ADD PRIMARY KEY (`id`);

--
-- Indeksi tabele `weapons_statistics`
--
ALTER TABLE `weapons_statistics`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK_WOU_id` (`id_user`) USING BTREE,
  ADD KEY `FK_WOW_id` (`id_weapon`) USING BTREE;

--
-- AUTO_INCREMENT zavrženih tabel
--

--
-- AUTO_INCREMENT tabele `authorities`
--
ALTER TABLE `authorities`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT tabele `contact`
--
ALTER TABLE `contact`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT tabele `followers`
--
ALTER TABLE `followers`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=278;

--
-- AUTO_INCREMENT tabele `missions`
--
ALTER TABLE `missions`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT tabele `missions_completed`
--
ALTER TABLE `missions_completed`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT tabele `missions_statistics`
--
ALTER TABLE `missions_statistics`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT tabele `news`
--
ALTER TABLE `news`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT tabele `news_images`
--
ALTER TABLE `news_images`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT tabele `statistics`
--
ALTER TABLE `statistics`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT tabele `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=37;

--
-- AUTO_INCREMENT tabele `users_authorities`
--
ALTER TABLE `users_authorities`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=36;

--
-- AUTO_INCREMENT tabele `weapons`
--
ALTER TABLE `weapons`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT tabele `weapons_statistics`
--
ALTER TABLE `weapons_statistics`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- Omejitve tabel za povzetek stanja
--

--
-- Omejitve za tabelo `followers`
--
ALTER TABLE `followers`
  ADD CONSTRAINT `FK_FU_follower_id_user` FOREIGN KEY (`follower_id_user`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `FK_FU_following_id_user` FOREIGN KEY (`following_id_user`) REFERENCES `users` (`id`);

--
-- Omejitve za tabelo `missions_completed`
--
ALTER TABLE `missions_completed`
  ADD CONSTRAINT `FK_MC_M` FOREIGN KEY (`id_mission`) REFERENCES `missions` (`id`),
  ADD CONSTRAINT `FK_MC_U` FOREIGN KEY (`id_user`) REFERENCES `users` (`id`);

--
-- Omejitve za tabelo `missions_statistics`
--
ALTER TABLE `missions_statistics`
  ADD CONSTRAINT `FK_MS_M_id` FOREIGN KEY (`id_mission`) REFERENCES `missions` (`id`),
  ADD CONSTRAINT `FK_MS_U_id` FOREIGN KEY (`id_user`) REFERENCES `users` (`id`);

--
-- Omejitve za tabelo `news_images`
--
ALTER TABLE `news_images`
  ADD CONSTRAINT `FK_N_id` FOREIGN KEY (`id_news`) REFERENCES `news` (`id`);

--
-- Omejitve za tabelo `statistics`
--
ALTER TABLE `statistics`
  ADD CONSTRAINT `FK_id_user` FOREIGN KEY (`id_user`) REFERENCES `users` (`id`);

--
-- Omejitve za tabelo `users_authorities`
--
ALTER TABLE `users_authorities`
  ADD CONSTRAINT `FK_AU_id` FOREIGN KEY (`id_authority`) REFERENCES `authorities` (`id`),
  ADD CONSTRAINT `FK_UA_id` FOREIGN KEY (`id_user`) REFERENCES `users` (`id`);

--
-- Omejitve za tabelo `weapons_statistics`
--
ALTER TABLE `weapons_statistics`
  ADD CONSTRAINT `FK_WOU` FOREIGN KEY (`id_user`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `FK_WOW` FOREIGN KEY (`id_weapon`) REFERENCES `weapons` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
