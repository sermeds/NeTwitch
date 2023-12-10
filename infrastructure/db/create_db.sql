create database NeTwitch
GO

CREATE LOGIN adm WITH PASSWORD = 'Qwerty123456';
GO

USE NeTwitch;
GO

CREATE USER adm FOR LOGIN adm;
GO

ALTER ROLE db_owner ADD MEMBER adm;
GO
