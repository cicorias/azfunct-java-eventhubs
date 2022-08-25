# Using BlobOutput and EventHubTrigger bindings with System Properties in Java Azure Functions

## Startup
Make sure you run `mvn clean package` (of course you need Maven installed) and then also run the Task "func: host start" at least once before attempting to debug. There are some strange Azure Functions tooling oddities that needs the JAR ready and the Task to be run once.


## Overview
One of the nice developer features of Azure Functions is the Bindings that provided pre-baked code and logic to help handle events inbound or sending things outbound

However, much of the documentation is centered around C# and I found it difficult to find any clear Java examples that use EventHub and Blob output.

So, after bunch of debugging and spelunking, I created a simple Azure Function in Java the illustrates pulling in System and Custom properties from the Event Hub message.

Adding to this is writing the output to Blob Storage using a best practice of slash separation such as YY/MM/DD/hh/mm/partition/item.json. The reason for this is the Storage API search provides a "prefix" option to narrow down the search, and tools such as Azure Stream Analytics strongly suggest this approach, so it doesn't have to enumerate ALL files to find the files within their window of search.

## Items of Note
One interesting item is the Enqueue Date Time while in UTC doesn't have a Time zone suffix of Z  (for UTC or zulu time) – so, for that you can't just using @Bindingname to a Date Time data type – as it fails the Google gson deserialization indicating it's missing a TZ indicator.

The documentation is quite Queue centric – never really showing methods for binding to EventHub meta-data – see here: Azure Functions bindings expressions and patterns | Microsoft Docs

Also note the use of percentage signs that allow externalizing values in App Settings. A sample local.settings.json file is provided so update that to your environment needs.

You'll also note that I used Azure Storage Emulator Azurite – unfortunately, there's some off known issue with missing MD5 hash values that is a regression – the issue is noted in the source code and hopefully that's fixed soon.

## Event Hub Sender
I also added in a folder under ./tools a TypeScript node app that sends a couple of messages to the EventHub of your choice – this is helpful for testing.

## Using storage emulation:

```
docker run -p 10000:10000 mcr.microsoft.com/azure-storage/azurite azurite-blob --blobHost 0.0.0.0
```
