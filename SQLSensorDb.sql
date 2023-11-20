/* Create Database */
-- CREATE DATABASE sensorDb;

/*
-- Tabela para a entidade User
CREATE TABLE Users (
    Id INT IDENTITY(1,1) PRIMARY KEY,
    Name NVARCHAR(255) NOT NULL,
    Email NVARCHAR(255) NOT NULL,
    Password NVARCHAR(255) NOT NULL
);

-- Tabela para a entidade Room
CREATE TABLE Rooms (
    Id INT IDENTITY(1,1) PRIMARY KEY,
    Name NVARCHAR(255) NOT NULL,
    Icon NVARCHAR(255),
    UserId INT,
    FOREIGN KEY (UserId) REFERENCES Users(Id)
);

-- Tabela para a entidade TypeDevice
CREATE TABLE TypeDevices (
    Id INT IDENTITY(1,1) PRIMARY KEY,
    Name NVARCHAR(255) NOT NULL
);

-- Tabela para a entidade Device
CREATE TABLE Devices (
    Id INT IDENTITY(1,1) PRIMARY KEY,
    Name NVARCHAR(255) NOT NULL,
    PinValue NVARCHAR(255) NOT NULL,
    State INT NOT NULL,
    Value FLOAT,
    Icon NVARCHAR(255),
    RoomId INT,
    TypeSensorId INT,
    FOREIGN KEY (RoomId) REFERENCES Rooms(Id),
    FOREIGN KEY (TypeSensorId) REFERENCES TypeDevices(Id)
);

-- Tabela para a entidade History
CREATE TABLE Histories (
    Id INT IDENTITY(1,1) PRIMARY KEY,
    Value FLOAT NOT NULL,
    Date DATETIME NOT NULL,
    DeviceId INT,
    FOREIGN KEY (DeviceId) REFERENCES Devices(Id)
);

-- Tabela para a entidade Restriction
CREATE TABLE Restrictions (
    Id INT IDENTITY(1,1) PRIMARY KEY,
    Name NVARCHAR(255) NOT NULL,
    State INT NOT NULL,
    Condition INT NOT NULL,
    PrimarySensorId INT,
    PrimarySensorState INT NOT NULL,
    PrimarySensorValue FLOAT NOT NULL,
    SecondarySensorId INT,
    SecondarySensorState INT NOT NULL,
    SecondarySensorValue FLOAT,
    FOREIGN KEY (PrimarySensorId) REFERENCES Devices(Id),
    FOREIGN KEY (SecondarySensorId) REFERENCES Devices(Id)
);

-- Tabela para a entidade Task
CREATE TABLE Tasks (
    Id INT IDENTITY(1,1) PRIMARY KEY,
    Name NVARCHAR(255) NOT NULL,
    State INT NOT NULL,
    InitHour TIME NOT NULL,
    EndHour TIME NOT NULL,
    DaysWeek NVARCHAR(255) NOT NULL,
);

-- Tabela para a entidade Notification
CREATE TABLE Notifications (
    Id INT IDENTITY(1,1) PRIMARY KEY,
    Name NVARCHAR(255) NOT NULL,
    Description NVARCHAR(255) NOT NULL,
    Time DATETIME NOT NULL,
    VisualizeState BIT NOT NULL,
    TaskId INT,
    DeviceId INT,
    FOREIGN KEY (TaskId) REFERENCES Tasks(Id),
    FOREIGN KEY (DeviceId) REFERENCES Devices(Id)
);

-- Tabela de associação para a relação muitos para muitos entre Task e Device
CREATE TABLE TaskDevices (
	Id INT IDENTITY(1,1) PRIMARY KEY,
    TaskId INT,
    DeviceId INT,
    FOREIGN KEY (TaskId) REFERENCES Tasks(Id),
    FOREIGN KEY (DeviceId) REFERENCES Devices(Id)
);

*/

drop database sensorDb;