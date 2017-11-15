@echo off
 
cmd /c Accounts\\01ClientAccounts\\RunAccountsLoader.bat

cmd /c Accounts\\02RegroupCFAccounts\\RunAccountsLoader.bat

cmd /c Accounts\\03CFAccounts\\RunAccountsLoader.bat

cmd /c Accounts\\04EPAccounts\\RunAccountsLoader.bat

cmd /c Subscriptions\\OldSubscriptions\\RunSubscriptionsLoader.bat

cmd /c GroupSubscriptions\\OldGroupSubscriptions\\RunGroupSubscriptionsLoader.bat

cmd /c Subscriptions\\NewSubscriptions\\RunSubscriptionsLoader.bat

cmd /c GroupSubscriptions\\NewGroupSubscriptions\\RunGroupSubscriptionsLoader.bat