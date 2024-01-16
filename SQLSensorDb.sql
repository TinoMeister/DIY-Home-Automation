/* Create Database */
 CREATE DATABASE sensorDb;

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
    State BIT NOT NULL,
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
    State BIT NOT NULL,
    Condition INT NOT NULL,
    PrimarySensorId INT NOT NULL,
    PrimarySensorState BIT NOT NULL,
    PrimarySensorValue FLOAT NOT NULL,
    SecondarySensorId INT,
    SecondarySensorState BIT,
    SecondarySensorValue FLOAT,
    FOREIGN KEY (PrimarySensorId) REFERENCES Devices(Id),
    FOREIGN KEY (SecondarySensorId) REFERENCES Devices(Id)
);

-- Tabela para a entidade Task
CREATE TABLE Tasks (
    Id INT IDENTITY(1,1) PRIMARY KEY,
    Name NVARCHAR(255) NOT NULL,
    State BIT NOT NULL,
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
    TaskId INT NOT NULL,
    DeviceId INT NOT NULL,
    FOREIGN KEY (TaskId) REFERENCES Tasks(Id),
    FOREIGN KEY (DeviceId) REFERENCES Devices(Id)
);

INSERT INTO TypeDevices (Name) VALUES ('Led');
INSERT INTO TypeDevices (Name) VALUES ('Sensor Light');
INSERT INTO TypeDevices (Name) VALUES ('Temperature & Humidity');

INSERT INTO Devices (Name, PinValue, State, Value, Icon, RoomId, TypeSensorId) VALUES ('Test2', 'A1', 0, NULL, NULL, 3, 2);

INSERT INTO Tasks (Name, State, InitHour, EndHour, DaysWeek) VALUES ('Test', 0, '10:00:00', '12:00:00', 'Seg');
INSERT INTO Tasks (Name, State, InitHour, EndHour, DaysWeek) VALUES ('Test2', 0, '10:00:00', '12:00:00', 'Seg');

INSERT INTO TaskDevices (TaskId, DeviceId) VALUES (1, 2);
INSERT INTO TaskDevices (TaskId, DeviceId) VALUES (1, 2);
INSERT INTO TaskDevices (TaskId, DeviceId) VALUES (1, 3);

INSERT INTO Notifications (Name, Description, Time, VisualizeState, TaskId, DeviceId) VALUES ('test', 'desc', '2023-01-01 12:00:00', 0, NULL, 2);
INSERT INTO Notifications (Name, Description, Time, VisualizeState, TaskId, DeviceId) VALUES ('test2', 'desc2', '2023-01-01 12:00:00', 0, 1, NULL);

INSERT INTO Restrictions (Name, State, Condition, PrimarySensorId, PrimarySensorState, PrimarySensorValue, SecondarySensorId, SecondarySensorState, SecondarySensorValue) 
VALUES ('test', 0, 0, 2, 0, 0, NULL, NULL, NULL);

INSERT INTO Restrictions (Name, State, Condition, PrimarySensorId, PrimarySensorState, PrimarySensorValue, SecondarySensorId, SecondarySensorState, SecondarySensorValue) 
VALUES ('test2', 0, 0, 2, 0, 0, NULL, NULL, NULL);


select * from AspNetUsers

select * from TypeDevices


select * from Histories


select * from TaskDevices
select * from Tasks

select * from Esps
select * from Restrictions

select * from Rooms
select * from Devices
select * from Notifications
