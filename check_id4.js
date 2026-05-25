const { MongoClient } = require('mongodb');

const uri = "mongodb://mongo:HXBiVSvgNNlZjSmLytbGCvFtiRoeRxLM@yamabiko.proxy.rlwy.net:11075/mobimanten_db?authSource=admin";

async function run() {
    const client = new MongoClient(uri);

    try {
        await client.connect();
        const db = client.db("mobimanten_db");
        const cochesColl = db.collection("coches");
        
        console.log("Searching for models with 'ID' or 'Volkswagen' or 'VW' using correct fields...");
        const list = await cochesColl.find({
            $or: [
                { marca: /vw/i },
                { marca: /volkswagen/i },
                { modelo: /id/i }
            ]
        }).toArray();

        console.log(`Found ${list.length} cars:`);
        list.forEach(c => {
            console.log(`- ${c.marca} ${c.modelo} (ID: ${c._id})`);
            console.log(`  Motor: ${c.motor}, Combustible: ${c.combustible}, Año: ${c.anio}`);
        });

    } finally {
        await client.close();
    }
}

run().catch(console.dir);
