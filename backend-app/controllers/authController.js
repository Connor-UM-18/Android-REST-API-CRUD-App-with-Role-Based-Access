const admin = require("../utils/firebase");
const jwt = require("jsonwebtoken");
const bcrypt = require("bcrypt");

const registerUser = async (req, res) => {
  const { email, password, nombre } = req.body;

  try {
      // 1. Hashear la contraseña
      const hashedPassword = await bcrypt.hash(password, 10);  // 10 es el "salt rounds"

      // 2. Crear el usuario en Firebase Authentication
      const userRecord = await admin
          .auth()
          .createUser({ email, password: hashedPassword });  // Usar la contraseña hasheada

      // 3. Guardar datos adicionales en Firestore (SIN la contraseña)
      const userData = {
          email: email,
          nombre: nombre,
          fotoPerfil: "",
          password: hashedPassword
      };
      await admin.firestore().collection("users").doc(userRecord.uid).set(userData);

      const token = jwt.sign({ uid: userRecord.uid }, "tu_secreto_secreto");

      res.status(201).json({ token, userData });
  } catch (error) {
      res.status(500).json({ message: error.message });
  }
};

const loginUser = async (req, res) => {
    const { email, password } = req.body; // La contraseña encriptada enviada desde el frontend

    try {
        // 1️⃣ Buscar el usuario en Firebase Authentication
        const userRecord = await admin.auth().getUserByEmail(email);

        // 2️⃣ Obtener los datos del usuario en Firestore
        const userDoc = await admin.firestore().collection("users").doc(userRecord.uid).get();
        if (!userDoc.exists) {
            console.log("Usuario no encontrado en Firestore");
            return res.status(404).json({ message: "Usuario no encontrado en Firestore" });
        }

        const userData = userDoc.data();
        const storedHashedPassword = userData.password; // Contraseña almacenada en Firestore

        // 🔍 LOG: Ver qué contraseñas se están comparando
        
        // 3️⃣ Comparar la contraseña hasheada recibida con la almacenada en Firestore
        const passwordMatch = await bcrypt.compare(password, storedHashedPassword);
        

        if (!passwordMatch) {
            
            return res.status(401).json({ message: "Credenciales inválidas" });
        }

        // 4️⃣ Generar el token JWT si la contraseña es correcta
        const token = jwt.sign({ uid: userRecord.uid }, "tu_secreto_secreto", { expiresIn: "2h" });

        res.status(200).json({ token, userData });

    } catch (error) {
        
        res.status(401).json({ message: "Credenciales inválidas" });
    }
};

module.exports = { registerUser, loginUser };