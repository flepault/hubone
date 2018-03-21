SELECT 'CLIENT ACCOUNT : ' + cast(count(*) as varchar)
FROM dbo.t_account 
WHERE id_type in (12)
union
SELECT 'REGROUP CF ACCOUNT : ' + cast(count(*) as varchar)
FROM dbo.t_account 
WHERE id_type in (14)
union
SELECT 'CF ACCOUNT : ' + cast(count(*) as varchar)
FROM dbo.t_account 
WHERE id_type in (11)
union
SELECT 'EP ACCOUNT : ' + cast(count(*) as varchar)
FROM dbo.t_account 
WHERE id_type in (13)

SELECT 'TOTAL SOUSCRIPTION : ' + cast(count(id_sub) as varchar)
FROM [dbo].[t_sub] sub

SELECT prop.nm_name+': '+cast(count(id_sub) as varchar)
FROM [dbo].[t_sub] sub, [dbo].[t_base_props] prop
where prop.id_prop = sub.id_po group by prop.nm_name

SELECT 'GROUP SOUSCRIPTION : '+ cast(count(id_group) as varchar)
FROM [dbo].[t_group_sub]  

SELECT'PRICELIST : '+ cast(count(id_pricelist) as varchar)
  FROM [dbo].[t_pricelist]  where n_type = 0

  SELECT'RATE SCHEDULED : '+ cast(count(id_sched) as varchar)
  FROM [dbo].[t_rsched] rs, [dbo].[t_pricelist] pl
  where rs.id_pricelist = pl.id_pricelist and n_type = 0  
  
select  * from t_acc_usage where id_acc = 31072740 

select * from t_invoice where id_acc = 31072740 

select * from t_acc_usage where id_sess not in (select id_sess from t_cust_usage_correction )

select * from t_usage_interval 

select * from t_svc_FlatRecurringCharge

update t_pv_NonRecurringCharge set id_usage_interval = 1150943265 , c_NRCIntervalEnd='2018-01-31 00:00:00.000' where  c_NRCIntervalSubscriptionStart < '2018-02-01 00:00:00.000'

update t_acc_usage set id_usage_interval = 1150943265 ,dt_crt='2018-01-31 00:00:00.000' where id_sess in ( select id_sess from t_pv_NonRecurringCharge where  c_NRCIntervalSubscriptionStart < '2018-02-01 00:00:00.000')

select * from t_pv_NonRecurringCharge where id_usage_interval != 1150943265

select * from t_pv_taxation 



where id_sess in (select id_sess from t_pv_NonRecurringCharge where id_usage_interval != 1150943265)

select * from t_prod_view

select acu.*
from t_acc_usage acu , t_prod_view pv , t_usage_interval inter 
where pv.id_view = acu.id_view and  pv.nm_name = 'metratech.com/FlatRecurringCharge' 
and inter.tx_interval_status = 'O' and acu.id_usage_interval = inter.id_interval

select acu.*
from t_acc_usage acu , t_prod_view pv , t_usage_interval inter 
where pv.id_view = acu.id_view and  pv.nm_name = 'metratech.com/NonRecurringCharge'
and inter.tx_interval_status = 'O' and acu.id_usage_interval = inter.id_interval


select * from t_usage_interval


select * from t_av_Common avC where c_ClientRootId is null
 

delete t_invoice where id_invoice <= 3336


--Chiffre d’affaire global HT et chiffre d’affaire global TTC.
select sum(invoice_amount-tax_ttl_amt) as HT,sum(tax_ttl_amt) as TVA,sum(invoice_amount) as TTC 
from t_invoice

--Chiffre d’affaire par CF
select 'C'+avC.c_ClientRootId as 'CODE_CLIENT_GENERIQUE',

select IIF(acc1.id_type=12, 'C', 'F')+map.nm_login as 'CODE_CLIENT_FACTURE', 
invoice_string as 'NO_FACTURE',
invoice_amount as 'MONTANT_TOTAL' ,
tax_ttl_amt as 'MONTANT_TVA',
invoice_amount-tax_ttl_amt as 'MONTANT_TOTAL_HT'
from t_invoice inv, t_account_mapper map, t_account acc1, t_av_Common avC 
 where inv.id_acc = map.id_acc and map.id_acc = acc1.id_acc and acc1.id_acc = avC.id_acc
 order by c_ClientRootId

--Chiffre d’affaire factures régulières et de régularisation
select  'Facture régulières' ,COALESCE(sum(invoice_amount),0) as 'Montant TTC' from t_invoice where invoice_string like 'L%'
union
select 'Facture de régularisation' ,COALESCE(sum(invoice_amount),0) as 'Montant TTC' from t_invoice where invoice_string like 'R%'

--Quantité d'ABO
select count(*) as 'Quantité Abonnement' from t_svc_FlatRecurringCharge rec where c__IntervalID = (select distinct id_interval from  t_invoice )

--Quantité de facture régulière
select count(*) as 'Quantité de Facture' from t_invoice where invoice_string like 'L%'

--Quantité de facture de régularisation
select count(*) as 'Quantité de Facture' from t_invoice where invoice_string like 'R%'

--Nombre d'entités facturables dans Rafael
select count(*) as 'NB Entité Facturable' from t_av_Internal where c_Billable = 1

select * from t_svc_FlatRecurringCharge
