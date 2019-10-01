# Existing issues

1. Service stops in developer mode (verified: on 9.0 ~not on 5.1~)
If service is active and user navigates to the developer options in settings of their phone, the process terminates abruptly.
Normal process-kill does not happens and probably the onDestroy method of the sevice is not executed.

2. Notification size
Notification is not collapsable, and takes a lot of space.
