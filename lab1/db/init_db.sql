-- Создание таблицы
CREATE TABLE Cars (
    ID INT PRIMARY KEY,
    Name VARCHAR(50),
    Country VARCHAR(50),
    Type VARCHAR(50),
    Year INT,
    WIN VARCHAR(50)
);

-- Вставка данных
INSERT INTO Cars (ID, Name, Country, Type, Year, WIN)VALUES
(1, 'ford', 'USA', 'Car', 2000, 'www1234'),
(2, 'bmw', 'Germany', 'SportCar', 2023, 'qwe1234'),
(3, 'volvo', 'Sweden', 'Car', 2015, 'asdf1234'),
(4, 'lada', 'Russia', 'Car', 2006, 'rrr1234'),
(5, 'maz', 'Russia', 'Truck', 2002, 'rrr4321');
