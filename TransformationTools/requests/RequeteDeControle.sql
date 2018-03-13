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

--Chiffre d’affaire global HT et chiffre d’affaire global TTC.
select sum(invoice_amount-tax_ttl_amt) as HT,sum(tax_ttl_amt) as TVA,sum(invoice_amount) as TTC from t_invoice

--Chiffre d’affaire par CF
select map.nm_login as 'Num Compte',invoice_amount-tax_ttl_amt as 'Montant HT', tax_ttl_amt as 'Montant TVA',invoice_amount as 'Montant TTC' 
from t_invoice inv, t_account_mapper map where inv.id_acc = map.id_acc order by nm_login

--Chiffre d’affaire factures régulières et de régularisation
select  'Facture régulières' ,COALESCE(sum(invoice_amount),0) as 'Montant TTC' from t_invoice where invoice_string like 'L%'
union
select 'Facture de régularisation' ,COALESCE(sum(invoice_amount),0) as 'Montant TTC' from t_invoice where invoice_string like 'R%'

--Quantité d'ABO
select count(*) as 'Quantité Abonnement' from t_svc_FlatRecurringCharge rec where c__IntervalID = (select distinct c__IntervalID from  t_invoice )

--Quantité de facture
select count(*) as 'Quantité de Facture' from t_invoice 

--Nombre d'entités facturables dans Rafael
select count(*) as 'NB Entité Facturable' from t_av_Internal where c_Billable = 1