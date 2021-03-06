-- PURGE SUBSCRIPTION

DECLARE @id_sub INT
DECLARE @date date = GETDATE()

DECLARE id_sub_cursor CURSOR FOR  
SELECT id_sub
FROM [dbo].[t_sub]

OPEN id_sub_cursor   
FETCH NEXT FROM id_sub_cursor INTO @id_sub   

WHILE @@FETCH_STATUS = 0   
BEGIN   
       exec dbo.RemoveSubscription @p_id_sub=@id_sub,@p_systemdate=@date
	   
	   delete from t_be_hub_cat_subscriptionin where c_SubId=@id_sub
	   
       FETCH NEXT FROM id_sub_cursor INTO @id_sub   
END   

CLOSE id_sub_cursor   
DEALLOCATE id_sub_cursor
