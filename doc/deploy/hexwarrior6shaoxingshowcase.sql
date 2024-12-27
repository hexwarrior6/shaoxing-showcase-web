/*
 Navicat Premium Dump SQL

 Source Server         : shaoxing-showcase
 Source Server Type    : MySQL
 Source Server Version : 50718 (5.7.18-log)
 Source Host           : sql.wsfdb.cn:3306
 Source Schema         : hexwarrior6shaoxingshowcase

 Target Server Type    : MySQL
 Target Server Version : 50718 (5.7.18-log)
 File Encoding         : 65001

 Date: 27/12/2024 14:05:20
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for account
-- ----------------------------
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `avatarUrl` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `unique_username`(`username`) USING BTREE,
  UNIQUE INDEX `unique_email`(`email`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of account
-- ----------------------------
INSERT INTO `account` VALUES (1, 'admin', '$2a$10$AQnfzg20a0KUbTK44auQfOOGv8zVQc4qhTj4JvuzDV4r7kISTZK3.', 'admin@qq.com', NULL);
INSERT INTO `account` VALUES (2, 'testuser', '$2a$10$RCn24OszEvmfcGdkiFNmH.mnKpAxeZAiyPLpVjXtsAeutAUhDj9EK', 'test@test.com', 'user_avatar/b0986aec-f279-4d9e-8058-2fc6c5ddd46c_头像.jpg');

-- ----------------------------
-- Table structure for local_culture
-- ----------------------------
DROP TABLE IF EXISTS `local_culture`;
CREATE TABLE `local_culture`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `culture_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `description` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `significance` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `image_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `time` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of local_culture
-- ----------------------------
INSERT INTO `local_culture` VALUES (1, '绍兴戏曲', '绍兴戏曲是地方性传统戏曲之一，具有浓郁的地方特色。', '绍兴戏曲代表了地方的民间艺术和文化传统，具有重要的历史和文化价值。', 'local_cultures/shao_xing_xi_qu.jpg', '全年演出');
INSERT INTO `local_culture` VALUES (2, '黄酒酿造文化', '绍兴黄酒酿造文化源远流长，属于中国非物质文化遗产。', '黄酒文化体现了绍兴地区的酿酒技艺和传统文化，是绍兴人生活的一部分。', 'local_cultures/huang_jiu_niang_zao.jpg', '全年举办');
INSERT INTO `local_culture` VALUES (3, '绍兴婚礼文化', '绍兴的传统婚礼习俗充满了地域特色，有许多独特的仪式。', '绍兴婚礼反映了当地的传统家庭观念和风俗，是了解绍兴文化的窗口。', 'local_cultures/shao_xing_hun_li.jpg', '每年婚庆季节');
INSERT INTO `local_culture` VALUES (4, '越剧', '越剧是浙江省绍兴市的传统戏剧形式，歌唱和表演艺术上独具特色。', '越剧是绍兴的标志性文化之一，作为传统戏剧，深受本地人和外地游客的喜爱。', 'local_cultures/yue_ju.jpg', '全年演出');
INSERT INTO `local_culture` VALUES (5, '古越文化节', '古越文化节是绍兴市的传统节庆活动，举办各种文艺表演和地方特产展示。', '古越文化节有助于弘扬绍兴地区的传统文化，增强当地居民的文化自信。', 'local_cultures/gu_yue_wen_hua_jie.jpg', '每年9月');

-- ----------------------------
-- Table structure for local_foods
-- ----------------------------
DROP TABLE IF EXISTS `local_foods`;
CREATE TABLE `local_foods`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `food_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `description` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `ingredients` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `image_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `origin` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of local_foods
-- ----------------------------
INSERT INTO `local_foods` VALUES (1, '臭豆腐', '臭豆腐是一种具有浓烈气味的地方小吃，外脆内嫩，味道独特。', '豆腐、油、调料', 'local_foods/chou_doufu.jpg', '越城');
INSERT INTO `local_foods` VALUES (2, '黄酒', '黄酒是绍兴传统的发酵酒，有着浓厚的历史文化背景，口感甘甜。', '米、麦曲、清水', 'local_foods/huang_jiu.jpg', '鉴湖');
INSERT INTO `local_foods` VALUES (3, '梅干菜扣肉', '梅干菜扣肉是绍兴特色传统菜肴，味道鲜美，色香味俱全。', '梅干菜、猪肉', 'local_foods/mei_gan_cai_kou_rou.jpg', '绍兴');
INSERT INTO `local_foods` VALUES (4, '鸡头米汤', '鸡头米汤清香味美，是绍兴传统的夏季消暑饮品。', '鸡头米、糖、椰浆', 'local_foods/ji_tou_mi_tang.jpg', '夏履');
INSERT INTO `local_foods` VALUES (5, '鲍汁焖蹄膀', '绍兴的鲍汁焖蹄膀口感鲜嫩，风味独特，配上浓郁的鲍汁。', '猪蹄、鲍汁、香料', 'local_foods/bao_zhi_men_ti_bang.jpg', '绍兴');
INSERT INTO `local_foods` VALUES (6, '黄酒棒冰', '将黄酒与冰淇淋相结合，增添了清凉的口感，给人一种独特的享受', '黄酒，米，纯净水', 'local_foods/huang_jiu_bang_bing.jpg', '鉴湖');

-- ----------------------------
-- Table structure for local_sights
-- ----------------------------
DROP TABLE IF EXISTS `local_sights`;
CREATE TABLE `local_sights`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sight_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `location` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `description` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `image_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `opening_hours` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of local_sights
-- ----------------------------
INSERT INTO `local_sights` VALUES (1, '东湖', '绍兴市区东南', '东湖是绍兴著名的自然景区，湖水清澈，风景如画。', 'local_sights/dong_hu.jpg', '07:00 - 18:00');
INSERT INTO `local_sights` VALUES (2, '鲁迅故里', '绍兴市区西北', '鲁迅故里是纪念鲁迅的历史文化景点，保留了很多鲁迅的生平遗迹。', 'local_sights/lu_xun_gu_li.jpg', '08:00 - 17:00');
INSERT INTO `local_sights` VALUES (3, '大禹陵', '绍兴市区南部', '大禹陵是纪念古代治水英雄大禹的地方，景区内有众多古迹。', 'local_sights/da_yu_ling.jpg', '08:00 - 18:00');
INSERT INTO `local_sights` VALUES (4, '沈园', '绍兴市区北部', '沈园是绍兴市内的古园林，景色宜人，有着深厚的文化底蕴。', 'local_sights/shen_yuan.jpg', '07:30 - 17:30');
INSERT INTO `local_sights` VALUES (5, '鉴湖', '绍兴市区东南', '鉴湖是绍兴市的著名景区，以水域辽阔和自然风光著称。', 'local_sights/jian_hu.jpg', '06:30 - 19:00');

-- ----------------------------
-- Table structure for persistent_logins
-- ----------------------------
DROP TABLE IF EXISTS `persistent_logins`;
CREATE TABLE `persistent_logins`  (
  `username` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `series` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `token` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `last_used` timestamp NOT NULL,
  PRIMARY KEY (`series`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of persistent_logins
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `real_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `hobby` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'admin', '管理员', '打篮球', NULL, '杭州市钱塘区');
INSERT INTO `user` VALUES (3, 'joker', 'John Doe', 'Reading, Hiking', 'http://example.com/avatar.jpg', '123 Main St, City, Country');

SET FOREIGN_KEY_CHECKS = 1;
