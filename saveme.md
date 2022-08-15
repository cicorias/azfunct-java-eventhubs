https://github.com/azure-samples/azure-functions-samples-java/blob/master/src/main/java/com/functions/EventHubTriggerFunction.java


https://docs.microsoft.com/en-us/azure/azure-functions/functions-bindings-event-hubs?tabs=in-process%2Cextensionv5&pivots=programming-language-java#hostjson-settings

https://github.com/Azure-Samples/azure-functions-samples-java/blob/master/.editorconfig

https://hub.docker.com/_/microsoft-azure-storage-azurite


https://docs.microsoft.com/en-us/azure/azure-functions/functions-triggers-bindings?tabs=csharp

https://docs.microsoft.com/en-us/azure/azure-functions/functions-reference-java?tabs=bash%2Cconsumption#folder-structure


    // @FunctionName("processSensorData")
    // public void processSensorData(
    //     @EventHubTrigger(
    //         name = "msg",
    //         eventHubName = "", // blank because the value is included in the connection string
    //         cardinality = Cardinality.ONE,
    //         connection = "EventHubConnectionString")
    //         byte[] content,

    //     @BlobOutput(
    //         name = "blobOutput",
    //         path = "iotevents/{Query.file}-viaIotHub"
    //     )
    //     OutputBinding<String> outputItem,
    //     final ExecutionContext context) {

    //     context.getLogger().info("Event hub message received: " );
    //     outputItem.setValue(new String(content, StandardCharsets.UTF_8));
    //     // outputItem.setValue(content);
    // }