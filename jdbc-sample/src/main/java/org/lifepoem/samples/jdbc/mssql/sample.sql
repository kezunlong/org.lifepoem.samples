--table for common JDBC samples
CREATE TABLE [dbo].[users](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[name] [varchar](50) NULL,
	[password] [varchar](50) NULL,
	[email] [varchar](50) NULL,
	[birthday] [datetime] NULL,
 CONSTRAINT [PK_users] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)) ON [PRIMARY]

--table for DataTypesDemo.basicDataType
CREATE TABLE DataTypesTable   
   (Col1 int IDENTITY,   
    Col2 char,  
    Col3 varchar(50),   
    Col4 bit,  
    Col5 decimal(18, 2),  
    Col6 money,  
    Col7 datetime,  
    Col8 date,  
    Col9 time,  
    Col10 datetime2,  
    Col11 datetimeoffset  
    );  
  
INSERT INTO DataTypesTable   
VALUES ('A', 'Some text.', 0, 15.25, 10.00, '01/01/2006 23:59:59.991', '01/01/2006', '23:59:59', '01/01/2006 23:59:59.12345', '01/01/2006 23:59:59.12345 -1:00')

--table for transaction demo
CREATE TABLE [dbo].[account](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[name] [varchar](50) NULL,
	[money] decimal NULL
 CONSTRAINT [PK_account] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)) ON [PRIMARY]

INSERT INTO account(name, money) VALUES('aaa', 1000);
INSERT INTO account(name, money) VALUES('bbb', 1000);
INSERT INTO account(name, money) VALUES('ccc', 1000);
