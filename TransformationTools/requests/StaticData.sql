SELECT nm_enum_data + '|' + CAST(id_enum_data as VARCHAR)
FROM dbo.t_enum_data
UNION ALL
SELECT nm_name + '|' + CAST(id_prop AS VARCHAR)
 FROM dbo.t_base_props join dbo.t_po ON id_prop = id_po


   select '<DynymicFieldMapper>'+'<CurrentValue>'+nm_name+'</CurrentValue>'+
    '<TargePropertyName>ProductOfferingId</TargePropertyName>'+
	'<NewValue>'+CAST(id_prop AS VARCHAR)+'</NewValue>'+
	'</DynymicFieldMapper>'
   from t_po po join t_base_props 
				props ON po.id_po = props.id_prop order by nm_name