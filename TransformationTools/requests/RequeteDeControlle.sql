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

SELECT 'SOUSCRIPTION : ' +prop.nm_name,cast(count(id_sub) as varchar)
FROM [dbo].[t_sub] sub, [dbo].[t_base_props] prop
where prop.id_prop = sub.id_po group by prop.nm_name

SELECT'PRICELIST : '+ cast(count(id_pricelist) as varchar)
  FROM [dbo].[t_pricelist]

SELECT 'GROUP SOUSCRIPTION : '+ cast(count(id_group) as varchar)
FROM [dbo].[t_group_sub]  

update t_db_values set value='false' where parameter='Instantrc'

select * from  dbo.t_db_values  where parameter='Instantrc'

SELECT * FROM [dbo].[t_account_type]

  11 CF ACCOUNT
  12 CLIENT ACCOUNT
  13 EP ACCOUNT
  14 REGROUP CF ACCOUNT