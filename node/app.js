const readline = require('readline');
const net = require('net');
const port = 12346;
const host = '0.0.0.0';

let count = 0;
let currentClientSocket = null;

const rl = readline.createInterface({
    input: process.stdin,
    output: process.stdout
});

const server = net.createServer((socket) => {
    console.log('New client connected.');

    if (currentClientSocket !== null) {
        console.log('Disconnecting previous client.');
        currentClientSocket.end();
    }

    currentClientSocket = socket;

    socket.on('data', (data) => {
        const msg = data.toString();
        console.log('Received from client: ' + msg);
        socket.write(`Received: ${msg} \n`);
    });

    socket.on('end', () => {
        console.log('Client disconnected');
        currentClientSocket = null;
    });

    socket.on('error', (err) => {
        console.error('Error: ' + err.message);
    });
});

server.listen(port, host, () => {
    console.log(`Server listening on ${host}:${port}`);
});

server.on('error', (err) => {
    console.error('Server error: ' + err.message);
});

rl.on('line', (input) => {
    if (currentClientSocket) {
        console.log(`Sending to client: ${input}`);
        currentClientSocket.write(input + '\n');
    }
});
