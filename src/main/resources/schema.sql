PRAGMA foreign_keys = off;
BEGIN TRANSACTION;

CREATE TABLE IF NOT EXISTS "orders" (
  "order_no" varchar(50) PRIMARY KEY NOT NULL,
  "pay_user" varchar(50) NOT NULL,
  "pay_email" varchar(100),
  "pay_comment" varchar(500) NOT NULL,
  "trade_no" varchar(50) NOT NULL,
  "channel" varchar(10),
  "platform" varchar(20) NOT NULL,
  "money" integer(10) NOT NULL,
  "order_status" integer(4) NOT NULL,
  "created" date NOT NULL
);

CREATE TABLE IF NOT EXISTS "options" (
  "key" varchar(100) PRIMARY KEY NOT NULL,
  "value" varchar(200) NOT NULL
);

COMMIT TRANSACTION;
PRAGMA foreign_keys = on;
