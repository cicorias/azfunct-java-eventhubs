import { EventHubProducerClient, OperationOptions } from "@azure/event-hubs";
import * as crypto from 'crypto';

import * as dotenv from "dotenv";
dotenv.config()

const connectionString = process.env["EH_CONNECTION_STRING"] || "";
const eventHubName = process.env["EH_NAME"] || "";
const batchCount:number = +(process.env["EH_BATCH_COUNT"] || 5);
const batchSize:number = +(process.env["EH_BATCH_SIZE"] || 5)
const batchDelay:number = +(process.env["EH_BATCH_DELAY"] || 5)

const delay = (ms:number) => new Promise(res => setTimeout(res, ms));

const options : OperationOptions = {};

const version = Buffer.alloc(1).toString('hex')
const flags = '01'

async function main() {
    const producer = new EventHubProducerClient(connectionString, eventHubName);

    for (let x = 0; x < batchSize; x++) {
        console.log(`prepare batch of ${x+1} of ${batchSize}`)
        // Prep
        const batch = await producer.createBatch();
        for (let i = 0; i < batchCount; i++) {
            const traceId = crypto.randomBytes(16).toString('hex')
            const id = crypto.randomBytes(8).toString('hex')
            const header = `${version}-${traceId}-${id}-${flags}`
            console.log("header: ", header)
            batch.tryAdd({body: {messageid: i}, correlationId: header, properties: { "Diagnostic-Id": header}})    
        }
        // Send the batch of events to the Event Hub.
        await producer.sendBatch(batch, options);
        console.log(`Sent batch of ${batchSize} -- pausing for ${batchDelay*1000} seconds`)
        await delay(batchDelay*1000)
    }
    // Close the producer client.
    await producer.close();

}

main().catch((err) => {
    console.log("Error occurred: ", err);
});
