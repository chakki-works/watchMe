# watchMe
watchMe is an IntelliJ IDEA plugin, notify your error message to Slack.

This plugin provides support for Solving your error in IntelliJ IDEA.
By notifying your error to your Slack team automatically, your team members can help you to solve the error.

## Getting started
1. If you’re using IntelliJ IDEA, install watchMe plugin in Preferences | Plugins - Install JetBrains plugins.
2. Get Incoming Webhook URL from [https://slack.com/services/new/incoming-webhook](https://slack.com/services/new/incoming-webhook).
   1. Select channel to notify error message.
   ![channel_selection](https://github.com/chakki-works/watchMe/blob/images/slack-screenshot-01.png)
   2. Get Webhook URL.
   ![webhook_url](https://github.com/chakki-works/watchMe/blob/images/slack-screenshot-02.png)
3. Set Webhook URL to Tools -> Slack Settings -> Add Slack Channel.
4. Open your own project.
5. If an error occurred on execution, watchMe send the error to your Slack team automatically.

## Licence

[MIT](https://github.com/chakki-works/watchMe/blob/master/LICENSE)

## Contributors

[Hironsan](https://github.com/Hironsan)