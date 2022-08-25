import { EventHubProducerClient, OperationOptions } from "@azure/event-hubs";


import * as dotenv from "dotenv";
dotenv.config()

const connectionString = process.env["EH_CONNECTION_STRING"] || "";
const eventHubName = process.env["EH_NAME"] || "";

const options : OperationOptions = {};

async function main() {
    const producer = new EventHubProducerClient(connectionString, eventHubName);

    // Prepare a batch of three events.
    const batch = await producer.createBatch();
    //   batch.tryAdd( "raw text event 0");
    batch.tryAdd({ body: "First event" });
    batch.tryAdd({ body: "Second event" });
    batch.tryAdd({ body: "Third event" });

    // Send the batch to the event hub.
    await producer.sendBatch(batch, options);

    // Close the producer client.
    await producer.close();

    console.log("A batch of three events have been sent to the event hub");
}

main().catch((err) => {
    console.log("Error occurred: ", err);
});
