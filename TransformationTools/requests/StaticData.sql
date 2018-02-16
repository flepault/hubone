// Request STATIC DATA

SELECT nm_enum_data + '|' + CAST(id_enum_data as VARCHAR)
FROM dbo.t_enum_data
UNION ALL
SELECT nm_name + '|' + CAST(id_prop AS VARCHAR)
 FROM dbo.t_base_props join dbo.t_po ON id_prop = id_po

// Request DYNAMIC FIELD MAPPER

select 
'<DynymicFieldMapper>'+'<CurrentValue>'+nm_name+'</CurrentValue>'+
'<TargePropertyName>ProductOfferingId</TargePropertyName>'+
'<NewValue>'+CAST(id_prop AS VARCHAR)+'</NewValue>'+
'</DynymicFieldMapper>'
from t_po po join t_base_props 
props ON po.id_po = props.id_prop order by nm_name

// Request RAMPBUCKET TARIFFCODE

  select substring(bp.nm_name,18,36)+'|'+rb.c_TariffCodesList
  from t_pt_RampBucket rb, t_rsched rs, t_base_props bp
  where bp.id_prop = rs.id_pricelist and rs.id_sched = rb.id_sched
  and (bp.nm_name like '%FORL%' or bp.nm_name like '%FORP%')
  
 // Request DESTZONEID TARIFFCODE
select c_dest_zoneid+'|'+c_tariffcode
from t_cust_tariffcode
group by c_dest_zoneid,c_tariffcode

// Request XPCMS Mapping
SELECT substring(bp.nm_name,18,36)+'|'+xp.c_TariffCode+'|'+ 
CAST(xp.c_TimeCredit as VARCHAR)+'|'+ CAST(xp.c_UndividedPeriod as VARCHAR)+'|'+ 
CAST(xp.c_ConnectionPrice as VARCHAR)+'|'+ xp.c_CodeGL+'|'+ xp.c_AnalysisCode
FROM t_pt_XPCMS xp, t_rsched rs, t_base_props bp
where xp.id_sched = rs.id_sched and bp.id_prop = rs.id_pricelist