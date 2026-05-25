const { MongoClient } = require('mongodb');

// URL con la nueva contraseña
const uri = "mongodb://mongo:Jp0Nr3qEmt3e7cgi2hhwJYxmuIpSyb3Y@yamabiko.proxy.rlwy.net:11075";

async function testConnection() {
    const client = new MongoClient(uri);
    try {
        console.log("Probando conexión con contraseña nueva...");
        await client.connect();
        console.log("¡CONEXIÓN EXITOSA! La base de datos responde correctamente con la nueva contraseña.");
    } catch (err) {
        console.error("Error al conectar:", err.message);
    } finally {
        await client.close();
    }
}

testConnection();
