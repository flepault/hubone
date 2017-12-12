@echo off
 
for %%f in (ClientAccount.input.*.dat) do ( 
 cp %%~f ..\\..\\Loader\\Accounts\\01ClientAccounts\\%%~f 
)

for %%f in (RegroupCFAccount.input.*.dat) do ( 
 cp %%~f ..\\..\\Loader\\Accounts\\02RegroupCFAccounts\\%%~f 
)

for %%f in (CFAccount.input.*.dat) do ( 
 cp %%~f ..\\..\\Loader\\Accounts\\03CFAccounts\\%%~f 
)

for %%f in (EPAccount.input.*.dat) do ( 
 cp %%~f ..\\..\\Loader\\Accounts\\04EPAccounts\\%%~f 
)

for %%f in (Subscription.Old.*.dat) do ( 
 cp %%~f ..\\..\\Loader\\Subscriptions\\OldSubscriptions\\%%~f 
)

for %%f in (GroupSubscription.Old.*.dat) do ( 
 cp %%~f ..\\..\\Loader\\GroupSubscriptions\\OldGroupSubscriptions\\%%~f 
)

for %%f in (Subscription.New.*.dat) do ( 
 cp %%~f ..\\..\\Loader\\Subscriptions\\NewSubscriptions\\%%~f 
)

for %%f in (GroupSubscription.New.*.dat) do ( 
 cp %%~f ..\\..\\Loader\\GroupSubscriptions\\NewGroupSubscriptions\\%%~f 
)

