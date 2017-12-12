SELECT 'CLIENT ACCOUNT : ' + cast(count(*) as varchar)
FROM dbo.t_account 
WHERE id_type in (12)

SELECT 'REGROUP CF ACCOUNT : ' + cast(count(*) as varchar)
FROM dbo.t_account 
WHERE id_type in (14)

SELECT 'CF ACCOUNT : ' + cast(count(*) as varchar)
FROM dbo.t_account 
WHERE id_type in (11)

SELECT 'EP ACCOUNT : ' + cast(count(*) as varchar)
FROM dbo.t_account 
WHERE id_type in (13)

SELECT 'SOUSCRIPTION : '+ cast(count(id_sub) as varchar)
FROM [dbo].[t_sub] 


SELECT 'GROUP SOUSCRIPTION : '+ cast(count(id_group) as varchar)
FROM [dbo].[t_group_sub]  


SELECT NEWID()


SELECT GETDATE()

update t_db_values set value='false' where parameter='Instantrc'

select * from  dbo.t_db_values  where parameter='Instantrc'

    SELECT *
FROM dbo.t_account 

SELECT TOP 1000 [id_type]
      ,[name]
      ,[b_CanSubscribe]
      ,[b_CanBePayer]
      ,[b_CanHaveSyntheticRoot]
      ,[b_CanParticipateInGSub]
      ,[b_IsVisibleInHierarchy]
      ,[b_CanHaveTemplates]
      ,[b_IsCorporate]
      ,[nm_description]
  FROM [dbo].[t_account_type]

  11 CF ACCOUNT
  12 CLIENT ACCOUNT
  13 EP ACCOUNT
  14 REGROUP CF ACCOUNT