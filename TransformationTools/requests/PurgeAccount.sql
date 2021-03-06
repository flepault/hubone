-- PURGE ACCOUNT 

DECLARE @id_acc INT

DECLARE id_acc_cursor CURSOR FOR  
SELECT id_acc
FROM dbo.t_account 
WHERE id_type in (11,12,13,14)

OPEN id_acc_cursor   
FETCH NEXT FROM id_acc_cursor INTO @id_acc   

WHILE @@FETCH_STATUS = 0   
BEGIN   
       exec dbo.DeleteAccounts @account_id_list=@id_acc,@table_name=null,@linked_server_name=null,@payment_server_dbname=null  

       FETCH NEXT FROM id_acc_cursor INTO @id_acc   
END   

CLOSE id_acc_cursor   
DEALLOCATE id_acc_cursor
