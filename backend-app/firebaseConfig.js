const admin = require("firebase-admin");
const { getAuth } = require("firebase/auth");
const { getFirestore } = require("firebase/firestore");

// Importar la clave de servicio
const serviceAccount = require("./serviceAccountKey.json");

// Inicializar Firebase Admin (para Firestore y Authentication)
admin.initializeApp({
  credential: admin.credential.cert(serviceAccount),
});

const auth = getAuth(); // Firebase Authentication
const db = getFirestore(); // Firestore

module.exports = { auth, db, admin };
