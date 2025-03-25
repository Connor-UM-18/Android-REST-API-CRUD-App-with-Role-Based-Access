const admin = require("../utils/firebase");
const { v4: uuidv4 } = require('uuid'); // Para generar nombres únicos
const bcrypt = require("bcrypt");

const getUser = async (req, res) => {
    try {
        const userRecord = await admin.auth().getUser(req.uid);
        res.status(200).json(userRecord);
    } catch (error) {
        res.status(500).json({ message: error.message });
    }
};
const getUserByEmail = async (req, res) => {
  try {
    const { email } = req.params;  // Obtener el email del parámetro
    const userRecord = await admin.auth().getUserByEmail(email);

    // Obtener los datos del usuario de Firestore
    const userDoc = await admin.firestore().collection("users").doc(userRecord.uid).get();
    if (!userDoc.exists) {
      return res.status(404).json({ message: "Usuario no encontrado en Firestore" });
    }
    
    const userData = userDoc.data();

    // 🚨 Si la contraseña está almacenada en Firestore
    res.status(200).json({
      ...userData,
      password: userData.password || "No disponible" // Asegurar que no sea undefined
    });

  } catch (error) {
    res.status(404).json({ message: "Usuario no encontrado" });
  }
};

const updateUserProfile = async (req, res) => {
    try {
        const { userId, nombre, email, password, image } = req.body;
        let profileImageUrl = null;

        // Obtener el usuario actual de Firebase Authentication
        const userRecord = await admin.auth().getUser(userId);

        // Actualizar datos en Firebase Authentication
        const updateAuthData = {};
        if (email && email !== userRecord.email) updateAuthData.email = email;
        if (password) updateAuthData.password = password; // No encriptamos aquí, Firebase Auth maneja esto

        if (Object.keys(updateAuthData).length > 0) {
            await admin.auth().updateUser(userId, updateAuthData);
        }

        // Si hay una imagen, la subimos a Firebase Storage
        if (image) {
            const bucket = admin.storage().bucket();
            const filename = `profile_images/${userId}-${uuidv4()}.jpg`;
            const file = bucket.file(filename);

            const buffer = Buffer.from(image, "base64");
            await file.save(buffer, {
                metadata: { contentType: "image/jpeg" },
            });

            // Obtener URL de la imagen
            const [url] = await file.getSignedUrl({ action: "read", expires: "03-01-2500" });
            profileImageUrl = url;
        }

        // Actualizar datos en Firestore
        const updateFirestoreData = {};
        if (nombre) updateFirestoreData.nombre = nombre;
        if (email) updateFirestoreData.email = email; // Asegurar que Firestore también tenga el email nuevo
        if (password) updateFirestoreData.password = await bcrypt.hash(password, 10); // Encriptar antes de guardar en Firestore
        if (profileImageUrl) updateFirestoreData.fotoPerfil = profileImageUrl;

        if (Object.keys(updateFirestoreData).length > 0) {
            await admin.firestore().collection("users").doc(userId).update(updateFirestoreData);
        }

        res.status(200).json({ message: "Perfil actualizado correctamente", profileImageUrl });

    } catch (error) {
        console.error("Error al actualizar el perfil:", error);
        res.status(500).json({ message: "Error al actualizar el perfil" });
    }
};

const getAllUsers = async (req, res) => {
  try {
      console.log("📌 Iniciando consulta de todos los usuarios...");

      // Verificar si el usuario es administrador (el rol debe venir en el token)
      const { role } = req.user; // El middleware debe agregar el `role` al objeto `req.user`
      if (role !== "admin") {
          return res.status(403).json({ message: "Acceso denegado: No eres administrador" });
      }

      // Obtener usuarios desde Firestore
      const usersSnapshot = await admin.firestore().collection("users").get();
      const users = usersSnapshot.docs.map(doc => ({
          id: doc.id,
          ...doc.data()
      }));

      console.log(`✅ Se encontraron ${users.length} usuarios.`);
      res.status(200).json(users);

  } catch (error) {
      console.error("❌ Error al obtener los usuarios:", error);
      res.status(500).json({ message: "Error al obtener los usuarios", error: error.message });
  }
};

module.exports = { getUser, updateUserProfile, getUserByEmail, getAllUsers};