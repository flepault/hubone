select id_descendent, nm_login 
into #tmpCorrectIdRoot
from t_account_ancestor , t_account_mapper map, t_av_common av where id_descendent = av.id_acc and 
num_generations= ( select max(num_generations) id_acc 
from t_account_ancestor ans ,  t_account_mapper map where nm_Login = 'HubOne' and id_ancestor  != 1 and
id_ancestor != map.id_acc and id_descendent = av.id_acc
) 
and id_ancestor = map.id_acc 


UPDATE t_av_Common 
SET c_ClientRootId = i.nm_login
FROM (
    select id_descendent, nm_login 
from #tmpCorrectIdRoot) i
WHERE 
    i.id_descendent = t_av_Common.id_acc