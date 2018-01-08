/****** Script for SelectTopNRows command from SSMS  ******/
SELECT TOP 1000 [id_po]
      ,[id_eff_date]
      ,[id_avail]
      ,[b_user_subscribe]
      ,[b_user_unsubscribe]
      ,[id_nonshared_pl]
      ,[c_POPartitionId]
      ,[b_hidden]
  FROM [dbo].[t_po]


  update [dbo].[t_po] set c_POPartitionId = 1813011002, id_eff_date = 1126, id_avail = 1126   
  --where id_po = 1129 or id_po = 1145 or id_po=1121 or id_po=1137

  update [dbo].t_pricelist set c_PLPartitionId = 1813011002
  
select nm_name,id_prop,pricelist.*,props.* from [dbo].t_base_props props, [dbo].[t_pricelist] pricelist where id_prop = id_pricelist


select * from t_pricelist