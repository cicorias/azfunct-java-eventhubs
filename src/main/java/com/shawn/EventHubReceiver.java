package com.shawn;

import java.util.List;

import com.microsoft.azure.functions.annotation.*;
import com.microsoft.azure.functions.*;

/**
 * Azure Functions with Event Hub trigger.
 */
public class EventHubReceiver {

    @FunctionName("EventHubReceiver")
    public void run(
        @EventHubTrigger(name = "messages",
        eventHubName = "", // use EntityPath in connection string.
        connection = "ehconnection",
        cardinality = Cardinality.MANY)
        List<String> messages,
        final ExecutionContext context
    )
    {
        context.getLogger().info("got a message");
    }

    /**
     * This function will be invoked when an event is received from Event Hub.
     */
    // @FunctionName("messagereceive")
    // public void run(
    //     @EventHubTrigger(name = "event",
    //         eventHubName = "vehiot-eventhub",
    //         connection = "ehconnection",
    //         consumerGroup = "$Default",
    //         cardinality = Cardinality.MANY) 
    //     List<String> message,
    //     final ExecutionContext context
    // ) {
    //     context.getLogger().info("Java Event Hub trigger function executed.");
    //     context.getLogger().info("Length:" + message.size());
    // //     message.forEach(singleMessage -> context.getLogger().info(singleMessage));
    // }

    // @FunctionName("processSensorData")
    // @StorageAccount("AzureWebJobsStorage")
    // // @BlobOutput(
    // //     name = "target",
    // //     path = "iotevents/{PartitionKey}/{SequenceNumber}"
    // // )
    // public void processSensorData(
    //     @EventHubTrigger(
    //         name = "msg",
    //         eventHubName = "", // blank because the value is included in the connection string
    //         cardinality = Cardinality.MANY,
    //         connection = "ehconnection")
    //         List<Object> message,
    //     // @BlobOutput(
    //     //     name = "blobOutput",
    //     //     path = "iotevents/{PartitionKey}/{SequenceNumber}"
    //     // )
    //     OutputBinding<String> outputItem,
    //     final ExecutionContext context) {

    //     context.getLogger().info("Event hub message received: " );
    //     int size = message.size();
    //     // outputItem.setValue(new String(content, StandardCharsets.UTF_8));
    //     // outputItem.setValue(content);
    // }
}
