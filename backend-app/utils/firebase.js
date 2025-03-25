const admin = require("firebase-admin");
const serviceAccount = require("../serviceAccountKey.json"); // Asegúrate que la ruta es correcta

admin.initializeApp({
  credential: admin.credential.cert(serviceAccount),
  storageBucket: "practica2-2b535.appspot.com" //  Añade esto (tu bucket name)
});

module.exports = admin;