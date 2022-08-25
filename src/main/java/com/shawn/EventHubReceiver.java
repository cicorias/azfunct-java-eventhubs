package com.shawn;

import java.util.Map;
import java.time.ZonedDateTime;

import com.microsoft.azure.functions.annotation.*;
import com.microsoft.azure.functions.*;

/**
 * Azure Functions with Event Hub trigger.
 * and Blob Output using date in path along with sequenct and partition ID
 */
public class EventHubReceiver {

    @FunctionName("EventHubReceiver")
    @StorageAccount("bloboutput") // unfortunately this won't work with Azurite emulator - unknown issue
                                  // see https://github.com/microsoft/AzureStorageExplorer/issues/6008
    public void run(
            @EventHubTrigger(name = "message",
                eventHubName = "%eventhub%",
                consumerGroup = "%consumergroup%",
                connection = "eventhubconnection",
                cardinality = Cardinality.ONE)
            String message,
            
            final ExecutionContext context,
            
            @BindingName("Properties") Map<String, Object> properties,
            @BindingName("SystemProperties") Map<String, Object> systemProperties,
            @BindingName("PartitionContext") Map<String, Object> partitionContext,
            @BindingName("EnqueuedTimeUtc") Object enqueuedTimeUtc,

            @BlobOutput(
                name = "outputItem",
                path = "iotevents/{datetime:yy}/{datetime:MM}/{datetime:dd}/{datetime:HH}/" +
                       "{datetime:mm}/{PartitionContext.PartitionId}/{SystemProperties.SequenceNumber}.json")
            OutputBinding<String> outputItem) {

        var et = ZonedDateTime.parse(enqueuedTimeUtc + "Z"); // needed as the UTC time presented does not have a TZ
                                                             // indicator
        context.getLogger().info("Event hub message received: " + message + ", properties: " + properties);
        context.getLogger().info("Properties: " + properties);
        context.getLogger().info("System Properties: " + systemProperties);
        context.getLogger().info("partitionContext: " + partitionContext);
        context.getLogger().info("EnqueuedTimeUtc: " + et);

        outputItem.setValue(message);
    }
}
