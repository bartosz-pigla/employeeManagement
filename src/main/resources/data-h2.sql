INSERT INTO EMPLOYEE (EMPLOYEE_ID,FIRST_NAME,LAST_NAME,DATE_OF_EMPLOYMENT,LEADER_ID)
VALUES
(1,'Anna','Zawiecka',parsedatetime('01-01-1999','dd-MM-yyyy'),NULL),
(2,'Krystyna','Jacak',parsedatetime('01-01-1990','dd-MM-yyyy'),NULL),
(3,'Wanda','Nowak',parsedatetime('11-11-2001','dd-MM-yyyy'),1),
(4,'Katarzyna','Kowalski',parsedatetime('10-08-2010','dd-MM-yyyy'),1),
(5,'Magdalena','Mazur',parsedatetime('14-03-2005','dd-MM-yyyy'),2),
(6,'Małgorzata','Irecka',parsedatetime('06-09-2009','dd-MM-yyyy'),5),
(7,'Jacek','Opińska',parsedatetime('02-11-2001','dd-MM-yyyy'),3),
(8,'Mariusz','Zły',parsedatetime('25-11-2011','dd-MM-yyyy'),3),
(9,'Krzysztof','Dobry',parsedatetime('21-04-2012','dd-MM-yyyy'),8);
-- (10,'Urszula','Naniewicz',parsedatetime('22-03-2008','dd-MM-yyyy'),NULL);