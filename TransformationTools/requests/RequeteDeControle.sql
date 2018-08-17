-- Suivi Migration

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
  
-- Suivi Valorisation
    
select sum(n_completed) as completed , sum(n_failed) as failed,
sum(n_expected) as expected,  
datediff(mi, min(dt_first), max(dt_last)) as minutes , (sum(n_completed)+sum(n_failed))*60/datediff(mi, min(dt_first), max(dt_last)) trx_hour
from t_batch where id_batch > 2 and tx_status != 'F'
  
select * from t_batch order by dt_last desc
  
-- KPI Facturation 
  
IF OBJECT_ID('tempdb.dbo.#RecTmpTable', 'U') IS NOT NULL
  DROP TABLE #RecTmpTable; 
select acu.*,pv_flat.c_ProratedOnSubscription
into #RecTmpTable
from t_acc_usage acu , t_prod_view pv , t_usage_interval inter , t_pv_FlatRecurringCharge pv_flat
where pv.id_view = acu.id_view and  pv.nm_name = 'metratech.com/FlatRecurringCharge' 
and inter.tx_interval_status = 'O' and acu.id_usage_interval = inter.id_interval
and GETDATE()> dt_end and pv_flat.id_sess = acu.id_sess and pv_flat.id_usage_interval = acu.id_usage_interval

IF OBJECT_ID('tempdb.dbo.#NRecTmpTable', 'U') IS NOT NULL
  DROP TABLE #NRecTmpTable; 
select acu.*
into #NRecTmpTable
from t_acc_usage acu , t_prod_view pv , t_usage_interval inter 
where pv.id_view = acu.id_view and  pv.nm_name = 'metratech.com/NonRecurringCharge'
and inter.tx_interval_status = 'O' and acu.id_usage_interval = inter.id_interval
and GETDATE()> dt_end

IF OBJECT_ID('tempdb.dbo.#DiscountTmpTable', 'U') IS NOT NULL
  DROP TABLE #DiscountTmpTable; 
select acu.*
into #DiscountTmpTable
from t_acc_usage acu , t_prod_view pv , t_usage_interval inter 
where pv.id_view = acu.id_view and  pv.nm_name = 'hubone.fr/%Discount%'
and inter.tx_interval_status = 'O' and acu.id_usage_interval = inter.id_interval
and GETDATE()> dt_end

IF OBJECT_ID('tempdb.dbo.#UsageTmpTable', 'U') IS NOT NULL
  DROP TABLE #UsageTmpTable; 
select acu.*
into #UsageTmpTable
from t_acc_usage acu , t_prod_view pv , t_usage_interval inter 
where pv.id_view = acu.id_view and 
( pv.nm_name = 'hubone.fr/Interco' or pv.nm_name = 'hubone.fr/Operateur' 
or pv.nm_name = 'hubone.fr/OrangeAbo' or pv.nm_name = 'hubone.fr/RampBucket'
or pv.nm_name = 'hubone.fr/Tetra' or pv.nm_name = 'hubone.fr/Voix' 
or pv.nm_name = 'hubone.fr/WifiRoaming')
and inter.tx_interval_status = 'O' and acu.id_usage_interval = inter.id_interval
and GETDATE()> dt_end 
 
--Chiffre d’affaire global HT et chiffre d’affaire global TTC.
select sum(invoice_amount-tax_ttl_amt) as HT,sum(tax_ttl_amt) as TVA,sum(invoice_amount) as TTC 
from t_invoice

--Chiffre d’affaire par CF
select 'C'+avC.c_ClientRootId as 'CODE_CLIENT_GENERIQUE',
IIF(acc1.id_type=12, 'C', 'F')+map.nm_login as 'CODE_CLIENT_FACTURE', 
'REGULIERE' as 'TYPE_FACTURE',
invoice_string as 'NO_FACTURE',
invoice_amount as 'MONTANT_TOTAL' ,
tax_ttl_amt as 'MONTANT_TVA',
invoice_amount-tax_ttl_amt as 'MONTANT_TOTAL_HT',
(select sum(amount) from #RecTmpTable rec where rec.id_acc = inv.id_acc and rec.c_ProratedOnSubscription=0
and rec.id_sess not in ( select id_sess from t_cust_usage_correction cor, t_invoice i  
						 where cor.id_invoice_num = i.id_invoice_num and i.id_acc = inv.id_acc and i.id_invoice_num!=inv.id_invoice_num)) as 'MONTANT_ABO',
(select sum(amount) from #NRecTmpTable nrec where nrec.id_acc = inv.id_acc 
and nrec.id_sess not in ( select id_sess from t_cust_usage_correction cor, t_invoice i  
						  where cor.id_invoice_num = i.id_invoice_num and i.id_acc = inv.id_acc and i.id_invoice_num!=inv.id_invoice_num)) as 'MONTANT_FAS',
(select sum(amount) from #DiscountTmpTable disc where disc.id_acc = inv.id_acc 
and disc.id_sess not in ( select id_sess from t_cust_usage_correction cor, t_invoice i  
						  where cor.id_invoice_num = i.id_invoice_num and i.id_acc = inv.id_acc and i.id_invoice_num!=inv.id_invoice_num)) as 'MONTANT_REM',
(select sum(amount) from #UsageTmpTable usg where usg.id_acc = inv.id_acc 
and usg.id_sess not in ( select id_sess from t_cust_usage_correction cor, t_invoice i 
						 where cor.id_invoice_num = i.id_invoice_num and i.id_acc = inv.id_acc and i.id_invoice_num!=inv.id_invoice_num)) as 'MONTANT_USG',
(select sum(amount) from #RecTmpTable rec where rec.id_acc = inv.id_acc and rec.c_ProratedOnSubscription=1
and rec.id_sess not in ( select id_sess from t_cust_usage_correction cor, t_invoice i  
						 where cor.id_invoice_num = i.id_invoice_num and i.id_acc = inv.id_acc and i.id_invoice_num!=inv.id_invoice_num)) as 'MONTANT_ABO_PRORATA_HORS_PERIODE',
(select count(amount) from #UsageTmpTable usg where usg.id_acc = inv.id_acc 
and usg.id_sess not in ( select id_sess from t_cust_usage_correction cor, t_invoice i 
						 where cor.id_invoice_num = i.id_invoice_num and i.id_acc = inv.id_acc and i.id_invoice_num!=inv.id_invoice_num)) as 'NB_USG',
from t_invoice inv, t_account_mapper map, t_account acc1, t_av_Common avC
where inv.id_acc = map.id_acc and map.id_acc = acc1.id_acc and acc1.id_acc = avC.id_acc
and invoice_string like 'L%'
union
select 'C'+avC.c_ClientRootId as 'CODE_CLIENT_GENERIQUE',
IIF(acc1.id_type=12, 'C', 'F')+map.nm_login as 'CODE_CLIENT_FACTURE', 
'RATTRAPAGE' as 'TYPE_FACTURE',
invoice_string as 'NO_FACTURE',
invoice_amount as 'MONTANT_TOTAL' ,
tax_ttl_amt as 'MONTANT_TVA',
invoice_amount-tax_ttl_amt as 'MONTANT_TOTAL_HT',
(select sum(amount) from #RecTmpTable rec where rec.id_acc = inv.id_acc 
and rec.id_sess in ( select id_sess from t_cust_usage_correction cor  where cor.id_invoice_num = inv.id_invoice_num)) as 'MONTANT_ABO',
0 as 'MONTANT_FAS',
0 as 'MONTANT_REM',
0 as 'MONTANT_USG',
0 as 'MONTANT_ABO_PRORATA_HORS_PERIODE',
0 as 'NB_USG',
from t_invoice inv, t_account_mapper map, t_account acc1, t_av_Common avC 
where inv.id_acc = map.id_acc and map.id_acc = acc1.id_acc and acc1.id_acc = avC.id_acc
and invoice_string like 'R%'

--Chiffre d’affaire factures régulières et de régularisation
select  'Facture régulières' ,COALESCE(sum(invoice_amount),0) as 'Montant TTC' from t_invoice where invoice_string like 'L%'
union
select 'Facture de régularisation' ,COALESCE(sum(invoice_amount),0) as 'Montant TTC' from t_invoice where invoice_string like 'R%'

--Quantité d'ABO
select count(*) as 'Quantité Abonnement' from t_svc_FlatRecurringCharge rec , t_usage_interval inter 
where inter.tx_interval_status = 'O' and rec.c__IntervalID = inter.id_interval
and GETDATE()> dt_end

--Quantité de facture régulière
select count(*) as 'Quantité de Facture' from t_invoice where invoice_string like 'L%'

--Quantité de facture de régularisation
select count(*) as 'Quantité de Facture' from t_invoice where invoice_string like 'R%'

--Nombre d'entités facturables dans Rafael
select count(*) as 'NB Entité Facturable' from t_av_Internal where c_Billable = 1

