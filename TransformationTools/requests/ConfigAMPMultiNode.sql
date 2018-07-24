update mvm_physical_cluster_def set server='SP652HTRAFECB1' where server='localhost'
insert into mvm_physical_cluster_def values ('default', 'SP652HTRAFECB2', 1, 0, null, null)
update mvm_logical_cluster_def set nodes = 10
update mvm_physical_cluster_def set num_cores = 5