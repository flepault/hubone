select nm_login,bme.c_ProductIntegrationId, bme.c_Quantity,bme.c_AppliedPrice  
from t_sub sub, t_be_hub_cat_subscriptionin bme , t_account_mapper map
 where sub.id_acc = map.id_acc and sub.id_po = 4281
 and sub.id_sub = bme.c_SubId 
 and nm_login in ( '044606', '103561', '126795','128256','128298') order by nm_login

 select nm_login,bme.c_ProductIntegrationId, bme.c_Quantity,bme.c_AppliedPrice  
from t_sub sub, t_be_hub_cat_subscriptionin bme , t_account_mapper map
 where sub.id_acc = map.id_acc and sub.id_po = 4274
 and sub.id_sub = bme.c_SubId 
 and nm_login in ( '128256','128298') order by nm_login