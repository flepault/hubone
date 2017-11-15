@echo off
 
for %%f in (ClientAccount.input.*.dat) do ( 
 echo "name: %%~f"  
 cp %%~f ..\\..\\Loader\\Accounts\\01ClientAccounts\\%%~f 
)
cmd /c ..\\..\\Loader\\Accounts\\01ClientAccounts\\RunAccountsLoader.bat


for %%f in (RegroupCFAccount.input.*.dat) do ( 
 echo "name: %%~f"  
 cp %%~f ..\\..\\Loader\\Accounts\\02RegroupCFAccounts\\%%~f 
)
cmd /c ..\\..\\Loader\\Accounts\\02RegroupCFAccounts\\RunAccountsLoader.bat

for %%f in (CFAccount.input.*.dat) do ( 
 echo "name: %%~f"  
 cp %%~f ..\\..\\Loader\\Accounts\\03CFAccounts\\%%~f 
)
cmd /c ..\\..\\Loader\\Accounts\\03CFAccounts\\RunAccountsLoader.bat

for %%f in (EPAccount.input.*.dat) do ( 
 echo "name: %%~f"  
 cp %%~f ..\\..\\Loader\\Accounts\\04EPAccounts\\%%~f 
)
cmd /c ..\\..\\Loader\\Accounts\\04EPAccounts\\RunAccountsLoader.bat

for %%f in (Subscription.Old.*.dat) do ( 
 echo "name: %%~f"  
 cp %%~f ..\\..\\Loader\\Subscriptions\\OldSubscriptions\\%%~f 
)

cmd /c ..\\..\\Loader\\Subscriptions\\OldSubscriptions\\RunSubscriptionsLoader.bat

for %%f in (GroupSubscription.Old.*.dat) do ( 
 echo "name: %%~f"  
 cp %%~f ..\\..\\Loader\\GroupSubscriptions\\OldGroupSubscriptions\\%%~f 
)

cmd /c ..\\..\\Loader\\GroupSubscriptions\\OldGroupSubscriptions\\RunGroupSubscriptionsLoader.bat

for %%f in (Subscription.New.*.dat) do ( 
 echo "name: %%~f"  
 cp %%~f ..\\..\\Loader\\Subscriptions\\NewSubscriptions\\%%~f 
)

cmd /c ..\\..\\Loader\\Subscriptions\\NewSubscriptions\\RunSubscriptionsLoader.bat

for %%f in (GroupSubscription.New.*.dat) do ( 
 echo "name: %%~f"  
 cp %%~f ..\\..\\Loader\\GroupSubscriptions\\NewGroupSubscriptions\\%%~f 
)

cmd /c ..\\..\\Loader\\GroupSubscriptions\\NewGroupSubscriptions\\RunGroupSubscriptionsLoader.bat