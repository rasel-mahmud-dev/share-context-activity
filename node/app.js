
const net = require('net');
const port = 12345;
//const host = '192.168.0.100';
const host = '192.168.0.103';


let count = 0


// Function to create the client and handle communication
function createClient() {
  const client = new net.Socket();

  // Try to connect to the server
  client.connect(port, host, () => {
    console.log('Connected to server');

    setInterval(()=>{
         count++
    client.write(`cur:${count},${count}\n`);
    }, 10)

//    setInterval(()=>{
//        count++
//        client.write(`cli:${count},${count}\n`);
//    }, 10000)

//     setInterval(()=>{
//            client.write("10\n");
//        }, 5000)


  });

  client.on('data', (data) => {
    const msg = data.toString()
    console.log('Received from server: ' + msg );
  });

  // Handle connection error
  client.on('error', (err) => {
    console.error('Connection error: ' + err.message);
    client.destroy(); // Destroy the client after an error
  });

  // Handle the client being closed or disconnected
  client.on('close', () => {
    console.log('Connection closed. Reconnecting...');
    setTimeout(createClient, 5000); // Try to reconnect after 5 seconds
  });

  // Handle client connection timeout
  client.on('timeout', () => {
    console.log('Connection timed out. Reconnecting...');
    client.destroy(); // Destroy and try to reconnect
  });
}

// Start the client
createClient();
