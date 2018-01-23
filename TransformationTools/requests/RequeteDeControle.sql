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

SELECT 'TOTAL SOUSCRIPTION : ',cast(count(id_sub) as varchar)
FROM [dbo].[t_sub] sub

SELECT 'SOUSCRIPTION : ' +prop.nm_name,cast(count(id_sub) as varchar)
FROM [dbo].[t_sub] sub, [dbo].[t_base_props] prop
where prop.id_prop = sub.id_po group by prop.nm_name

SELECT'PRICELIST : '+ cast(count(id_pricelist) as varchar)
  FROM [dbo].[t_pricelist]  where n_type = 0

  SELECT'RATE SCHEDULED : '+ cast(count(id_sched) as varchar)
  FROM [dbo].[t_rsched] rs, [dbo].[t_pricelist] pl
  where rs.id_pricelist = pl.id_pricelist and n_type = 0  
  
SELECT 'GROUP SOUSCRIPTION : '+ cast(count(id_group) as varchar)
FROM [dbo].[t_group_sub]  