import express from 'express';
import { v4 } from 'uuid';

const app = express();
const port = 2707;
// Define a basic route


async function waitFor(time) {
    return new Promise((resolve, reject) => {
        setTimeout(() => {
            resolve("done");
        }, time);
    });
}
// Initialize variables to track request counts and time intervals
let requestCount = 0;
let startTime = Date.now();

app.use((req, res, next) => {
    requestCount++;
    next();
});

let waitForTime = 1200;
const setIntervalReturn = setInterval(() => {
    // reset count;
    console.log(`Requests per second: ${requestCount}`, new Date(), "WAITEFOR =>", waitForTime);
    requestCount = 0;
    // waitForTime = Math.floor(Math.random() * 1000)
}, 1000);

app.get('/', async (req, res) => {
    await waitFor(34);
    res.send('Hello, Express!');
});

let count = 0;

app.use(express.json({ limit: '100mb' }));

app.post('/secured/apigateway/bulkapi', async (req, res) => {
    count++;
    const requestId = v4();
    // await waitFor(waitForTime);
    return res.json({ status: "SUCCESS", message: "Hello, Express!", requestId });
});

// let waitForTime = 1200;

app.post('/post', async (req, res) => {

    await waitFor(waitForTime);
    count++;
    const requestId = v4();
    return res.json({ status: "success", code: 200, waitForTime, data: [{ uniqueid: requestId }] });
});

app.get('/redirect/:id', async (req, res) => {
    console.log(req.params.id)
    // res.status(301).redirect('https://google.com/')
    res.json({ status: "ok" })
});
// Start the server
app.listen(port, () => {
    console.log(`Server is running on http://localhost:${port}`);
});

process.on('exit', () => {
    clearInterval(setIntervalReturn);
});