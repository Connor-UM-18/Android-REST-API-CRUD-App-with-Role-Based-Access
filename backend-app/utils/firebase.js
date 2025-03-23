const admin = require("firebase-admin");
const serviceAccount = require("../serviceAccountKey.json"); // Asegúrate que la ruta es correcta

admin.initializeApp({
  credential: admin.credential.cert(serviceAccount)
});

module.exports = admin;